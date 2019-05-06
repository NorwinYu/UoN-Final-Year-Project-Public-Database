package at.jugger.tracker.service.impl.template;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AuthenticationEmailData {
    private String vorname;
    private String authenticationUrl;
    private LocalDateTime ablaufdatum;
}
