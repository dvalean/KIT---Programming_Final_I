package edu.kit.informatik.model.network.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;

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
        IP source = edge.getSource();
        IP destination = edge.getDestination();
        Set<IP> vertices = adjMap.keySet();

        int flag = 0;
        for (IP vertex : vertices) {
            if (vertex.equals(edge.getSource())) {
                source = vertex;
                flag = 1;
            }
        }

        if (flag == 0) {
            adjMap.put(edge.getSource(), new ArrayList<>());
        }

        flag = 0;
        for (IP vertex : vertices) {
            if (vertex.equals(edge.getDestination())) {
                destination = vertex;
                flag = 1;
            }
        }

        if (flag == 0) {
            adjMap.put(edge.getDestination(), new ArrayList<>());
        }

        adjMap.get(source).add(edge.getDestination());
        adjMap.get(destination).add(edge.getSource());

        this.edges.add(edge);
    }

    public void removeEdge(IP ip1, IP ip2) {
        edges.removeIf(i -> i.getSource().equals(ip1) && i.getDestination().equals(ip2));

        IP ip = null;
        Set<IP> vertices = adjMap.keySet();
        for (IP vertex : vertices) {
            if (vertex.compareTo(ip1) == 0) {
                ip = vertex;
            }
        }

        adjMap.get(ip).remove(ip2);

        if (adjMap.get(ip).isEmpty()) {
            adjMap.remove(ip);
        }

        for (IP vertex : vertices) {
            if (vertex.compareTo(ip2) == 0) {
                ip = vertex;
            }
        }

        adjMap.get(ip).remove(ip1);

        if (adjMap.get(ip).isEmpty()) {
            adjMap.remove(ip);
        }
    }

    private boolean isCyclicUtil(IP v, List<IP> visited, IP parent) throws ParseException {
        visited.add(v);
        IP i;

        IP ip = null;
        Set<IP> vertices = adjMap.keySet();
        for (IP vertex : vertices) {
            if (vertex.compareTo(v) == 0) {
                ip = vertex;
            }
        }

        Iterator<IP> ips = adjMap.get(ip).iterator();
        while (ips.hasNext()) {
            i = ips.next();

            if (!visited.contains(i)) {
                if (isCyclicUtil(i, visited, ip)) {
                    return true;
                }
            } else if (!i.equals(parent)) {
                return true;
            }
        }

        return false;
    }

    public boolean isCyclic() throws ParseException {
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

    public List<IP> getRoute(IP start, IP end, List<IP> visited, List<IP> cpath) {
        cpath.add(start);
        visited.add(start);
        List<IP> spath = null;

        if (start.equals(end)) {
            return cpath;
        }

        for (IP ip : adjMap.get(start)) {
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

    public List<Edge> getEdges() {
        return this.edges;
    }

    public Map<IP, List<IP>> getAdjMap() {
        return this.adjMap;
    }
}
