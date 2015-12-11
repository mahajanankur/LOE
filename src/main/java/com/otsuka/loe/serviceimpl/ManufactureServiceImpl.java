package com.otsuka.loe.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otsuka.loe.dao.ManufactureDao;
import com.otsuka.loe.model.LoeManufactureInfo;
import com.otsuka.loe.service.ManufactureService;

@Service("manufactureService")
@Transactional
public class ManufactureServiceImpl implements ManufactureService {
	@Autowired
	ManufactureDao manufactureDao;

	public List<LoeManufactureInfo> getManufatureDataList(String drugName,
			Date date, String strength) {
		// TODO Auto-generated method stub
		return manufactureDao.getManufatureDataList(drugName, date, strength);
	}

	public List<LoeManufactureInfo> getManufatureData(String groupName, Date date) {
		// TODO Auto-generated method stub
		return manufactureDao.getManufatureData(groupName, date);
	}
	
	public List<LoeManufactureInfo> getManufatureDetails(String drugName,
			Date date) {
		// TODO Auto-generated method stub
		return manufactureDao.getManufatureDetails(drugName, date);
	}

	public void saveManInfo(LoeManufactureInfo loeManufactureInfo) {
		// TODO Auto-generated method stub
		manufactureDao.saveManInfo(loeManufactureInfo);

	}

	public List<String> getLotNumbers(String drugName, String strength) {
		// TODO Auto-generated method stub
		return manufactureDao.getLotNumbers(drugName, strength);
	}

	public List<String> getLotNumbersByDrugName(String drugName) {
		// TODO Auto-generated method stub
		return manufactureDao.getLotNumbersByDrugName(drugName);
	}

	public List<String> getDrugs() {
		// TODO Auto-generated method stub
		return manufactureDao.getDrugs();
	}

	public List<String> getStrength(String drugName) {
		// TODO Auto-generated method stub
		return manufactureDao.getStrength(drugName);
	}

	public LoeManufactureInfo checkLotNumber(String lotNumber) {
		// TODO Auto-generated method stub
		return manufactureDao.checkLotNumber(lotNumber);
	}

	

}
