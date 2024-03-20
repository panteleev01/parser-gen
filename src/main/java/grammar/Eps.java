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
    public String toString() {
        return "eps";
    }
}
