package generators;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

import static generators.UtilClassGenerator.toPackage;

public class LexerGenerator {

    private final static String LEXER_TEMPLATE = """
            ${package}
            
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
                
                public int curPos() {
                    return curPos;
                }
            }
            """;

    private final StringSubstitutor substitutor;

    private LexerGenerator(final StringSubstitutor substitutor) {
        this.substitutor = substitutor;
    }

    public String g() {
        return substitutor.replace(LEXER_TEMPLATE);
    }

    public static String gen(final StringSubstitutor substitutor) {
        return new LexerGenerator(substitutor).g();
    }

}
