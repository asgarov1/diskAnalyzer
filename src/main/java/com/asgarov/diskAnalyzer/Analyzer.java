package com.asgarov.diskAnalyzer;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static com.asgarov.diskAnalyzer.visitor.FileVisitor.getVisitor;
import static java.util.Optional.ofNullable;

public class Analyzer {

    @SneakyThrows
    public Map<String, Long> calculateDirectorySize(Path path) {
        HashMap<String, Long> sizes = new HashMap<>();
        Files.walkFileTree(path, getVisitor(sizes));
        return sizes;
    }
}
