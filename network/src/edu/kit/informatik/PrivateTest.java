package edu.kit.informatik;

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
        System.out.println(network.toString(root));
    }
}
