package com.hfhk.system.service.modules.dictionary;

import com.hfhk.cairo.security.oauth2.user.AuthPrincipal;
import com.hfhk.cairo.starter.service.web.handler.BusinessResult;
import com.hfhk.system.dictionary.domain.Dictionary;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionaryItemDeleteRequest;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionaryItemModifyRequest;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionaryItemPutRequest;
import com.hfhk.system.service.modules.dictionary.domain.request.DictionarySaveRequest;
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
@RequestMapping("/Dictionary")
public class DictionaryApi {
	private final DictionaryService dictionaryService;

	public DictionaryApi(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	/**
	 * dictionary save
	 *
	 * @param principal principal
	 * @param request   request
	 * @return dictionary optional
	 */
	@PostMapping("/Save")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public Optional<Dictionary> save(
		@AuthenticationPrincipal AuthPrincipal principal,
		@RequestBody DictionarySaveRequest request) {
		return dictionaryService.save(request);
	}

	/**
	 * dictionary modify
	 *
	 * @param principal principal
	 * @param request   request
	 * @return dictionary optional
	 */
	@PutMapping("/Modify")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public Optional<Dictionary> modify(
		@AuthenticationPrincipal AuthPrincipal principal,
		@RequestBody DictionarySaveRequest request) {
		return dictionaryService.modify(request);
	}

	/**
	 * dictionary delete
	 *
	 * @param principal principal
	 * @param code      code
	 * @return dictionary optional
	 */
	@DeleteMapping("/Delete")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public Optional<Dictionary> delete(
		@AuthenticationPrincipal AuthPrincipal principal,
		@RequestBody String code
	) {
		return dictionaryService.delete(code);
	}

	/**
	 * dictionary item put
	 *
	 * @param principal principal
	 * @param request   request
	 * @return dictionary optional
	 */
	@PutMapping("/Item/Put")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public Optional<Dictionary> putItems(@AuthenticationPrincipal AuthPrincipal principal,
										 @RequestBody DictionaryItemPutRequest request) {
		return dictionaryService.putItems(request);
	}

	/**
	 * dictionary item modify
	 *
	 * @param principal principal
	 * @param request   request
	 * @return dictionary optional
	 */
	@PutMapping("/Item/Modify")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public Optional<Dictionary> modifyItems(@AuthenticationPrincipal AuthPrincipal principal,
											@RequestBody DictionaryItemModifyRequest request) {
		return dictionaryService.modifyItems(request);
	}

	/**
	 * dictionary item delete
	 *
	 * @param principal principal
	 * @param request   request
	 * @return dictionary optional
	 */
	@DeleteMapping("/Item/Delete")
	@PreAuthorize("isAuthenticated()")
	@BusinessResult
	public Optional<Dictionary> deleteItems(@AuthenticationPrincipal AuthPrincipal principal,
											@RequestBody DictionaryItemDeleteRequest request) {
		return dictionaryService.deleteItems(request);
	}
}
