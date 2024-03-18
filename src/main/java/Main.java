import antlr4.GrammarLexer;
import antlr4.GrammarParser;
import generators.TerminalWrapperGenerator;
import generators.TokenGenerator;
import grammar.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


public class Main {


    public static void create(Path grammarPath, String dirPath, String prefix) throws IOException {
        firstForNon = new HashMap<>();
        followForNon = new HashMap<>();

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

        var regex = getLexerRegex(g);

        calcFirst(g);
        calcFollow(g);
        checkLL1(g);

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

        final String lexerStr = LexerGenerator
                .generate(regex.replace("\\", "\\\\"), prefix);

        Files.writeString(
                Path.of(dirPath + prefix + "LexicalAnalyzer.java"),
                lexerStr
        );

        String parserStr = ParserGen
                .generate(g, prefix);
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

    private static void checkLL1(Grammar g) {
        for (var declListEntry : g.rules.entrySet()) {
            String A = declListEntry.getKey().name;
            for (int i = 0; i < declListEntry.getValue().size(); ++i) {
                for (int j = 0; j < declListEntry.getValue().size(); ++j) {
                    if (i == j) continue;
                    var alpha = declListEntry.getValue().get(i).rightSide;
                    var beta = declListEntry.getValue().get(j).rightSide;

                    var alphaFirst = getSimpleFirst(alpha);
                    var betaFirst = getSimpleFirst(beta);

                    var x = new HashSet<>(alphaFirst);
                    x.retainAll(betaFirst);
                    if (!x.isEmpty()) throw new AssertionError("Not LL1");

                    if (alphaFirst.contains("eps")) {
                        var y = new HashSet<>(betaFirst);
                        y.retainAll(followForNon.get(A));
                        if (!y.isEmpty()) throw new AssertionError("Not LL1");
                    }
                }
            }
        }
    }

    private static Map<String, Set<String>> firstForNon = new HashMap<>();
    public static Map<String, Set<String>> followForNon = new HashMap<>();

    private static void calcFollow(final Grammar grammar) {
        followForNon.put(grammar.mainRule, new HashSet<>(Set.of("$")));
        while (true) {
            boolean changed = false;

            final Map<Decl, List<Alternative>> rules = grammar.rules;
            for (var entry: rules.entrySet()) {
                final String left = entry.getKey().name;
                for (var alt: entry.getValue()) {
                    changed |= updateFollow(left, alt.rightSide);
                }
            }
            if (!changed) break;
        }
    }


    public static boolean updateFollow(final String left, final List<Unit> alpha) {
        boolean updated = false;
        for (int i = 0; i < alpha.size(); ++i) {
            final var cur = alpha.get(i);
            if (!cur.isNonTerminal()) continue;
            final var term = (NonTerminal) cur;
            var oldFollow = followForNon.computeIfAbsent(term.str, k -> new HashSet<>());
            var initSize = oldFollow.size();

            var gamma = alpha.subList(i + 1, alpha.size());

            var gammaFirst = getSimpleFirst(gamma);

            if (gammaFirst.contains("eps")) {
                gammaFirst.remove("eps");
                var aFollow =
                        new HashSet<>(followForNon.getOrDefault(left, new HashSet<>()));
                gammaFirst.addAll(aFollow);

                followForNon.get(term.str).addAll(gammaFirst);
            } else {
                gammaFirst.remove("eps");
                followForNon.get(term.str).addAll(gammaFirst);
            }
            updated |=
                    initSize != followForNon.get(term.str).size();
        }
        return updated;
    }


    public static void calcFirst(final Grammar grammar) {
        while (true) {
            boolean changed = false;

            final Map<Decl, List<Alternative>> rules = grammar.rules;
            for (var entry: rules.entrySet()) {
                final String left = entry.getKey().name;
                for (var alt: entry.getValue()) {
                    changed |= updateFirst(left, alt.rightSide);
                }
            }
            if (!changed) break;
        }
    }

    public static boolean updateFirst(final String A, final List<Unit> alpha) {
        Set<String> simpleFirst = getSimpleFirst(alpha);
        Set<String> fullFirst = firstForNon.get(A);
        if (fullFirst == null) {
            firstForNon.put(A, simpleFirst);
            return true;
        } else {
            int size = fullFirst.size();
            fullFirst.addAll(simpleFirst);
            return fullFirst.size() != size;
        }
    }

    public static Set<String> getSimpleFirst(final List<Unit> alpha) {
        if (alpha.isEmpty()) return new HashSet<>(Set.of("eps"));
        if (alpha.size() == 1 && alpha.get(0).equals(Eps.get())) {
            return new HashSet<>(Set.of("eps"));
        } else if (alpha.get(0) instanceof Terminal t) {
            return new HashSet<>(Set.of(t.str));
        } else {
            final NonTerminal nt = (NonTerminal) alpha.get(0);
            Set<String> forHead = firstForNon.getOrDefault(nt.str, new HashSet<>());
            forHead = new HashSet<>(forHead);
            if (forHead.contains("eps")) {
                forHead.remove("eps");
                Set<String> forTail = getSimpleFirst(alpha.subList(1, alpha.size()));
                forHead.addAll(forTail);
            } else {
                forHead.remove("eps");
            }
            return forHead;
        }
    }



    public static String getLexerRegex(final Grammar grammar) {
        final List<Map.Entry<String, String>> entries = grammar.terminals.entrySet().stream().toList();

        final List<String> regexs = entries
                        .stream()
                        .map(Map.Entry::getValue)
                        .toList();

        return regexs.stream()
                .map(s -> "(" + s + ")")
                .collect(Collectors.joining("|"));
    }

}
