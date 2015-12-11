package com.otsuka.loe.dao;

import java.util.List;

import com.otsuka.loe.model.ComplaintInfo;

public interface ComplaintDao {
	public abstract void saveComplaintInfo(ComplaintInfo complaintInfo);

	public abstract ComplaintInfo checkReqId(String reqId);

	public abstract List<ComplaintInfo> getListOfComplaint(String gName);

	/**
	 * This method is used to get the request id's linked to a complaint info
	 * object.
	 * 
	 * @return List<String>
	 */
	public abstract List<String> getListOfRequestIds();
public abstract List<ComplaintInfo> getTotalComplaints(String name);
public abstract List<ComplaintInfo> getComplaintsList();
}
