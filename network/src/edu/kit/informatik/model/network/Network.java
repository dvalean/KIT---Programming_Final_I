package edu.kit.informatik.model.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;
import edu.kit.informatik.model.network.graph.Edge;
import edu.kit.informatik.model.network.graph.Graph;
import edu.kit.informatik.model.network.tree.Tree;

public class Network {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String SEPARATOR = " ";

    private final Graph graph;

    public Network(final IP root, final List<IP> children) {
        this.graph = new Graph(compileEdges(root, children));
    }

    public Network(final String bracketNotation) throws ParseException {
        this.graph = new Graph(fromString(bracketNotation));
    }

    private List<Edge> compileEdges(IP root, List<IP> children) {
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < children.size(); i++) {
            edges.add(new Edge(root, children.get(i)));
        }

        return edges;
    }

    private List<Edge> fromString(String s) throws ParseException {
        int i = 1;
        List<Edge> edges = new ArrayList<>();
        IP root = new IP(s.substring(i, ipLength(s)));
        i += ipLength(s);

        while (i < s.length()) {
            if (s.charAt(i) == OPEN_BRACKET.charAt(0)) {
                String temp = s.substring(i);
                edges.addAll(fromString(findInnerString(temp)));
                edges.add(new Edge(root, edges.get(edges.size() - 1).getSource()));
                i += findInnerString(temp).length() + 2;
            } else {
                String temp = s.substring(i);
                edges.add(new Edge(root, new IP(s.substring(i, ipLength(temp) + i))));
                i += ipLength(temp);
            }

            i++;
        }

        return edges;
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
                return s.substring(0, i + 1);
            }
        }

        // if flag not 0 there is an error

        return s.substring(1, s.length());
    }

    private int ipLength(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == SEPARATOR.charAt(0) || s.charAt(i) == CLOSE_BRACKET.charAt(0)) {
                return i;
            }
        }

        return s.length();
    }

    public boolean add(final Network subnet) {
        Graph graph = new Graph(this.graph.getEdges());
        graph.addEdges(subnet.graph.getEdges());

        if (graph.isCyclic()) {
            return false;
        }

        this.graph.addEdges(subnet.graph.getEdges());
        return true;
    }

    public List<IP> list() {
        List<IP> ips = new ArrayList<>();
        Set<IP> vertices = graph.getAdjMap().keySet();
        for (IP vertex : vertices) {
            ips.add(vertex);
        }

        Collections.sort(ips);
        return ips;
    }

    public boolean connect(final IP ip1, final IP ip2) {
        this.graph.addEdge(new Edge(ip1, ip2));
        return true;
    }

    public boolean disconnect(final IP ip1, final IP ip2) {
        this.graph.removeEdge(ip1, ip2);
        return true;
    }

    public boolean contains(final IP ip) {
        Set<IP> vertices = graph.getAdjMap().keySet();
        for (IP vertex : vertices) {
            if (vertex.compareTo(ip) == 0) {
                return true;
            }
        }

        return false;
    }

    public int getHeight(final IP root) {
        Set<IP> vertices = graph.getAdjMap().keySet();
        for (IP vertex : vertices) {
            if (vertex.compareTo(root) == 0) {
                Tree tree = new Tree(vertex, graph);
                return tree.getHeight(tree.getRoot());
            }
        }

        return 0;
    }

    public List<List<IP>> getLevels(final IP root) {
        Set<IP> vertices = graph.getAdjMap().keySet();
        for (IP vertex : vertices) {
            if (vertex.compareTo(root) == 0) {
                Tree tree = new Tree(vertex, graph);
                return tree.getLevels(tree.getRoot(), 0, new ArrayList<>());
            }
        }

        return null;
    }

    public List<IP> getRoute(final IP start, final IP end) {
        return null;
    }

    public String toString(IP root) {
        Set<IP> vertices = graph.getAdjMap().keySet();
        for (IP vertex : vertices) {
            if (vertex.compareTo(root) == 0) {
                Tree tree = new Tree(vertex, graph);
                return tree.toStringRecursion(tree.getRoot());
            }
        }

        return null;
    }
}
