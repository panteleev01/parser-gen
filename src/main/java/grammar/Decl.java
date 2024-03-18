package grammar;

import java.util.List;
import java.util.Objects;

public class Decl {

    public final String name;
    public final String type;
    public final List<String> argsNames;
    public final List<String> argsTypes;

    public Decl(final String name, final String type, List<String> argsNames, List<String> argsTypes) {
        this.name = name;
        this.type = type;
        this.argsNames = argsNames;
        this.argsTypes = argsTypes;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Decl d) {
            return d.name.equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
