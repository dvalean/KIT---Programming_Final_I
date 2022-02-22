package edu.kit.informatik.model.network.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.informatik.model.IP;

public class Graph {
    private Map<IP, List<IP>> adjMap = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();

    public Graph(List<Edge> edges) {
        addEdges(edges);
    }

    public void addEdges(List<Edge> edges) {
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    public void addEdge(Edge edge) {
        if (!adjMap.containsKey(edge.getSource())) {
            adjMap.put(edge.getSource(), new ArrayList<>());
        }

        if (!adjMap.containsKey(edge.getDestination())) {
            adjMap.put(edge.getDestination(), new ArrayList<>());
        }

        adjMap.get(edge.getSource()).add(edge.getDestination());
        adjMap.get(edge.getDestination()).add(edge.getSource());

        this.edges.add(edge);
    }

    public void removeEdge(IP ip1, IP ip2) {
        edges.removeIf(i -> i.getSource().equals(ip1) && i.getDestination().equals(ip2));
        adjMap.get(ip1).remove(ip2);
        adjMap.get(ip2).remove(ip1);
    }

    private boolean isCyclicUtil(IP v, List<IP> visited, IP parent) {
        visited.add(v);
        IP i;

        Iterator<IP> ips = adjMap.get(v).iterator();
        while (ips.hasNext()) {
           i = ips.next();
           
           if (!visited.contains(i)) {
               if (isCyclicUtil(i, visited, v)) {
                   return true;
               }
           } else if (i != parent) {
                return true;
           }
        }

        return false;
    }

    public boolean isCyclic() {
        List<IP> visited = new ArrayList<>();
        
        Set<IP> vertices = adjMap.keySet();
        for (IP vertex : vertices) {
            if (!visited.contains(vertex)) {
                if (isCyclicUtil(vertex, visited, null)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public Map<IP, List<IP>> getAdjMap() {
        return this.adjMap;
    }
}
