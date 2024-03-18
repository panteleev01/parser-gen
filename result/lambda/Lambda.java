import java.util.Random;

public class Lambda {
    public final Args names1;
    public final Node e3;

    public Lambda(Args names1, Node e3) {
        this.names1 = names1;
        this.e3 = e3;
    }

    @Override
    public String toString() {
        return "Lambda{" +
                "names1=" + names1 +
                ", e3=" + e3 +
                '}';
    }

    String id = "";

    public void register(StringBuilder sb) {
        id = "ID" + new Random().nextLong(0, 100000000000L);
        sb.append(id).append(" [label=\"");
        sb.append("lambda");
        sb.append("\"]\n");

        names1.register(sb);
        e3.register(sb);
    }

    public void link(StringBuilder sb) {
        sb.append(id).append(" -- ");
        sb.append(names1.id);
        sb.append("\n");

        sb.append(id).append(" -- ");
        sb.append(e3.id);
        sb.append("\n");

        names1.link(sb);
        e3.link(sb);
    }
}
