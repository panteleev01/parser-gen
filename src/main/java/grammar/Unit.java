package grammar;

public interface Unit {
    boolean isTerminal();
    boolean isNonTerminal();
    boolean isEnd();
    boolean isEps();
}
