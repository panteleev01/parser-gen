package grammar;

import java.util.List;
import java.util.stream.Collectors;

public class Alternative {

    private final List<Unit> rightSide;
    private final String codeBlock;
    private final List<List<String>> args;

    public Alternative(final List<String> rightSide, final String codeBlock, List<List<String>> args) {
        this.rightSide = mapTerms(rightSide);
        this.codeBlock = codeBlock;
        this.args = args;
    }

    private static List<Unit> mapTerms(final List<String> rightSide) {
        return rightSide.stream().map(s -> {
            if (s.equals("EPS")) return Eps.get();
            if (Character.isUpperCase(s.charAt(0))) {
                return new Terminal(s);
            } else {
                return new NonTerminal(s);
            }
        }).toList();
    }

    public List<Unit> getRightSide() {
        return rightSide;
    }

    public String getCodeBlock() {
        return codeBlock;
    }

    public List<List<String>> getArgs() {
        return args;
    }
}
