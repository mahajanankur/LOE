package com.otsuka.loe.service;

import java.util.Date;
import java.util.List;

import com.otsuka.loe.model.LoeManufactureInfo;

public interface ManufactureService {
public List<LoeManufactureInfo>getManufatureDataList(String drugName,Date date,String strength);
public List<LoeManufactureInfo>getManufatureData(String groupName,Date date);
public List<LoeManufactureInfo>getManufatureDetails(String drugName,Date date);
public void saveManInfo(LoeManufactureInfo loeManufactureInfo);
public List<String>getLotNumbers(String drugName,String strength);
public List<String>getLotNumbersByDrugName(String drugName);
public List<String>getDrugs();
public List<String> getStrength(String drugName);
public LoeManufactureInfo checkLotNumber(String lotNumber);
}
