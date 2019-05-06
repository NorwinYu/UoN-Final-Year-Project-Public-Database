package hhu.ausleihservice.databasemodel;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Period {

	private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

	private final LocalDate start;
	private final LocalDate end;

	public Period(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}


	public String toString() {

		if (start.isAfter(end)) {
			return "Invalid";
		}
		if (start.equals(end)) {
			return start.format(DATEFORMAT);
		}

		return start.format(DATEFORMAT) + " - " + end.format(DATEFORMAT);
	}
}
