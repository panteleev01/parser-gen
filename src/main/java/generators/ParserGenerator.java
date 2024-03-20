package generators;

import grammar.Alternative;
import grammar.Decl;
import grammar.Grammar;
import grammar.Unit;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParserGenerator {

    private final StringSubstitutor substitutor;
    private final CompilationResult result;
    private final Grammar g;
    private final String prefix;

    public static String gen(final Grammar grammar, final String prefix, final CompilationResult cr) {
        return new ParserGenerator(prefix, cr, grammar).g();
    }

    public ParserGenerator(final String prefix, final CompilationResult res, final Grammar g) {
        final Map<String, String> map = Map.of(
                "prefix", prefix
        );

        this.substitutor = new StringSubstitutor(map);
        this.result = res;
        this.g = g;
        this.prefix = prefix;
    }

    private String g() {
        final StringBuilder sb = new StringBuilder();
        sb.append(genHeader());

        for (var rules : g.rules.entrySet()) {
            sb.append(genRule(rules.getKey(), rules.getValue()));
        }

        sb.append(substitutor.replace(UTIL_FUNCTIONS_TEMPLATE));

        sb.append("\n}");

        return sb.toString();
    }

    private static final String HEADER_TEMPLATE = """
                        
            import static util.${prefix}Util.*;
                        
            import java.text.ParseException;
            import java.util.EnumSet;
                        
            public class ${prefix}Parser {
                private final ${prefix}LexicalAnalyzer analyzer;
                        
                public ${prefix}Parser(${prefix}LexicalAnalyzer analyzer) throws ParseException {
                    this.analyzer = analyzer;
                    analyzer.nextToken();
                }
                        
            """;

    private static final String UTIL_FUNCTIONS_TEMPLATE = """
               private ${prefix}TokenWrapper curToken() {
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
            """;

    private String genHeader() {
        return substitutor.replace(HEADER_TEMPLATE);
    }

    private String argsList(final Decl decl) {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < decl.argsNames.size(); ++i) {
            builder
                    .append(decl.argsTypes.get(i))
                    .append(" ")
                    .append(decl.argsNames.get(i));

            if (i != decl.argsNames.size() - 1) builder.append(", ");
        }

        return builder.toString();
    }

    private String genRule(final Decl d, final List<Alternative> alts) {
        final StringBuilder builder = new StringBuilder();

        final String funcDeclTemplate = """
                                
                public ${returnType} ${funcName}(${funcArgs}) throws ParseException {
                        switch (curToken().token) {
                                
                """;

        final String funcDecl = TokenGenerator.substitute(
                funcDeclTemplate,
                Map.entry("returnType", d.type),
                Map.entry("funcName", d.name),
                Map.entry("funcArgs", argsList(d))
        );

        builder.append(funcDecl);

        int ix = 0;

        for (var alt : alts) {

            final List<String> first = calcFirst(d.name, alt.rightSide, this.result).stream().map(x -> {
                if (x.equals("$")) return "END";
                return x;
            }).toList();

            // leading cases
            for (int i = 0; i < first.size() - 1; ++i) {
                builder.append("case ").append(first.get(i)).append(":\n");
            }

            // final case
            builder.append("case ").append(first.get(first.size() - 1)).append(":\n");

            for (int i = 0; i < alt.rightSide.size(); ++i) {
                final Unit unit = alt.rightSide.get(i);
                final List<String> args = alt.args.get(i);
                if (unit.isTerminal()) {

                    final String checkTemplate = """
                            check(curToken().token == ${prefix}Token.${unit});
                            var ${unit}${index} = curToken().value;
                            nextToken();
                            """;
                    builder.append(
                            TokenGenerator.substitute(
                                    checkTemplate,
                                    Map.entry("prefix", prefix),
                                    Map.entry("unit", unit.toString()),
                                    Map.entry("index", String.valueOf(ix))
                            )
                    );

                } else if (unit.isNonTerminal()) {
                    builder.append("var ").append(unit).append(ix).append(" = ");
                    builder.append(unit).append("(");
                    for (int j = 0; j < args.size(); ++j) {
                        builder.append(args.get(j));
                        if (j != args.size() - 1) builder.append(", ");
                    }
                    builder.append(");");
                }
                ix += 1;
            }

            builder.append(alt.codeBlock);
        }


        builder.append("}");
        builder.append("throw new RuntimeException(\"unknown token\");");
        builder.append("}");

        return builder.toString();
    }


    public static Set<String> calcFirst(final String A, final List<Unit> right, final CompilationResult res) {
        var first = new HashSet<>(res.simple(right));
        if (first.contains("eps")) {
            first.remove("eps");

            var follow = new HashSet<>(res.follow(A));
            first.addAll(follow);
            return first;
        } else {
            first.remove("eps");
            return first;
        }
    }

//    private static StringBuilder str = new StringBuilder();
//    private static String prefix;
//    private static CompilationResult compilationResult;

//    public static String generate(Grammar g, String prefix, CompilationResult result) {
//        str = new StringBuilder();
//        ParserGenerator.prefix = prefix;
//        compilationResult = result;
//        addHeader();
//        addBody(g);
//        addCurToken();
//        addBottom();
//        return str.toString();
//    }
//
//    public static void addBody(Grammar g) {
//        for (var rules : g.rules.entrySet()) {
//            addRule(rules.getKey(), rules.getValue());
//        }
//    }
//
//    public static void addRule(Decl d, List<Alternative> alts) {
//        str.append("public ").append(d.type).append(" ").append(d.name);
//        str.append("(");
//        for (int i = 0; i < d.argsNames.size(); ++i) {
//            str.append(d.argsTypes.get(i)).append(" ").append(d.argsNames.get(i));
//            if (i != d.argsNames.size() - 1) str.append(", ");
//        }
//        str.append(") throws ParseException {\n");
//        str.append("switch(curToken().token) {");
//
//        int ix = 0;
//
//        for (var alt : alts) {
//            var first = calcFirst(d.name, alt.rightSide, compilationResult).stream().toList();
//
//            first = first.stream().map(x -> {
//                if (x.equals("$")) return "END";
//                return x;
//            }).toList();
//
//            for (int i = 0; i < first.size() - 1; ++i) {
//                str.append("case ").append(first.get(i)).append(":\n");
//            }
//            str.append("case ").append(first.get(first.size() - 1)).append(":\n");
//
//
//            for (int i = 0; i < alt.rightSide.size(); ++i) {
//                var unit = alt.rightSide.get(i);
//                var args = alt.args.get(i);
//                if (unit.isTerminal()) {
//                    str.append("check(").append("curToken().token == ");
//                    str.append(prefix + "Token.").append(unit).append(");\n");
//                    str.append("var ").append(unit).append(ix).append(" = curToken().value;");
//                    str.append("nextToken();");
//                } else if (unit.isNonTerminal()) {
//                    str.append("var ").append(unit).append(ix).append(" = ");
//                    str.append(unit).append("(");
//                    for (int j = 0; j < args.size(); ++j) {
//                        str.append(args.get(j));
//                        if (j != args.size() - 1) str.append(", ");
//                    }
//                    str.append(");");
//                }
//                ix += 1;
//            }
//
//            str.append(alt.codeBlock);
//        }
//
//
//        str.append("}");
//
//        str.append("throw new RuntimeException(\"unknown token\");");
//        str.append("}");
//    }

//    public static void addCurToken() {
//        str.append("private " + prefix + "TokenWrapper ");
//        str.append("""
//                curToken() {
//                    return analyzer.curToken;
//                }
//
//                private void nextToken() throws ParseException {
//                    analyzer.nextToken();
//                }
//
//                private void check(boolean b) {
//                        if (!b) {
//                            throw new RuntimeException("error");
//                        }
//                }
//                """);
//    }
//
//    public static void addBottom() {
//        str.append("}");
//    }
//
//    public static void addHeader() {
//        var name = "import static util." + prefix + "Util.*;";
//        str.append(name).append('\n');
//        str.append("""
//                import java.text.ParseException;
//                import java.util.EnumSet;
//
//                public class
//                """);
//        str.append(" ").append(prefix).append("Parser ");
//        str.append("""
//                {
//                    private final
//                """);
//        str.append(" ").append(prefix).append("LexicalAnalyzer analyzer;\n");
//        str.append("public ").append(prefix).append("Parser(").append(prefix).append("LexicalAnalyzer analyzer");
//        str.append("""
//                ) throws ParseException {
//                        this.analyzer = analyzer;
//                        analyzer.nextToken();
//                    }
//                """);
//    }

}
