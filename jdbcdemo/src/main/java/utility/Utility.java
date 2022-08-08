package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import com.jdbcdemo.jdbcdemo.coreCall.CoreServiceCall;
import com.jdbcdemo.jdbcdemo.dto.BaseOutput;
import com.jdbcdemo.jdbcdemo.dto.InsertEmployeeList;

public class Utility {

	public String changeData(ArrayList<InsertEmployeeList> arrayData, String seperator, Object ob) {

		String stringData = "";
		String tempString = "";

		for (InsertEmployeeList empList : arrayData) {
			tempString = empList.getDepartmentId() + "$$" + empList.getCommisionPct();

		}

		return stringData;
	}
	
	public static String createTable(String tableName, String fileLocation) throws FileNotFoundException, IOException {

		CSVParser parser = new CSVParser(new FileReader(fileLocation), CSVFormat.DEFAULT.withHeader());

		Map<String, Integer> retMap = parser.getHeaderMap();

		List<CSVRecord> retMap3 = parser.getRecords();

		String response = "Success";

		// System.out.println(retMap3.get(1));
		// CSVRecord st = retMap3.get(0).get(null);

		// System.out.println(retMap3);
		// System.out.println(retMap3.get(0));
		// testCSV();

		Map<Integer, String> retMap2 = new HashMap<>();
		// System.out.println(retMap);
		String columnNames = "";
		// System.out.println(retMap.size());

		Map<Integer, String> myNewHashMap = new HashMap<>();
		for (Map.Entry<String, Integer> entry : retMap.entrySet()) {
			myNewHashMap.put(entry.getValue(), entry.getKey());
		}
		// System.out.println(myNewHashMap);
		for (int i = 0; i < myNewHashMap.size(); i++) {
			columnNames = columnNames + myNewHashMap.get(i) + "##";
			// System.out.println(response);
			// retMap2.put(retMap.get, response)
		}
		columnNames = columnNames.substring(0, columnNames.length() - 2);
		columnNames = columnNames.replace(' ', '_');

		try {
			response = CoreServiceCall.createTable(tableName, columnNames);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;

	}
	

	public BaseOutput insertDataInTable(String tableName, String location) {

		StringBuilder sb = null;
		BaseOutput response = new BaseOutput();
		int count = 0;
		int i;
		// Reader reader =
		// Files.newBufferedReader(Paths.get("C:/Users/junai/Downloads/Document3.csv"));
		try {
			Reader in = new FileReader(location);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
			for (CSVRecord record : records) {
				String values = "";
				if (count > 0) {
					for (i = 0; i < record.size() - 1; i++) {
						values = values + record.get(i) + "~~";
					}
					values = values.replace('\'', ' ');
					values = values.replace('(', ' ');
					values = values.replace(')', ' ');
					values = values.replace(',', ' ');
					values = values.replace('/', ' ');
					// values=values.replace('-', ' ');
					// values=values.replace('#', ' ');
					if (i == record.size() - 1) {
						values = values + record.get(i);
					}
					sb = new StringBuilder(values);
					// sb.deleteCharAt(values.length() - 3);

				}

				System.out.println(sb);
				if (count > 0) {
					try {
						CoreServiceCall csc = new CoreServiceCall();
						response = csc.insertDataInTable(tableName, sb.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				count++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public static String convertCLOBToString(java.sql.Clob clobObject) {

		String clobAsString = null;

		try {
			InputStream in = clobObject.getAsciiStream();
			StringWriter w = new StringWriter();
			IOUtils.copy(in, w);
			clobAsString = w.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clobAsString;
	}

	public BaseOutput fetchAndWriteTableDataQuery(String tableName) {
		BaseOutput response = new BaseOutput();

		String clobString = "";
		ArrayList<String> masterQueryList = new ArrayList<>();
		CoreServiceCall csc = new CoreServiceCall();

		try {
			clobString = csc.getClobTableData(tableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String superMasterQuery = "";
		// Utility util = new Utility();
		try {
			masterQueryList = convertTableStringToQuery(clobString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String masterQuery : masterQueryList) {

			superMasterQuery = superMasterQuery + "\r\n" + masterQuery;

		}
		System.out.println(tableName);
		System.out.println(superMasterQuery);

		try {
			response = createAndWriteInAndWriteInAFile(tableName, superMasterQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	public BaseOutput createAndWriteInAndWriteInAFile(String tableName, String tableQuery) {

		BaseOutput output = new BaseOutput();
		String dir = "C:\\My_Workbench\\MAVENWORKSPACE\\DATABASE\\FILES\\SCHEMA\\DEV\\TABLE_DATA_SCRIPTS\\";

		try {
			createAFile(tableName, dir);
			witeInAFile(tableName, tableQuery, dir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		output.setErrorCode(0);
		output.setErrorDesc("Success");

		return output;

	}

	public static void createAFile(String tableName, String dir) {

		// String location = "C:\\Users\\junai\\OneDrive\\Documents\\";
		String fileName = dir + tableName + ".sql";
		System.out.println(fileName);

		try {
			File myObj = new File(fileName);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();

		}
	}

	public static void witeInAFile(String tableName, String tableScrpts, String dir) {

		// String location = "C:\\Users\\junai\\OneDrive\\Documents\\";
		String fileName = dir + tableName + ".sql";
		System.out.println(fileName);

		try {
			FileWriter myWriter = new FileWriter(fileName);
			myWriter.write(tableScrpts);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public ArrayList<String> convertTableStringToQuery(String rawSQLData) {

		String newSt = rawSQLData;

		newSt = newSt.substring(0, newSt.length() - 3);
		System.out.println(newSt);
		String masterQuery = "";

		ArrayList<String> masterQueryList = new ArrayList<>();

		String masterData[] = newSt.split("~~~");

		for (int j = 0; j < masterData.length; j++) {

			String a = masterData[j];
			// System.out.println(a);

			// a = "JOB_HISTORY####EMPLOYEE_ID,START_DATE,END_DATE,JOB_ID,DEPARTMENT_ID ####
			// 200,01-JUL-02,31-DEC-06,AC_ACCOUNT,90####NUMBER,DATE,DATE,VARCHAR2,NUMBER";

			String ar[] = a.split("####");

			/*
			 * System.out.println(ar[0]); System.out.println(ar[1]);
			 * System.out.println(ar[2].trim()); System.out.println(ar[3]);
			 */
			try {
				String tableName = ar[0];
				String rows[] = ar[1].split(",");
				String tableData[] = ar[2].split(",");
				String tableDataType[] = ar[3].split(",");

				String rowName = "";
				String valueName = "";
				String v = ") values (";
				String ends = "');";
				String dateTypeFlag = "";

				String aa = "insert into " + tableName + " (";

				// System.out.println(aa);

				for (int i = 0; i < rows.length; i++) {

					rowName = rowName + "," + rows[i];
					if (!tableDataType[i].equals("DATE")) {

						valueName = valueName + "','" + tableData[i].trim();

					}
					if (tableDataType[i].trim().equals("DATE")) {
						// valueName=valueName+"','"+tableData[i].trim();
						dateTypeFlag = "T";
						valueName = valueName + "',to_date('" + tableData[i].trim() + "','dd-mm-yyyy')";

						// System.out.println("substringTest-------"+valueName.substring(valueName.length()-1,
						// valueName.length()));
					}
				}

				rowName = rowName.substring(1);
				valueName = valueName.substring(2);

				String fromReplace = "\\)',to_date\\('";
				String toReplace = "\\),to_date\\('";

				// System.out.println(v + valueName);
				String query = aa + rowName + v + valueName + ends;
				// System.out.println(query);
				masterQuery = query.replaceAll(fromReplace, toReplace);

				fromReplace = "-yyyy'\\)',";
				toReplace = "-yyyy'\\),";
				masterQuery = query.replaceAll(fromReplace, toReplace);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Some error");
			}

			masterQueryList.add(masterQuery);

		}
		return masterQueryList;
	}

	


}
