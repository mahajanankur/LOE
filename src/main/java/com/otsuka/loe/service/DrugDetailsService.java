package com.otsuka.loe.service;

import java.util.List;
import java.util.Set;

import com.otsuka.loe.model.DrugDetailsInfo;

public interface DrugDetailsService {
	public abstract void saveDrugDetails(DrugDetailsInfo drugDetails);

	public abstract Set<String> getListOfDrugsNames(String drugGroup);

	public abstract Set<String> getListOfDrugsStrengths(String drugName);

	public abstract List<DrugDetailsInfo> getListOfDrugs();

	public abstract Set<String> getGroupNameList();
}
