package com.otsuka.loe.dao;

import java.util.List;

import com.otsuka.loe.model.LotHistory;

public interface LotHistoryDao {
	public abstract void saveLotHistory(LotHistory lotHistory);
	public abstract List<LotHistory> findLotHistory(String name);
	public abstract boolean checkReqIdForThisUser(String reqId); 
		
	

}
