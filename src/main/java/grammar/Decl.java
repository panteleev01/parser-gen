package grammar;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Decl {

    private final String name;
    private final String type;
    private final List<Variable> variables;

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

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public String getTypedVariables() {
        return variables
                .stream()
                .map(v -> v.type() + v.name())
                .collect(Collectors.joining(", "));
    }

    public String getVariablesList() {
        return variables
                .stream()
                .map(Variable::name)
                .collect(Collectors.joining(", "));
    }
}
