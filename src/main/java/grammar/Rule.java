package grammar;

import java.util.List;

public record Rule(Decl decl, List<Alternative> alternatives) {
}
