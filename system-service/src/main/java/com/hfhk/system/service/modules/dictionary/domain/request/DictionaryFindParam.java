package com.hfhk.system.service.modules.dictionary.domain.request;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryFindParam extends AbstractPage<DictionaryFindParam> {
	private String code;
	private String name;
}
