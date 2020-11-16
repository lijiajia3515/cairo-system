package com.hfhk.cairo.system.service.modules.dictionary;

import com.hfhk.cairo.security.oauth2.server.resource.authentication.CairoAuthenticationToken;
import com.hfhk.cairo.starter.web.handler.StatusResult;
import com.hfhk.cairo.system.dictionary.domain.Dictionary;
import com.hfhk.cairo.system.service.modules.dictionary.domain.request.DictionaryItemDeleteRequest;
import com.hfhk.cairo.system.service.modules.dictionary.domain.request.DictionaryItemPutRequest;
import com.hfhk.cairo.system.service.modules.dictionary.domain.request.DictionarySaveRequest;
import com.hfhk.cairo.system.service.modules.dictionary.domain.request.DictionaryItemModifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * dictionary api
 */
@Slf4j(topic = "[dictionary][api]")
@RestController
@RequestMapping("/dictionary")
public class DictionaryApi {
	private final DictionaryService dictionaryService;

	public DictionaryApi(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	/**
	 * dictionary save
	 *
	 * @param token   token
	 * @param request request
	 * @return dictionary optional
	 */
	@PostMapping("/save")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public Optional<Dictionary> save(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@RequestBody DictionarySaveRequest request) {
		return dictionaryService.save(request);
	}

	/**
	 * dictionary modify
	 *
	 * @param token   token
	 * @param request request
	 * @return dictionary optional
	 */
	@PutMapping("/modify")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public Optional<Dictionary> modify(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@RequestBody DictionarySaveRequest request) {
		return dictionaryService.modify(request);
	}

	/**
	 * dictionary delete
	 *
	 * @param token token
	 * @param code  code
	 * @return dictionary optional
	 */
	@DeleteMapping("/delete")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public Optional<Dictionary> delete(
		@AuthenticationPrincipal CairoAuthenticationToken token,
		@RequestBody String code
	) {
		return dictionaryService.delete(code);
	}

	/**
	 * dictionary item put
	 *
	 * @param token   token
	 * @param request request
	 * @return dictionary optional
	 */
	@PutMapping("/item/put")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public Optional<Dictionary> putItems(@AuthenticationPrincipal CairoAuthenticationToken token,
										 @RequestBody DictionaryItemPutRequest request) {
		return dictionaryService.putItems(request);
	}

	/**
	 * dictionary item modify
	 *
	 * @param token   token
	 * @param request request
	 * @return dictionary optional
	 */
	@PutMapping("/item/modify")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public Optional<Dictionary> modifyItems(@AuthenticationPrincipal CairoAuthenticationToken token,
											@RequestBody DictionaryItemModifyRequest request) {
		return dictionaryService.modifyItems(request);
	}

	/**
	 * dictionary item delete
	 *
	 * @param token   token
	 * @param request request
	 * @return dictionary optional
	 */
	@DeleteMapping("/item/delete")
	@PreAuthorize("isAuthenticated()")
	@StatusResult
	public Optional<Dictionary> deleteItems(@AuthenticationPrincipal CairoAuthenticationToken token,
											@RequestBody DictionaryItemDeleteRequest request) {
		return dictionaryService.deleteItems(request);
	}
}
