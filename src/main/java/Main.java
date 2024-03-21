import antlr4.GrammarLexer;
import antlr4.GrammarParser;
import generators.*;
import grammar.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.text.StringSubstitutor;
import parser.CustomErrorListener;
import parser.GrammarVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static generators.UtilClassGenerator.toPackage;


public class Main {

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
            final String className
    ) throws IOException {
        Files.createDirectories(Path.of(dirPath));

        final String fileName = filePrefix + className + ".java";

        Files.writeString(
                Path.of(dirPath + fileName),
                text
        );
    }

    public static void create(
            final Path grammarPath,
            final String dirPath,
            final String packageStr,
            final String prefix
    ) throws IOException {
        final Grammar g = parseGrammar(grammarPath);

        final CompilationResult result = GrammarCompilation.compile(g);

        final Map<String, String> parameters = Map.of(
                "prefix", prefix,
                "package", toPackage(packageStr)
        );
        final StringSubstitutor defaultSubstitutor = new StringSubstitutor(parameters);

        final String utilSrc = UtilClassGenerator.generate(prefix, packageStr);
        createFile(utilSrc,dirPath + "/util/", prefix, "Util");

        final String tokenWrapperSrc = TokenWrapperGenerator.gen(defaultSubstitutor);
        createFile(tokenWrapperSrc, dirPath, prefix, "TokenWrapper");

        final String tokenSrc = TokenGenerator.generate(g, defaultSubstitutor);
        createFile(tokenSrc, dirPath, prefix, "Token");

        final String lexerSrc = LexerGenerator.gen(defaultSubstitutor);
        createFile(lexerSrc, dirPath, prefix, "LexicalAnalyzer");

        String parserSrc = ParserGenerator.gen(g, prefix, result, defaultSubstitutor);
        createFile(parserSrc, dirPath, prefix, "Parser");
    }

    public static void createExpression() throws IOException {
        final Path grammarPath = Path.of("src/test/java/expression/exprGrammar.txt");
        final String sourcesPath = "result/expression/";
        create(grammarPath, sourcesPath, "", "Expression");
    }

    public static void createLambda() throws IOException {
        final Path grammarPath = Path.of("src/test/java/lambda/lambdaGrammar.txt");
        final String sourcesPath = "result/lambda/";
        create(grammarPath, sourcesPath, "", "Lambda");
    }

    public static void main(final String[] args) throws IOException {
        createExpression();
        createLambda();
    }

}
