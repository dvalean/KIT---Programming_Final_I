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
        if (root == null || children == null) {
            throw new RuntimeException(ExceptionMessage.NULL_NETWORK_VALUES.toString());
        }

        if (children.contains(root)
                || children.stream().distinct().toArray().length != children.stream().toArray().length) {
            throw new RuntimeException(ExceptionMessage.INVALID_NETWORK_VALUES.toString());
        }

        // Create edges between the root and every child
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            edges.add(new Edge(root, children.get(i)));
        }

        this.graph = new Graph(edges);
    }

    /**
     * Creates a new instance of a Network from BracketNotaion.
     * 
     * @param bracketNotation String value of a Network
     * @throws ParseException if the String value isn't a valid Network
     */
    public Network(final String bracketNotation) throws ParseException {
        if (bracketNotation == null) {
            throw new ParseException(ExceptionMessage.NULL_BRACKET_NOTATION.toString());
        }

        this.graph = new Graph(fromString(findInnerString(bracketNotation)));

        if (this.graph.getEdges().size() == 0) {
            throw new ParseException(ExceptionMessage.INVALID_BRACKET_NOTATION.toString());
        }
    }

    // Main method to converting from String to Graph
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

    // Finds the inner String between brackets
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

    // Determines the length of an IP in a String
    private int ipLength(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ParseException.getNetworkSeparator().charAt(0)
                    || s.charAt(i) == ParseException.getCloseBracket().charAt(0)) {
                return i;
            }
        }

        return s.length();
    }

    /**
     * Determines if a sub Network can be added to this Network.
     * 
     * @param subnet the sub Network to be added
     * @return true - if the subnet can and was added, false - otherwise
     * @throws ParseException
     */
    public boolean add(final Network subnet) throws ParseException {
        if (subnet == null || this.graph.hasSubgraph(subnet.graph)) {
            return false;
        }

        Graph graph = new Graph(this.graph.getEdges());
        graph.addEdges(subnet.graph.getEdges());

        if (graph.isLooped(graph.getEdges().get(0).getNodes().get(0), null, new ArrayList<>())) {
            return false;
        }

        this.graph.addEdges(subnet.graph.getEdges());
        return true;
    }

    /**
     * Gets all the IPs in this Network in a sorted list form.
     * 
     * @return a list of IPs
     */
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

    /**
     * Connects two IPs in the Network.
     * 
     * @param ip1 first IP
     * @param ip2 second IP
     * @return true - if the IPs can and were connected, false - otherwise
     */
    public boolean connect(final IP ip1, final IP ip2) {
        if (this.graph.contains(ip1) == null || this.graph.contains(ip2) == null || ip1.equals(ip2)) {
            return false;
        }

        List<IP> ips = List.of(this.graph.contains(ip1), this.graph.contains(ip2));

        if (this.graph.hasSubgraph(new Graph(List.of(new Edge(ips.get(0), ips.get(1)))))) {
            return false;
        }

        Graph graph = new Graph(this.graph.getEdges());
        graph.addEdge(new Edge(ips.get(0), ips.get(1)));

        if (graph.isLooped(graph.getEdges().get(0).getNodes().get(0), null, new ArrayList<>())) {
            return false;
        }

        this.graph.addEdge(new Edge(ips.get(0), ips.get(1)));
        return true;
    }

    /**
     * Disconnects two IPs in the Network.
     * 
     * @param ip1 first IP
     * @param ip2 second IP
     * @return true - if the IPs can and were disconnected (deletes the IPs that
     *         have no more connections), false - otherwise
     */
    public boolean disconnect(final IP ip1, final IP ip2) {
        if (this.graph.contains(ip1) == null || this.graph.contains(ip2) == null) {
            return false;
        }

        List<IP> ips = List.of(this.graph.contains(ip1), this.graph.contains(ip2));

        Graph graph = new Graph(this.graph.getEdges());
        graph.removeEdge(ips.get(0), ips.get(1));
        if (graph.getEdges().isEmpty()) {
            return false;
        }

        return this.graph.removeEdge(ips.get(0), ips.get(1));
    }

    /**
     * Determines if the Network containes an IP.
     * 
     * @param ip the IP to verify
     * @return true - if it the IP is contained and false - if the IP is not
     *         contained
     */
    public boolean contains(final IP ip) {
        return (this.graph.contains(ip) != null) ? true : false;
    }

    /**
     * Determines the height of the Network from a specific IP.
     * 
     * @param root where the calculation begins
     * @return integer value of the height
     */
    public int getHeight(final IP root) {
        if (root != null) {
            IP ip = this.graph.contains(root);

            if (ip != null) {
                return this.graph.getHeight(ip, new ArrayList<>());
            }
        }

        return 0;
    }

    /**
     * Gets the levels of the Network.
     * 
     * @param root where the calculation begins
     * @return a list of lists* of IPs where every list* represents another level
     */
    public List<List<IP>> getLevels(final IP root) {
        return (this.graph.contains(root) != null)
                ? this.graph.getLevels(this.graph.contains(root), new ArrayList<>(), 0, new ArrayList<>())
                : List.of();
    }

    /**
     * Gets the route from IP (start) to IP (end).
     * 
     * @param start where the route begins
     * @param end   where the route ends
     * @return a list of IPs to get from start to end (null if any of the IPs are
     *         not in the graph)
     */
    public List<IP> getRoute(final IP start, final IP end) {
        return (this.graph.contains(start) != null && this.graph.contains(end) != null)
                ? this.graph.getRoute(this.graph.contains(start), this.graph.contains(end), new ArrayList<>(),
                        new ArrayList<>())
                : List.of();
    }

    /**
     * Converts the Network to BracketNotation String.
     * 
     * @param root the root of the Network
     * @return String value (BracketNotation)
     */
    public String toString(IP root) {
        if (this.graph.contains(root) != null) {
            return this.graph.toString(this.graph.contains(root), new ArrayList<>());
        }

        return "";
    }
}
