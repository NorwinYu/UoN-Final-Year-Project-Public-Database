package hhu.ausleihservice.web.responsestatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ausleihe nicht vorhanden")
	public class AusleiheNichtVorhanden extends RuntimeException {
	}
