import antlr4.GrammarBaseVisitor;
import antlr4.GrammarParser;
import grammar.Decl;
import grammar.Grammar;
import grammar.Alternative;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GrammarVisitor extends GrammarBaseVisitor<Grammar> {

    @Override
    public Grammar visitMain(final GrammarParser.MainContext ctx) {
        final String mainRule = ctx.mainRule().ID().getText();

        final Map<Decl, List<Alternative>> rules = new HashMap<>();
        final Map<String, String> terminals = new HashMap<>();
        ctx.matcher().forEach(c -> {
            if (c instanceof GrammarParser.MatchTerminalContext term) {
                final String terminal = term.terminal().ID().getText();
                final String regex = term.terminal().REGEX().getText();
                terminals.put(terminal, regex);
                return;
            } else if (c instanceof GrammarParser.MatchRuleContext rule) {
                final String name = rule.rule_().ID(0).getText();
                final String type = rule.rule_().ID(1).getText();
                final List<Alternative> objects =
                        rule.rule_().namedRule().stream().map(this::parseNamedRule).toList();

                final var args = rule.rule_().args().ID();
                final List<String> names = new ArrayList<>();
                final List<String> types = new ArrayList<>();
                for (int i = 0; i < args.size(); i += 2) {
                    var argType = args.get(i).getText();
                    var argName = args.get(i + 1).getText();
                    names.add(argName);
                    types.add(argType);
                }

                rules.put(
                    new Decl(name, type, names, types),
                    objects
                );
                return;
            }
            throw new RuntimeException("Unknown type " + c.getClass().getSimpleName());
        });

        return new Grammar(
                mainRule,
                rules,
                terminals
        );
    }

    public Alternative parseNamedRule(GrammarParser.NamedRuleContext ctx) {
        final var pairs = extractRightSide(ctx.rightSide());

        final var terminals = pairs.stream().map(Pair::getLeft).toList();
        final var args = pairs.stream().map(Pair::getRight).toList();
        final var block = ctx.BLOCK().getText();
        return new Alternative(terminals, block, args);
    }

    public List<Pair<String, List<String>>> extractRightSide(final GrammarParser.RightSideContext ctx) {
        return ctx.callArgs().stream().map(call -> {
            final var name = call.ID().getText();
            final var args = call.REGEX().stream()
                    .map(ParseTree::getText)
                    .map(s -> s.substring(1, s.length() - 1))
                    .toList();
            return Pair.of(name, args);
        }).toList();
    }



}
