package generators;

import grammar.*;

import java.util.*;

public class GrammarCompilation {

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
        calcFirst();
        calcFollow();
        checkLL1();

        return new CompilationResult(firstForNon, followForNon);
    }

    private void calcFollow() {
        followForNon.put(grammar.mainRule, new HashSet<>(Set.of("$")));
        while (true) {
            boolean changed = false;

            for (var entry: grammar.rules) {
                final String left = entry.decl().name;
                for (var alt: entry.alternatives()) {
                    changed |= updateFollow(left, alt.rightSide);
                }
            }
            if (!changed) break;
        }
    }

    private boolean updateFollow(final String left, final List<Unit> alpha) {
        boolean updated = false;
        for (int i = 0; i < alpha.size(); ++i) {
            final var cur = alpha.get(i);
            if (!cur.isNonTerminal()) continue;
            final var term = (NonTerminal) cur;
            var oldFollow = followForNon.computeIfAbsent(term.str(), k -> new HashSet<>());
            var initSize = oldFollow.size();

            var gamma = alpha.subList(i + 1, alpha.size());

            var gammaFirst = getCurSimpleFirst(gamma);

            if (gammaFirst.contains("eps")) {
                gammaFirst.remove("eps");
                var aFollow =
                        new HashSet<>(followForNon.getOrDefault(left, new HashSet<>()));
                gammaFirst.addAll(aFollow);

                followForNon.get(term.str()).addAll(gammaFirst);
            } else {
                gammaFirst.remove("eps");
                followForNon.get(term.str()).addAll(gammaFirst);
            }
            updated |=
                    initSize != followForNon.get(term.str()).size();
        }
        return updated;
    }

    private void calcFirst() {
        while (true) {
            boolean stateUpdated = false;

            for (var entry: grammar.rules) {
                final String left = entry.decl().name;
                for (var alt: entry.alternatives()) {
                    stateUpdated |= updateFirst(left, alt.rightSide);
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


    private void checkLL1() {
        for (var declListEntry : grammar.rules) {
            String A = declListEntry.decl().name;
            for (int i = 0; i < declListEntry.alternatives().size(); ++i) {
                for (int j = 0; j < declListEntry.alternatives().size(); ++j) {
                    if (i == j) continue;
                    var alpha = declListEntry.alternatives().get(i).rightSide;
                    var beta = declListEntry.alternatives().get(j).rightSide;

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
