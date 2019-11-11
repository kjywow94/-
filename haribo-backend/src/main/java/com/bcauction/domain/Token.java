package com.bcauction.domain;

public class Token {

	private Long id;
	private Long user_id;
	private String token;

	public Token() {}
	public Token(Long id, Long user_id, String token) {
		this.id = id;
		this.user_id = user_id;
		this.token = token;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Token [id=" + id + ", user_id=" + user_id + ", token=" + token + ", getUser_id()=" + getUser_id()
				+ ", getToken()=" + getToken() + ", getId()=" + getId() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
