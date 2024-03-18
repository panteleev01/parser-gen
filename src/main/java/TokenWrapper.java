public class TokenWrapper{
public String value;public Token token;public TokenWrapper( String v1, Token t) {
this.value = v1;
this.token = t;

}@Override
    public String toString() {
        return "TestTokenWrapper{" +
                "value='" + value + '\'' +
                ", token=" + token +
                '}';
    }

}