package grammar;

public class Eps implements Unit {

    private static final Eps instance = new Eps();

    public static Eps get() {
        return instance;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Eps;
    }

    @Override
    public int hashCode() {
        return 19203910;
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
        return false;
    }

    @Override
    public boolean isEps() {
        return true;
    }

    @Override
    public String toString() {
        return "eps";
    }
}
