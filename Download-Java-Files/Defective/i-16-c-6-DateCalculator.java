package com.qa.apartmentManager.util;

import java.util.ArrayList;
import java.util.List;

import com.qa.apartmentManager.apartmentapi.persistence.domain.ApartmentManager;

public class DateCalculator {

	public static List<ApartmentManager> verifyDates(List<ApartmentManager> allApts, String[] dates) {
		List<ApartmentManager> afterRequired = new ArrayList<>();
		for (ApartmentManager apt : allApts) {
			if (apt.getStartDate() == null) {
				apt.setOccupied(false);
				continue;
			}
			apt.setOccupied(true);
			String[] startDates = apt.getStartDate().split("-");
			String[] endDates = apt.getEndDate().split("-");

			for (int i = 0; i < 3; i++) {
				if (!compareInBetween(startDates[i], dates[i], endDates[i])) {
					if (compareDateBefore(dates[i], startDates[i]).equals("earlier")) {
						apt.setOccupied(false);
						afterRequired.add(apt);
					} else if (compareDateBefore(dates[i], endDates[i]).equals("later")) {
						apt.setOccupied(false);
					}
				}
			}
		}
		return checkForPrevious(allApts, afterRequired, dates);
	}

	public static List<ApartmentManager> checkForPrevious(List<ApartmentManager> all,
			List<ApartmentManager> afterRequired, String[] dates) {
		for (ApartmentManager apt : all) {
			for (ApartmentManager toCheck : afterRequired) {
				if (apt.getStartDate() != null) {
					if (apt.getApartmentBuilding().equals(toCheck.getApartmentBuilding())
							&& apt.getApartmentNumber() == toCheck.getApartmentNumber()
							&& apt.getRoomNumber() == toCheck.getRoomNumber()) {
						String[] startDateOriginal = apt.getStartDate().split("-");
						String[] startDateToCheck = toCheck.getStartDate().split("-");
						for (int i = 0; i < 3; i++) {
							if (compareDateBefore(startDateOriginal[i], startDateToCheck[i]).equals("earlier")) {
								apt.setOccupied(true);
							}
						}
					}
				}
			}
		}
		return all;
	}

	public static List<ApartmentManager> checkForMultipleIntakes(List<ApartmentManager> all,
			List<ApartmentManager> afterRequired) {
		List<Long> ids = new ArrayList<>();
		for (ApartmentManager apt : all) {
			for (ApartmentManager toCheck : afterRequired) {
				try {
					if (apt.getStartDate() != null) {
						if (apt.getApartmentBuilding().equals(toCheck.getApartmentBuilding())
								&& apt.getApartmentNumber() == toCheck.getApartmentNumber()
								&& apt.getRoomNumber() == toCheck.getRoomNumber()) {
							String[] startDateOriginal = apt.getStartDate().split("-");
							String[] startDateToCheck = toCheck.getStartDate().split("-");
							for (int i = 0; i < 3; i++) {

								if (compareDateBefore(startDateOriginal[i], startDateToCheck[i]).equals("earlier")) {
									apt.setOccupied(false);
									ids.add(apt.getApartmentId());
								} else if (compareDateBefore(startDateOriginal[i], startDateToCheck[i])
										.equals("same")) {
								}
								else {
									apt.setOccupied(true);
									ids.add(apt.getApartmentId());
								}
							}
						}
					}
					else {
						apt.setOccupied(false);
						ids.add(apt.getApartmentId());
					}
				} catch (Exception e) {
					apt.setOccupied(false);
					ids.add(apt.getApartmentId());
				}
			}
		}
		for (ApartmentManager apt : all) {
			int found = 0;
			for (Long id : ids) {
				if (apt.getApartmentId() == id) {
					found = 1;
				}
			}
			if (found == 0) {
				apt.setOccupied(false);
			}
		}
		return all;
	}

	public static String compareDateBefore(String requestDate, String foundDate) {
		int reqDate = Integer.parseInt(requestDate);
		int foundDat = Integer.parseInt(foundDate);
		if (reqDate == foundDat) {
			return "same";
		} else if (reqDate < foundDat) {
			return "earlier";
		}
		return "later";
	}

	public static boolean compareInBetween(String startDate, String endDate, String requestDate) {
		int start = Integer.parseInt(startDate);
		int end = Integer.parseInt(endDate);
		int request = Integer.parseInt(requestDate);
		if (start <= request && request <= end) {
			return true;
		}
		return false;
	}
}
