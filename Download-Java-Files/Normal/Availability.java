package tivoli.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="Availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVAIL_ID")
    private int id;

    @NotNull(message = "{availability.account.missing}")
    @ManyToOne
    @JoinColumn(name = "AVAIL_ACCOUNT")
    private Account account;

    @NotNull(message = "{availability.from.missing}")
    @Column(name = "AVAIL_FROM")
    private Date from;

    @NotNull(message = "{availability.to.missing}")
    @Column(name = "AVAIL_TO")
    private Date to;

    /**
     * part of an application, details when a user is able to work
     * @param account the account who made the application
     * @param from starting date when the applicant gets available to work
     * @param to finishing date when the applicant can no longer work
     */
    public Availability(Account account, Date from, Date to){
        this.account = account;
        this.from = from;
        this.to = to;
    }

    /**
     * Used by JPA. Do not use.
     */
    protected Availability(){

    }

    /**
     * gets id of availability object
     * @return id key of availability object
     */
    public long getId(){
        return id;
    }

    /**
     * gets the from date of applicant
     * @return starting date when applicant can start to work
     */
    public Date getFrom(){
        return from;
    }

    /**
     * gets the to date of applicant
     * @return to date when applicant can no longer work
     */
    public Date getTo(){
        return to;
    }

    /**
     * changes the from and to dates
     * @param from starting date when applicant can start to work
     * @param to date when applicant can no longer work
     */
    public void editAvailability(Date from, Date to){
        this.from = from;
        this.to = to;

    }

}
