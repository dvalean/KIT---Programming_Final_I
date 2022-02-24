package edu.kit.informatik.model.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;
import edu.kit.informatik.model.network.graph.Edge;
import edu.kit.informatik.model.network.graph.Graph;
import edu.kit.informatik.resources.ExceptionMessage;

/**
 * Represents a Network.
 * 
 * @author uiltc
 * @version 1.0
 */
public class Network {

    private final Graph graph;

    /**
     * Creates a new instance of a Network with two levels.
     * 
     * @param root     IP value that is connected to all children
     * @param children a list of IPs that are connected to the root
     */
    public Network(final IP root, final List<IP> children) {
        // Create edges between the root and every child
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            edges.add(new Edge(root, children.get(i)));
        }

        this.graph = new Graph(edges);
    }

    public Network(final String bracketNotation) throws ParseException {
        this.graph = new Graph(fromString(findInnerString(bracketNotation)));
    }

    private List<Edge> fromString(String bracket) throws ParseException {
        List<Edge> edges = new ArrayList<>();

        int i = 0;
        IP source = new IP(bracket.substring(i, ipLength(bracket)));
        i = ipLength(bracket) + 1;

        while (i < bracket.length()) {
            if (bracket.charAt(i) == ParseException.getOpenBracket().charAt(0)) {
                String remaining = bracket.substring(i);

                String newBracket = findInnerString(remaining);
                edges.addAll(fromString(newBracket));
                IP destination = edges.get(edges.size() - 1).getNodes().get(0);
                edges.add(new Edge(source, destination));

                i += newBracket.length() + 2;
            } else {
                String remaining = bracket.substring(i);
                IP destination = new IP(bracket.substring(i, ipLength(remaining) + i));

                edges.add(new Edge(source, destination));
                i += ipLength(remaining);
            }

            i++;
        }

        return edges;
    }

    private String findInnerString(String s) throws ParseException {
        int flag = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ParseException.getOpenBracket().charAt(0)) {
                flag++;
            } else if (s.charAt(i) == ParseException.getCloseBracket().charAt(0)) {
                flag--;
            }

            if (flag == 0) {
                return s.substring(1, i);
            }
        }

        throw new ParseException(ExceptionMessage.INVALID_BRACKET_NOTATION.toString());
    }

    private int ipLength(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ParseException.getNetworkSeparator().charAt(0)
                    || s.charAt(i) == ParseException.getCloseBracket().charAt(0)) {
                return i;
            }
        }

        return s.length();
    }

    public boolean add(final Network subnet) throws ParseException {
        Graph graph = new Graph(this.graph.getEdges());
        graph.addEdges(subnet.graph.getEdges());

        if (graph.isLooped(graph.getEdges().get(0).getNodes().get(0), null, new ArrayList<>())) {
            return false;
        }

        this.graph.addEdges(subnet.graph.getEdges());
        return true;
    }

    public List<IP> list() {
        List<IP> ips = new ArrayList<>();

        for (Edge edge : this.graph.getEdges()) {
            for (IP ip : edge.getNodes()) {
                if (!ips.contains(ip)) {
                    ips.add(ip);
                }
            }
        }

        Collections.sort(ips);
        return ips;
    }

    public boolean connect(final IP ip1, final IP ip2) {
        Graph graph = new Graph(this.graph.getEdges());
        graph.addEdge(new Edge(this.graph.contains(ip1), this.graph.contains(ip2)));

        if (graph.isLooped(graph.getEdges().get(0).getNodes().get(0), null, new ArrayList<>())) {
            return false;
        }

        this.graph.addEdge(new Edge(this.graph.contains(ip1), this.graph.contains(ip2)));
        return true;
    }

    public boolean disconnect(final IP ip1, final IP ip2) {
        List<IP> ip = List.of(this.graph.contains(ip1), this.graph.contains(ip2));

        if (ip.get(0) != null && ip.get(1) != null) {
            return this.graph.removeEdge(ip.get(0), ip.get(1));
        }

        return false;
    }

    public boolean contains(final IP ip) {
        return (this.graph.contains(ip) != null) ? true : false;
    }

    public int getHeight(final IP root) {
        IP ip = this.graph.contains(root);

        if (ip != null) {
            return this.graph.getHeight(ip, new ArrayList<>());
        }

        return 0;
    }

    public List<List<IP>> getLevels(final IP root) {
        IP ip = this.graph.contains(root);

        if (ip != null) {
            return this.graph.getLevels(ip, new ArrayList<>(), 0, new ArrayList<>());
        }

        return null;
    }

    public List<IP> getRoute(final IP start, final IP end) {
        List<IP> ip = List.of(this.graph.contains(start), this.graph.contains(end));

        if (ip.get(0) != null && ip.get(1) != null) {
            return this.graph.getRoute(ip.get(0), ip.get(1), new ArrayList<>(), new ArrayList<>());
        }

        return null;
    }

    public String toString(IP root) {
        root = this.graph.contains(root);

        if (root != null) {
            return this.graph.toString(root, new ArrayList<>());
        }

        return null;
    }
}
