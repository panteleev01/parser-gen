import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class LexerGenerator {

//    private static StringBuilder res = new StringBuilder();
//    private static String prefix;
//    private static String className;
//    private static String tokenName;

    private final static String LEXER_TEMPLATE = """
            import java.text.ParseException;
            import java.util.regex.Pattern;
                        
            public class ${prefix}LexicalAnalyzer {
                public ${prefix}TokenWrapper curToken;
                private final String stream;
                private int curPos = 0;
                        
                public ${prefix}LexicalAnalyzer(final String stream) {
                    this.stream = stream;
                }
                        
                public void nextToken() throws ParseException {
                    if (curPos >= stream.length()) {
                        curToken = new ${prefix}TokenWrapper("", ${prefix}Token.END);
                        return;
                    }
                        
                    while (curPos < stream.length() && Character.isWhitespace(stream.charAt(curPos))) {
                        curPos += 1;
                    }
                        
                    var tail = stream.substring(curPos);
                        
                    for (var token: ${prefix}Token.values()) {
                        var matcher = Pattern.compile(token.regex).matcher(tail);
                        if (matcher.lookingAt()) {
                            String head = matcher.group();
                            curPos += head.length();
                            curToken = new ${prefix}TokenWrapper(head, token);
                            return;
                        }
                    }
                    throw new ParseException("Didn't match anything", curPos);
                }
               \s
            }
            """;

    private final StringSubstitutor substitutor;

    private LexerGenerator(final String prefix) {
        final Map<String, String> map = Map.of(
                "prefix", prefix
        );
        this.substitutor = new StringSubstitutor(map);
    }

    public String g() {
        return substitutor.replace(LEXER_TEMPLATE);
    }

    public static String gen(final String prefix) {
        return new LexerGenerator(prefix).g();
    }

}
