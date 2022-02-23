package edu.kit.informatik.model.network.graph;

import edu.kit.informatik.model.IP;

/**
 * Represents an Edge of a Graph.
 * 
 * @author uiltc
 * @version 1.0
 */
public class Edge {
    private final IP source;
    private final IP destination;

    /**
     * Creates a new instance of an Edge.
     * 
     * @param source IP value, where the Edge begins
     * @param destination IP value, where the Edge ends
     */
    public Edge(IP source, IP destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Gets the source of the Edge.
     * 
     * @return IP value
     */
    public IP getSource() {
        return this.source;
    }

    /**
     * Gets the destination of the Edge.
     * 
     * @return IP value
     */
    public IP getDestination() {
        return this.destination;
    }
}
