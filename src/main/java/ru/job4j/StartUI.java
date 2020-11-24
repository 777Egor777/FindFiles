package ru.job4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

public class StartUI {
    public static void main(String[] args) throws IOException {
        Validation validation = new Validation(args);
        Predicate<Path> predicate;
        if (!validation.isCorrect()) {
            System.err.println(validation.getErrorMsg());
            return;
        }
        if (validation.getType().equals("name")) {
            predicate = p -> p.toFile().getName().endsWith(validation.getName());
        } else {
            Mask mask = new Mask(validation.getMask());
            predicate = p -> mask.accept(p.toFile().getName());
        }
        SearchFiles searcher = new SearchFiles(predicate);
        Files.walkFileTree(Paths.get(validation.getDir()), searcher);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                validation.getOutputFileName()
        ))) {
            for (Path path: searcher.getPaths()) {
                writer.write(path.toString() + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
