import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertThrows;

public class LambdaTest {

    public Lambda run(final String input) throws ParseException {
        var lexer = new LambdaLexicalAnalyzer(input);

        var p = new LambdaParser(lexer);
        var x = p.main();
        if (lexer.curToken.token != LambdaToken.END) throw new RuntimeException("fail");
        return x;
    }

    public void test(final String input, boolean shouldFail, Set<String> names) throws ParseException {
        if (shouldFail) {
            assertThrows(Exception.class, () -> run(input));
            return;
        }
        var lambda = run(input);
        Assert.assertEquals(names, new HashSet<>(lambda.names1.args));
    }

    public void write(final String input, final int i) throws ParseException, IOException {
        var lambda = run(input);
        var sb = new StringBuilder();
        sb.append("graph G {");
        lambda.register(sb);
        lambda.link(sb);
        sb.append("}");

        String text = sb.toString();
        Files.writeString(Path.of("graphs/plot" + i + ".txt"), text);
    }

    @Test
    public void plot() throws ParseException, IOException {
        final List<String> lambdas = List.of(
                "LAMBDA x,y,variable: 1+x*3/(1+4)",
                "LAMBDA : 10",
                "LAMBDA x: x + y * -10",
                "LAMBDA x, y, z: x + (y + z / 123)"
        );
        for (int i = 0; i < lambdas.size(); ++i) {
            write(lambdas.get(i), i);
        }
    }

    @Test
    public void correctTests() throws ParseException {
        test("LAMBDA x,y,adsf: 1+2", false, Set.of("x", "y", "adsf"));
        test("LAMBDA : 10", false, Set.of());
        test("LAMBDA x: x*x+x-1", false, Set.of("x"));
        test("LAMBDA x,y: x  -  y  * (z + t)  - (1 - 2 * -3) * x - 3", false, Set.of("x", "y"));
    }

    @Test
    public void incorrectTests() throws ParseException {
        test("", true, null);
        test("LAMBDA ,y,adsf; 1+2", true, null);
        test("LAMBDA 1 + 2", true, null);
        test("10", true, null);
        test("LAMBDA : ", true, null);
        test("LAMBDA : +-1", true, null);
        test("random x: x + 1", true, null);
        test("LAMBDA x: *3 + 1 - 4", true, null);
        test("LAMBDA x: (1+2-4", true, null);
        test("LAMBDA x: 1+2)-4", true, null);
    }

}
