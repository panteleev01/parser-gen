package generators;

import grammar.Grammar;
import grammar.GrammarTerminal;
import org.apache.commons.text.StringSubstitutor;

import java.util.List;
import java.util.Map;


public class TokenGenerator {

    public static String generate(
            Grammar grammar,
            String prefix
    ) {
        return new TokenGenerator(grammar, prefix).genEnum();
    }

    private final Grammar grammar;
    private final StringSubstitutor substitutor;

    private TokenGenerator(final Grammar grammar, final String prefix) {
        this.grammar = grammar;

        final Map<String, String> parameters = Map.of(
                "regex", prefix
        );
        this.substitutor = new StringSubstitutor(parameters);
    }

    public String genEnum() {
        final StringBuilder enumBuilder = new StringBuilder();

        final String enumHeader = genHeader();
        enumBuilder.append(enumHeader).append('\n');

        grammar.terminalsList.forEach(t -> {
            final String enumInstance = genInstance(t.name(), t.regex());
            enumBuilder.append(enumInstance).append('\n');
        });

        final String endTerm = genInstance("END", "$");
        enumBuilder.append(endTerm).append(";\n");

        final String constructor = genConstructor();
        enumBuilder.append(constructor);

        enumBuilder.append("}");

        return enumBuilder.toString();
    }

    private String genInstance(final String name, String regex) {
        regex = regex.replace("\\", "\\\\");
        final String enumInstanceTemplate = "${name}(\"${regex}\"),";

        return substitute(
                enumInstanceTemplate,
                Map.entry("name", name),
                Map.entry("regex", regex)
        );
    }

    public static String substitute(
            final String template,
            final Map.Entry<String, String>... variables
    ) {
        final Map<String, String> map = Map.ofEntries(variables);
        return StringSubstitutor.replace(template, map);
    }

    private String genHeader() {
        final String headerTemplate = "public enum ${regex}Token {";
        return substitutor.replace(headerTemplate);
    }

    private String genConstructor() {
        final String constructorTemplate = """
                
                public final String regex;
                
                ${regex}Token (final String regex) {
                    this.regex = regex;
                }
                
                """;

        return substitutor.replace(constructorTemplate);
    }

}
