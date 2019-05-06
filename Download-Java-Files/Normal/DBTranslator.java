package com.qa.apartmentManager.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.qa.apartmentManager.apartmentapi.persistence.domain.ApartmentManager;

public class DBTranslator {

	@SuppressWarnings("rawtypes")
	public static List<ApartmentManager> getForMongo(Object[] mongoList) {
		List<ApartmentManager> toConvert = new ArrayList<>();

		for (Object obj : mongoList) {
			LinkedHashMap map = (LinkedHashMap) obj;
			int id = (int) map.get("apartmentId");
			String some = JSONUtil.getJSONForObject(obj);
			ApartmentManager toPrint = JSONUtil.getObjectForJSON(some, ApartmentManager.class);
			toPrint.setApartmentId((long) id);
			toConvert.add(toPrint);
		}
		return toConvert;
	}
	
}
