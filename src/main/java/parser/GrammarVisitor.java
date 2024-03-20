package parser;

import antlr4.GrammarBaseVisitor;
import antlr4.GrammarParser;
import grammar.Decl;
import grammar.Grammar;
import grammar.Alternative;
import grammar.Variable;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GrammarVisitor extends GrammarBaseVisitor<Grammar> {

    private Map<String, String> terminals;
    private Map<Decl, List<Alternative>> rules;

    private void parseTerminal(final GrammarParser.TerminalContext term) {
        final String terminal = term.ID().getText();
        final String regex = term.REGEX().getText();
        terminals.put(terminal, regex);
    }


    private void parseMatchRule(final GrammarParser.RuleContext ctx) {
        final String name = ctx.ID(0).getText();
        final String type = ctx.ID(1).getText();

        final List<Alternative> objects =
                ctx.namedRule().stream().map(this::parseNamedRule).toList();

        final List<TerminalNode> args = ctx.args().ID();
        final List<String> names = new ArrayList<>();
        final List<String> types = new ArrayList<>();

        final List<Variable> variables = new ArrayList<>();

        for (int i = 0; i < args.size(); i += 2) {
            final String argType = args.get(i).getText();
            final String argName = args.get(i + 1).getText();
            variables.add(new Variable(argType, argName));
        }

        rules.put(
                new Decl(name, type, variables),
                objects
        );
    }

    @Override
    public Grammar visitMain(final GrammarParser.MainContext ctx) {
        final String mainRule = ctx.mainRule().ID().getText();

        terminals = new HashMap<>();
        rules = new HashMap<>();

        ctx.matcher().forEach(c -> {
            if (c instanceof GrammarParser.MatchTerminalContext terminalCtx) {
                parseTerminal(terminalCtx.terminal());
                return;
            } else if (c instanceof GrammarParser.MatchRuleContext rule) {
                parseMatchRule(rule.rule_());
                return;
            }
            throw new RuntimeException("Unknown type " + c.getClass().getSimpleName());
        });

        return new Grammar(
                mainRule,
                new HashMap<>(rules),
                new HashMap<>(terminals)
        );
    }

    public Alternative parseNamedRule(GrammarParser.NamedRuleContext ctx) {
        final var pairs = extractRightSide(ctx.rightSide());

        final List<String> terminals = pairs.stream().map(Pair::getLeft).toList();
        final List<List<String>> args = pairs.stream().map(Pair::getRight).toList();
        final String block = ctx.BLOCK().getText();
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
