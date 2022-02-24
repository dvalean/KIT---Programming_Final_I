package edu.kit.informatik.resources;

/**
 * Represents Exception Messages.
 * 
 * @author uiltc
 * @version 1.0
 */
public enum ExceptionMessage {
    INVALID_IPV4("an invalid IP was entered."),
    INVALID_BRACKET_NOTATION("an invalid bracket notation for a network was entered.");

    private static final String PREFIX = "Error, ";
    private final String message;

    private ExceptionMessage(String message) {
        this.message = message;
    }

    // Converts an ExceptionMassage to String
    @Override
    public String toString() {
        return PREFIX + this.message;
    }
}
