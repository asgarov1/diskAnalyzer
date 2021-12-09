package com.asgarov.diskAnalyzer.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class FileVisitor {

    public static SimpleFileVisitor<Path> getVisitor(HashMap<String, Long> sizes) {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                long size = Files.size(file);
                updateDirectorySize(file, size, sizes);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }


            private void updateDirectorySize(Path path, Long size, Map<String, Long> sizes) {
                String key = path.toString();
                sizes.compute(key, (k, v) -> ofNullable(v).orElse(0L) + size);

                ofNullable(path.getParent()).ifPresent(
                        parent -> updateDirectorySize(parent, size, sizes)
                );
            }
        };
    }
}
