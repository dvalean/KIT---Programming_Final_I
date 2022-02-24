package edu.kit.informatik;

import java.util.List;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;
import edu.kit.informatik.model.network.Network;

public class PrivateTest {
    public static void main(String[] args) throws ParseException {
        // network: FromString -> toString
        IP root = new IP("122.117.67.158");
        Network network = new Network("(122.117.67.158 141.255.1.133 0.146.197.108)");
        System.out.println(network.toString(root));

        root = new IP("122.117.67.158");
        network = new Network("(122.117.67.158 (141.255.1.133 0.146.197.108))");
        System.out.println(network.toString(root));

        root = new IP("252.29.23.0");
        network = new Network("(231.189.0.127 252.29.23.0" + " 116.132.83.77 39.20.222.120 77.135.84.171)");
        System.out.println(network.toString(root));

        root = new IP("85.193.148.81");
        network = new Network("(85.193.148.81 34.49.145.239 (141.255.1.133 0.146.197.108"
                + " 122.117.67.158) (231.189.0.127 39.20.222.120"
                + " 77.135.84.171 116.132.83.77 252.29.23.0))");
        System.out.println(network.toString(root) + "\n");

        // network: add, connect, disconnect
        root = new IP("0.0.0.0");
        network = new Network(root, List.of(new IP("1.0.0.0"), new IP("2.0.0.0"), new IP("3.0.0.0")));
        System.out.println(network.toString(root));

        System.out.println(network.add(new Network(new IP("0.0.0.0"), List.of(new IP("4.0.0.0")))));
        System.out.println(network.toString(root));

        System.out.println(network.add(new Network(new IP("3.0.0.0"), List.of(new IP("4.0.0.0")))));
        System.out.println(network.toString(root));

        System.out.println(network.add(new Network(new IP("3.0.0.0"), List.of(new IP("6.0.0.0"), new IP("5.0.0.0")))));
        System.out.println(network.toString(root));
        
        System.out.println(network.connect(new IP("3.0.0.0"),new IP("4.0.0.0")));
        System.out.println(network.toString(root));

        System.out.println(network.disconnect(new IP("3.0.0.0"),new IP("4.0.0.0")));
        System.out.println(network.toString(root));

        System.out.println(network.disconnect(new IP("0.0.0.0"),new IP("3.0.0.0")));
        System.out.println(network.toString(root));
        root = new IP("3.0.0.0");
        System.out.println(network.toString(root));

        root = new IP("0.0.0.0");
        System.out.println(network.connect(new IP("3.0.0.0"),new IP("4.0.0.0")));
        System.out.println(network.toString(root) + "\n");

        // network: list, getLevels, getHeight, getRoute
        System.out.println(network.list().toString());
        System.out.println(network.getLevels(root).toString());
        System.out.println(network.getHeight(root));
        System.out.println(network.getRoute(new IP("2.0.0.0"), new IP("5.0.0.0")));
    }
}
