package grammar;

public class Terminal implements Unit {

    public final String str;

    public Terminal(final String str) {
        this.str = str;
    }

    @Override
    public boolean isTerminal() {
        return true;
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
        return false;
    }

    @Override
    public String toString() {
        return str;
    }
}

