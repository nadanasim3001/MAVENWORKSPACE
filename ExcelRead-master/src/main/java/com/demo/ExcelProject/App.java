package com.demo.ExcelProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		CSVParser parser = new CSVParser(new FileReader("C:/Users/junai/Downloads/Document3.csv"),
				CSVFormat.DEFAULT.withHeader());
		Map<String, Integer> retMap = parser.getHeaderMap();

		List<CSVRecord> retMap3 = parser.getRecords();

		// System.out.println(retMap3.get(1));
		// CSVRecord st = retMap3.get(0).get(null);

		// System.out.println(retMap3);
		System.out.println(retMap3.get(0));
		testCSV();

		Map<Integer, String> retMap2 = new HashMap<>();
		// System.out.println(retMap);
		String response = "";
		// System.out.println(retMap.size());

		for (int i = 0; i < retMap.size(); i++) {
			// response=response+retMap.get(i);
			// retMap2.put(retMap.get, response)
		}

		Map<Integer, String> myNewHashMap = new HashMap<>();
		for (Map.Entry<String, Integer> entry : retMap.entrySet()) {
			myNewHashMap.put(entry.getValue(), entry.getKey());
		}

		for (int i = 0; i < myNewHashMap.size(); i++) {
			response = response + myNewHashMap.get(i) + "##";
			// retMap2.put(retMap.get, response)
		}
		response = response.substring(0, response.length() - 2);

		// System.out.println(myNewHashMap);
		// System.out.println(response);
	}
	
	

	public static void testCSV() throws IOException {

		 
		StringBuilder sb=null;
		int count=0;
		//Reader reader = Files.newBufferedReader(Paths.get("C:/Users/junai/Downloads/Document3.csv"));
		Reader in = new FileReader("C:/Users/junai/Downloads/BulkEmployees.csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
		for (CSVRecord record : records) {
			String values="";
			if(count>0) {
			for(int i=0; i<record.size(); i++) {
				values=values+record.get(i)+",";
			}
			 sb = new StringBuilder(values);
			sb.deleteCharAt(values.length()-1);
			//sb=sb.append("~~");
		    String columnOne = record.get(0);
		    String columnTwo = record.get(1);
		    
			} 
		    count++;
		    System.out.println(sb);
		}
		
		//System.out.println(record.size());
		   
		/*
		 * public static String
		 * parseCSV2("C://Users//junai//Downloads//BulkEmployees.csv") throws
		 * FileNotFoundException, IOException { CSVParser parser = new CSVParser(new
		 * FileReader(fileLocation), CSVFormat.DEFAULT.withHeader()); String st2 =
		 * parser.getHeaderMap().toString(); return "ok"; }
		 */
	}

}
