package propra2.leihOrDie.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

@Data
public class UserForm {
    @NotNull
    @Size(min = 1, max = 50, message="Benutzernamen müssen 1-50 Zeichen lang sein")
    private String username;

    @NotNull
    @Size(min = 1, message = "Dieses Feld darf nicht leer bleiben")
    @Email(message = "Gib eine gültige Email-Adresse ein")
    private String email;

    @NotNull
    @Size(min = 6, max = 50, message="Bitte gib ein (sicheres) Passwort zwischen 6-50 Zeichen ein.")
    private String password;

    @NotNull
    @Size(min = 5, message="Straßennamen müssen mindestens 5 Zeichen lang sein.")
    private String street;

    @NotEmpty
    @Size(min = 1, message="Dieses Feld darf nicht leer bleiben.")
    private String houseNumber;

    @NotNull
    @Size(min = 5, message="Stadtnamen müssen mindestens 5 Zeichen lang sein.")
    private String city;

    @NotNull
    @Size(min = 5, message="Eine Postleitzahl hat mindestens 5 Ziffern.")
    private String postcode;
}

