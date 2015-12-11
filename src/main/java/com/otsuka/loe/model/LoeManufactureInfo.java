package com.otsuka.loe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "loemanufactureinfo")
public class LoeManufactureInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String strength;

	private String drugName;

	private String lotNumber;

	private String groupName;

	@NumberFormat(style = Style.NUMBER)
	private int quantityReleased;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date dateOfReleased;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date dateOfManufacture;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date dateOfExpire;

	private String lastModifiedByUser;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date lastModifiedDate;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "manf_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Column(name = "lotnumber")
	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	@Column(name = "quantityreleased")
	public int getQuantityReleased() {
		return quantityReleased;
	}

	public void setQuantityReleased(int quantityReleased) {
		this.quantityReleased = quantityReleased;
	}

	@Column(name = "dateofrelease")
	public Date getDateOfReleased() {
		return dateOfReleased;
	}

	public void setDateOfReleased(Date dateOfReleased) {
		this.dateOfReleased = dateOfReleased;
	}

	@Column(name = "dateofmanufacture")
	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}

	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	@Column(name = "dateofexpire")
	public Date getDateOfExpire() {
		return dateOfExpire;
	}

	public void setDateOfExpire(Date dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
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

	/**
	 * @return the groupName
	 */
	@Column(name = "groupname")
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
