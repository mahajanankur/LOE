package com.otsuka.loe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login")
//@NamedQueries({
//		@NamedQuery(name = "getUsersList", query = "select u from Users u"),
//		@NamedQuery(name = "getUserDetailById", query = "select u from Users u where u.id = : id"),
//		@NamedQuery(name = "getUserDetailByUserNameAndPassword", query = "select u   from Users u where u.uname = :uname and u.password = :password") })
public class Users implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String uname;
	private String password;
	private String email;
	private String phone;
	private String role;

	@Column(name = "Role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "userName")
	public String getUname() {
		return uname;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "emailAddress")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phoneNumber")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
