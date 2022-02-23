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

    public List<Edge> getEdges() {
        return this.edges;
    }

    private void setAdjacency() {
        this.adjacency = new HashMap<>();

        for (Edge edge : this.edges) {
            for (int i = 0; i < edge.getNodes().size(); i++) {
                setAdjacent(edge.getNodes().get(i), edge.getDestination(edge.getNodes().get(i)));
            }
        }
    }

    private void setAdjacent(IP source, IP destination) {
        source = contains(source);
        destination = contains(destination);

        if (!this.adjacency.containsKey(source)) {
            this.adjacency.put(source, new ArrayList<>());
        }

        this.adjacency.get(source).add(destination);
    }

    private Map<IP, List<IP>> getAdjacency() {
        setAdjacency();
        return this.adjacency;
    }

    public IP contains(IP ip) {
        for (Edge e : this.edges) {
            for (IP i : e.getNodes()) {
                if (i.equals(ip)) {
                    return i;
                }
            }
        }

        return null;
    }

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

    public void removeEdge(IP ip1, IP ip2) {
        for (Edge edge : this.edges) {
            if ((new HashSet<>(edge.getNodes())).equals(new HashSet<>(List.of(ip1, ip2)))) {
                this.edges.remove(edge);
                return;
            }
        }
    }

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
