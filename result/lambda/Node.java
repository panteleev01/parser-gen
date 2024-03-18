import java.util.Random;

public class Node {

    public Node left;
    public Node right;
    public String name;

    public Node(Node left, Node right, String name) {
        this.left = left;
        this.right = right;
        this.name = name;
    }

    public Node(String name) {
        this(null, null, name);
    }

    public Integer eval() {
        if (name.equals("add")) {
            return left.eval() + right.eval();
        }
        if (name.equals("sub")) {
            return left.eval() - right.eval();
        }
        if (name.equals("mul")) {
            return left.eval() * right.eval();
        }
        if (name.equals("div")) {
            return left.eval() / right.eval();
        }
        return Integer.parseInt(name);
    }

    private String type() {
        if (left != null) return name.toUpperCase();
        return name;
    }

    String id = "";

    public void register(StringBuilder sb) {
        id = "ID" + new Random().nextLong(0, 100000000000L);
        sb.append(id).append(" [label=\"");
        sb.append(type());
        sb.append("\"]\n");

        if (left != null) {
            left.register(sb);
            right.register(sb);
        }
    }

    public void link(StringBuilder sb) {
        if (left != null) {
            sb.append(id).append(" -- ");
            sb.append(left.id);
            sb.append("\n");

            sb.append(id).append(" -- ");
            sb.append(right.id);
            sb.append("\n");

            left.link(sb);
            right.link(sb);
        }

    }

}
