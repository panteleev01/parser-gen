public enum ExpressionToken {
COMMA(","),
SC("\\]"),
DIV("/"),
C("\\)"),
MUL("\\*"),
eps("eps"),
SO("\\["),
N("[0-9]+"),
MINUS("-"),
PLUS("\\+"),
O("\\("),
END("$"),;

public final String regex;

ExpressionToken (final String regex) {
    this.regex = regex;
}

}