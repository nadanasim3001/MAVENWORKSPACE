package com.irctc.gettrainbetweenstations.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irctc.gettrainbetweenstations.dto.TrainData;
import com.irctc.gettrainbetweenstations.dto.TrainStationResponse;

public class RetrieveData {

	public TrainStationResponse getDataFromJson(String st1) {

		TrainStationResponse response = new TrainStationResponse();

		Map<String, Object> map = new HashMap<>();
		ArrayList<TrainData> trainDetails = new ArrayList<TrainData>();

		ObjectMapper mapper = new ObjectMapper();
		try {

			// convert JSON string to Map
			map = mapper.readValue(st1, Map.class);
			response.setStatus(map.get("status").toString());
			response.setMessage(map.get("message").toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Object> ar = new ArrayList<>();
		map.remove("run_days");
		map.remove("class_type");
		ar = (List<Object>) map.get("data");

		// System.out.println(ar);

		for (Object ar1 : ar) {
			// System.out.println(ar1);

			StringBuffer st4 = new StringBuffer(ar1.toString());

			st4.replace(ar1.toString().length() - 1, ar1.toString().length(), "");
			st4.replace(0, 1, "");
			// System.out.println(st4);
			Map<String, String> retMap = new HashMap<>();
			TrainData data = new TrainData();

			for (String st3 : st4.toString().split(",")) {

				if (st3.contains("run_days") == false) {
					if (st3.contains("=") == true) {
						if (st3.contains("[") == false) {
							// System.out.println(st3.trim());

							String arr3[] = st3.trim().split("=");

							retMap.put(arr3[0], arr3[1]);

							System.out.println(retMap);

							/*
							 * String st4[] = st3.split("="); if (st4[0]!=("run_days")) {
							 * 
							 * try { System.out.println(st4[0]+":"+st4[1]); } catch (Exception e) { // TODO
							 * Auto-generated catch block
							 * 
							 * } }
							 */
						}
					}
				}

			}
			data.setTrain_number(retMap.get("train_number"));
			data.setTrain_name(retMap.get("train_name"));
			data.setTrain_type(retMap.get("train_type"));
			data.setTrain_origin_station(retMap.get("train_origin_station"));
			data.setTrain_origin_station_code(retMap.get("train_origin_station_code"));
			data.setTrain_destination_station(retMap.get("train_destination_station"));
			data.setTrain_destination_station_code(retMap.get("train_destination_station_code"));
			data.setDepart_time(retMap.get("depart_time"));
			data.setArrival_time(retMap.get("arrival_time"));
			data.setDistance(retMap.get("distance"));
			trainDetails.add(data);
			System.out.println("####################################################################");
		}
		response.setData(trainDetails);

		return response;

	}

}
