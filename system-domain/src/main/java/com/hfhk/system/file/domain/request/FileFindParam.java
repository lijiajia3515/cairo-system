package com.hfhk.system.file.domain.request;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileFindParam extends AbstractPage<FileFindParam> implements Serializable {

	private String filepath;
	private String filename;
}
