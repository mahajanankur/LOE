package com.otsuka.loe.serviceimpl;



import java.util.Date;
import java.util.List;

import com.otsuka.loe.dao.SalesDao;
import com.otsuka.loe.model.LoeSalesInfo;
import com.otsuka.loe.service.SalesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("salesService")
@Transactional
public class SalesServiceImpl implements SalesService{

	@Autowired
	SalesDao salesDao;

	public void saveSalesInfo(LoeSalesInfo loeSalesInfo) {
		// TODO Auto-generated method stub
		salesDao.saveSalesInfo(loeSalesInfo);
	}

	public List<LoeSalesInfo> getSaleDataList(String drugName, Date date,
			String strength) {
		// TODO Auto-generated method stub
		return salesDao.getSaleDataList(drugName,date,strength);
	}

	public List<LoeSalesInfo> getSaleData(String drugName, Date date) {
		// TODO Auto-generated method stub
		return salesDao.getSaleData(drugName,date);
	}

	public List<LoeSalesInfo> getSaleDetails(String groupName, Date date) {
		// TODO Auto-generated method stub
		return salesDao.getSaleDetails(groupName,date);
	}

	
}
