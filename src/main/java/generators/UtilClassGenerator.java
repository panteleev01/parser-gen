package generators;

import java.util.Map;

public class UtilClassGenerator {

    private final static String UTIL_TEMPLATE = """
            
            package util;
                        
            public class ${prefix}Util {
            
            }
            
            """;

    public static String generate(final String prefix) {
        return TokenGenerator.substitute(
                UTIL_TEMPLATE,
                Map.entry("prefix", prefix)
        );
    }

}
