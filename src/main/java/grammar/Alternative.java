package grammar;

import java.util.List;
import java.util.stream.Collectors;

public class Alternative {
    public final List<Unit> rightSide;
    public final String codeBlock;
    public final List<List<String>> args;

    public Alternative(final List<String> rightSide, final String codeBlock, List<List<String>> args) {
        this.rightSide = mapTerms(rightSide);
        this.codeBlock = codeBlock;
        this.args = args;
    }

    private List<Unit> mapTerms(final List<String> rightSide) {
        return rightSide.stream().map(s -> {
            if (s.equals("EPS")) return Eps.get();
            if (Character.isUpperCase(s.charAt(0))) {
                return new Terminal(s);
            } else {
                return new NonTerminal(s);
            }
        }).collect(Collectors.toList());
    }

    public void print() {
        System.out.print(rightSide);
        System.out.print(" ");
        System.out.println(codeBlock);
    }
}
