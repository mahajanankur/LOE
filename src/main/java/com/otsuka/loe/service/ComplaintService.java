package com.otsuka.loe.service;

import java.util.List;

import com.otsuka.loe.model.ComplaintInfo;

public interface ComplaintService {
	public void saveComplaintInfo(ComplaintInfo complaintInfo);

	public ComplaintInfo checkReqId(String reqId);

	public List<ComplaintInfo> getListOfComplaint(String gName);
	public List<ComplaintInfo> getComplaintsList();

	/**
	 * This method is used to get the request id's linked to a complaint info
	 * object.
	 * 
	 * @return List<String>
	 */
	public List<String> getListOfRequestIds();
 public List<ComplaintInfo> getTotalComplaints(String name);
}
