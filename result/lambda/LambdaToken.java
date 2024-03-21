     
     public enum LambdaToken {

LAMBDA("LAMBDA"),
SEMICOLON(":"),
VARNAME("[a-z]+"),
COMMA(","),
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

LambdaToken (final String regex) {
    this.regex = regex;
}

}