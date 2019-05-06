package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.Abholort;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AbholortValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Abholort.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Abholort abholort = (Abholort) target;

		validateLatitude(abholort, errors);
		validateLongitude(abholort, errors);
		validateDescriptionEmpty(errors);
		validateDescriptionSize(abholort, errors);
	}

	private void validateDescriptionEmpty(Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beschreibung", Messages.notEmpty);
	}

	private void validateDescriptionSize(Abholort abholort, Errors errors) {
		if (abholort.getBeschreibung().length() < 6 || abholort.getBeschreibung().length() > 250) {
			errors.rejectValue("beschreibung", Messages.sizeLocationDescription);
		}
	}

	private void validateLongitude(Abholort abholort, Errors errors) {
		if (abholort.getLongitude() != null) {
			validateLongitudeOutOfBounds(abholort, errors);
		} else {
			validateNotEmpty(errors, "longitude", Messages.notEmpty);
		}
	}

	private void validateNotEmpty(Errors errors, String longitude, String notEmpty) {
		errors.rejectValue(longitude, notEmpty);
	}

	private void validateLongitudeOutOfBounds(Abholort abholort, Errors errors) {
		if (abholort.getLongitude() < -180 || abholort.getLongitude() > 180) {
			errors.rejectValue("longitude", Messages.longitudeOutOfBounds);
		}
	}

	private void validateLatitude(Abholort abholort, Errors errors) {
		if (abholort.getLatitude() != null) {
			validateLatitudeOutOfBounds(abholort, errors);
		} else {
			errors.rejectValue("latitude", Messages.notEmpty);
		}
	}

	private void validateLatitudeOutOfBounds(Abholort abholort, Errors errors) {
		if (abholort.getLatitude() < -90 || abholort.getLatitude() > 90) {
			errors.rejectValue("latitude", Messages.latitudeOutOfBounds);
		}
	}
}
