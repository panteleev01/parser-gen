import grammar.Alternative;
import grammar.Decl;
import grammar.Grammar;
import grammar.Unit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParserGen {

    private static StringBuilder str = new StringBuilder();
    private static String prefix;

    public static String generate(Grammar g, String prefix) {
        str = new StringBuilder();
        ParserGen.prefix = prefix;
        addHeader();
        addBody(g);
        addCurToken();
        addBottom();
        return str.toString();
    }

    public static void addBody(Grammar g) {
        for (var rules: g.rules.entrySet()) {
            addRule(rules.getKey(), rules.getValue());
        }
    }

    public static void addRule(Decl d, List<Alternative> alts) {
        str.append("public ").append(d.type).append(" ").append(d.name);
        str.append("(");
        for (int i = 0; i < d.argsNames.size(); ++i) {
            str.append(d.argsTypes.get(i)).append(" ").append(d.argsNames.get(i));
            if (i != d.argsNames.size() - 1) str.append(", ");
        }
        str.append(") throws ParseException {\n");
        str.append("switch(curToken().token) {");

        int ix = 0;

        for (var alt: alts) {
            var first = calcFirst(d.name, alt.rightSide).stream().toList();

            first = first.stream().map(x -> {
                if (x.equals("$")) return "END";
                return x;
            }).toList();

            for (int i = 0; i < first.size() - 1; ++i) {
                str.append("case ").append(first.get(i)).append(":\n");
            }
            System.out.println(d.name);
            str.append("case ").append(first.get(first.size() - 1)).append(":\n");


            for (int i = 0; i < alt.rightSide.size(); ++i) {
                var unit = alt.rightSide.get(i);
                var args = alt.args.get(i);
                if (unit.isTerminal()) {
                    str.append("check(").append("curToken().token == ");
                    str.append(prefix + "Token.").append(unit).append(");\n");
                    str.append("var ").append(unit).append(ix).append(" = curToken().value;");
                    str.append("nextToken();");
                } else if (unit.isNonTerminal()) {
                    str.append("var ").append(unit).append(ix).append(" = ");
                    str.append(unit).append("(");
                    for (int j = 0; j < args.size(); ++j) {
                        str.append(args.get(j));
                        if (j != args.size() - 1) str.append(", ");
                    }
                    str.append(");");
                }
                ix += 1;
            }

            str.append(alt.codeBlock);
        }


        str.append("}");

        str.append("throw new RuntimeException(\"unknown token\");");
        str.append("}");
    }

    public static Set<String> calcFirst(final String A, final List<Unit> right) {
        var first = new HashSet<>(Main.getSimpleFirst(right));
        if (first.contains("eps")) {
            first.remove("eps");

            var follow = new HashSet<>(Main.followForNon.get(A));
            first.addAll(follow);
            return first;
        } else {
            first.remove("eps");
            return first;
        }
    }

    public static void addCurToken() {
        str.append("private " + prefix + "TokenWrapper ");
        str.append("""
                curToken() {
                    return analyzer.curToken;
                }
                                
                private void nextToken() throws ParseException {
                    analyzer.nextToken();
                }
                
                private void check(boolean b) {
                        if (!b) {
                            throw new RuntimeException("error");
                        }
                }
                """);
    }

    public static void addBottom() {
        str.append("}");
    }

    public static void addHeader() {
        var name = "import static util." + prefix + "Util.*;";
        str.append(name).append('\n');
        str.append("""
                import java.text.ParseException;
                import java.util.EnumSet;
                                
                public class
                """);
        str.append(" ").append(prefix).append("Parser ");
        str.append("""
                {
                    private final
                """);
        str.append(" ").append(prefix).append("LexicalAnalyzer analyzer;\n");
        str.append("public ").append(prefix).append("Parser(").append(prefix).append("LexicalAnalyzer analyzer");
        str.append("""
                ) throws ParseException {
                        this.analyzer = analyzer;
                        analyzer.nextToken();
                    }
                """);
    }

}
