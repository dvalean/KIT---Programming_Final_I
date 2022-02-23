package edu.kit.informatik.model.network.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.network.graph.Graph;

public class Tree {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String SEPARATOR = " ";

    private Node root;

    public Tree(IP root, Graph graph) {
        this.root = new Node(root);
        List<IP> visited = new ArrayList<>();
        this.root.addChildren(initialization(this.root, graph, visited));
    }

    private List<Node> initialization(Node root, Graph graph, List<IP> visited) {
        visited.add(root.getValue());

        IP rootIP = null;
        Set<IP> vertices = graph.getAdjMap().keySet();
        for (IP vertex : vertices) {
            if (vertex.compareTo(root.getValue()) == 0) {
                rootIP = vertex;
            }
        }

        int j = 0;
        List<Node> children = new ArrayList<>();

        for (int i = 0; i < graph.getAdjMap().get(rootIP).size(); i++) {
            if (!visited.contains(graph.getAdjMap().get(rootIP).get(i))) {
                children.add(new Node(graph.getAdjMap().get(rootIP).get(i)));
                children.get(j).setParent(root);
                children.get(j).addChildren(initialization(children.get(j), graph, visited));
                j++;
            }
        }

        Collections.sort(children);
        return children;
    }

    public int getHeight(Node root) {
        if (root.getChildren().isEmpty()) {
            return 0;
        }

        List<Integer> depth = new ArrayList<>();
        root.getChildren().stream().forEach(each -> depth.add(1));

        for (int i = 0; i < depth.size(); i++) {
            depth.set(i, depth.get(i) + getHeight(root.getChildren().get(i)));
        }

        return depth.stream().mapToInt(each -> each).max().orElseThrow(null);
    }

    public List<List<IP>> getLevels(Node root, int currentLevel, List<List<IP>> levels) {
        if (!(currentLevel < levels.size())) {
            levels.add(new ArrayList<>());
        }

        levels.get(currentLevel).add(root.getValue());

        for (Node child : root.getChildren()) {
            levels = getLevels(child, currentLevel + 1, levels);
        }

        return levels;
    }

    public String toStringRecursion(Node root) {
        String result = root.getValue().toString();
        if (root.getChildren().isEmpty()) {
            return result;
        } else {
            for (Node child : root.getChildren()) {
                result += SEPARATOR + toStringRecursion(child);
            }
        }

        return OPEN_BRACKET + result + CLOSE_BRACKET;
    }

    public Node getRoot() {
        return this.root;
    }
}