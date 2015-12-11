package com.otsuka.loe.service;

import java.util.List;

import com.otsuka.loe.model.LotHistory;

public interface LotHistoryService {
public void saveLotHistory(LotHistory lotHistory);
public List<LotHistory> findLotHistory(String name);
public boolean checkReqIdForThisUser(String reqId);
}
