package edu.kit.informatik.model;

/**
 * Represents a ParseException.
 * An extention of Exception for invalid parsing.
 * 
 * @author uiltc
 * @version 1.0
 */
public class ParseException extends Exception {
    /**
     * Creates a new instance of ParseException.
     * 
     * @param message String value that explains the error
     */
    public ParseException(String message) {
        super(message);
    }
}
