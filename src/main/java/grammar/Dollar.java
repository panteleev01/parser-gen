package grammar;

public class Dollar implements Unit {

    private static final Dollar instance = new Dollar();

    public static Dollar get() {
        return instance;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Dollar;
    }

    @Override
    public int hashCode() {
        return 7123812;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public boolean isNonTerminal() {
        return false;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public boolean isEps() {
        return false;
    }

}
