package com.otsuka.loe.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.CSVReader;
import com.otsuka.loe.dto.DtoComplaintInfo;
import com.otsuka.loe.model.ComplaintInfo;
import com.otsuka.loe.model.DBConnection;
import com.otsuka.loe.model.FileUpload;
import com.otsuka.loe.model.LoeManufactureInfo;
import com.otsuka.loe.model.LoeSalesInfo;
import com.otsuka.loe.model.LotHistory;
import com.otsuka.loe.model.Users;
import com.otsuka.loe.service.ComplaintService;
import com.otsuka.loe.service.DrugDetailsService;
import com.otsuka.loe.service.LotHistoryService;
import com.otsuka.loe.service.ManufactureService;
import com.otsuka.loe.service.SalesService;
import com.otsuka.loe.service.UserService;
import com.otsuka.loe.util.CommonUtil;

@Controller
public class LoeController {
	private static final int BUFFER_SIZE = 4096;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	UserService userService;

	@Autowired
	ComplaintService complaintService;

	@Autowired
	ManufactureService manufactureService;

	@Autowired
	SalesService salesService;

	@Autowired
	LotHistoryService lotHistoryService;

	@Autowired
	DrugDetailsService drugDetailsService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/")
	public String welcome() throws Exception {
		return "loe_login";
	}

	/*
	 * @RequestMapping(value = "/errorpage") public String errorPage() throws
	 * Exception { return "errorpage"; }
	 */

	@RequestMapping(value = "/errorpage")
	public ModelAndView errorPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("errorpage");

		return modelAndView;
	}

	@RequestMapping(value = "/errorpage_admin")
	public ModelAndView errorPageAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("errorpage_admin");

		return modelAndView;
	}

	@RequestMapping(value = "/logout")
	public String logout(
			HttpServletRequest request,
			@RequestParam(value = "confirmationPopUp", required = false) String confirmationPopUp) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		// Functionality - ON HOLD
		ComplaintInfo complaintInfo = (ComplaintInfo) session
				.getAttribute("notSavedComplaintInfo");
		boolean complaintInfoSaved = CommonUtil
				.isComplaintInfoSaved(complaintInfo);
		if (!complaintInfoSaved && confirmationPopUp != null
				&& confirmationPopUp.equals("YesClicked")) {

			ComplaintInfo check = complaintService.checkReqId(complaintInfo
					.getReqId().trim());
			if (check != null) {
				complaintInfo.setLastModifiedDate(new Date());
				// trim request Id
				complaintInfo.setReqId(complaintInfo.getReqId().trim());
				complaintInfo.setLastModifiedByUser(userName);
				complaintService.saveComplaintInfo(complaintInfo);
			} else {
				ComplaintInfo comInfo = new ComplaintInfo();
				comInfo.setComplaintDate(complaintInfo.getComplaintDate());
				comInfo.setDescription(complaintInfo.getDescription());
				comInfo.setDrugName(complaintInfo.getDrugName());
				comInfo.setStrength(complaintInfo.getStrength());
				comInfo.setState(complaintInfo.getState());
				comInfo.setLastModifiedDate(new Date());
				// trim request Id
				comInfo.setReqId(complaintInfo.getReqId().trim());
				comInfo.setLastModifiedByUser(userName);
				// setting groupName attribute
				comInfo.setGroupName(complaintInfo.getGroupName());

				complaintService.saveComplaintInfo(comInfo);
			}

		}
		// Functionality - ON HOLD
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/home")
	public ModelAndView homePage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.getAttribute("userName");
		boolean isPrevious = true;
		ComplaintInfo complaintInfo = (ComplaintInfo) session
				.getAttribute("complaintInfo");
		if (complaintInfo == null) {
			complaintInfo = (ComplaintInfo) session
					.getAttribute("notSavedComplaintInfo");
		}
		List<ComplaintInfo> complaintList = complaintService
				.getComplaintsList();
		// list of request id's
		List<String> requestList = complaintService.getListOfRequestIds();
		// set of group names
		Set<String> groupList = drugDetailsService.getGroupNameList();
		ComplaintInfo comInfo = new ComplaintInfo();
		if (complaintInfo == null) {

			ModelAndView modelAndView = new ModelAndView("complaintinfo",
					"command", comInfo);
			modelAndView.addObject("requestIdsList", requestList);
			modelAndView.addObject("groupList", groupList);
			modelAndView.addObject("ComplaintList", complaintList);
			modelAndView.addObject("previous", isPrevious);
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("complaintinfo",
					"command", complaintInfo);

			// dependent drop -START
			Set<String> drugNamesList = drugDetailsService
					.getListOfDrugsNames(complaintInfo.getGroupName());
			Set<String> strengthsList = drugDetailsService
					.getListOfDrugsStrengths(complaintInfo.getDrugName());
			modelAndView.addObject("drugNamesList", drugNamesList);
			modelAndView.addObject("strengthsList", strengthsList);
			// dependent drop -END
			modelAndView.addObject("requestIdsList", requestList);
			modelAndView.addObject("groupList", groupList);
			modelAndView.addObject("ComplaintList", complaintList);
			modelAndView.addObject("previous", isPrevious);
			return modelAndView;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView userLogin(HttpServletRequest request,
			@RequestParam(value = "uname", required = true) String userName,
			@RequestParam(value = "password", required = true) String password,
			RedirectAttributes redirectAttributes) {
		try {
			HttpSession session = request.getSession();
			String role = userService.checkLogin(userName, password);
			String emailAddress = null;
			if (role.equals("Admin")) {
				session.setAttribute("userName", userName);
				ModelAndView modelAndView = new ModelAndView("redirect:/admin");
				return modelAndView;
			} else if (role.equals("General User")) {
				emailAddress = userService.getEmail(userName);
				session.setAttribute("userName", userName);
				session.setAttribute("emailAddress", emailAddress);
				ModelAndView modelAndView = new ModelAndView(
						"redirect:/complaininfo");
				return modelAndView;
			} else {
				ModelAndView modelAndView = new ModelAndView("loe_login");
				modelAndView.addObject("loginError",
						"Username or Password invalid");
				return modelAndView;
			}

		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/admin")
	public ModelAndView admin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");

		if (session != null && userName != null) {
			Users user = new Users();
			ModelAndView modelAndView = new ModelAndView("admin_login",
					"command", user);
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("loe_login");
			modelAndView.addObject("LoginFirst", "Login First");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/complaininfo")
	public ModelAndView complaint(HttpServletRequest request) {
		try {

			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("userName");
			if (session != null && userName != null) {
				ComplaintInfo complaintInfo = new ComplaintInfo();
				// to find data of complaints to show on slick grid
				HashMap<String, List<ComplaintInfo>> hm = new HashMap<String, List<ComplaintInfo>>();
				// list of unique group names
				Set<String> groupList = drugDetailsService.getGroupNameList();
				Iterator<String> itr = groupList.iterator();
				while (itr.hasNext()) {
					String gName = itr.next();
					List<ComplaintInfo> comInfo = complaintService
							.getListOfComplaint(gName);
					hm.put(gName, comInfo);
				}
				// testing code to print value on console
				for (Iterator<Entry<String, List<ComplaintInfo>>> iterator = hm
						.entrySet().iterator(); iterator.hasNext();) {
					Entry<String, List<ComplaintInfo>> m = iterator.next();
					System.out.println(m.getKey());
					for (int i = 0; i < m.getValue().size(); i++) {
						System.out.println(m.getValue().get(i).getDrugName());
						System.out.println(m.getValue().get(i).getStrength());

					}
				}
				List<ComplaintInfo> complaintList = complaintService
						.getComplaintsList();

				// list of request id's
				List<String> requestList = complaintService
						.getListOfRequestIds();
				ModelAndView modelAndView = new ModelAndView("complaintinfo",
						"command", complaintInfo);
				modelAndView.addObject("requestIdsList", requestList);
				modelAndView.addObject("groupList", groupList);
				modelAndView.addObject("ComplaintList", complaintList);
				return modelAndView;
			} else {
				ModelAndView modelAndView = new ModelAndView("loe_login");
				modelAndView.addObject("LoginFirst", "Login First");
				return modelAndView;
			}
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/saveuserinfo", method = RequestMethod.POST)
	public ModelAndView saveUserInfo(@ModelAttribute("userInfo") Users user,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("userName");
			if (session != null && userName != null) {
				String emailId = user.getEmail();
				String pass = user.getPassword();
				String name = user.getUname();

				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(emailId);
				email.setSubject("Your Account Details");
				email.setText("Your Otsuka Account is created Successfully.\n Your UserName is:"
						+ name + "\n Password is:" + pass);

				System.out
						.println("Your Otsuka Account is created Successfully.\n Your UserName is:"
								+ name + "\n Password is:" + pass);

				mailSender.send(email);
				userService.saveUserData(user);
				ModelAndView modelAndView = new ModelAndView("admin_login");
				return modelAndView;
			} else {
				ModelAndView modelAndView = new ModelAndView("loe_login");
				modelAndView.addObject("LoginFirst", "Login First");
				return modelAndView;
			}
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage_admin");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/searchbyemail", method = RequestMethod.GET)
	public @ResponseBody
	Users searchUpdate(
			@RequestParam(value = "email", required = true) String email,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if (session != null && userName != null) {
			Users user = userService.getUserByEmail(email);
			return user;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/updateuserinfo", method = RequestMethod.POST)
	public ModelAndView updateUserInfo(@ModelAttribute("user") Users user) {
		try {
			int id = user.getId();
			Users oldUser = userService.getUserById(id);
			oldUser.setUname(user.getUname());
			oldUser.setPassword(user.getPassword());
			oldUser.setEmail(user.getEmail());
			oldUser.setPhone(user.getPhone());
			oldUser.setRole(user.getRole());

			String emailId = user.getEmail();
			String pass = user.getPassword();
			String name = user.getUname();

			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailId);
			email.setSubject("Your Account Details");
			email.setText("Your Otsuka Account is created Successfully.\n Your UserName is:"
					+ name + "\n Password is:" + pass);

			System.out
					.println("Your Otsuka Account is created Successfully.\n Your UserName is:"
							+ name + "\n Password is:" + pass);

			mailSender.send(email);
			userService.saveUserData(oldUser);
			ModelAndView modelAndView = new ModelAndView("admin_login");
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage_admin");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/deleteuserinfo", method = RequestMethod.POST)
	public ModelAndView deleteUserInfo(@ModelAttribute("user") Users user) {
		try {
			userService.deleteUser(user.getId());
			ModelAndView modelAndView = new ModelAndView("admin_login");
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage_admin");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/submitcomplaintinfo", method = RequestMethod.POST)
	public ModelAndView saveComplaintInfo(
			@ModelAttribute("compInfo") ComplaintInfo complaintInfo,
			HttpServletRequest request,
			@RequestParam(value = "confirmationPopUp", required = true) String confirmationPopUp,
			@RequestParam(value = "newRequest", required = true) String newRequest) {
		try {
			HttpSession session = request.getSession();
			if (!confirmationPopUp.equals("")
					&& confirmationPopUp.equals("YesClicked")) {
				session.setAttribute("complaintInfo", complaintInfo);
				String userName = (String) session.getAttribute("userName");
				ComplaintInfo comInfo = complaintService
						.checkReqId(complaintInfo.getReqId().trim());
				if (comInfo != null) {
					comInfo.setLastModifiedDate(new Date());
					// trim request Id
					comInfo.setReqId(complaintInfo.getReqId().trim());
					comInfo.setLastModifiedByUser(userName);
					comInfo.setComplaintDate(complaintInfo.getComplaintDate());
					comInfo.setDescription(complaintInfo.getDescription());
					comInfo.setDrugName(complaintInfo.getDrugName());
					comInfo.setStrength(complaintInfo.getStrength());
					comInfo.setState(complaintInfo.getState());
					comInfo.setGroupName(complaintInfo.getGroupName());
					complaintService.saveComplaintInfo(comInfo);
				} else {
					complaintInfo.setReqId(complaintInfo.getReqId().trim());
					complaintInfo.setLastModifiedDate(new Date());
					complaintInfo.setLastModifiedByUser(userName);
					complaintService.saveComplaintInfo(complaintInfo);
				}
			} else if (!confirmationPopUp.equals("")
					&& confirmationPopUp.equals("NoClicked")) {
				session.setAttribute("notSavedComplaintInfo", complaintInfo);
			}
			// Common functionality
			ModelAndView modelAndView = new ModelAndView(
					"redirect:/manufactureinfo");
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/savesessiondata", method = RequestMethod.POST)
	public ModelAndView saveSessionData(
			@RequestParam(value = "str", required = true) String str,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		ComplaintInfo complaintInfo = (ComplaintInfo) session
				.getAttribute("notSavedComplaintInfo");
		String name = (String) session.getAttribute("userName");
		complaintInfo.setLastModifiedDate(new Date());
		complaintInfo.setLastModifiedByUser(name);
		complaintService.saveComplaintInfo(complaintInfo);
		String groupName = complaintInfo.getGroupName();
		String drugName = complaintInfo.getDrugName();
		String strength = complaintInfo.getStrength();
		Date date = complaintInfo.getComplaintDate();
		ModelAndView modelAndView = new ModelAndView("lotnumber");
		List<LoeManufactureInfo> manufactureList = null;
		if (drugName.equals("unknown")) {
			manufactureList = manufactureService.getManufatureData(groupName,
					date);
			modelAndView.addObject("verify", "unknown drug");
		} else if (strength.equals("unknown")) {
			manufactureList = manufactureService.getManufatureDetails(drugName,
					date);
			modelAndView.addObject("verify", "unknown strength");
		} else {
			manufactureList = manufactureService.getManufatureDataList(
					drugName, date, strength);
			modelAndView.addObject("verify", strength);
		}
		modelAndView.addObject("ManufactureDataList", manufactureList);
		session.removeAttribute("notSavedComplaintInfo");
		modelAndView.addObject("sessionDataSaved",
				"Your complaint data in session is saved sucessfully!!");
		return modelAndView;
	}

	@RequestMapping(value = "/manufactureinfo", method = RequestMethod.GET)
	public ModelAndView redirectToManufactureInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ComplaintInfo complaintInfo = (ComplaintInfo) session
				.getAttribute("complaintInfo");
		if (complaintInfo == null) {
			complaintInfo = (ComplaintInfo) session
					.getAttribute("notSavedComplaintInfo");
		}
		// Common functionality
		LoeManufactureInfo loeManufactureInfo = new LoeManufactureInfo();
		String groupName = complaintInfo.getGroupName();
		String drugName = complaintInfo.getDrugName();
		String strength = complaintInfo.getStrength();
		Date date = complaintInfo.getComplaintDate();
		ModelAndView modelAndView = new ModelAndView("manufactureinfo",
				"command", loeManufactureInfo);
		List<LoeManufactureInfo> manufactureList = null;
		if (drugName.equals("unknown")) {
			manufactureList = manufactureService.getManufatureData(groupName,
					date);
			modelAndView.addObject("verify", "unknown drug");
		} else if (strength.equals("unknown")) {
			manufactureList = manufactureService.getManufatureDetails(drugName,
					date);
			modelAndView.addObject("verify", "unknown strength");
		} else {
			manufactureList = manufactureService.getManufatureDataList(
					drugName, date, strength);
			modelAndView.addObject("verify", strength);
		}
		// list of unique group names
		Set<String> groupList = drugDetailsService.getGroupNameList();
		modelAndView.addObject("groupList", groupList);
		modelAndView.addObject("ManufactureDataList", manufactureList);
		return modelAndView;
	}

	@RequestMapping(value = "/submitManufactureInfo", method = RequestMethod.POST)
	public ModelAndView saveManufactureInfo(
			@ModelAttribute("manInfo") LoeManufactureInfo loeManufactureInfo,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("userName");
			ComplaintInfo complaintInfo = (ComplaintInfo) session
					.getAttribute("complaintInfo");
			if (complaintInfo == null) {
				complaintInfo = (ComplaintInfo) session
						.getAttribute("notSavedComplaintInfo");
			}
			LoeManufactureInfo loeMan = new LoeManufactureInfo();
			String ltNumber = loeManufactureInfo.getLotNumber();
			LoeManufactureInfo loe = manufactureService
					.checkLotNumber(ltNumber);
			ModelAndView modelAndView = new ModelAndView();
			if (loe != null && !loe.equals("")) {
				modelAndView = new ModelAndView("manufactureinfo", "command",
						loeMan);
				modelAndView
						.addObject("lotExist",
								"This Lot Number is already exist, Please insert different");
			} else {
				loeManufactureInfo.setLastModifiedDate(new Date());
				loeManufactureInfo.setLastModifiedByUser(name);
				manufactureService.saveManInfo(loeManufactureInfo);
				modelAndView = new ModelAndView("manufactureinfo", "command",
						loeMan);
			}
			String groupName = complaintInfo.getGroupName();
			String drugName = complaintInfo.getDrugName();
			String strength = complaintInfo.getStrength();
			Date date = complaintInfo.getComplaintDate();
			List<LoeManufactureInfo> manufactureList = null;
			if (drugName.equals("unknown")) {
				manufactureList = manufactureService.getManufatureData(
						groupName, date);
				modelAndView.addObject("verify", "unknown drug");
			} else if (strength.equals("unknown")) {
				manufactureList = manufactureService.getManufatureDetails(
						drugName, date);
				modelAndView.addObject("verify", "unknown strength");
			} else {
				manufactureList = manufactureService.getManufatureDataList(
						drugName, date, strength);
				modelAndView.addObject("verify", strength);
			}
			Set<String> groupList = drugDetailsService.getGroupNameList();
			modelAndView.addObject("groupList", groupList);
			modelAndView.addObject("ManufactureDataList", manufactureList);
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/salespage", method = RequestMethod.GET)
	public ModelAndView salesPage(
			@ModelAttribute("salesInfo") LoeSalesInfo loeSalesInfo,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			ComplaintInfo complaintInfo = (ComplaintInfo) session
					.getAttribute("complaintInfo");
			// Test -- Not saved complaint info object.
			if (complaintInfo == null) {
				complaintInfo = (ComplaintInfo) session
						.getAttribute("notSavedComplaintInfo");
			}
			String groupName = complaintInfo.getGroupName();
			String drugName = complaintInfo.getDrugName();
			String strength = complaintInfo.getStrength();
			Date date = complaintInfo.getComplaintDate();
			ModelAndView modelAndView = new ModelAndView("salesinfo",
					"command", loeSalesInfo);
			List<LoeSalesInfo> salesInfo = null;
			if (drugName.equals("unknown")) {
				salesInfo = salesService.getSaleDetails(groupName, date);
				modelAndView.addObject("verify", "unknown drug");
			} else if (strength.equals("unknown")) {
				salesInfo = salesService.getSaleData(drugName, date);
				modelAndView.addObject("verify", "unknown strength");
			} else {
				salesInfo = salesService.getSaleDataList(drugName, date,
						strength);
				modelAndView.addObject("verify", strength);
			}
			// list of unique group names
			Set<String> groupList = drugDetailsService.getGroupNameList();
			modelAndView.addObject("groupList", groupList);
			modelAndView.addObject("SalesData", salesInfo);
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/submitsalesInfo", method = RequestMethod.POST)
	public ModelAndView saveSalesInfo(
			@ModelAttribute("salesInfo") LoeSalesInfo loeSalesInfo,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("userName");
			ComplaintInfo complaintInfo = (ComplaintInfo) session
					.getAttribute("complaintInfo");
			if (complaintInfo == null) {
				complaintInfo = (ComplaintInfo) session
						.getAttribute("notSavedComplaintInfo");
			}
			String groupName = complaintInfo.getGroupName();
			String drugName = complaintInfo.getDrugName();
			String strength = complaintInfo.getStrength();
			Date date = complaintInfo.getComplaintDate();
			loeSalesInfo.setLastModifiedByUser(name);
			loeSalesInfo.setLastModifiedDate(new Date());
			salesService.saveSalesInfo(loeSalesInfo);
			LoeSalesInfo loeSales = new LoeSalesInfo();
			ModelAndView modelAndView = new ModelAndView("salesinfo",
					"command", loeSales);
			List<LoeSalesInfo> salesInfo = null;
			if (drugName.equals("unknown")) {
				salesInfo = salesService.getSaleDetails(groupName, date);
				modelAndView.addObject("verify", "unknown drug");
			} else if (strength.equals("unknown")) {
				salesInfo = salesService.getSaleData(drugName, date);
				modelAndView.addObject("verify", "unknown strength");
			} else {
				salesInfo = salesService.getSaleDataList(drugName, date,
						strength);
				modelAndView.addObject("verify", strength);
			}
			Set<String> groupList = drugDetailsService.getGroupNameList();
			modelAndView.addObject("groupList", groupList);
			modelAndView.addObject("SalesData", salesInfo);
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/lotnumber", method = RequestMethod.GET)
	public ModelAndView lotInfo(HttpServletRequest request) {
		try {
			String groupName;
			String drugName;
			String strength;
			Date date;
			HttpSession session = request.getSession();
			ComplaintInfo complaintInfo = (ComplaintInfo) session
					.getAttribute("complaintInfo");
			ComplaintInfo compInfo = (ComplaintInfo) session
					.getAttribute("notSavedComplaintInfo");
			if (complaintInfo == null) {
				groupName = compInfo.getGroupName();
				drugName = compInfo.getDrugName();
				strength = compInfo.getStrength();
				date = compInfo.getComplaintDate();
			} else {
				groupName = complaintInfo.getGroupName();
				drugName = complaintInfo.getDrugName();
				strength = complaintInfo.getStrength();
				date = complaintInfo.getComplaintDate();
			}
			ModelAndView modelAndView = new ModelAndView("lotnumber");
			List<LoeManufactureInfo> manufactureList = null;
			if (drugName.equals("unknown")) {
				manufactureList = manufactureService.getManufatureData(
						groupName, date);
				modelAndView.addObject("verify", "unknown drug");
			} else if (strength.equals("unknown")) {
				manufactureList = manufactureService.getManufatureDetails(
						drugName, date);
				modelAndView.addObject("verify", "unknown strength");
			} else {
				manufactureList = manufactureService.getManufatureDataList(
						drugName, date, strength);
				modelAndView.addObject("verify", strength);
			}
			modelAndView.addObject("ManufactureDataList", manufactureList);
			modelAndView.addObject("ComplaintSessionData", compInfo);
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/myreports", method = RequestMethod.GET)
	public ModelAndView myReports(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("userName");
			List<ComplaintInfo> totalComplaintsByUser = complaintService
					.getTotalComplaints(name);
			for (int i = 0; i < totalComplaintsByUser.size(); i++) {
				String sb = "";
				ComplaintInfo compList = totalComplaintsByUser.get(i);
				String strength = compList.getStrength();
				String drug = compList.getDrugName();
				String reqId = compList.getReqId();
				boolean checkReqId = lotHistoryService
						.checkReqIdForThisUser(reqId);
				if (checkReqId) {
					if(!strength.equals("unknown") && !drug.equals("unknown")){
					LotHistory lotHistory = new LotHistory();
					lotHistory.setReqId(compList.getReqId());
					lotHistory.setStrength(compList.getStrength());
					lotHistory.setDrugName(compList.getDrugName());
					lotHistory.setComplainDesc(compList.getDescription());
					lotHistory.setDateOfComplain(compList.getComplaintDate());
					lotHistory.setLastModifiedByUser(name);
					lotHistory.setLastModifiedDate(compList
							.getLastModifiedDate());
					lotHistory.setGroupName(compList.getGroupName());
					List<String> ltNums = manufactureService.getLotNumbers(
							drug, strength);
					for (int j = 0; j < ltNums.size(); j++) {

						if (sb.length() != 0 && sb != "") {
							String comma = ",";
							sb = sb.concat(comma);
						}
						sb = sb.concat(ltNums.get(j));
					}
					lotHistory.setLotNumbers(sb);
					lotHistoryService.saveLotHistory(lotHistory);
					}
				}
			}
			List<LotHistory> ltHistory = lotHistoryService.findLotHistory(name);
			ModelAndView modelAndView = new ModelAndView("myreports");
			modelAndView.addObject("LotHistory", ltHistory);
			return modelAndView;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ModelAndView modelAndView = new ModelAndView("errorpage");
			return modelAndView;
		}
	}

	/**
	 * This method is used to return the complain information linked to a
	 * request id to AJAX method.
	 * 
	 * @param requestId
	 * @return comInfo
	 */
	@RequestMapping(value = "/getComplaintInfoByReqId", method = RequestMethod.GET)
	public @ResponseBody
	DtoComplaintInfo getComplaintInfoByReqId(
			@RequestParam(value = "requestId", required = true) String requestId) {
		DtoComplaintInfo response = new DtoComplaintInfo();
		ComplaintInfo comInfo = complaintService.checkReqId(requestId.trim());
		if (comInfo != null && !comInfo.equals("")) {
			Date compDate = comInfo.getComplaintDate();
			// convert timestamp to MM/dd/yyyy format
			String complaintDate = CommonUtil.changeDateFormat(compDate);
			response.setComplaintDate(complaintDate);
			response.setDescription(comInfo.getDescription());
			response.setState(comInfo.getState());
			response.setDrugName(comInfo.getDrugName());
			response.setStrength(comInfo.getStrength());
			response.setComplaintId(comInfo.getComplaintId());
			response.setGroupName(comInfo.getGroupName());
		}
		return response;
	}

	// This method use to verify Lot Number using ajax
	@RequestMapping(value = "/verifylotnumber", method = RequestMethod.GET)
	public @ResponseBody
	boolean verifyLotNumber(
			@RequestParam(value = "lotNumber", required = true) String lotNumber) {
		LoeManufactureInfo loeManufactureInfo = manufactureService
				.checkLotNumber(lotNumber);
		if (loeManufactureInfo != null && !loeManufactureInfo.equals("")) {
			return true;
		} else
			return false;
	}

	/*
	 * @RequestMapping(value = "/resetcomplaintform", method =
	 * RequestMethod.GET) public @ResponseBody ComplaintInfo
	 * resetComplaintPage(){
	 * 
	 * ComplaintInfo }
	 */

	/**
	 * This method is used to fetch the drug name list linked to a group.
	 * 
	 * AJAX Call
	 * 
	 * @param groupName
	 * @return drugNames
	 */
	@RequestMapping(value = "/getDrugNamesOnGroup", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getDrugNamesOnGroup(
			@RequestParam(value = "groupName", required = true) String groupName) {

		Set<String> drugNames = drugDetailsService
				.getListOfDrugsNames(groupName);
		return drugNames;
	}

	/**
	 * This method is used to fetch the strength list linked to a drug name.
	 * 
	 * AJAX Call
	 * 
	 * @param drugName
	 * @return strengths
	 */
	@RequestMapping(value = "/getStrengthOnDrugName", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getStrengthOnDrugName(
			@RequestParam(value = "drugName", required = true) String drugName) {
		Set<String> strengths = drugDetailsService
				.getListOfDrugsStrengths(drugName);
		return strengths;
	}

	// @RequestMapping(value = "/saveComplaintDataOnLogout", method =
	// RequestMethod.POST)
	// public @ResponseBody
	// String saveComplaintDataOnLogout(
	// @ModelAttribute(value = "compInfo") ComplaintInfo complaintInfo,
	// @RequestParam(value = "compInfo") String c) {
	// System.out.println(complaintInfo);
	// return "Your data has been saved successfully.";
	// }

	@RequestMapping(value = "/email")
	public ModelAndView emailReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("userName");
		String emailAddress = (String) session.getAttribute("emailAddress");
		List<LotHistory> ltHistory = lotHistoryService.findLotHistory(name);
		Properties configProperties = new Properties();
		String propertyFile = "configurations.properties";
		if (LoeController.class.getClassLoader().getResourceAsStream(
				propertyFile) == null) {
			System.out.println("configurations file not loaded");
		}
		configProperties.load(LoeController.class.getClassLoader()
				.getResourceAsStream(propertyFile));
		String outputDir = (String) configProperties
				.get("config.data.save.dir");
		CommonUtil.writeCsvFile(outputDir, ltHistory);
		String dataDir = (String) configProperties.get("config.data.save.dir");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		System.out.println("email address is : " + emailAddress);
		helper.setTo(emailAddress);
		helper.setSubject("Your Account Details");
		helper.setText("your have a new message");
		FileSystemResource file = new FileSystemResource(dataDir + "\\"
				+ Calendar.getInstance().get(Calendar.MONTH) + "_"
				+ Calendar.getInstance().get(Calendar.DATE) + "_"
				+ Calendar.getInstance().get(Calendar.YEAR) + ".csv");
		helper.addAttachment("MyReports" + ".csv", file);
		mailSender.send(message);
		ModelAndView modelAndView = new ModelAndView("myreports");
		modelAndView.addObject("LotHistory", ltHistory);
		modelAndView.addObject("emailmessage", "Email sent sucessfully!!!");
		return modelAndView;
	}

	@RequestMapping(value = "/savereport", method = RequestMethod.GET)
	public void fileSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("userName");
		List<LotHistory> ltHistory = lotHistoryService.findLotHistory(name);
		Properties configProperties = new Properties();
		String propertyFile = "configurations.properties";
		try {
			if (LoeController.class.getClassLoader().getResourceAsStream(
					propertyFile) == null) {
				System.out.println("configurations file not loaded");
			}
			configProperties.load(LoeController.class.getClassLoader()
					.getResourceAsStream(propertyFile));
			String outputDir = (String) configProperties
					.get("config.data.download.dir");
			CommonUtil.writeCsvFile(outputDir, ltHistory);
			String dataDir = (String) configProperties
					.get("config.data.download.dir");
			String fullPath = dataDir + "\\"
					+ Calendar.getInstance().get(Calendar.MONTH) + "_"
					+ Calendar.getInstance().get(Calendar.DATE) + "_"
					+ Calendar.getInstance().get(Calendar.YEAR) + ".csv";
			String mimeType = servletContext.getMimeType(fullPath);
			File downloadFile = new File(fullPath);
			FileInputStream inputStream = new FileInputStream(downloadFile);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());
			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					"test.csv");
			response.setHeader(headerKey, headerValue);
			// get output stream of the response
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Console Daat" + e.getLocalizedMessage());
			// TODO: handle exception
		}
	}

	@RequestMapping(value = "/uploadfile")
	public ModelAndView fileUpload(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("fileUpload") FileUpload file,
			BindingResult result, HttpSession session) throws Exception {
		try {

			Properties configProperties = new Properties();
			String propertyFile = "configurations.properties";
			if (LoeController.class.getClassLoader().getResourceAsStream(
					propertyFile) == null) {
				System.out.println("configurations file not loaded");
			}

			try {
				configProperties.load(LoeController.class.getClassLoader()
						.getResourceAsStream(propertyFile));
			} catch (Exception e) {
				e.printStackTrace();
				ModelAndView modelAndView = new ModelAndView("errorpage_admin");
				return modelAndView;
			}
			String dataDir = (String) configProperties.get("config.data.dir");
			// String dataDir = "c://Otsuka//data";
			int dataFlag = 1;
			String type = request.getParameter("filename");
			// String dateModifed = new Date().toString();
			String name = (String) session.getAttribute("name");
			MultipartFile multipartFile = file.getFile();
			String originalFileName = multipartFile.getOriginalFilename();
			if (originalFileName != null
					&& (originalFileName.indexOf(".csv") > -1)) {
				File newFile = new File(dataDir, originalFileName);
				FileUtils.writeByteArrayToFile(newFile,
						multipartFile.getBytes());
				readCsv(newFile, type);
			} else {
				dataFlag = 0;
			}
			ModelAndView modelAndView = new ModelAndView("admin_login");
			return modelAndView;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ModelAndView modelAndView = new ModelAndView("errorpage_admin");
			return modelAndView;
		}
	}

	public void readCsv(File file, String type) throws Exception {
		if (type.equals("Complaint")) {
			// try {
			CSVReader reader = new CSVReader(new FileReader(file), ',');
			Connection connection = DBConnection.getConnection();
			String insertQuery = "Insert into complaintinfo (complaint_id, reqid, strength, drugname,groupname,state,description,complaintdate,lastmodifiedbyuser,lastmodifieddate) values (null,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertQuery);
			String[] rowData = null;
			int i = 0;
			while ((rowData = reader.readNext()) != null) {
				// rowData[rowData.length - 1] = getDate(rowData[rowData.length
				// - 1]);
				java.sql.Date complaintdate = new java.sql.Date(new Date(
						rowData[6]).getTime());

				rowData[6] = complaintdate.toString();
				java.sql.Date lastmodifieddate = new java.sql.Date(new Date(
						rowData[8]).getTime());

				rowData[8] = lastmodifieddate.toString();
				for (String data : rowData) {
					pstmt.setString((i % 9) + 1, data);
					if (++i % 9 == 0)
						pstmt.addBatch();
					// if (i % 80 == 0)
					// pstmt.executeBatch();
				}
				pstmt.executeBatch();
			}
			System.out.println("Data saved in DB");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		} else if (type.equals("Manufacture")) {
			// try {
			CSVReader reader = new CSVReader(new FileReader(file), ',');
			Connection connection = DBConnection.getConnection();
			String insertQuery = "Insert into loemanufactureinfo (manf_id, strength, groupname,drugname,lotnumber,quantityreleased,dateofrelease,dateofmanufacture,dateofexpire,lastmodifiedbyuser,lastmodifieddate) values (null,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertQuery);
			String[] rowData = null;
			int i = 0;
			while ((rowData = reader.readNext()) != null) {
				// rowData[4]=getDate(rowData[4]);
				// rowData[rowData.length - 1] = getDate(rowData[rowData.length
				// - 1]);
				java.sql.Date dateofrelease = new java.sql.Date(new Date(
						rowData[5]).getTime());
				java.sql.Date dateofmanufacture = new java.sql.Date(new Date(
						rowData[6]).getTime());
				java.sql.Date dateofexpire = new java.sql.Date(new Date(
						rowData[7]).getTime());
				java.sql.Date lastupdateddt = new java.sql.Date(new Date(
						rowData[9]).getTime());
				rowData[5] = dateofrelease.toString();
				rowData[6] = dateofmanufacture.toString();
				rowData[7] = dateofexpire.toString();
				rowData[9] = lastupdateddt.toString();
				for (String data : rowData) {
					pstmt.setString((i % 10) + 1, data);
					if (++i % 10 == 0)
						pstmt.addBatch();
					// if (i % 90 == 0)
					// pstmt.executeBatch();
				}
				pstmt.executeBatch();
			}
			System.out.println("Data saved in DB");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		} else if (type.equals("Sails")) {
			// try {
			CSVReader reader = new CSVReader(new FileReader(file), ',');
			Connection connection = DBConnection.getConnection();
			String insertQuery = "Insert into loesales (id, strength, drugname,groupname,salebydate,quantitysold,yeartodatesale,lastmodifiedbyuser,lastmodifieddate) values (null,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertQuery);
			String[] rowData = null;
			int i = 0;
			while ((rowData = reader.readNext()) != null) {
				// rowData[rowData.length - 1] = getDate(rowData[rowData.length
				// - 1]);
				java.sql.Date lastupdateddt = new java.sql.Date(new Date(
						rowData[7]).getTime());
				java.sql.Date saleDate = new java.sql.Date(
						new Date(rowData[3]).getTime());
				rowData[7] = lastupdateddt.toString();
				rowData[3] = saleDate.toString();
				for (String data : rowData) {
					pstmt.setString((i % 8) + 1, data);
					if (++i % 8 == 0)
						pstmt.addBatch();
					// if (i % 70 == 0)
					// pstmt.executeBatch();
				}
				pstmt.executeBatch();
			}
			System.out.println("Data saved in DB");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		}

	}
}
