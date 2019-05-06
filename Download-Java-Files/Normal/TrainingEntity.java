package at.jugger.tracker.domain;

import at.jugger.tracker.dto.Training;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "training")
@NoArgsConstructor
public class TrainingEntity {
    @Id
    @Column(name = "training_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long trainingId;

    @Column(name = "date", nullable = false, unique = true)
    @NonNull
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Training.TypeEnum type;

    @ManyToMany
    @JoinTable(name = "user_participates_training",
            joinColumns = @JoinColumn(name = "training_id", referencedColumnName = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    private List<UserEntity> participants;
}
