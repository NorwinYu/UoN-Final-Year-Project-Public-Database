package hhu.ausleihservice.databasemodel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"ausleiher", "item"})
public class Ausleihe {
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;
	@ManyToOne
	private AusleihItem item;
	@ManyToOne
	private Person ausleiher;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDatum;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDatum;
	private Long reservationId;
	private boolean konflikt = false;
	private Status status;
}
