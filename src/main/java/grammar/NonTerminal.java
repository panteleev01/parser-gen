package grammar;

public record NonTerminal(String str) implements Unit {

    @Override
    public boolean isNonTerminal() {
        return true;
    }

    @Override
    public String toString() {
        return str;
    }
}
