package com.otsuka.loe.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.otsuka.loe.dao.ComplaintDao;
import com.otsuka.loe.model.ComplaintInfo;

@Repository("complaintDao")
public class ComplaintDaoImpl extends AbstractDao implements ComplaintDao {

	public void saveComplaintInfo(ComplaintInfo complaintInfo) {
		getSession().saveOrUpdate(complaintInfo);

	}

	public ComplaintInfo checkReqId(String reqId) {
		Criteria criteria = getSession().createCriteria(ComplaintInfo.class);
		criteria.add(Restrictions.eq("reqId", reqId));
		return (ComplaintInfo) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<ComplaintInfo> getListOfComplaint(String gName) {
		String hql = "select c from ComplaintInfo c where c.groupName = :gName";
		Query query = getSession().createQuery(hql);
        query.setParameter("gName", gName);
		List<ComplaintInfo> results = query.list();
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.otsuka.loe.dao.ComplaintDao#getListOfRequestIds()
	 * 
	 * This method is used to get the request id's linked to a complaint info
	 * object.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getListOfRequestIds() {
		String hql = "select c.reqId from ComplaintInfo c ";
		Query query = getSession().createQuery(hql);
		List<String> results = query.list();
		return results;
	}

	public List<ComplaintInfo> getTotalComplaints(String name) {
		// TODO Auto-generated method stub
		String hql = "select c from ComplaintInfo c where c.lastModifiedByUser = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<ComplaintInfo> results = query.list();
		return (List<ComplaintInfo>) results;
	}
	@SuppressWarnings("unchecked")
	public List<ComplaintInfo> getComplaintsList() {
		// TODO Auto-generated method stub
		String hql = "from ComplaintInfo c ";
		Query query = getSession().createQuery(hql);
		List<ComplaintInfo> results = query.list();
		return results;
	}

}
