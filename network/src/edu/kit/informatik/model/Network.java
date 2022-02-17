package edu.kit.informatik.model;

import java.util.List;

public class Network {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String SEPARATOR = " ";

    private Node<IP> root;

    public Network(final IP root, final List<IP> children) {
        this.root = initNetwork(root, children);
    }

    public Network(final String bracketNotation) throws ParseException {
        this.root = fromString(findInnerString(bracketNotation));
    }

    private Node<IP> initNetwork(IP parent, List<IP> children) {
        Node<IP> root = new Node<IP>(parent);
        children.stream().forEach(each -> root.addChild(new Node<IP>(each)));

        return root;
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

    public boolean add(final Network subnet) {
        return false;
    }

    public List<IP> list() {
        return null;
    }

    public boolean connect(final IP ip1, final IP ip2) {
        return false;
    }

    public boolean disconnect(final IP ip1, final IP ip2) {
        return false;
    }

    public boolean contains(final IP ip) {
        return false;
    }

    public int getHeight(final IP root) {
        return 0;
    }

    public List<List<IP>> getLevels(final IP root) {
        return null;
    }

    public List<IP> getRoute(final IP start, final IP end) {
        return null;
    }

    public String toString(IP root) {
        return null;
    }
}
