

import java.text.ParseException;
import java.util.regex.Pattern;

public class LambdaLexicalAnalyzer {
    public LambdaTokenWrapper curToken;
    private final String stream;
    private int curPos = 0;

    public LambdaLexicalAnalyzer(final String stream) {
        this.stream = stream;
    }

    public void nextToken() throws ParseException {
        if (curPos >= stream.length()) {
            curToken = new LambdaTokenWrapper("", LambdaToken.END);
            return;
        }

        while (curPos < stream.length() && Character.isWhitespace(stream.charAt(curPos))) {
            curPos += 1;
        }

        var tail = stream.substring(curPos);

        for (var token: LambdaToken.values()) {
            var matcher = Pattern.compile(token.regex).matcher(tail);
            if (matcher.lookingAt()) {
                String head = matcher.group();
                curPos += head.length();
                curToken = new LambdaTokenWrapper(head, token);
                return;
            }
        }
        throw new ParseException("Didn't match anything", curPos);
    }

    public int curPos() {
        return curPos;
    }
}
