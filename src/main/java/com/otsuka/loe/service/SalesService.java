package com.otsuka.loe.service;

import java.util.Date;
import java.util.List;


import com.otsuka.loe.model.LoeSalesInfo;



public interface SalesService {

	public void saveSalesInfo(LoeSalesInfo loeSalesInfo);
	public List<LoeSalesInfo>getSaleDataList(String drugName,Date date,String strength);
	public List<LoeSalesInfo>getSaleData(String drugName,Date date);
	public List<LoeSalesInfo>getSaleDetails(String groupName,Date date);
}
