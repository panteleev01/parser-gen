import antlr4.GrammarLexer;
import antlr4.GrammarParser;
import generators.*;
import grammar.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

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
                Path.of(dirPath + filePrefix + name),
                text
        );
    }

    public static void create(Path grammarPath, String dirPath, String prefix) throws IOException {
        final Grammar g = parseGrammar(grammarPath);

        final CompilationResult result = GrammarCompilation.compile(g);

        final String tokenWrapperSrc = TokenWrapperGenerator.gen(prefix);
        createFile(tokenWrapperSrc, dirPath, prefix, "TokenWrapper.java");

        final String tokenSrc = TokenGenerator.generate(g, prefix);
        createFile(tokenSrc, dirPath, prefix, "Token.java");

        final String lexerSrc = LexerGenerator.gen(prefix);
        createFile(lexerSrc, dirPath, prefix, "LexicalAnalyzer.java");

        String parserSrc = ParserGenerator.gen(g, prefix, result);
        createFile(parserSrc, dirPath, prefix, "Parser.java");
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
