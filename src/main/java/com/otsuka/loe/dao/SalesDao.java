package com.otsuka.loe.dao;



import java.util.Date;
import java.util.List;

import com.otsuka.loe.model.LoeSalesInfo;

public interface SalesDao {

	public abstract void saveSalesInfo(LoeSalesInfo loeSalesInfo);
	
	public abstract List<LoeSalesInfo>getSaleDataList(String drugName, Date date,String strength);
	public abstract List<LoeSalesInfo>getSaleData(String drugName, Date date);
	public abstract List<LoeSalesInfo>getSaleDetails(String groupName, Date date);
	
}
