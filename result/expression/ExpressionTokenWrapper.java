

public class ExpressionTokenWrapper {
    public String value;
    public ExpressionToken token;

    public ExpressionTokenWrapper(String v1, ExpressionToken t) {
        this.value = v1;
        this.token = t;
    }

    @Override
    public String toString() {
        return "ExpressionTokenWrapper{" +
                "value='" + value + '\'' +
                ", token=" + token +
                '}';
    }

}

