package generators;

import java.util.Map;

import static generators.TokenGenerator.withLayer;

public class UtilClassGenerator {

    private final static String UTIL_TEMPLATE = """
            ${packageStr}
            
            public class ${prefix}Util {
            
            }
            
            """;

    public static String generate(final String prefix, final String packageStr) {
        return TokenGenerator.substitute(
                UTIL_TEMPLATE,
                Map.entry("prefix", prefix),
                Map.entry("packageStr", withLayer(packageStr, "util"))
        );
    }

}
