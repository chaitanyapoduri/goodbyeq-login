package com.goodbyeq.login.exception;

public class MySqlPoolableException  extends Exception {

	private static final long serialVersionUID = 1L;

	public MySqlPoolableException(final String msg, Exception e) {
        super(msg, e);
    }
}