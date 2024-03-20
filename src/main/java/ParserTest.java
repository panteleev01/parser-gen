import java.text.ParseException;

public class ParserTest {

    public static void main(String[] args) throws ParseException {
        var lexer = new ExpressionLexicalAnalyzer("(1+5)*4-1*32/4");
        var p = new ExpressionParser(lexer);
        var x = p.e();

        System.out.println(x);
    }

}
