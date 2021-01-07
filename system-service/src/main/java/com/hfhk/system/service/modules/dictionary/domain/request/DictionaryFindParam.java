package com.hfhk.system.service.modules.dictionary.domain.request;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryFindParam extends AbstractPage<DictionaryFindParam> {
	private Set<String> ids;
	private String keyword;
}
