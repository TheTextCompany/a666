package com.freakurl.engine;

/**
 * Exception, die geschmissen wird, wenn in der Engine ein Fehler auftritt.
 * 
 * @author Jakob
 */
public class EngineException extends Exception {
    public EngineException() {};
    public EngineException(String message) {
        super(message);
    }
}
