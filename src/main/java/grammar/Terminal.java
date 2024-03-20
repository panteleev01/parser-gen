package grammar;

public record Terminal(String str) implements Unit {

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public String toString() {
        return str;
    }
}

