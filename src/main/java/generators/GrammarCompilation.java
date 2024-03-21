package generators;

import com.google.common.collect.Sets;
import grammar.*;

import java.util.*;

public class GrammarCompilation {


    private static final String EPS = "eps";
    private static final String DOLLAR = "$";

    private final Map<String, Set<String>> firstForNon = new HashMap<>();
    private final Map<String, Set<String>> followForNon = new HashMap<>();
    private final Grammar grammar;

    private GrammarCompilation(final Grammar g) {
        this.grammar = g;
    }

    public static CompilationResult compile(final Grammar g) {
        return new GrammarCompilation(g).compile();
    }

    private CompilationResult compile() {
        calculateFirstTokens();
        calculateFollowTokens();
        checkLL1Grammar();

        return new CompilationResult(firstForNon, followForNon);
    }

    private void calculateFollowTokens() {
        followForNon.put(grammar.getMainRule(), Sets.newHashSet(DOLLAR));

        while (true) {
            boolean changed = false;

            for (var rule: grammar.getRules()) {
                final String ruleName = rule.decl().getName();
                for (var alternative: rule.alternatives()) {
                    changed |= updateFollow(ruleName, alternative.getRightSide());
                }
            }
            if (!changed) break;
        }
    }

    private boolean updateFollow(final String ruleName, final List<Unit> alternative) {
        boolean updated = false;
        for (int i = 0; i < alternative.size(); ++i) {

            final var symbol = alternative.get(i);
            if (!symbol.isNonTerminal()) continue;

            final NonTerminal nonTerm = (NonTerminal) symbol;
            final String nonTermName = nonTerm.str();

            final Set<String> followSet = followForNon.computeIfAbsent(nonTermName, k -> new HashSet<>());
            final int currentSize = followSet.size();

            final List<Unit> ruleTail = alternative.subList(i + 1, alternative.size());
            final Set<String> tailFirst = getCurSimpleFirst(ruleTail);

            if (tailFirst.remove(EPS)) {
                var ruleFollow = followForNon.getOrDefault(ruleName, new HashSet<>());
                tailFirst.addAll(ruleFollow);
            }

            followSet.addAll(tailFirst);
            updated |= (currentSize != followSet.size());
        }
        return updated;
    }

    private void calculateFirstTokens() {
        while (true) {
            boolean stateUpdated = false;

            for (var rule: grammar.getRules()) {
                final String ruleName = rule.decl().getName();
                for (var alt: rule.alternatives()) {
                    stateUpdated |= updateFirst(ruleName, alt.getRightSide());
                }
            }
            if (!stateUpdated) break;
        }
    }

    private boolean updateFirst(final String A, final List<Unit> alpha) {
        Set<String> simpleFirst = getCurSimpleFirst(alpha);
        Set<String> fullFirst = firstForNon.get(A);
        if (fullFirst == null) {
            firstForNon.put(A, simpleFirst);
            return true;
        } else {
            int size = fullFirst.size();
            fullFirst.addAll(simpleFirst);
            return fullFirst.size() != size;
        }
    }

    private Set<String> getCurSimpleFirst(final List<Unit> alpha) {
        return getSimpleFirst(alpha, firstForNon);
    }

    public static Set<String> getSimpleFirst(
            final List<Unit> alpha,
            final Map<String, Set<String>> first
    ) {
        if (alpha.isEmpty()) return new HashSet<>(Set.of("eps"));
        if (alpha.size() == 1 && alpha.get(0).equals(Eps.get())) {
            return new HashSet<>(Set.of("eps"));
        } else if (alpha.get(0) instanceof Terminal t) {
            return new HashSet<>(Set.of(t.str()));
        } else {
            final NonTerminal nt = (NonTerminal) alpha.get(0);
            Set<String> forHead = first.getOrDefault(nt.str(), new HashSet<>());
            forHead = new HashSet<>(forHead);
            if (forHead.contains("eps")) {
                forHead.remove("eps");
                Set<String> forTail = getSimpleFirst(alpha.subList(1, alpha.size()), first);
                forHead.addAll(forTail);
            } else {
                forHead.remove("eps");
            }
            return forHead;
        }
    }


    private void checkLL1Grammar() {
        for (var declListEntry : grammar.getRules()) {
            String A = declListEntry.decl().getName();
            for (int i = 0; i < declListEntry.alternatives().size(); ++i) {
                for (int j = 0; j < declListEntry.alternatives().size(); ++j) {
                    if (i == j) continue;
                    var alpha = declListEntry.alternatives().get(i).getRightSide();
                    var beta = declListEntry.alternatives().get(j).getRightSide();

                    var alphaFirst = getCurSimpleFirst(alpha);
                    var betaFirst = getCurSimpleFirst(beta);

                    var x = new HashSet<>(alphaFirst);
                    x.retainAll(betaFirst);
                    if (!x.isEmpty()) throw new AssertionError("Not LL1 grammar");

                    if (alphaFirst.contains("eps")) {
                        var y = new HashSet<>(betaFirst);
                        y.retainAll(followForNon.get(A));
                        if (!y.isEmpty()) throw new AssertionError("Not LL1 grammar");
                    }
                }
            }
        }
    }

}
