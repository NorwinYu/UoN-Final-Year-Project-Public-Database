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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "login_token")
@NoArgsConstructor
public class LoginTokenEntity {
    @Id
    @Column(name = "token_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long tokenId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(name = "token", nullable = false, unique = true)
    @NonNull
    private String token;

    @Column(name = "expiry_date", nullable = false)
    @NonNull
    private LocalDateTime expiryDate;

    @Column(name = "used")
    private boolean used;
}
