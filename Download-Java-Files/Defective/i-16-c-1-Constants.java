package com.qa.apartmentManager.apartmentapi.constants;

public class Constants {
	
	public static final String H2MESSAGE = "{\"message\": \"H2 updated\"}";
	
	public static final String SUCCESSMSG = "{\"message\": \"Action successful\"}";
	
	public static final String FAILMSG = "{\"message\": \"Action failed\"}";
	
	public static final String CLEANSTATUSQUERY = "SELECT u FROM ApartmentManager u WHERE u.cleanStatus = :value";
	
	public static final String ISOCCUPIEDQUERY = "SELECT u FROM ApartmentManager u WHERE u.occupied = :value";
	
	public static final String FINDINTAKEQUERY = "SELECT u FROM ApartmentManager u WHERE u.intake = :value";
	
	public static final String FINDAPARTMENTQUERY = "SELECT u FROM ApartmentManager u WHERE u.apartmentBuilding = :apartmentBuilding AND u.apartmentNumber = :apartmentNumber";
	
}
