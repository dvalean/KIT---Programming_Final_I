package edu.kit.informatik.model;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.model.tree.Tree;

public class Network {
    private final List<Tree> network = new ArrayList<>();

    public Network(final IP root, final List<IP> children) {
        this.network.add(new Tree(root, children));
    }

    public Network(final String bracketNotation) throws ParseException {
        this.network.add(new Tree(bracketNotation));
    }

    public boolean add(final Network subnet) {
        subnet.network.stream().forEach(each -> this.network.add(each));
        return true;
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
        for (Tree tree : this.network) {
            if (tree.findNode(tree.getRoot(), ip) != null) {
                return true;
            }
        }

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
        for (Tree tree : this.network) {
            if (tree.getRoot().getData().compareTo(root) == 0) {
                return tree.toStringRecursion(tree.getRoot());
            }
        }

        return null;
    }
}
