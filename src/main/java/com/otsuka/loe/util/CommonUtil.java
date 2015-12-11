package com.otsuka.loe.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.otsuka.loe.model.ComplaintInfo;
import com.otsuka.loe.model.LotHistory;

/**
 * @author amahajan
 * 
 *         This class contains all the common methods.
 */
public class CommonUtil {

	/**
	 * This method is used to change the date format to MM/dd/yyyy.
	 * 
	 * @param compDate
	 * @return dateString
	 */
	public static String changeDateFormat(Date compDate) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = dateFormat.format(compDate);
		return dateString;
	}

	/**
	 * This method is used to append the list values with quotes e.g one is
	 * change to "one".
	 * 
	 * @param inputlist
	 * @return result
	 */
	public static List<String> appendQuotesInListValues(List<String> inputlist) {
		List<String> result = new ArrayList<String>();
		for (String s : inputlist) {
			StringBuilder builder = new StringBuilder();
			if (s != null && !s.equals("")) {
				builder.append("\"").append(s).append("\"");
				result.add(builder.toString());
			}
		}
		return result;
	}

	/**
	 * This method is used to convert the list of string to json format.
	 * 
	 * @param inputlist
	 * @return json
	 */
	public static String convertListToJsonFormat(List<String> inputlist) {
		String json = new Gson().toJson(inputlist);
		return json;
	}

	/**
	 * This method is used to check if the data on the complaint info page is
	 * saved or not.
	 * 
	 * @param compInfo
	 * @return boolean
	 */
	public static boolean isComplaintInfoSaved(ComplaintInfo complaintInfo) {
		if (complaintInfo == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to save excel sheet of MyReports in directory
	 * 
	 * 
	 * 
	 */
	public static String writeCsvFile(String fileName, List<LotHistory> data)
			throws Exception {
		System.out.println("save csv file report method");
		// Delimiter used in CSV file
		final String COMMA_DELIMITER = ",";
		final String NEW_LINE_SEPARATOR = "\n";

		// CSV file header
		final String FILE_HEADER = "Request Id,Drug Name,Strength,Complaint Date,Complaint Desc, Last Modified Date, Lot Numbers, Group Name";
		// final String FILE_HEADER =
		// "Request Id,Drug Name,Strength,Complaint Desc,  Lot Numbers";
		StringBuffer fileData = new StringBuffer();
		String fileNewName = "";
		File newFile = new File(fileName);
		if (newFile.isDirectory()) {
			fileName = fileName + "\\"
					+ Calendar.getInstance().get(Calendar.MONTH) + "_"
					+ Calendar.getInstance().get(Calendar.DATE) + "_"
					+ Calendar.getInstance().get(Calendar.YEAR) + ".csv";
			newFile = new File(fileName);
			newFile.createNewFile();
			fileNewName = Calendar.getInstance().get(Calendar.MONTH) + "_"
					+ Calendar.getInstance().get(Calendar.DATE) + "_"
					+ Calendar.getInstance().get(Calendar.YEAR) + ".csv";
		}
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);

			fileWriter.append(FILE_HEADER.toString());

			fileWriter.append(NEW_LINE_SEPARATOR);

			fileData.append(FILE_HEADER);
			fileData.append(NEW_LINE_SEPARATOR);
			Iterator<LotHistory> iter = data.iterator();
			while (iter.hasNext()) {
				LotHistory lot = iter.next();
				fileWriter.append(lot.getReqId());
				fileWriter.append(COMMA_DELIMITER);

				fileData.append(lot.getReqId());
				fileData.append(COMMA_DELIMITER);

				fileWriter.append(lot.getDrugName());
				fileWriter.append(COMMA_DELIMITER);

				fileData.append(lot.getDrugName());
				fileData.append(COMMA_DELIMITER);

				fileWriter.append(lot.getStrength());
				fileWriter.append(COMMA_DELIMITER);

				fileData.append(lot.getStrength());
				fileData.append(COMMA_DELIMITER);

				fileWriter.append(lot.getDateOfComplain().toString());
				fileWriter.append(COMMA_DELIMITER);

				fileData.append(lot.getDateOfComplain());
				fileData.append(COMMA_DELIMITER);

				fileWriter.append(lot.getComplainDesc());
				fileWriter.append(COMMA_DELIMITER);

				fileData.append(lot.getComplainDesc());
				fileData.append(COMMA_DELIMITER);

				fileWriter.append(lot.getLastModifiedDate().toString());
				fileWriter.append(COMMA_DELIMITER);

				fileData.append(lot.getLastModifiedDate());
				fileData.append(COMMA_DELIMITER);

				fileData.append(lot.getLotNumbers());
				fileData.append(COMMA_DELIMITER);

				fileWriter.append(lot.getLotNumbers().replace(",", "|"));
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(lot.getGroupName());
				fileWriter.append(COMMA_DELIMITER);

				fileData.append(lot.getGroupName());
				fileData.append(COMMA_DELIMITER);

				fileWriter.append(NEW_LINE_SEPARATOR);

			}

			System.out.println("CSV file was created successfully !!!"
					+ fileData.toString().length());
			return fileNewName;
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
			return null;
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out
						.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

}
