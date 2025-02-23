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

            for (final Rule rule : grammar.getRules()) {
                final String ruleName = rule.decl().getName();
                for (var alternative : rule.alternatives()) {
                    changed |= updateFollow(ruleName, alternative.getRightSide());
                }
            }
            if (!changed) break;
        }
    }

    private boolean updateFollow(final String ruleName, final List<Unit> alternative) {
        boolean updatedState = false;
        for (int i = 0; i < alternative.size(); ++i) {

            final Unit symbol = alternative.get(i);
            if (!symbol.isNonTerminal()) continue;

            final NonTerminal nonTerm = (NonTerminal) symbol;
            final String nonTermName = nonTerm.str();

            final Set<String> followSet = followForNon.computeIfAbsent(nonTermName, k -> new HashSet<>());
            final int currentSize = followSet.size();

            final List<Unit> ruleTail = alternative.subList(i + 1, alternative.size());
            final Set<String> tailFirst = calcSimpleFirst(ruleTail);

            if (tailFirst.remove(EPS)) {
                var ruleFollow = followForNon.getOrDefault(ruleName, new HashSet<>());
                tailFirst.addAll(ruleFollow);
            }

            followSet.addAll(tailFirst);
            updatedState |= (currentSize != followSet.size());
        }
        return updatedState;
    }

    private void calculateFirstTokens() {
        while (true) {
            boolean stateUpdated = false;

            for (final Rule rule : grammar.getRules()) {
                final String ruleName = rule.decl().getName();
                for (final var alternative : rule.alternatives()) {
                    stateUpdated |= updateFirst(ruleName, alternative.getRightSide());
                }
            }
            if (!stateUpdated) break;
        }
    }

    private boolean updateFirst(final String ruleName, final List<Unit> alternative) {
        final Set<String> simpleFirst = calcSimpleFirst(alternative);

        final Set<String> firstSet = firstForNon.computeIfAbsent(ruleName, k -> new HashSet<>());
        final int initSize = firstSet.size();
        firstSet.addAll(simpleFirst);
        return initSize != firstSet.size();
    }

    private Set<String> calcSimpleFirst(final List<Unit> alternative) {
        return calcSimpleFirstForMap(alternative, firstForNon);
    }

    public static Set<String> calcSimpleFirstForMap(
            final List<Unit> alternative,
            final Map<String, Set<String>> first
    ) {
        if (alternative.isEmpty()) return Sets.newHashSet(EPS);

        final Unit headSymbol = alternative.get(0);

        if (alternative.size() == 1 && headSymbol.equals(Eps.get())) {
            return Sets.newHashSet(EPS);
        } else if (headSymbol instanceof Terminal terminal) {
            return Sets.newHashSet(terminal.str());
        } else {
            final NonTerminal nonTerminal = (NonTerminal) headSymbol;

            final Set<String> firstForHead = new HashSet<>(
                    first.getOrDefault(nonTerminal.str(), new HashSet<>())
            );

            if (firstForHead.remove(EPS)) {
                final List<Unit> tail = alternative.subList(1, alternative.size());
                final Set<String> firstForTail = calcSimpleFirstForMap(tail, first);
                firstForHead.addAll(firstForTail);
            }

            return firstForHead;
        }
    }


    private void checkLL1Grammar() {
        for (var rules : grammar.getRules()) {
            final String ruleName = rules.decl().getName();
            final List<Alternative> alternatives = rules.alternatives();
            for (int i = 0; i < alternatives.size(); ++i) {
                for (int j = 0; j < alternatives.size(); ++j) {
                    if (i == j) continue;
                    checkLL1Pair(
                            ruleName,
                            alternatives.get(i).getRightSide(),
                            alternatives.get(j).getRightSide()
                    );
                }
            }
        }
    }

    private void checkLL1Pair(final String ruleName, final List<Unit> alpha, final List<Unit> beta) {
        final Set<String> alphaFirst = calcSimpleFirst(alpha);
        final Set<String> betaFirst = calcSimpleFirst(beta);

        final var diff = Sets.intersection(alphaFirst, betaFirst);
        if (!diff.isEmpty()) throw new AssertionError("Not LL1 grammar");

        if (alphaFirst.contains(EPS)) {
            final var diff2 = Sets.intersection(betaFirst, followForNon.get(ruleName));
            if (!diff2.isEmpty()) throw new AssertionError("Not LL1 grammar");
        }
    }

}
