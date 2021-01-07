package com.hfhk.system.file.domain;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDeleteParam extends AbstractPage<FileDeleteParam> implements Serializable {

	@NotNull
	@NotEmpty
	private Collection<String> ids;
}
