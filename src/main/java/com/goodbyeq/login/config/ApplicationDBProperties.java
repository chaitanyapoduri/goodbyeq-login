package com.goodbyeq.login.config;

public class ApplicationDBProperties {

	private String connectionClass;
	private String connectionHost;
	private String connectionPort;
	private String connectionUser;
	private String connectionPassword;
	private String connectionURL;

	public String getConnectionHost() {
		return connectionHost;
	}

	public void setConnectionHost(String connectionHost) {
		this.connectionHost = connectionHost;
	}

	public String getConnectionPort() {
		return connectionPort;
	}

	public void setConnectionPort(String connectionPort) {
		this.connectionPort = connectionPort;
	}

	public String getConnectionUser() {
		return connectionUser;
	}

	public void setConnectionUser(String connectionUser) {
		this.connectionUser = connectionUser;
	}

	public String getConnectionPassword() {
		return connectionPassword;
	}

	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	public String getConnectionClass() {
		return connectionClass;
	}

	public void setConnectionClass(String connectionClass) {
		this.connectionClass = connectionClass;
	}

	public String getConnectionURL() {
		return connectionURL;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

}
