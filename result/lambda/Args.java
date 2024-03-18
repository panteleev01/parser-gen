import java.util.ArrayList;
import java.util.List;

public class Args {

    public List<String> args = new ArrayList<>();

    public Args () {

    }

    public Args add(String arg) {
        args.add(arg);
        return this;
    }

    @Override
    public String toString() {
        return "Args{" +
                "args=" + args +
                '}';
    }

    String id = "args";

    public void register(StringBuilder sb) {
        sb.append("args [label=\"args\"]");
        for (var arg: args) {
            sb.append(arg);
            sb.append(" [label=\"");
            sb.append(arg);
            sb.append("\"]\n");
        }
    }

    public void link(StringBuilder sb) {
        for (var arg: args) {
            sb.append("args -- ");
            sb.append(arg);
            sb.append("\n");
        }
    }
}
