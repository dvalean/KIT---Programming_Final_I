package edu.kit.informatik.model.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<IP> {
    private IP data = null;
    private List<Node<IP>> children = new ArrayList<>();
    private Node<IP> parent = null;

    public Node(IP data) {
        this.data = data;
    }

    public void addChildren(List<Node<IP>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public Node<IP> addChild(Node<IP> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public List<Node<IP>> getChildren() {
        return this.children;
    }

    public IP getData() {
        return data;
    }

    public void setData(IP data) {
        this.data = data;
    }

    private void setParent(Node<IP> parent) {
        this.parent = parent;
    }

    public Node<IP> getParent() {
        return parent;
    }
}
