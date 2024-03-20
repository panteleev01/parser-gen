package grammar;

public interface Unit {
    default boolean isTerminal() {
        return false;
    }
    default boolean isNonTerminal() {
        return false;
    }

}
