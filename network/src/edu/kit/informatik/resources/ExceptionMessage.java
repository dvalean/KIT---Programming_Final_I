package edu.kit.informatik.resources;

public enum ExceptionMessage {
    INVALID_IPV4("an invalid IP was inserted.");

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
