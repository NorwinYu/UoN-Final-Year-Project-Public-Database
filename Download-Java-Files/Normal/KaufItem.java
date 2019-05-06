package hhu.ausleihservice.databasemodel;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class KaufItem extends Item {
	private String titel = "";
	@Lob
	private String beschreibung = "";
	private Integer kaufpreis;
	private Status status;

	public Status getStatus() {
		if (this.status == null) {
			return null;
		}
		return this.status;
	}
}
