package generators;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class LexerGenerator {

    private final static String LEXER_TEMPLATE = """
            ${package}
            
            import java.text.ParseException;
            import java.util.regex.Pattern;
                        
            public class ${regex}LexicalAnalyzer {
                public ${regex}TokenWrapper curToken;
                private final String stream;
                private int curPos = 0;
                        
                public ${regex}LexicalAnalyzer(final String stream) {
                    this.stream = stream;
                }
                        
                public void nextToken() throws ParseException {
                    if (curPos >= stream.length()) {
                        curToken = new ${regex}TokenWrapper("", ${regex}Token.END);
                        return;
                    }
                        
                    while (curPos < stream.length() && Character.isWhitespace(stream.charAt(curPos))) {
                        curPos += 1;
                    }
                        
                    var tail = stream.substring(curPos);
                        
                    for (var token: ${regex}Token.values()) {
                        var matcher = Pattern.compile(token.regex).matcher(tail);
                        if (matcher.lookingAt()) {
                            String head = matcher.group();
                            curPos += head.length();
                            curToken = new ${regex}TokenWrapper(head, token);
                            return;
                        }
                    }
                    throw new ParseException("Didn't match anything", curPos);
                }
               \s
            }
            """;

    private final StringSubstitutor substitutor;

    private LexerGenerator(final String prefix, final String packageStr) {
        final Map<String, String> map = Map.of(
                "regex", prefix,
                "package", packageStr
        );
        this.substitutor = new StringSubstitutor(map);
    }

    public String g() {
        return substitutor.replace(LEXER_TEMPLATE);
    }

    public static String gen(final String prefix, final String packageStr) {
        return new LexerGenerator(prefix, packageStr).g();
    }

}
