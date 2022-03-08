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

    private final List<Integer> ipValues;

    /**
     * Creates a new instance of an IP from a String value.
     * 
     * @param pointNotation ip's value represented with a String
     * @throws ParseException if the String value isn't a valid IP address
     */
    public IP(final String pointNotation) throws ParseException {
        if (pointNotation == null) {
            throw new ParseException(ExceptionMessage.NULL_IPV4.toString());
        }

        if (!isValidIP(pointNotation)) {
            throw new ParseException(ExceptionMessage.INVALID_IPV4.toString());
        }

        this.ipValues = new ArrayList<>();
        fromString(pointNotation);
    }

    // Checks the validity of an IP
    private boolean isValidIP(String ip) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    // Converts a String into an IP
    private void fromString(String s) {
        String ipStringValues[] = s.split("\\" + ParseException.getIpSeparator());

        for (String string : ipStringValues) {
            this.ipValues.add(Integer.parseInt(string));
        }
    }

    // Converts an IP into a String
    @Override
    public String toString() {
        return this.ipValues.stream().map(Object::toString)
                .collect(Collectors.joining(ParseException.getIpSeparator()));
    }

    // Compares this instance of IP with another IP
    // Returns 1 - if this is bigger, -1 - if this is smaller and 0 - if both are
    // equal
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        for (Integer integer : ipValues) {
            result = prime * result + integer.hashCode();
        }

        return result;
    }

    // Checks if this isntance of IP is equal with another IP
    // Returns true if they are equal and false otherwise
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        IP ip = (IP) o;
        if (compareTo(ip) == 0) {
            return true;
        }

        return false;
    }
}
