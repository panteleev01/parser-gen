import java.text.ParseException;

public class LexerMain {

    public static void main(String[] args) throws ParseException {

        var lexer = new ExpressionLexicalAnalyzer("(1+5)*4-1*32");
        var p = new ExpressionParser(lexer);
        var x = p.e();

        System.out.println(x);
    }


}
