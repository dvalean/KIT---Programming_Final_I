package edu.kit.informatik.model.network.graph;

import edu.kit.informatik.model.IP;

public class Edge {
    private IP source;
    private IP destination;

    public Edge(IP source, IP destination) {
        this.source = source;
        this.destination = destination;
    }

    public IP getSource() {
        return this.source;
    }

    public IP getDestination() {
        return destination;
    }
}
