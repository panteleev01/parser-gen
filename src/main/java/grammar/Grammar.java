package grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {

    public final String mainRule;

    public final Map<Decl, List<Alternative>> rules;

    public final List<GrammarTerminal> terminalsList;


    public Grammar(
            final String mainRule,
            final Map<Decl, List<Alternative>> rules,
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

    private String convertRegex(String regex) {
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

    public void print() {
        System.out.println(mainRule);
        rules.entrySet().forEach(entry -> {
            System.out.println(entry.getKey());
            System.out.println(" = ");
            entry.getValue().forEach(Alternative::print);
        });

    }
}
