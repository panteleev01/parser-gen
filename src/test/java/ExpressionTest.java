import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertThrows;

public class ExpressionTest {

    public Integer run(final String input) throws ParseException {
        var lexer = new ExpressionLexicalAnalyzer(input);

        var p = new ExpressionParser(lexer);
        var x = p.e();
        if (lexer.curToken.token != ExpressionToken.END) throw new RuntimeException("fail");
        return x;
    }

    public void test(final String input, boolean shouldFail, Integer res) throws ParseException {
        if (shouldFail) {
            assertThrows(Exception.class, () -> run(input));
            return;
        }
        Assert.assertEquals(res, run(input));
    }

    @Test
    public void correctTests() throws ParseException {
        test("[2, 1]", false, 2);
        test("[10, 3]", false, 120);
        test("[1+2*5-1, 3]", false, 120);
        test("[1+2*5-1, 1+2*5-1]", false, 1);
        test("[1+2+4-1-2, 1+2+4-1-2]", false, 1);

//        test("1+1", false, 2);
//        test("2*3", false, 6);
//        test("2*3+4*5", false, 26);
//        test("2*(3+4)*5", false, 70);
//        test("1+-2", false, -1);
//        test("100/2/2", false, 25);
//        test("100/2/2*3", false, 75);
//        test("100/2/2+6/2", false, 28);
//        test("(15 * 7 + 40 / 5) - (20 - 4)", false, 97);
//        test("  (15 * 7 + 40 / 5) - (20 - 4)  ", false, 97);
//        test("((((10 + 5) * 3) - 7) / 2) + (15 * 4 - 6 / 2)", false, 76);
    }

    @Test
    public void incorrectTests() throws ParseException {
        test("", true, null);
        test("2*", true, null);
        test("1+3*4+1)", true, null);
        test("1)", true, null);
        test("(1", true, null);
        test("(1+", true, null);
        test("2**3", true, null);
        test("a", true, null);
    }

}
