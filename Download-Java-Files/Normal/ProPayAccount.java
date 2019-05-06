package hhu.ausleihservice.propay;

import lombok.Data;

import java.util.Set;

@Data
public class ProPayAccount {

	private String account;
	private double amount;
	private Set<ProPayReservation> reservations;
}
