import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertThrows;

public class ExpressionTest {

    public Integer run(final String input) throws ParseException {
        final var lexer = new ExpressionLexicalAnalyzer(input);
        final var parser = new ExpressionParser(lexer);
        return parser.parse();
    }

    public void shouldSucceed(final String input, final Integer expected) throws ParseException {
        Assert.assertEquals(expected, run(input));
    }

    public void shouldFail(final String input) {
        assertThrows(ParseException.class, () -> run(input));
    }

    @Test
    public void correctTests() throws ParseException {
        shouldSucceed("[2, 1]", 2);


        shouldSucceed("[10, 3]", 120);
        shouldSucceed("[1+2*5-1, 3]",120);
        shouldSucceed("[1+2*5-1, 1+2*5-1]",  1);
        shouldSucceed("[1+2+4-1-2, 1+2+4-1-2]", 1);

        shouldSucceed("1+1", 2);
        shouldSucceed("2*3", 6);
        shouldSucceed("2*3+4*5", 26);
        shouldSucceed("2*(3+4)*5", 70);
        shouldSucceed("1+-2", -1);
        shouldSucceed("100/2/2", 25);
        shouldSucceed("100/2/2*3", 75);
        shouldSucceed("100/2/2+6/2", 28);
        shouldSucceed("(15 * 7 + 40 / 5) - (20 - 4)", 97);
        shouldSucceed("  (15 * 7 + 40 / 5) - (20 - 4)  ", 97);
        shouldSucceed("((((10 + 5) * 3) - 7) / 2) + (15 * 4 - 6 / 2)", 76);
    }

    @Test
    public void incorrectTests() {
        shouldFail("");
        shouldFail("2*");
        shouldFail("1+3*4+1)");
        shouldFail("1)");
        shouldFail("(1");
        shouldFail("(1+");
        shouldFail("2**3");
        shouldFail("a");
    }

}
