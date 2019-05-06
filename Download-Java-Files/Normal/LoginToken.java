package at.jugger.tracker.service.dto;

import at.jugger.tracker.dto.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoginToken {
    private User user;
    private String token;
    private LocalDateTime expiryDate;
}
