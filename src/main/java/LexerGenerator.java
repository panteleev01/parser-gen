import grammar.Grammar;

public class LexerGenerator {

    private static StringBuilder res = new StringBuilder();
    private static String prefix;
    private static String regex;
    private static String className;
    private static String tokenName;

    public static String generate(
            String regex,
            String prefix) {
        className = prefix + "LexicalAnalyzer";
        tokenName = prefix + "TokenWrapper";
        res = new StringBuilder();
        LexerGenerator.prefix = prefix;
        LexerGenerator.regex =  regex;

        addHeader();
        addFields();
        addConstructor();
        addNextChar();
        addBottom();
        return res.toString();
    }

    public static void addNextChar() {

        res.append("""
                public void nextToken() throws ParseException {
                            if (curPos >= stream.length()) {
                                curToken = new 
                """);
        res.append(" " + tokenName);
        res.append("""
                ("", 
                """);
        res.append(prefix + "Token.END);");
        res.append("""
                return;
                            }
                               
                            
                            while (curPos < stream.length() && Character.isWhitespace(stream.charAt(curPos))) {
                                curPos += 1;
                            }
                    
                            var tail = stream.substring(curPos);
                    
                            for (var token: 
                """);

        res.append(prefix + "Token");
        res.append("""
                .values()) {
                                var matcher = Pattern.compile(token.regex).matcher(tail);
                                if (matcher.lookingAt()) {
                                    String head = matcher.group();
                                    curPos += head.length();
                                    curToken = new
                """);
        res.append(" " + tokenName);
        res.append("""
                (head, token);
                                    return;
                                }
                            }
                            throw new ParseException("didnt match anything", curPos);
                        }
                """);

        res.append("""
                
                public boolean done() {
                    return curPos >= stream.length();
                }
                
                """);
    }

    public static void addConstructor() {
        res.append("public ");
        res.append(className);

        res.append("""
                    (final String stream) {
                        this.stream = stream;
                        curPos = 0;
                    }
                """);
    }

    public static void addFields() {
        res.append("    public " + tokenName + " curToken; \n");
        res.append("""
                    private String stream;
                    private int curChar;
                    private int curPos;
                   """);
        res.append(";\n");
    }

    public static void addHeader() {
        res.append("""
                import java.io.IOException;
                import java.text.ParseException;
                import java.util.regex.Pattern;
                
                public class
                """);
        res.append(className).append(" {\n");
    }

    public static void addBottom() {
        res.append("\n}");
    }





}
