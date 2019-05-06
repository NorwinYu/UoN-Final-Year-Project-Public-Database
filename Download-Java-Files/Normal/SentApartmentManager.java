package com.qa.apartmentManager.apartmentapi.persistence.domain;


public class SentApartmentManager {

	private Long apartmentId;
	
	private int apartmentNumber; 
	private String apartmentBuilding;
	private int roomNumber;
	private String studentName;
	private String intake;
	private String startDate;
	private String endDate;
	private String cleanStatus;
	private boolean occupied; 
	private boolean ensuite;
	private String bedStatus;
	private String notes;
	
	public SentApartmentManager(ApartmentManager apartmentManager) {
		this.apartmentNumber = apartmentManager.getApartmentNumber();
		this.apartmentBuilding = apartmentManager.getApartmentBuilding();
		this.roomNumber = apartmentManager.getRoomNumber();
		this.studentName = apartmentManager.getStudentName(); 
		this.intake = apartmentManager.getIntake();
		this.startDate = apartmentManager.getStartDate();
		this.endDate = apartmentManager.getEndDate();
		this.cleanStatus = apartmentManager.getCleanStatus();
		this.occupied = apartmentManager.isOccupied();
		this.ensuite = apartmentManager.isEnsuite();
		this.bedStatus = apartmentManager.getBedStatus(); 
		this.notes = apartmentManager.getNotes();
	}
	
	public SentApartmentManager() {
		
	}

	public Long getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}
	public int getApartmentNumber() {
		return apartmentNumber;
	}
	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}
	public String getApartmentBuilding() {
		return apartmentBuilding;
	}
	public void setApartmentBuilding(String apartmentBuilding) {
		this.apartmentBuilding = apartmentBuilding;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getIntake() {
		return intake;
	}
	public void setIntake(String intake) {
		this.intake = intake;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCleanStatus() {
		return cleanStatus;
	}
	public void setCleanStatus(String cleanStatus) {
		this.cleanStatus = cleanStatus;
	}
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public boolean isEnsuite() {
		return ensuite;
	}
	public void setEnsuite(boolean ensuite) {
		this.ensuite = ensuite;
	}
	public String getBedStatus() {
		return bedStatus;
	}
	public void setBedStatus(String bedStatus) {
		this.bedStatus = bedStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
