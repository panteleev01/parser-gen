package generators;

import grammar.Grammar;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

import static generators.TokenGenerator.toPackage;

public class TokenWrapperGenerator {

    private final static String WRAPPER_TEMPLATE = """
            ${package}
        
            public class ${regex}TokenWrapper {
                public String value;
                public ${regex}Token token;
                        
                public ${regex}TokenWrapper(String v1, ${regex}Token t) {
                    this.value = v1;
                    this.token = t;
                }
                        
                @Override
                public String toString() {
                    return "${regex}TokenWrapper{" +
                            "value='" + value + '\\'' +
                            ", token=" + token +
                            '}';
                }
                
            }
            
            """;

    private final StringSubstitutor substitutor;

    public TokenWrapperGenerator(final String prefix, final String packageStr) {
        final Map<String, String> parameters = Map.of(
                "regex", prefix,
                "package", toPackage(packageStr)
        );
        this.substitutor = new StringSubstitutor(parameters);
    }

    public static String gen(
            final String prefix,
            final String packageStr
    ) {
        return new TokenWrapperGenerator(prefix, packageStr).gen();
    }

    private String gen() {
        return substitutor.replace(WRAPPER_TEMPLATE);
    }

}
