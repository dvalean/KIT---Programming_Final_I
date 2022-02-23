package edu.kit.informatik.model.network.tree;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.model.IP;

public class Node implements Comparable<Node> {
    private IP value = null;
    private List<Node> children = new ArrayList<>();
    private Node parent = null;

    public Node(IP value) {
        this.value = value;
    }

    public void addChildren(List<Node> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public Node addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public IP getValue() {
        return this.value;
    }

    public void setValue(IP value) {
        this.value = value;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return this.parent;
    }

    @Override
    public int compareTo(Node o) {
        return this.value.compareTo(o.getValue());
    }
}
