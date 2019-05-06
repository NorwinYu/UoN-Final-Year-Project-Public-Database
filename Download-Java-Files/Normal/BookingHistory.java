package com.hotel.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.joda.time.LocalDate;

@Entity
@Table(name="BOOKING-HISTORY")
public class BookingHistory {
	@Id
	private String bid;
	@NotNull
	private String uid;
	@NotNull
	private String transId;
	@NotNull
	private String hotID;
	@NotNull
	private int no_of_rooms;
	@NotNull
	private LocalDate CIND;
	@NotNull
	private LocalDate COTD;
	@NotNull
	private String PaymentMode;
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getHotID() {
		return hotID;
	}
	public void setHotID(String hotID) {
		this.hotID = hotID;
	}
	public int getNo_of_rooms() {
		return no_of_rooms;
	}
	public void setNo_of_rooms(int no_of_rooms) {
		this.no_of_rooms = no_of_rooms;
	}
	public LocalDate getCIND() {
		return CIND;
	}
	public void setCIND(LocalDate cIND) {
		CIND = cIND;
	}
	public LocalDate getCOTD() {
		return COTD;
	}
	public void setCOTD(LocalDate cOTD) {
		COTD = cOTD;
	}
	public String getPaymentMode() {
		return PaymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		PaymentMode = paymentMode;
	}
}
