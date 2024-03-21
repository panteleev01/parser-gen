package generators;

import grammar.Grammar;
import grammar.GrammarTerminal;
import org.apache.commons.text.StringSubstitutor;

import java.util.List;
import java.util.Map;

import static generators.UtilClassGenerator.substitute;
import static generators.UtilClassGenerator.toPackage;


public class TokenGenerator {

    public static String generate(
            final Grammar grammar,
            final StringSubstitutor substitutor
    ) {
        return new TokenGenerator(grammar, substitutor).genEnum();
    }

    private final Grammar grammar;
    private final StringSubstitutor substitutor;

    private TokenGenerator(
            final Grammar grammar,
            final StringSubstitutor substitutor
    ) {
        this.grammar = grammar;
        this.substitutor = substitutor;
    }

    public String genEnum() {
        final StringBuilder enumBuilder = new StringBuilder();

        final String enumHeader = genHeader();
        enumBuilder.append(enumHeader).append('\n');

        grammar.getTerminalsList().forEach(t -> {
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



    private String genHeader() {
        final String headerTemplate = """
                ${package}
                public enum ${prefix}Token {
           """;
        return substitutor.replace(headerTemplate);
    }

    private String genConstructor() {
        final String constructorTemplate = """
                
                public final String regex;
                
                ${prefix}Token (final String regex) {
                    this.regex = regex;
                }
                
                """;

        return substitutor.replace(constructorTemplate);
    }

}
