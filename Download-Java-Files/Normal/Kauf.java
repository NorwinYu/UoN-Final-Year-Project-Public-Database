package hhu.ausleihservice.databasemodel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"kaeufer", "item"})
public class Kauf {
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private KaufItem item;
	@ManyToOne
	private Person kaeufer;
}
