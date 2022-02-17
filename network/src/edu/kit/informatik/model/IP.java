package edu.kit.informatik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.kit.informatik.resources.ExceptionMessage;

/**
 * Represents an IPv4 address.
 * Instances of IP cannot be changed after initialization.
 * 
 * @author uiltc
 * @version 1.0
 */
public class IP implements Comparable<IP> {

    // A regular expression for the format of any IPv4 address.
    private static final String REGEX = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private static final String SEPARATOR = ".";

    private final List<Integer> ipValues;

    /**
     * Creates a new instance of an IP from a String value.
     * 
     * @param pointNotation ip's value represented with a String
     * @throws ParseException if the String value isn't a valid IP address
     */
    public IP(final String pointNotation) throws ParseException {
        if (!isValidIP(pointNotation)) {
            throw new ParseException(ExceptionMessage.INVALID_IPV4.toString());
        }

        this.ipValues = new ArrayList<>();
        fromString(pointNotation);
    }

    // Checks the validity of an IP
    private boolean isValidIP(String IP) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(IP);
        return matcher.matches();
    }

    // Converts a String into an IP
    private void fromString(String s) {
        String ipStringValues[] = s.split("\\" + SEPARATOR);

        for (String string : ipStringValues) {
            this.ipValues.add(Integer.parseInt(string));
        }
    }

    /**
     * Converts an IP into a String.
     * 
     * @return a String value
     */
    @Override
    public String toString() {
        return this.ipValues.stream().map(Object::toString).collect(Collectors.joining(SEPARATOR));
    }

    /**
     * Compares this.IP with another IP.
     * 
     * @param o the IP that this.IP is compared with
     * @return 1 - if this.IP is greater; -1 - if this.IP is smaller and 0 - both
     *         are equal.
     */
    @Override
    public int compareTo(IP o) {
        for (int i = 0; i < this.ipValues.size(); i++) {
            if (this.ipValues.get(i) > o.ipValues.get(i)) {
                return 1;
            } else if (this.ipValues.get(i) < o.ipValues.get(i)) {
                return -1;
            }
        }

        return 0;
    }
}
