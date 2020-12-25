package com.hfhk.system.service.modules.dictionary;

import cn.hutool.core.util.IdUtil;
import com.hfhk.cairo.mongo.data.Metadata;
import com.hfhk.system.dictionary.domain.Dictionary;
import com.hfhk.system.service.domain.mongo.DictionaryMongo;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionaryItemDeleteRequest;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionaryItemModifyRequest;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionaryItemPutRequest;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionarySaveRequest;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j(topic = "[dictionary][service]")
@Service
public class DictionaryService {
    private final MongoTemplate mongoTemplate;

    public DictionaryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * dictionary save
     *
     * @param request request
     * @return dictionary optional
     */
    public Optional<Dictionary> save(DictionarySaveRequest request) {
        AtomicLong sortValue = new AtomicLong(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)));
        DictionaryMongo dictionaryMongo = DictionaryMongo.builder()
                .code(Optional.ofNullable(request.getCode()).orElse(IdUtil.objectId()))
                .name(request.getName())
                .items(
                        Optional.ofNullable(request.getItems())
                                .stream()
                                .flatMap(Collection::stream)
                                .map(x ->
                                        DictionaryMongo.Item.builder()
                                                .code(Optional.ofNullable(x.getCode()).orElse(IdUtil.objectId()))
                                                .value(x.getValue())
                                                .metadata(new Metadata().setSort(sortValue.incrementAndGet()))
                                                .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
        dictionaryMongo = mongoTemplate.insert(dictionaryMongo);
        return convert(dictionaryMongo);
    }

    /**
     * dictionary modify
     *
     * @param request request
     * @return 1
     */
    public Optional<Dictionary> modify(DictionarySaveRequest request) {
        AtomicLong sortValue = new AtomicLong(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)));

        final List<DictionaryMongo.Item> items = Optional.ofNullable(request.getItems())
                .stream()
                .flatMap(Collection::stream)
                .map(x ->
                        DictionaryMongo.Item.builder()
                                .code(Optional.ofNullable(x.getCode()).orElse(IdUtil.objectId()))
                                .value(x.getValue())
                                .metadata(new Metadata().setSort(sortValue.incrementAndGet()))
                                .build()
                )
                .collect(Collectors.toList());
        Query query = Query.query(Criteria.where("code").is(request.getCode()));
        Update update = Update.update("name", request.getName()).set("items", items);
        final UpdateResult updateResult = mongoTemplate.updateFirst(query, update, DictionaryMongo.class);
        log.debug("[modify] result-> {}", updateResult);
        return convert(mongoTemplate.findOne(query, DictionaryMongo.class));

    }

    /**
     * dictionary delete
     *
     * @param code code
     * @return dictionary optional
     */
    public Optional<Dictionary> delete(String code) {
        Query query = Query.query(Criteria.where("code").is(code));
        return convert(mongoTemplate.findAndRemove(query, DictionaryMongo.class));
    }

    /**
     * dictionary find list
     *
     * @return dictionary list
     */
    public List<Dictionary> find() {
        return mongoTemplate.findAll(DictionaryMongo.class)
                .stream()
                .flatMap(x -> convert(x).stream())
                .collect(Collectors.toList());
    }

    /**
     * put Item
     *
     * @param request request
     * @return dictionary optional
     */
    public Optional<Dictionary> putItems(DictionaryItemPutRequest request) {
        final Criteria criteria = Criteria.where("code").is(request.getCode());
        final List<DictionaryMongo.Item> newItems = Optional.ofNullable(request.getItems())
                .stream()
                .flatMap(Collection::stream)
                .map(x -> DictionaryMongo.Item.builder()
                        .code(x.getCode())
                        .value(x.getValue())
                        .metadata(new Metadata().setSort(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8))))
                        .build())
                .collect(Collectors.toList());

        final Object[] x = newItems.toArray();
        Query query = Query.query(criteria);
        Update update = new Update().addToSet("items").each(newItems);
        final UpdateResult updateResult = mongoTemplate.updateFirst(query, update, DictionaryMongo.class);
        log.debug("[dictionary][putItem] result-> : {}", updateResult);
        return convert(mongoTemplate.findOne(query, DictionaryMongo.class));
    }

    /**
     * dictionary item modify
     *
     * @param request request
     * @return dictionary optional
     */
    public Optional<Dictionary> modifyItems(DictionaryItemModifyRequest request) {
        final Criteria criteria = Criteria.where("code").is(request.getCode()).and("items.code").is(request.getItem());
        Query query = Query.query(criteria);
        Update update = Update.update("items.value", request.getItem().getValue());
        final UpdateResult updateResult = mongoTemplate.updateFirst(query, update, DictionaryMongo.class);
        log.debug("[dictionary][putItem] result-> : {}", updateResult);
        return convert(mongoTemplate.findOne(Query.query(Criteria.where("code").is(request.getCode())), DictionaryMongo.class));
    }

    /**
     * dictionary item delete
     *
     * @param request request
     * @return dictionary optional
     */
    public Optional<Dictionary> deleteItems(DictionaryItemDeleteRequest request) {
        final Criteria criteria = Criteria.where("code").is(request.getCode());
        final Query query = Query.query(criteria);
        Update update = new Update();
        update.pullAll("items.code", request.getItemCodes().toArray(new String[0]));
        final UpdateResult updateResult = mongoTemplate.updateFirst(query, update, DictionaryMongo.class);
        log.debug("[dictionary][deleteItem] result-> : {}", updateResult);
        return convert(mongoTemplate.findOne(query, DictionaryMongo.class));
    }

    /**
     * mongo to model
     *
     * @param mongo mongo
     * @return dictionary optional
     */
    private Optional<Dictionary> convert(DictionaryMongo mongo) {
        return Optional.ofNullable(mongo)
                .map(x ->
                        Dictionary.builder()
                                .code(x.getCode())
                                .name(x.getName())
                                .items(
                                        Optional.ofNullable(x.getItems())
                                                .stream().flatMap(Collection::stream)
                                                .map(y -> Dictionary.Item.builder().code(y.getCode()).value(y.getValue()).build())
                                                .collect(Collectors.toList())
                                )
                                .build()
                );
    }
}
