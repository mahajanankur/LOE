package com.otsuka.loe.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otsuka.loe.dao.ComplaintDao;
import com.otsuka.loe.model.ComplaintInfo;
import com.otsuka.loe.service.ComplaintService;

@Service("complaintService")
@Transactional
public class ComplaintServiceImpl implements ComplaintService {
	@Autowired
	ComplaintDao complaintDao;

	public void saveComplaintInfo(ComplaintInfo complaintInfo) {
		String description = complaintInfo.getDescription();
		if(description.length() > 1000){
			complaintInfo.setDescription(description.substring(0,999).trim());
		}
		complaintDao.saveComplaintInfo(complaintInfo);
	}

	public ComplaintInfo checkReqId(String reqId) {
		return complaintDao.checkReqId(reqId);
	}

	public List<ComplaintInfo> getListOfComplaint(String gName) {
		return complaintDao.getListOfComplaint(gName);
	}


public List<ComplaintInfo> getTotalComplaints(String name) {
	// TODO Auto-generated method stub
	return complaintDao.getTotalComplaints(name);
}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.otsuka.loe.service.ComplaintService#getListOfRequestIds()
	 * 
	 * This method is used to get the request id's linked to a complaint info
	 * object.
	 */
	public List<String> getListOfRequestIds() {
		return complaintDao.getListOfRequestIds();
	}

	public List<ComplaintInfo> getComplaintsList() {
		// TODO Auto-generated method stub
		return complaintDao.getComplaintsList();
	}



}
