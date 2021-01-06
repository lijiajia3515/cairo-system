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
public class FolderFindParam extends AbstractPage<FolderFindParam> implements Serializable {

	private String path;
}
