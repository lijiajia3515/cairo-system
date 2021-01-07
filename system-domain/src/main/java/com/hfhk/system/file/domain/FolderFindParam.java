package com.hfhk.system.file.domain;

import com.hfhk.cairo.core.page.AbstractPage;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderFindParam extends AbstractPage<FolderFindParam> implements Serializable {

	private Set<String> paths;
}
