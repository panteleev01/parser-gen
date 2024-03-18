public enum LambdaToken {
COMMA(","),
DIV("/"),
C("\\)"),
MUL("\\*"),
SEMICOLON(":"),
VARNAME("[a-z]+"),
eps("eps"),
N("[0-9]+"),
LAMBDA("LAMBDA"),
MINUS("-"),
PLUS("\\+"),
O("\\("),
END("$");

    public final String regex;

    private
 LambdaToken(final String regex) {
        this.regex = regex;
    }

}