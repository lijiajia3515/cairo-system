package com.hfhk.system.service.modules.file;

import com.hfhk.cairo.core.Constants;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class FolderUtil {
	public static final String DELIMITER = FileConstant.DELIMITER;

	public static Collection<String> paths(String path) {
		AtomicReference<String> parent = new AtomicReference<>(DELIMITER);
		return Optional.ofNullable(path).map(x -> x.split(DELIMITER))
			.stream()
			.flatMap(x -> Arrays.stream(x.clone()))
			.filter(x -> !x.trim().isBlank())
			.map(x -> parent.get().equals(DELIMITER) ? DELIMITER.concat(x) : parent.get().concat(DELIMITER).concat(x))
			.peek(parent::set)
			.collect(Collectors.toSet());
	}

	public static String parentPath(String path) {
		return Optional.ofNullable(path)
			.filter(x -> !x.equals(DELIMITER) && x.lastIndexOf(DELIMITER) > 0)
			.map(x -> x.substring(0, x.lastIndexOf(DELIMITER)))
			.orElse(Constants.SNOWFLAKE.nextIdStr());
	}
}
