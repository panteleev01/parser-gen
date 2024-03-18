package grammar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {

    public final String mainRule;
    public final Map<Decl, List<Alternative>> rules;
    public final Map<String, String> terminals;

    public Grammar(
            final String mainRule,
            final Map<Decl, List<Alternative>> rules,
            final Map<String, String> terminals
    ) {
        this.mainRule = mainRule;
        this.rules = rules;
        this.terminals = new HashMap<>();

        terminals.forEach((k, v) -> {
            if (k.equals("EPS")) this.terminals.put("eps", convertRegex(v));
            else this.terminals.put(k.toUpperCase(), convertRegex(v));
        });
    }

    public String convertRegex(String regex) {
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
