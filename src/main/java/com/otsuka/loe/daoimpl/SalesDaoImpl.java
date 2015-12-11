package com.otsuka.loe.daoimpl;

import java.util.Date;
import java.util.List;

import com.otsuka.loe.dao.SalesDao;
import com.otsuka.loe.model.LoeSalesInfo;
import com.otsuka.loe.daoimpl.AbstractDao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("salesDao")
public class SalesDaoImpl extends AbstractDao implements SalesDao {

	public void saveSalesInfo(LoeSalesInfo loeSalesInfo) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(loeSalesInfo);

	}

	public List<LoeSalesInfo> getSaleDataList(String drugName, Date date,
			String strength) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select u from LoeSalesInfo u where (u.salebydate <= :date) and (u.drugName = :drugName and u.strength = :strength) order by u.salebydate DESC";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("drugName", drugName);
		query.setParameter("strength", strength);
		query.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<LoeSalesInfo> results = query.list();
		return (List<LoeSalesInfo>) results;
	}

	public List<LoeSalesInfo> getSaleData(String drugName, Date date) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select u from LoeSalesInfo u where (u.salebydate <= :date) and (u.drugName = :drugName ) order by u.salebydate DESC";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("drugName", drugName);
		query.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<LoeSalesInfo> results = query.list();
		return (List<LoeSalesInfo>) results;
	}

	public List<LoeSalesInfo> getSaleDetails(String groupName, Date date) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select u from LoeSalesInfo u where (u.salebydate <= :date) and (u.groupName = :groupName ) order by u.salebydate DESC";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("groupName", groupName);
		query.setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<LoeSalesInfo> results = query.list();
		return (List<LoeSalesInfo>) results;
	}

}
