package com.teamanime.Propra.Exceptions;

public class PropraException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
     * Constructeurs
     */
    public PropraException( String message ) {
        super( message );
    }

    public PropraException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PropraException( Throwable cause ) {
        super( cause );
    }

}
