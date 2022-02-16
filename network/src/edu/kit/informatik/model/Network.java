package edu.kit.informatik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.kit.informatik.resources.ExceptionMessage;

public class Network {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String SEPARATOR = " ";
    private static final int ROOT_INDEX = 0;

    private TreeNode<IP> network;

    public Network(final IP root, final List<IP> children) {
        network = new TreeNode<IP>(root);
        children.stream().forEach(each -> network.addChild(new TreeNode<IP>(each)));
    }

    public Network(final String bracketNotation) throws ParseException {
        if (bracketNotation.isEmpty()) {
            throw new ParseException(ExceptionMessage.EMPTY_NETWORK.toString());
        }

        this.network = fromString(bracketNotation);

        if (this.network == null) {
            throw new ParseException(ExceptionMessage.INVALID_NETWORK.toString());
        }
    }

    private TreeNode<IP> fromString(String s) throws ParseException {
        TreeNode<IP> network = null;
        String strNetwork = s;

        // make it cleaner
        // firstIndex - inclusive & lastIndex - exclusive => thus adding the +1;
        network = subTree(strNetwork.substring(strNetwork.lastIndexOf(OPEN_BRACKET) + 1,
                strNetwork.indexOf(CLOSE_BRACKET, strNetwork.lastIndexOf(OPEN_BRACKET))));
        strNetwork = strNetwork.replace(
                strNetwork.substring(strNetwork.lastIndexOf(OPEN_BRACKET),
                        strNetwork.indexOf(CLOSE_BRACKET, strNetwork.lastIndexOf(OPEN_BRACKET)) + 1),
                subTree(strNetwork.substring(strNetwork.lastIndexOf(OPEN_BRACKET) + 1,
                        strNetwork.indexOf(CLOSE_BRACKET, strNetwork.lastIndexOf(OPEN_BRACKET)))).getData().toString());

        if (strNetwork.contains(OPEN_BRACKET) || strNetwork.contains(CLOSE_BRACKET)) {
            return null;
        }

        return network;
    }

    private TreeNode<IP> subTree(String s) throws ParseException {
        String networkIPs[] = s.split(" ");
        IP root = new IP(networkIPs[ROOT_INDEX]);
        List<IP> children = new ArrayList<>();

        for (int i = 1; i < networkIPs.length; i++) {
            children.add(new IP(networkIPs[i]));
        }

        Network network = new Network(root, children);
        return network.getTree();
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
        TreeNode<IP> start = findNode(root, this.network);
        String result = null;

        if (!start.getChildren().isEmpty()) {
            result = OPEN_BRACKET + start.getData().toString() + SEPARATOR
                    + start.getChildren().stream().map(each -> toString(each.getData())).collect(Collectors.joining(SEPARATOR))
                    + CLOSE_BRACKET;
        } else {
            result = start.getData().toString();
        }

        return result;
    }

    private TreeNode<IP> findNode(IP ip, TreeNode<IP> root) {
        TreeNode<IP> temp = root;
        if (temp.getData().compareTo(ip) == 0) {
            return temp;
        }

        while (!temp.getChildren().isEmpty()) {
            temp.getChildren().stream().forEach(each -> findNode(ip, each));
            return null;
        }

        return null;
    }

    public TreeNode<IP> getTree() {
        return this.network;
    }
}
