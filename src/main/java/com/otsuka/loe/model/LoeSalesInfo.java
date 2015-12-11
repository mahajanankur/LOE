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
@Table(name = "loesales")
public class LoeSalesInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String strength;

	private String drugName;

	private String groupName;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date salebydate;

	@NumberFormat(style = Style.NUMBER)
	private int quantitySold;

	@NumberFormat(style = Style.NUMBER)
	private int yearToDateSale;

	private String lastModifiedByUser;

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

	@Column(name = "salebydate")
	public Date getSalebydate() {
		return salebydate;
	}

	public void setSalebydate(Date salebydate) {
		this.salebydate = salebydate;
	}

	@Column(name = "quantitysold")
	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}

	@Column(name = "yeartodatesale")
	public int getYearToDateSale() {
		return yearToDateSale;
	}

	public void setYearToDateSale(int yearToDateSale) {
		this.yearToDateSale = yearToDateSale;
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
