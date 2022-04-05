package opengal.core;

import opengal.excpetions.CompileNodeException;
import opengal.excpetions.CompileNodeLangException;
import opengal.nl.NodeLang;
import opengal.syntax.IAnalyzer;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Compiler {
    public IAnalyzer analyzer;
    public NodeLang nodeLang;

    @NotNull
    public NodeTree compile(@NotNull File file) {
        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't load file" + file.getAbsolutePath());
        }
        return analyzer.analyze(reader);
    }

    @NotNull
    public NodeTree compile(@NotNull String source) {
        try {
            return analyzer.analyze(source);
        } catch (Exception e) {
            throw new CompileNodeException(source, e);
        }
    }

    public byte[] compileNodeLang(@NotNull String source) {
        NodeTree tree = compile(source);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        try {
            nodeLang.serializeTo(tree, byteOutput);
            byteOutput.flush();
            byteOutput.close();
        } catch (Exception e) {
            throw new CompileNodeLangException(source, e);
        }
        return byteOutput.toByteArray();
    }
}
