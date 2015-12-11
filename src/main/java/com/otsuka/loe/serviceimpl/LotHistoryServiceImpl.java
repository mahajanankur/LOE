package com.otsuka.loe.serviceimpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otsuka.loe.dao.LotHistoryDao;
import com.otsuka.loe.model.LotHistory;
import com.otsuka.loe.service.LotHistoryService;
@Service("lotHistoryService")
@Transactional
public class LotHistoryServiceImpl implements LotHistoryService {
	@Autowired
	LotHistoryDao lotHistoryDao;

	public void saveLotHistory(LotHistory lotHistory) {
		// TODO Auto-generated method stub
		lotHistoryDao.saveLotHistory(lotHistory);
	}

	public List<LotHistory> findLotHistory(String name) {
		// TODO Auto-generated method stub
		return lotHistoryDao.findLotHistory(name);
	}

	public boolean checkReqIdForThisUser(String reqId) {
		// TODO Auto-generated method stub
		return lotHistoryDao.checkReqIdForThisUser(reqId);
	}

	

}
