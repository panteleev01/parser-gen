import antlr4.GrammarLexer;
import antlr4.GrammarParser;
import generators.*;
import grammar.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {

    public static void create(Path grammarPath, String dirPath, String prefix) throws IOException {
        final String str = Files.readString(grammarPath);
        final CharStream input = CharStreams.fromString(str);

        final GrammarLexer lexer = new GrammarLexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final GrammarParser parser = new GrammarParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new CustomErrorListener());
        final ParseTree main = parser.main();
        final GrammarVisitor visitor = new GrammarVisitor();

        final Grammar g = visitor.visit(main);

        final CompilationResult result = GrammarCompilation.compile(g);

        final String wrapper = TerminalWrapperGenerator.gen(prefix);
        Files.writeString(
                Path.of(dirPath + prefix + "TokenWrapper.java"),
                wrapper
        );

        final String tokenStr = TokenGenerator
                .generate(g, prefix);
        Files.writeString(
                Path.of(dirPath + prefix + "Token.java"),
                tokenStr
        );

        final String lexerStr = LexerGenerator.gen(prefix);


        Files.writeString(
                Path.of(dirPath + prefix + "LexicalAnalyzer.java"),
                lexerStr
        );

        String parserStr = ParserGenerator
                .generate(g, prefix, result);

        Files.writeString(
                Path.of(dirPath + prefix + "Parser.java"),
                parserStr
        );
    }

    public static void createExpression() throws IOException {
        final Path grammarPath = Path.of("src/test/java/expression/exprGrammar.txt");
        final String sourcesPath = "result/expression/";
        create(grammarPath, sourcesPath, "Expression");
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
