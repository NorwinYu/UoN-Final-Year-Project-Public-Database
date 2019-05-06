package com.qa.apartmentManager.apartmentapi.service;


import java.text.ParseException;
import java.util.List;

import com.qa.apartmentManager.apartmentapi.persistence.domain.ApartmentManager;

public interface ApartmentManagerService {

	ApartmentManager getApartmentManager(Long id);
	
	ApartmentManager addApartmentManager(ApartmentManager apartmentmanager);
	
	String deleteApartmentManager(Long Id);
	
	String updateApartmentManager(ApartmentManager apartmentManager, Long Id);
	
	String upDateH2(List<ApartmentManager> toSave);
	
	List<ApartmentManager> getApartmentManager();
	
	List<ApartmentManager> getCurrentApartmentManager(String intake);
	
	List<ApartmentManager> getApartmentByDetails(String apartmentBuilding, int apartmentNumber);
	
	List<ApartmentManager> getByACleanStatus(String value);
	
	List<ApartmentManager> getIsOccupied(boolean value);
	
	List<ApartmentManager> getIntake(String value);
	
	List<ApartmentManager> checkDate(String date) throws ParseException;
}
