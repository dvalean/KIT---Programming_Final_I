package edu.kit.informatik.model.network.tree;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.model.IP;

/**
 * Represents a Node of a Tree.
 * 
 * @author uiltc
 * @version 1.0
 */
public class Node implements Comparable<Node> {
    private final IP value;

    private Node parent = null;
    private final List<Node> children = new ArrayList<>();

    /**
     * Creates a new instance of a Node.
     * 
     * @param value the IP that is stored in the Node
     */
    public Node(IP value) {
        this.value = value;
    }

    /**
     * Adds children to this Node.
     * 
     * @param children list of Nodes that need to be added
     */
    public void addChildren(List<Node> children) {
        children.stream().forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    /**
     * Adds one child to this Node.
     * 
     * @param child the child that needs to be added
     * @return Node value of the added child
     */
    public Node addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    /**
     * Gets the children of this Node.
     * 
     * @return list of Nodes of all the children
     */
    public List<Node> getChildren() {
        return this.children;
    }

    /**
     * Gets the IP value of this Node.
     * 
     * @return the IP value of the Node
     */
    public IP getValue() {
        return this.value;
    }

    /**
     * Sets the parent of this Node.
     * 
     * @param parent the Node that needs to be set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent of this Node.
     * 
     * @return Node value of the parent
     */
    public Node getParent() {
        return this.parent;
    }

    // Compares this instance of Node with another Node
    // It compares the IP values of both Nodes, thus we can use "compareTo"
    // between two IPs
    @Override
    public int compareTo(Node o) {
        return this.value.compareTo(o.getValue());
    }
}
