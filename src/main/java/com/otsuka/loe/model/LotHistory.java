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
@Table(name = "lothistory")
public class LotHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String reqId;
	private String strength;
	private String drugName;
	private String lotNumbers;
	private String complainDesc;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date dateOfComplain;
	private String lastModifiedByUser;
	private String groupName;

	@Column(name = "groupname")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

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

	@Column(name = "reqid")
	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	@Column(name = "strength")
	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	@Column(name = "drugname")
	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	@Column(name = "lotnumbers")
	public String getLotNumbers() {
		return lotNumbers;
	}

	public void setLotNumbers(String lotNumbers) {
		this.lotNumbers = lotNumbers;
	}

	@Column(name = "complaindesc")
	public String getComplainDesc() {
		return complainDesc;
	}

	public void setComplainDesc(String complainDesc) {
		this.complainDesc = complainDesc;
	}

	@Column(name = "dateofcomplain")
	public Date getDateOfComplain() {
		return dateOfComplain;
	}

	public void setDateOfComplain(Date dateOfComplain) {
		this.dateOfComplain = dateOfComplain;
	}

	@Column(name = "lastmodifiedbyuser")
	public String getLastModifiedByUser() {
		return lastModifiedByUser;
	}

	public void setLastModifiedByUser(String lastModifiedByUser) {
		this.lastModifiedByUser = lastModifiedByUser;
	}

	@Column(name = "lastmodifieddate")
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}