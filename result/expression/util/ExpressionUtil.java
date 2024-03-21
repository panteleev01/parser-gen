package util;

public class ExpressionUtil {
    public static Integer fact(Integer i) {
        if (i <= 1) return 1;
        return i * fact(i - 1);
    }
}

