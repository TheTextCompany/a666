package com.freakurl.engine;

/**
 * Exception, die geschmissen wird, sollte die Engine stolpern.
 * 
 * @author
 */
public class EngineException extends Exception {
    public EngineException() {};
    public EngineException(String message) {
        super(message);
    }
}
