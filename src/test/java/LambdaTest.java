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
        return p.parse();
    }

    public void shouldSucceed(final String input, final Set<String> expectedNames) throws ParseException {
        final Lambda lambda = run(input);
        Assert.assertEquals(expectedNames, new HashSet<>(lambda.names1.args));
    }

    public void shouldFail(final String input) {
        assertThrows(ParseException.class, () -> run(input));
    }

    @Test
    public void correctTests() throws ParseException {
        shouldSucceed("LAMBDA x,y,adsf: 1+2", Set.of("x", "y", "adsf"));
        shouldSucceed("LAMBDA : 10", Set.of());
        shouldSucceed("LAMBDA x: x*x+x-1", Set.of("x"));
        shouldSucceed("LAMBDA x,y: x  -  y  * (z + t)  - (1 - 2 * -3) * x - 3", Set.of("x", "y"));
    }

    @Test
    public void incorrectTests() {
        shouldFail("");
        shouldFail("LAMBDA ,y,adsf; 1+2");
        shouldFail("LAMBDA 1 + 2");
        shouldFail("10");
        shouldFail("LAMBDA : ");
        shouldFail("LAMBDA : +-1");
        shouldFail("random x: x + 1");
        shouldFail("LAMBDA x: *3 + 1 - 4");
        shouldFail("LAMBDA x: (1+2-4");
        shouldFail("LAMBDA x: 1+2)-4");
    }


    public void write(final String input, final int i) throws ParseException, IOException {
        final Lambda lambda = run(input);
        final StringBuilder graphBuilder = new StringBuilder();
        graphBuilder.append("graph G {");
        lambda.register(graphBuilder);
        lambda.link(graphBuilder);
        graphBuilder.append("}");

        final String graph = graphBuilder.toString();
        Files.writeString(Path.of("graphs/plot" + i + ".txt"), graph);
    }

    @Test
    public void visualize() throws ParseException, IOException {
        final List<String> inputs = List.of(
                "LAMBDA x,y,variable: 1+x*3/(1+4)",
                "LAMBDA : 10",
                "LAMBDA x: x + y * -10",
                "LAMBDA x, y, z: x + (y + z / 123)"
        );
        for (int i = 0; i < inputs.size(); ++i) {
            write(inputs.get(i), i);
        }
    }

}
