package recruitment.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>This class represents the different competences a person can have.</p>
 */
@Entity
@Table(name = "COMPETENCE")
public class Competence {
    private static final String SEQUENCE_NAME_KEY = "SEQ_NAME";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME_KEY)
    @SequenceGenerator(name = SEQUENCE_NAME_KEY, sequenceName = "COMPETENCE_SEQUENCE")
    @Column(name = "COMPETENCE_ID")
    private int competenceId;

    @NotNull
    @Column(name = "NAME", unique = true)
    private String name;

    @OneToMany
    private Set<CompetenceProfile> competenceProfiles = new HashSet<>();

    /**
     * Creates an instance of a competence, from the specified name.
     *
     * @param competenceId The identifier.
     * @param name The name of the competence.
     */
    public Competence(int competenceId, String name) {
        this.competenceId = competenceId;
        this.name = name;
    }

    protected Competence() {

    }

}


