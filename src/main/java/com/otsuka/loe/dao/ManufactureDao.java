package com.otsuka.loe.dao;

import java.util.Date;
import java.util.List;

import com.otsuka.loe.model.LoeManufactureInfo;

public interface ManufactureDao {
public abstract List<LoeManufactureInfo>getManufatureDataList(String drugName,Date date,String strength);
public abstract List<LoeManufactureInfo>getManufatureData(String groupName,Date date);
public abstract List<LoeManufactureInfo>getManufatureDetails(String drugName,Date date);
public abstract void saveManInfo(LoeManufactureInfo loeManufactureInfo);
public abstract List<String>getLotNumbers(String drugName,String strength);
public abstract List<String>getLotNumbersByDrugName(String drugName);
public abstract List<String>getDrugs();
public List<String> getStrength(String drugName);
public abstract LoeManufactureInfo checkLotNumber(String lotNumber);
}
