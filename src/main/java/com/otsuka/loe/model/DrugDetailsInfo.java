package com.otsuka.loe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "drugdetails")
public class DrugDetailsInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String drugName;
	private String strength;
	private String groupName;
	private String lastModifiedUser;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date lastModifiedDate;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "drugname")
	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugname) {
		this.drugName = drugname;
	}

	@Column(name = "strength")
	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	@Column(name = "groupname")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupname) {
		this.groupName = groupname;
	}

	@Column(name = "lastmodifieduser")
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastmodifieduser) {
		this.lastModifiedUser = lastmodifieduser;
	}

	@Column(name = "lastmodifieddate")
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastmodifieddate) {
		this.lastModifiedDate = lastmodifieddate;
	}

}
