package com.hfhk.system.service.modules.file;

import com.hfhk.cairo.core.Constants;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class FolderUtil {
	public static final String SPLIT = "/";

	public static Collection<String> paths(String path) {
		AtomicReference<String> parent = new AtomicReference<>(SPLIT);
		return Optional.ofNullable(path).map(x -> x.split(SPLIT))
			.stream()
			.flatMap(x -> Arrays.stream(x.clone()))
			.filter(x -> !x.trim().isBlank())
			.map(x -> parent.get().equals(SPLIT) ? SPLIT.concat(x) : parent.get().concat(SPLIT).concat(x))
			.peek(parent::set)
			.collect(Collectors.toSet());
	}

	public static String parentPath(String path) {
		return Optional.ofNullable(path)
			.filter(x -> !x.equals(SPLIT) && x.lastIndexOf(SPLIT) > 0)
			.map(x -> x.substring(0, x.lastIndexOf(SPLIT)))
			.orElse(Constants.SNOWFLAKE.nextIdStr());
	}
}
