package edu.mum.library.model;

import edu.mum.library.model.base.BaseEntityWithPrimaryKey;

public class Staff extends BaseEntityWithPrimaryKey<String> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3685593797957016882L;
	private AuthorizationLevel authorizationLevel;
	private String password;

	private String userId;

	public Staff(String userId, String password, AuthorizationLevel adminLevel) {
		super();
		this.userId = userId;
		this.password = password;
		this.authorizationLevel = adminLevel;
	}

	public AuthorizationLevel getAuthorizationLevel() {
		return authorizationLevel;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getPrimaryKey() {
		return getUserId();
	}

	public String getUserId() {
		return userId;
	}
}
