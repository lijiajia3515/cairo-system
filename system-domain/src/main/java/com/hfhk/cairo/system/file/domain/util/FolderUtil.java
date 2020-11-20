package com.hfhk.cairo.system.file.domain.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class FolderUtil {
	public static final String SPLIT = "/";

	public static Collection<String> allPaths(String path) {
		AtomicReference<String> parentId = new AtomicReference<>(SPLIT);
		return Optional.ofNullable(path).map(x -> x.split(SPLIT))
			.stream()
			.flatMap(x -> Arrays.stream(x.clone()))
			.filter(x -> !x.trim().isBlank())
			.map(x -> parentId.get().equals(SPLIT) ? SPLIT.concat(x) : parentId.get().concat(SPLIT).concat(x))
			.peek(parentId::set)
			.collect(Collectors.toSet());
	}

	public static String parentPath(String path) {
		return Optional.ofNullable(path)
			.filter(x -> x.contains(SPLIT))
			.map(x -> x.substring(0, x.lastIndexOf(SPLIT)))
			.orElse(SPLIT);
	}
}
