package recruitment.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.sql.*;
import javax.persistence.OneToMany;

/**
 * <p>This class represents availabilities connected to specified persons.</p>
 */
@Entity
@Table(name = "AVAILABILITY")
public class Availability{
    private static final String SEQUENCE_NAME_KEY = "SEQ_NAME";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME_KEY)
    @SequenceGenerator(name = SEQUENCE_NAME_KEY, sequenceName = "AVAILABILITY_SEQUENCE")
    @Column(name = "AVAILABILITY_ID")
    private int availabilityId;

    @NotNull
    @Column(name = "PERSON_ID")
    private int pid;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "FROM_DATE")
    private Date fromDate;

    @NotNull
    @Future
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "TO_DATE")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable=false)
    private Person person;

    public void setPerson(Person person){ this.person = person;}
    public Person getPerson() {return person;}


    /**
     * Creates a new instance of an availability for a certain person.
     *
     * @param availabilityId The id of the instance.
     * @param pid The id of the person relating to the specified instance.
     * @param fromDate The start date of the specified instance.
     * @param toDate The end date of the specified instance.
     */
    public Availability(int availabilityId, int pid, Date fromDate, Date toDate) {
        this.availabilityId = availabilityId;
        this.pid = pid;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    protected Availability() {

    }
}

