package edu.kit.informatik.resources;

public enum ExceptionMessage {
    INVALID_IPV4("an invalid IP was entered."),
    INVALID_BRACKET_NOTATION("an invalid bracket notation for a network was entered.");

    private static final String PREFIX = "Error, ";
    private final String message;

    private ExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return PREFIX + this.message;
    }
}
