package edu.kit.informatik.model.network.graph;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.model.IP;

/**
 * Represents an Edge of a Graph.
 * 
 * @author uiltc
 * @version 1.0
 */
public class Edge {
    private final List<IP> ip;

    /**
     * Creates a new instance of an Edge.
     * 
     * @param source      IP value, where the Edge begins
     * @param destination IP value, where the Edge ends
     */
    public Edge(IP source, IP destination) {
        this.ip = new ArrayList<>();
        this.ip.add(source);
        this.ip.add(destination);
    }

    /**
     * Gets the IPs that are connected by the Edge.
     * 
     * @return a list of IPs
     */
    public List<IP> getNodes() {
        return this.ip;
    }

    /**
     * Gets the destination of the Edge.
     * 
     * @param source IP value where the Edge starts
     * @return IP value where the Edge will end
     */
    public IP getDestination(IP source) {
        for (IP ip : this.ip) {
            if (!ip.equals(source)) {
                return ip;
            }
        }

        return null;
    }

    @Override
    public int hashCode() {
        return ip.get(0).hashCode() + ip.get(1).hashCode();
    }

    // Checks if this instance of Edge is equal with another Edge
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        Edge edge = (Edge) o;
        for (int i = 0; i < this.ip.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < edge.ip.size(); j++) {
                if (this.ip.get(i).equals(edge.ip.get(j))) {
                    flag = true;
                }
            }

            if (!flag) {
                return false;
            }
        }

        return true;
    }
}
