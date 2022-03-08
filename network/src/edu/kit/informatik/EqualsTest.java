package edu.kit.informatik;

import edu.kit.informatik.model.IP;
import edu.kit.informatik.model.ParseException;
import edu.kit.informatik.model.network.Network;

public class EqualsTest {
    public static void main(String[] args) throws ParseException{
        IP root = new IP("85.193.148.81");
        Network net1 = new Network("(85.193.148.81 (141.255.1.133 122.117.67.158 0.146.197.108) 34.49.145.239 (231.189.0.127 77.135.84.171 39.20.222.120 252.29.23.0 116.132.83.77))");
        System.out.println(net1.toString(root));

        Network net2 = new Network("(77.135.84.171 (231.189.0.127 39.20.222.120 252.29.23.0 116.132.83.77 (85.193.148.81 (141.255.1.133 122.117.67.158 0.146.197.108) 34.49.145.239)))");
        System.out.println(net2.toString(root));

        System.out.println(net1.equals(net2));
        System.out.println(net1.hashCode() + " " + net2.hashCode());
    }
}
