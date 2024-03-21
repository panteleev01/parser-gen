package grammar;

import java.util.List;
import java.util.Objects;

public class Decl {

    public final String name;
    public final String type;
    public final List<Variable> variables;

    public Decl(final String name, final String type, final List<Variable> variables) {
        this.name = name;
        this.type = type;
        this.variables = variables;
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
