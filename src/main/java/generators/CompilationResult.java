package generators;

import grammar.Unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompilationResult {

    private final Map<String, Set<String>> firstForNon;
    private final Map<String, Set<String>> followForNon;

    public CompilationResult(Map<String, Set<String>> firstForNon, Map<String, Set<String>> followForNon) {
        this.firstForNon = new HashMap<>(firstForNon);
        this.followForNon = new HashMap<>(followForNon);
    }

    public Set<String> follow(final String key) {
        return followForNon.get(key);
    }

    public Set<String> simple(final List<Unit> alpha) {
        return GrammarCompilation.getSimpleFirst(alpha, firstForNon);
    }
}
