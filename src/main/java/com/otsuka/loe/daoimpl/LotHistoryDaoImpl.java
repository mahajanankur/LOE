package com.otsuka.loe.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.otsuka.loe.dao.LotHistoryDao;
import com.otsuka.loe.model.LotHistory;
@Repository("lotHistoryDao")
public class LotHistoryDaoImpl extends AbstractDao implements LotHistoryDao {

	public void saveLotHistory(LotHistory lotHistory) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(lotHistory);
	}

	public List<LotHistory> findLotHistory(String name) {
		// TODO Auto-generated method stub
		String hql = "select l from LotHistory l where l.lastModifiedByUser = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name",name);
		@SuppressWarnings("unchecked")
		List<LotHistory> results = query.list();
		return (List<LotHistory>) results;
	}

	public boolean checkReqIdForThisUser(String reqId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(LotHistory.class);
		criteria.add(Restrictions.eq("reqId",reqId));
		LotHistory lotHistory = (LotHistory) criteria.uniqueResult();
		if(lotHistory!=null){
			
			return false;
		}
		else{
		return true;
	}
		}

}
