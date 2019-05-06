package com.hotel.Service;

import java.util.List;

import com.hotel.model.Payment;

public interface PaymentService {
	public void add(Payment payment);

	public void edit(String trans_id);

	public void remove(String trans_id);

	public Payment view(String trans_id);

	public List<Payment> viewAll();
}
