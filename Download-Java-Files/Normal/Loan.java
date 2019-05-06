package propra2.leihOrDie.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="loan_id")
    private Long id;

    private String state;
    private int duration;
    private long proPayReservationId;

    @ManyToOne
    @JoinColumn(name="username")
    private User user;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    private LocalDate dayOfRental;
    private LocalDate dayOfReturn;
    private boolean isExceeded;

    public Loan() {}

    public Loan(String state, int duration, User user, Item item, long proPayReservationId) {
        this.proPayReservationId = proPayReservationId;
        this.state = state;
        this.duration = duration;
        this.user = user;
        this.item = item;
        this.isExceeded = false;
    }
}
