package generators;


import org.apache.commons.text.StringSubstitutor;

public class TokenWrapperGenerator {

    private final static String WRAPPER_TEMPLATE = """
            ${package}
        
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
                            "value=" + value +
                            ", token=" + token +
                            "}";
                }
            }
            
            """;

    private final StringSubstitutor substitutor;

    public TokenWrapperGenerator(final StringSubstitutor substitutor) {
        this.substitutor = substitutor;
    }

    public static String gen(
            final StringSubstitutor substitutor
    ) {
        return new TokenWrapperGenerator(substitutor).gen();
    }

    private String gen() {
        return substitutor.replace(WRAPPER_TEMPLATE);
    }

}
