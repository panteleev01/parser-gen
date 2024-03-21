package generators;

import grammar.*;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static generators.UtilClassGenerator.substitute;

public class ParserGenerator {

    private final StringSubstitutor substitutor;
    private final CompilationResult result;
    private final Grammar g;
    private final String prefix;

    public static String gen(
            final Grammar grammar,
            final String prefix,
            final CompilationResult cr,
            final StringSubstitutor substitutor
    ) {
        return new ParserGenerator(prefix, cr, grammar, substitutor).g();
    }

    public ParserGenerator(
            final String prefix,
            final CompilationResult res,
            final Grammar g,
            final StringSubstitutor substitutor
    ) {
        this.substitutor = substitutor;
        this.result = res;
        this.g = g;
        this.prefix = prefix;
    }

    private String g() {
        final StringBuilder sb = new StringBuilder();
        sb.append(genHeader());

        for (var rules : g.getRules()) {
            sb.append(genRule(rules.decl(), rules.alternatives()));
        }

        sb.append(substitutor.replace(UTIL_FUNCTIONS_TEMPLATE));

        sb.append(genParseFunction());

        sb.append("\n}");

        return sb.toString();
    }

    private static final String PARSE_FUNCTION_TEMPLATE = """
                public ${returnType} parse(${typedArgs}) throws ParseException {
                    var res = ${mainRule}(${args});
                    if (analyzer.curToken.token != ${prefix}Token.END)
                        throw new ParseException("End of input expected", analyzer.curPos());
                    return res;
                }
            """;

    private String genParseFunction() {
        final String mainRuleName = g.getMainRule();

        final Decl mainRuleDecl = g.getRules().stream()
                .filter(rule -> rule.decl().getName().equals(mainRuleName))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("no rule matching main rule: " + mainRuleName))
                .decl();

        final String returnType = mainRuleDecl.getType();
        final String typedArgs = mainRuleDecl.getTypedVariables();
        final String args = mainRuleDecl.getVariablesList();

        return substitute(
                PARSE_FUNCTION_TEMPLATE,
                Map.entry("returnType", returnType),
                Map.entry("typedArgs", typedArgs),
                Map.entry("mainRule", mainRuleName),
                Map.entry("args", args),
                Map.entry("prefix", prefix)
        );
    }

    private static final String HEADER_TEMPLATE = """
            ${package}
                        
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
                
               private void check(boolean b) throws ParseException {
                   if (!b) {
                       throw new ParseException("error", analyzer.curPos());
                   }
               }
            """;

    private String genHeader() {
        return substitutor.replace(HEADER_TEMPLATE);
    }

    private String argsList(final Decl decl) {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < decl.getVariables().size(); ++i) {
            final Variable v = decl.getVariables().get(i);
            builder
                    .append(v.type())
                    .append(" ")
                    .append(v.name());
            if (i != decl.getVariables().size() - 1) builder.append(", ");
        }

        return builder.toString();
    }

    private String genRule(final Decl d, final List<Alternative> alts) {
        final StringBuilder builder = new StringBuilder();

        final String funcDeclTemplate = """
                                
                public ${returnType} ${funcName}(${funcArgs}) throws ParseException {
                        switch (curToken().token) {
                                
                """;

        final String funcDecl = substitute(
                funcDeclTemplate,
                Map.entry("returnType", d.getType()),
                Map.entry("funcName", d.getName()),
                Map.entry("funcArgs", argsList(d))
        );

        builder.append(funcDecl);

        int ix = 0;

        for (var alt : alts) {

            final List<String> first = calcFirst(d.getName(), alt.getRightSide(), this.result).stream().map(x -> {
                if (x.equals("$")) return "END";
                return x;
            }).toList();

            // leading cases
            for (int i = 0; i < first.size() - 1; ++i) {
                builder.append("case ").append(first.get(i)).append(":\n");
            }

            // final case
            builder.append("case ").append(first.get(first.size() - 1)).append(":\n");

            for (int i = 0; i < alt.getRightSide().size(); ++i) {
                final Unit unit = alt.getRightSide().get(i);
                final List<String> args = alt.getArgs().get(i);
                if (unit.isTerminal()) {

                    final String checkTemplate = """
                            check(curToken().token == ${prefix}Token.${unit});
                            var ${unit}${index} = curToken().value;
                            nextToken();
                            """;
                    builder.append(
                            substitute(
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

            builder.append(alt.getCodeBlock());
        }


        builder.append("}");
        builder.append("throw new ParseException(\"unknown token\", analyzer.curPos());");
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


}
