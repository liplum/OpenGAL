package opengal.core;

import opengal.excpetions.CompileNodeException;
import opengal.excpetions.CompileNodeLangException;
import opengal.nl.NodeLang;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class GalCompiler {
    public IAnalyzer analyzer;
    public NodeLang nodeLang = NodeLang.Default;

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

    public void compileNodeLang(@NotNull String source, OutputStream output) {
        NodeTree tree = compile(source);
        try {
            nodeLang.serializeTo(tree, output);
        } catch (Exception e) {
            throw new CompileNodeLangException(source, e);
        }
    }
}
