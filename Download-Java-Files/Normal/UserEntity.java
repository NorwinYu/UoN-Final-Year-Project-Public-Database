package at.jugger.tracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 20)
    @NonNull
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    @NonNull
    private String lastName;

    @Column(name = "email", nullable = false, length = 50)
    @NonNull
    private String email;

    @Column(name = "role", nullable = false, length = 20)
    @NonNull
    private String role;

    @Column(name = "trackable", nullable = false)
    private boolean trackable;
}
