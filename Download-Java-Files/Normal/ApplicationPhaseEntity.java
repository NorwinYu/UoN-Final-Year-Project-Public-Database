package at.jugger.tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "application_phase")
@NoArgsConstructor
public class ApplicationPhaseEntity {
    @Id
    @Column(name = "application_phase_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long applicationPhaseId;

    @Column(name = "from_date", nullable = false)
    @NonNull
    private LocalDate from;

    @Column(name = "to_date", nullable = false)
    @NonNull
    private LocalDate to;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false, insertable = false, updatable = false)
    private TournamentEntity tournament;

    @ManyToMany
    @JoinTable(name = "user_applies_for_phase",
            joinColumns = @JoinColumn(name = "application_phase_id", referencedColumnName = "application_phase_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, updatable = false))
    private List<UserEntity> participants;
}
