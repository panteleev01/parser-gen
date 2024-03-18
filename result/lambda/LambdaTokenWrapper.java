public class LambdaTokenWrapper{
public String value;public LambdaToken token;public LambdaTokenWrapper( String v1, LambdaToken t) {
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