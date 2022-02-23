package edu.kit.informatik;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;
import edu.kit.informatik.model.network.Network;

public class PrivateTest {
    public static void main(String[] args) throws ParseException {
        IP root = new IP("122.117.67.158");
        Network network = new Network("(122.117.67.158 (141.255.1.133 0.146.197.108))");
        System.out.println(network.toString(root));
    }
}
