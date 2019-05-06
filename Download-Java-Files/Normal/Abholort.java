package hhu.ausleihservice.databasemodel;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Abholort {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Double latitude;
	private Double longitude;
	private String beschreibung = "";

	public void setBeschreibung(String s) {
		if (s != null) {
			beschreibung = s.trim();
		}
	}
}
