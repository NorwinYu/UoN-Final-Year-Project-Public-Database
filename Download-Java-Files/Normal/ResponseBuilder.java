package propra2.leihOrDie.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import propra2.leihOrDie.model.Buy;
import propra2.leihOrDie.model.User;

public class ResponseBuilder {
    public ResponseEntity createBadRequestResponse(String errorMessage) {
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity createSuccessResponse(String successMessage) {
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    public ResponseEntity createUnauthorizedResponse() {
        String errorMessage = "Du bist nicht authorisiert diese Aktion auszuführen.";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity createProPayErrorResponse() {
        String errorMessage = "Fehler bei der Verarbeitung der Zahlung.";
        return new ResponseEntity<>(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity createProPayErrorResponse(User user, Buy buy) {
        String errorMessage = "Es war nicht möglich den Betrag zu überweisen. Bitte sende eine Email mit der genauen Beschreibung Deines Problems und dem Betreff \"" + user.getUsername() + " - " + buy.getId().toString() + "\" an conflict@leihordie.de";
        return new ResponseEntity<>(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
    }
}