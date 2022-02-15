package edu.kit.informatik.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.kit.informatik.resources.ExceptionMessage;

public class IP implements Comparable<IP> {

    private static final String REGEX = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private static final String SEPARATOR = ".";

    private final List<Integer> ipValues;

    public IP(final String pointNotation) throws ParseException {
        if (!isValidIP(pointNotation)) {
            throw new ParseException(ExceptionMessage.INVALID_IPV4.toString());
        }

        this.ipValues = new ArrayList<>();
        fromString(pointNotation);
    }

    private boolean isValidIP(String IP) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(IP);
        return matcher.matches();
    }

    private void fromString(String s) {
        String ipStringValues[] = s.split("\\" + SEPARATOR);

        for (String string : ipStringValues) {
            this.ipValues.add(Integer.parseInt(string));
        }
    }

    @Override
    public String toString() {
        return this.ipValues.stream().map(Object::toString).collect(Collectors.joining(SEPARATOR));
    }
    
    // There may be a better way to compare using streams.
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
