package com.hfhk.system.service.modules.file;

import com.hfhk.cairo.core.Constants;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderUtil {
	public static final String DELIMITER = FileConstant.DELIMITER;

	public static Collection<String> paths(String path) {
		AtomicReference<String> parent = new AtomicReference<>(DELIMITER);
		return Optional.ofNullable(path).map(x -> x.split(DELIMITER))
			.stream()
			.flatMap(Arrays::stream)
			.filter(x -> !x.trim().isBlank())
			.map(x -> parent.get().equals(DELIMITER) ? DELIMITER.concat(x) : parent.get().concat(DELIMITER).concat(x))
			.peek(parent::set)
			.collect(Collectors.toSet());
	}

	public static String parentPath(String path) {
		return Optional.ofNullable(path)
			.filter(x -> !x.equals(DELIMITER) && x.startsWith(DELIMITER))
			.map(filterPath -> {
				String parentPath = filterPath.substring(0, filterPath.lastIndexOf(DELIMITER));
				return parentPath.isEmpty() ? DELIMITER : parentPath;
			})
			.orElse(Constants.SNOWFLAKE.nextIdStr());
	}
}
