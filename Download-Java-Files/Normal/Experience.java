package tivoli.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXP_ID")
    private int id;

    @NotNull(message = "{experience.account.missing}")
    @ManyToOne
    @JoinColumn(name = "EXP_ACCOUNT")
    private Account account;

    @NotNull(message = "{experience.position.missing}")
    @ManyToOne
    @JoinColumn(name = "EXP_POS")
    private Position position;

    @NotNull(message = "{experience.years.missing}")
    @Column(name = "EXP_YEARS")
    private double years;

    /**
     * part of an application and creates an experience object which details a previous job experience
     * @param account the user who have had the experience
     * @param position the position that the user worked as
     * @param years the time that they worked
     */
    public Experience(Account account, Position position, double years){

        this.account = account;
        this.position = position;
        this.years = years;
    }

    /**
     * Required by JPA. Do not use.
     */
    protected Experience(){

    }

    /**
     * gets the id of the experience object
     * @return id key of the experience object
     */
    public long getId(){
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Position getPosition() { return position; }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setYears(double years) {
        this.years = years;
    }

    public double getYears() {
        return years;
    }
}


