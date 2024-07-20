package com.edu.beans;

public class Member {
	private String id;
	private String passwd;
	private String name;
	private String mail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String toString() {
		return "Member [id=" + id + ", passwd=" + passwd + ", name=" + name + ", mail=" + mail + "]";
	}

}
