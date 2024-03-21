package grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {

    public final String mainRule;

    public final List<GrammarTerminal> terminalsList;
    public final List<Rule> rules;

    public Grammar(
            final String mainRule,
            final List<Rule> rules,
            final List<GrammarTerminal> terminalsList
    ) {
        this.mainRule = mainRule;
        this.rules = rules;

        this.terminalsList = terminalsList.stream().map(t -> {
            final String regex = convertRegex(t.regex());
            final String name = t.name();

            if (name.equals("EPS")) return new GrammarTerminal("eps", regex);
            else return new GrammarTerminal(name.toUpperCase(), regex);
        }).toList();
    }

    private static String convertRegex(String regex) {
        if (regex.length() < 2) {
            throw new IllegalArgumentException("regex length should be bigger than 1");
        }

        regex = regex.substring(1, regex.length() - 1);
        if (regex.equals("*")) {
            regex = "\\*";
        }
        if (regex.equals("+")) {
            regex = "\\+";
        }
        if (regex.equals("(")) return "\\(";
        if (regex.equals(")")) return "\\)";
        if (regex.equals("[")) return "\\[";
        if (regex.equals("]")) return "\\]";
        return regex;
    }
}
