package edu.kit.informatik.model;

/**
 * Represents a ParseException.
 * An extention of Exception for invalid parsing.
 * 
 * @author uiltc
 * @version 1.0
 */
public class ParseException extends Exception {    
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String NETWORK_SEPARATOR = " ";
    private static final String IP_SEPARATOR = ".";

    /**
     * Creates a new instance of ParseException.
     * 
     * @param message String value that explains the error
     */
    public ParseException(String message) {
        super(message);
    }

    public static String getCloseBracket() {
        return CLOSE_BRACKET;
    }

    public static String getOpenBracket() {
        return OPEN_BRACKET;
    }

    public static String getNetworkSeparator() {
        return NETWORK_SEPARATOR;
    }

    public static String getIpSeparator() {
        return IP_SEPARATOR;
    }
}
