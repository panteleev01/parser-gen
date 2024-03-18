package generators;

import grammar.Grammar;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class TerminalWrapperGenerator {

    private final static String WRAPPER_TEMPLATE = """
       
            public class ${prefix}TokenWrapper {
                public String value;
                public ${prefix}Token token;
                        
                public ${prefix}TokenWrapper(String v1, ${prefix}Token t) {
                    this.value = v1;
                    this.token = t;
                }
                        
                @Override
                public String toString() {
                    return "${prefix}TokenWrapper{" +
                            "value='" + value + '\\'' +
                            ", token=" + token +
                            '}';
                }
                
            }
            
            """;

    private final StringSubstitutor substitutor;

    public TerminalWrapperGenerator(String prefix) {
        final Map<String, String> parameters = Map.of(
                "prefix", prefix
        );
        this.substitutor = new StringSubstitutor(parameters);
    }

    public static String gen(
            final String prefix
    ) {
        return new TerminalWrapperGenerator(prefix).gen();
    }

    private String gen() {
        return substitutor.replace(WRAPPER_TEMPLATE);
    }

    public static String generate(
            Grammar g,
            String prefix) {
        final StringBuilder res = new StringBuilder();

        var className = prefix + "TokenWrapper";
        var tokenType = prefix + "Token";

        res.append("public class ");
        res.append(className + "{\n");
        res.append("public String value;");
        res.append("public " + tokenType + " token;");

        res.append("public " + className + "( String v1, " + tokenType + " t) {\n");
        res.append("""
                this.value = v1;
                this.token = t;
                """);
        res.append("\n}");

        res.append("""
                @Override
                    public String toString() {
                        return "TestTokenWrapper{" +
                                "value='" + value + '\\'' +
                                ", token=" + token +
                                '}';
                    }
                """);

        res.append("\n}");
        return res.toString();
    }

}
