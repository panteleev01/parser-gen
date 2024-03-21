     
     public enum ExpressionToken {

COMMA(","),
SO("\\["),
SC("\\]"),
eps("eps"),
N("[0-9]+"),
MINUS("-"),
PLUS("\\+"),
MUL("\\*"),
DIV("/"),
O("\\("),
C("\\)"),
END("$"),;

public final String regex;

ExpressionToken (final String regex) {
    this.regex = regex;
}

}