package opengal.core;

import opengal.syntax.IAnalyzer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Compiler {
    public IAnalyzer analyzer;
    public StoryTree tree;

    @NotNull
    public StoryTree compile(@NotNull File file) {
        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't load file" + file.getAbsolutePath());
        }
        return analyzer.analyze(reader);
    }

    @NotNull
    public StoryTree compile(@NotNull String source) {
        return analyzer.analyze(source);
    }
}
