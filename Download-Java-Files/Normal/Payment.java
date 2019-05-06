package com.hotel.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Payment")
public class Payment {
	@Id
	private String transID;
	@NotNull
	private String bid;
	public String getTransID() {
		return transID;
	}
	public void setTransID(String transID) {
		this.transID = transID;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public double getAmount_payable() {
		return amount_payable;
	}
	public void setAmount_payable(double amount_payable) {
		this.amount_payable = amount_payable;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	@NotNull
	private double amount_payable;
	@NotNull
	private String payment_mode;
}
