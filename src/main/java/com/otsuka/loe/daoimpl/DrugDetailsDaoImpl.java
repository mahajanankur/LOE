package com.otsuka.loe.daoimpl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.otsuka.loe.dao.DrugDetailsDao;
import com.otsuka.loe.model.DrugDetailsInfo;

@Repository("drugDetailsDao")
public class DrugDetailsDaoImpl extends AbstractDao implements DrugDetailsDao {

	public void saveDrugDetails(DrugDetailsInfo drugDetails) {
		getSession().saveOrUpdate(drugDetails);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.otsuka.loe.dao.DrugDetailsDao#getListOfDrugsNames(java.lang.String)
	 * 
	 * This method is used to get the unique drug names list based on the group
	 * name.
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getListOfDrugsNames(String drugGroup) {
		Set<String> resultSet = new LinkedHashSet<String>();
		String hql = "select c.drugName from DrugDetailsInfo c where c.groupName = :groupName";
		Query query = getSession().createQuery(hql);
		query.setParameter("groupName", drugGroup);
		List<String> drugNameList = (List<String>) query.list();
		for (String drugName : drugNameList) {
			resultSet.add(drugName);
		}
		return resultSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.otsuka.loe.dao.DrugDetailsDao#getListOfDrugsStrengths(java.lang.String
	 * ) This method is used to get the unique strength list based on the drug
	 * name.
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getListOfDrugsStrengths(String drugName) {
		Set<String> resultSet = new LinkedHashSet<String>();
		String hql = "select c.strength from DrugDetailsInfo c where c.drugName = :drugName";
		Query query = getSession().createQuery(hql);
		query.setParameter("drugName", drugName);
		List<String> strengthList = (List<String>) query.list();
		for (String strength : strengthList) {
			resultSet.add(strength);
		}
		return resultSet;
	}

	@SuppressWarnings("unchecked")
	public List<DrugDetailsInfo> getListOfDrugs() {
		String hql = "select c from DrugDetailsInfo c";
		Query query = getSession().createQuery(hql);
		List<DrugDetailsInfo> results = query.list();
		return (List<DrugDetailsInfo>) results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.otsuka.loe.dao.DrugDetailsDao#getGroupNameList()
	 * 
	 * This method is used to get the unique group name list.
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getGroupNameList() {
		Set<String> resultSet = new LinkedHashSet<String>();
		String hql = "select c.groupName from DrugDetailsInfo c";
		Query query = getSession().createQuery(hql);
		List<String> groupNameList = (List<String>) query.list();
		for (String groupName : groupNameList) {
			resultSet.add(groupName);
		}
		return resultSet;
	}

}
