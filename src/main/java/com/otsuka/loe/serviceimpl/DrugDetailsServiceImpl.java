package com.otsuka.loe.serviceimpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otsuka.loe.dao.DrugDetailsDao;
import com.otsuka.loe.model.DrugDetailsInfo;
import com.otsuka.loe.service.DrugDetailsService;

@Service("drugDetailsService")
@Transactional
public class DrugDetailsServiceImpl implements DrugDetailsService {

	@Autowired
	DrugDetailsDao drugcDetailsDao;

	public DrugDetailsDao getDrugDetailsDao() {
		return drugcDetailsDao;
	}

	public void setDrugDetailsDao(DrugDetailsDao drugDetailsDao) {
		this.drugcDetailsDao = drugDetailsDao;
	}

	public void saveDrugDetails(DrugDetailsInfo drugDetails) {
		drugcDetailsDao.saveDrugDetails(drugDetails);
	}

	public Set<String> getListOfDrugsNames(String drugGroup) {
		return drugcDetailsDao.getListOfDrugsNames(drugGroup);
	}

	public Set<String> getListOfDrugsStrengths(String drugName) {
		return drugcDetailsDao.getListOfDrugsStrengths(drugName);
	}

	public List<DrugDetailsInfo> getListOfDrugs() {
		return drugcDetailsDao.getListOfDrugs();
	}

	public Set<String> getGroupNameList() {
		return drugcDetailsDao.getGroupNameList();
	}
}
