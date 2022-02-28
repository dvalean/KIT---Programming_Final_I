package edu.kit.informatik.model.network.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;

/**
 * Represents an undirected Graph.
 * 
 * @author uiltc
 * @version 1.0
 */
public class Graph {

    // Adjacency Map for this Graph (derived from an Adjecency List/Array)
    private Map<IP, List<IP>> adjacency;
    // List of all edges of the Graph
    private List<Edge> edges = new ArrayList<>();

    /**
     * Creates a new instance of a Graph.
     * 
     * @param edges list of Edges that this Graph should contain
     */
    public Graph(List<Edge> edges) {
        addEdges(edges);
    }

    /**
     * Adds Edges to this Graph.
     * 
     * @param edges a list of Edges to be added
     */
    public void addEdges(List<Edge> edges) {
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    /**
     * Adds an Edge to this Graph.
     * 
     * @param edge the Edge to be added
     */
    public void addEdge(Edge edge) {
        if (!this.edges.contains(edge)) {
            this.edges.add(edge);
        }
    }

    /**
     * Gets the Edges of this Graph.
     * 
     * @return a list of Edges
     */
    public List<Edge> getEdges() {
        return this.edges;
    }

    // Creates an adjacency map for this Graph
    private void setAdjacency() {
        this.adjacency = new HashMap<>();

        for (Edge edge : this.edges) {
            for (int i = 0; i < edge.getNodes().size(); i++) {
                setAdjacent(edge.getNodes().get(i), edge.getDestination(edge.getNodes().get(i)));
            }
        }
    }

    // Makes two IPs adjacent and adds them to the adjacency map
    private void setAdjacent(IP source, IP destination) {
        source = contains(source);
        destination = contains(destination);

        if (!this.adjacency.containsKey(source)) {
            this.adjacency.put(source, new ArrayList<>());
        }

        this.adjacency.get(source).add(destination);
    }

    // Gets the adjacency map of this graph
    private Map<IP, List<IP>> getAdjacency() {
        setAdjacency();
        return this.adjacency;
    }

    /**
     * Determines if this graph containes an IP.
     * 
     * @param ip that is verified
     * @return back the IP if it is contained and null otherwise
     */
    public IP contains(IP ip) {
        if (ip == null) {
            return null;
        }

        for (Edge e : this.edges) {
            for (IP i : e.getNodes()) {
                if (i.equals(ip)) {
                    return i;
                }
            }
        }

        return null;
    }

    /**
     * Recursive method that determines if this graph has a loop or not.
     * 
     * @param ip      where the search begins
     * @param root    where the IP originated
     * @param visited what IPs were already checked
     * @return true - if the graph has a loop and false - if the graph is loop-free
     */
    public boolean isLooped(IP ip, IP root, List<IP> visited) {
        visited.add(ip);
        List<IP> adjacent = getAdjacency().get(ip);
        Iterator<IP> iterator = adjacent.iterator();
        IP counter;

        while (iterator.hasNext()) {
            counter = iterator.next();

            if (!visited.contains(counter)) {
                if (isLooped(counter, ip, visited)) {
                    return true;
                }
            } else if (!counter.equals(root)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasSubgraph(Graph graph) {
        if (graph == this) {
            return true;
        }

        if (graph.edges.size() > this.edges.size()) {
            return false;
        }

        for (int i = 0; i < graph.edges.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < this.edges.size(); j++) {
                if (this.edges.get(j).equals(graph.edges.get(i))) {
                    flag = true;
                }
            }

            if (!flag) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates the height of graph (number of Edges in the longest path from the
     * root).
     * 
     * @param root    where the calculation starts
     * @param visited what IPs were already checked
     * @return integer value of the height of the graph
     */
    public int getHeight(IP root, List<IP> visited) {
        visited.add(root);
        List<Integer> height = new ArrayList<>();
        List<IP> adjacent = getAdjacency().get(root);

        for (IP ip : adjacent) {
            if (!visited.contains(ip)) {
                height.add(getHeight(ip, visited) + 1);
            }
        }

        return height.stream().mapToInt(each -> each).max().orElse(0);
    }

    /**
     * Gets the levels of this graph (where level 0 is the root).
     * 
     * @param root    where the calculation begins
     * @param visited what IPs were already checked
     * @param lvl     current level
     * @param lvls    already known levels
     * @return a list of lists* of IPs where every list* is a level
     */
    public List<List<IP>> getLevels(IP root, List<IP> visited, int lvl, List<List<IP>> lvls) {
        if (!(lvl < lvls.size())) {
            lvls.add(new ArrayList<>());
        }

        visited.add(root);
        List<IP> adjacent = getAdjacency().get(root);
        lvls.get(lvl).add(root);

        for (IP ip : adjacent) {
            if (!visited.contains(ip)) {
                lvls = getLevels(ip, visited, lvl + 1, lvls);
            }
        }

        Collections.sort(lvls.get(lvl));
        return lvls;
    }

    /**
     * Removes an Edge between two IPs.
     * 
     * @param ip1 first IP that the Edge connects
     * @param ip2 second IP that the Edge connects
     * @return true - if the removal was successful and false - if the removal was
     *         unsuccessful
     */
    public boolean removeEdge(IP ip1, IP ip2) {
        for (Edge edge : this.edges) {
            if ((new HashSet<>(edge.getNodes())).equals(new HashSet<>(List.of(ip1, ip2)))) {
                this.edges.remove(edge);
                return true;
            }
        }

        return false;
    }

    /**
     * A recursive method that determines the route from IP (start) to IP (end).
     * 
     * @param start   where the route begins
     * @param end     where the route ends
     * @param visited what IPs were already checked
     * @param cpath   current path
     * @return a list of IPs to get from start to end
     */
    public List<IP> getRoute(IP start, IP end, List<IP> visited, List<IP> cpath) {
        cpath.add(start);
        visited.add(start);
        List<IP> adjacent = getAdjacency().get(start);
        List<IP> spath = null;

        if (start.equals(end)) {
            return cpath;
        }

        for (IP ip : adjacent) {
            if (!visited.contains(ip)) {
                cpath = getRoute(ip, end, visited, cpath);
                if ((spath == null || spath.size() > cpath.size()) && cpath.contains(end)) {
                    spath = cpath;
                    return spath;
                }
            }
        }

        cpath.remove(start);
        return cpath;
    }

    /**
     * Converts a graph to BracketNotation String.
     * 
     * @param root    the root of the graph
     * @param visited what IPs were already checked
     * @return String value (BracketNotaion)
     */
    public String toString(IP root, List<IP> visited) {
        visited.add(root);
        String result = root.toString();
        List<IP> adjacent = getAdjacency().get(root);
        Collections.sort(adjacent);
        boolean hasAdjacent = false;

        for (IP ip : adjacent) {
            if (!visited.contains(ip)) {
                result += ParseException.getNetworkSeparator() + toString(ip, visited);
                hasAdjacent = true;
            }
        }

        return (hasAdjacent) ? ParseException.getOpenBracket() + result + ParseException.getCloseBracket() : result;
    }
}
