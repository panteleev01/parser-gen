import antlr4.GrammarLexer;
import antlr4.GrammarParser;
import generators.*;
import grammar.*;
import jdk.jshell.execution.Util;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.CustomErrorListener;
import parser.GrammarVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {

    // throws IO signature if grammar path is absent
    private static Grammar parseGrammar(final Path grammarPath) throws IOException {
        final String str = Files.readString(grammarPath);
        final CharStream input = CharStreams.fromString(str);

        final GrammarLexer lexer = new GrammarLexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final GrammarParser parser = new GrammarParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new CustomErrorListener());

        final GrammarVisitor visitor = new GrammarVisitor();
        return visitor.visit(parser.main());
    }

    private static void createFile(
            final String text,
            final String dirPath,
            final String filePrefix,
            final String name
    ) throws IOException {
        Files.createDirectories(Path.of(dirPath));
        Files.writeString(
                Path.of(dirPath + filePrefix + name + ".java"),
                text
        );
    }

    public static void create(
            final Path grammarPath,
            final String dirPath,
            final String prefix
    ) throws IOException {
        final Grammar g = parseGrammar(grammarPath);

        final CompilationResult result = GrammarCompilation.compile(g);

        final String utilSrc = UtilClassGenerator.generate(prefix);
        createFile(utilSrc, dirPath + "/util/", prefix, "Util");

        final String tokenWrapperSrc = TokenWrapperGenerator.gen(prefix);
        createFile(tokenWrapperSrc, dirPath, prefix, "TokenWrapper");

        final String tokenSrc = TokenGenerator.generate(g, prefix);
        createFile(tokenSrc, dirPath, prefix, "Token");

        final String lexerSrc = LexerGenerator.gen(prefix);
        createFile(lexerSrc, dirPath, prefix, "LexicalAnalyzer");

        String parserSrc = ParserGenerator.gen(g, prefix, result);
        createFile(parserSrc, dirPath, prefix, "Parser");
    }

    public static void createExpression() throws IOException {
        final Path grammarPath = Path.of("src/test/java/expression/exprGrammar.txt");
        final String sourcesPath = "result/expressionx/";
        create(grammarPath, sourcesPath, "Expressionx");
    }

    public static void createLambda() throws IOException {
        final Path grammarPath = Path.of("src/test/java/lambda/lambdaGrammar.txt");
        final String sourcesPath = "result/lambda/";
        create(grammarPath, sourcesPath, "Lambda");
    }

    public static void main(final String[] args) throws IOException {
        createExpression();
//        createLambda();
    }

}
