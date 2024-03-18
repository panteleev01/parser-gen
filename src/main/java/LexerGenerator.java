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

//    public static String generate(
//            String prefix) {
//        className = prefix + "LexicalAnalyzer";
//        tokenName = prefix + "TokenWrapper";
//        res = new StringBuilder();
//        LexerGenerator.prefix = prefix;
//
//        addHeader();
//        addFields();
//        addConstructor();
//        addNextChar();
//        addBottom();
//        return res.toString();
//    }
//
//    public static void addNextChar() {
//
//        res.append("""
//                public void nextToken() throws ParseException {
//                            if (curPos >= stream.length()) {
//                                curToken = new
//                """);
//        res.append(" " + tokenName);
//        res.append("""
//                ("",
//                """);
//        res.append(prefix + "Token.END);");
//        res.append("""
//                return;
//                            }
//
//
//                            while (curPos < stream.length() && Character.isWhitespace(stream.charAt(curPos))) {
//                                curPos += 1;
//                            }
//
//                            var tail = stream.substring(curPos);
//
//                            for (var token:
//                """);
//
//        res.append(prefix + "Token");
//        res.append("""
//                .values()) {
//                                var matcher = Pattern.compile(token.regex).matcher(tail);
//                                if (matcher.lookingAt()) {
//                                    String head = matcher.group();
//                                    curPos += head.length();
//                                    curToken = new
//                """);
//        res.append(" " + tokenName);
//        res.append("""
//                (head, token);
//                                    return;
//                                }
//                            }
//                            throw new ParseException("didnt match anything", curPos);
//                        }
//                """);
//
//        res.append("""
//
//                public boolean done() {
//                    return curPos >= stream.length();
//                }
//
//                """);
//    }
//
//    public static void addConstructor() {
//        res.append("public ");
//        res.append(className);
//
//        res.append("""
//                    (final String stream) {
//                        this.stream = stream;
//                        curPos = 0;
//                    }
//                """);
//    }
//
//    public static void addFields() {
//        res.append("    public " + tokenName + " curToken; \n");
//        res.append("""
//                    private String stream;
//                    private int curChar;
//                    private int curPos;
//                   """);
//        res.append(";\n");
//    }
//
//    public static void addHeader() {
//        res.append("""
//                import java.io.IOException;
//                import java.text.ParseException;
//                import java.util.regex.Pattern;
//
//                public class
//                """);
//        res.append(className).append(" {\n");
//    }
//
//    public static void addBottom() {
//        res.append("\n}");
//    }





}
