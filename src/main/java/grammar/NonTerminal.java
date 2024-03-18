package grammar;

public class NonTerminal implements Unit {

    public final String str;

    public NonTerminal(final String str) {
        this.str = str;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public boolean isNonTerminal() {
        return true;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean isEps() {
        return false;
    }

    @Override
    public String toString() {
        return str;
    }
}
