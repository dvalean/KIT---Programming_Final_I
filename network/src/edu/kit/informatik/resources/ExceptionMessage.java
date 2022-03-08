package edu.kit.informatik.resources;

/**
 * Represents Exception Messages.
 * 
 * @author uiltc
 * @version 1.0
 */
public enum ExceptionMessage {
    /** Message for an invalid IPv4 address */
    INVALID_IPV4("an invalid IP was entered."),
    /** Message for a null IPv4 address */
    NULL_IPV4("a null IP was entered."),
    /** Message for invalid network values */
    INVALID_NETWORK_VALUES("invalid root or children were entered."),
    /** Message for null network values */
    NULL_NETWORK_VALUES("a null root or child for a network was entered."),
    /** Message for an invalid bracket notation */
    INVALID_BRACKET_NOTATION("an invalid bracket notation for a network was entered."),
    /** Message for a null bracket notation */
    NULL_BRACKET_NOTATION("a null bracket notaion for a network was entered.");

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
