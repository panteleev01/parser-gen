package generators;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class UtilClassGenerator {

    private final static String UTIL_TEMPLATE = """
            ${packageStr}
            
            public class ${prefix}Util {
            
            }
            
            """;

    public static String generate(final String prefix, final String packageStr) {
        return substitute(
                UTIL_TEMPLATE,
                Map.entry("prefix", prefix),
                Map.entry("packageStr", withLayer(packageStr, "util"))
        );
    }

    public static String substitute(
            final String template,
            final Map.Entry<String, String>... variables
    ) {
        final Map<String, String> map = Map.ofEntries(variables);
        return StringSubstitutor.replace(template, map);
    }

    public static String toPackage(final String packageName) {
        if (packageName.isEmpty()) return "";
        return "package " + packageName + ";";
    }

    public static String withLayer(final String rootPackage, final String layer) {
        if (rootPackage.isEmpty()) return toPackage(layer);
        return toPackage(rootPackage + "." + layer);
    }

}
