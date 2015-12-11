package com.otsuka.loe.daoimpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.otsuka.loe.dao.ManufactureDao;
import com.otsuka.loe.model.LoeManufactureInfo;

@Repository("manufactureDao")
public class ManufactureDaoImpl extends AbstractDao implements ManufactureDao {

	public List<LoeManufactureInfo> getManufatureDataList(String drugName,
			Date date, String strength) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select l from LoeManufactureInfo l where (l.dateOfReleased <= :date) and (l.drugName = :drugName and l.strength = :strength) order by l.dateOfReleased DESC";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("drugName", drugName);
		query.setParameter("strength", strength);
		query.setParameter("date", date);
		query.setMaxResults(3);
		@SuppressWarnings("unchecked")
		List<LoeManufactureInfo> results = query.list();
		return (List<LoeManufactureInfo>) results;
	}

	public List<LoeManufactureInfo> getManufatureData(String groupName,
			Date date) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select l from LoeManufactureInfo l where (l.dateOfReleased <= :date) and (l.groupName = :groupName ) order by l.dateOfReleased DESC";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("groupName", groupName);
		query.setParameter("date", date);
		query.setMaxResults(3);
		@SuppressWarnings("unchecked")
		List<LoeManufactureInfo> results = query.list();
		return (List<LoeManufactureInfo>) results;
	}

	public List<LoeManufactureInfo> getManufatureDetails(String drugName,
			Date date) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select l from LoeManufactureInfo l where (l.dateOfReleased <= :date) and (l.drugName = :drugName ) order by l.dateOfReleased DESC";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("drugName", drugName);
		query.setParameter("date", date);
		query.setMaxResults(3);
		@SuppressWarnings("unchecked")
		List<LoeManufactureInfo> results = query.list();
		return (List<LoeManufactureInfo>) results;
	}

	public void saveManInfo(LoeManufactureInfo loeManufactureInfo) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(loeManufactureInfo);
	}

	public List<String> getLotNumbers(String drugName, String strength) {
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select distinct u.lotNumber from LoeManufactureInfo u where u.drugName = :drugName and u.strength = :strength";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("drugName", drugName);
		query.setParameter("strength", strength);
		@SuppressWarnings("unchecked")
		List<String> results = query.list();
		return (List<String>) results;
	}

	public List<String> getLotNumbersByDrugName(String drugName) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select distinct u.lotNumber from LoeManufactureInfo u where u.drugName = :drugName";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("drugName", drugName);
		@SuppressWarnings("unchecked")
		List<String> results = query.list();
		return (List<String>) results;
	}

	public List<String> getDrugs() {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select distinct l.drugName from LoeManufactureInfo l";
		query = getSession().createQuery(SQL_QUERY);
		@SuppressWarnings("unchecked")
		List<String> results = query.list();
		return (List<String>) results;
	}

	public List<String> getStrength(String drugName) {
		// TODO Auto-generated method stub
		String SQL_QUERY = null;
		Query query = null;
		SQL_QUERY = "select distinct l.strength from LoeManufactureInfo l where l.drugName = :drugName";
		query = getSession().createQuery(SQL_QUERY);
		query.setParameter("drugName", drugName);
		@SuppressWarnings("unchecked")
		List<String> results = query.list();
		return (List<String>) results;
	}

	public LoeManufactureInfo checkLotNumber(String lotNumber) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(LoeManufactureInfo.class);
		criteria.add(Restrictions.eq("lotNumber", lotNumber));
		return (LoeManufactureInfo) criteria.uniqueResult();
	}

}
