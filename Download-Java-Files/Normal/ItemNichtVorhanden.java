package hhu.ausleihservice.web.responsestatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Item nicht vorhanden")
public class ItemNichtVorhanden extends RuntimeException {

}
