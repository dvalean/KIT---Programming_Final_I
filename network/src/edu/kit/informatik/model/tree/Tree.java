package edu.kit.informatik.model.tree;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;

public class Tree {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String SEPARATOR = " ";

    private Node<IP> root;

    public Tree(IP root, List<IP> children) {
        this.root = new Node<IP>(root);
        children.stream().forEach(each -> this.root.addChild(new Node<IP>(each)));
    }

    public Tree(String bracketNotation) throws ParseException {
        this.root = fromString(findInnerString(bracketNotation));
    }

    private Node<IP> fromString(String s) throws ParseException {
        int i = 0;
        Node<IP> root = new Node<IP>(new IP(s.substring(i, ipLength(s))));
        i += ipLength(s);

        while (i < s.length()) {
            i++;
            if (s.charAt(i) == OPEN_BRACKET.charAt(0)) {
                root.addChild(fromString(findInnerString(s.substring(i))));
                i += findInnerString(s.substring(i)).length() + 2;
            } else {
                root.addChild(new Node<IP>(new IP(s.substring(i, ipLength(s.substring(i)) + i))));
                i += ipLength(s.substring(i));
            }
        }

        return root;
    }

    private String findInnerString(String s) {
        int flag = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == OPEN_BRACKET.charAt(0)) {
                flag++;
            } else if (s.charAt(i) == CLOSE_BRACKET.charAt(0)) {
                flag--;
            }

            if (flag == 0) {
                // begin - inclusive (next char after "("), end - exclusive (exactly where ")"
                // is seen)
                return s.substring(1, i);
            }
        }

        // if flag not 0 there is an error

        return s.substring(1, s.length());
    }

    private int ipLength(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == SEPARATOR.charAt(0)) {
                return i;
            }
        }

        return s.length();
    }

    public Node<IP> findNode(Node<IP> root, IP data) {
        Node<IP> counter = root;

        if (counter.getData().compareTo(data) == 0) {
            return counter;
        }

        if (!counter.getChildren().isEmpty()) {
            for (Node<IP> child : counter.getChildren()) {
                findNode(child, data);
            }
        }

        return null;
    }

    public int maxDepth(Node<IP> root) {
        if (root.getChildren().isEmpty()) {
            return 0;
        }

        List<Integer> depth = new ArrayList<>();
        root.getChildren().stream().forEach(each -> depth.add(1));

        for (int i = 0; i < depth.size(); i++) {
            depth.set(i, depth.get(i) + maxDepth(root.getChildren().get(i)));
        }

        return depth.stream().mapToInt(each -> each).max().orElseThrow(null);
    }

    public String toStringRecursion(Node<IP> parent) {
        String result = parent.getData().toString();
        if (parent.getChildren().isEmpty()) {
            return result;
        } else {
            for (Node<IP> child : parent.getChildren()) {
                result += SEPARATOR + toStringRecursion(child);
            }
        }

        return OPEN_BRACKET + result + CLOSE_BRACKET;
    }

    public Node<IP> getRoot() {
        return this.root;
    }
}
