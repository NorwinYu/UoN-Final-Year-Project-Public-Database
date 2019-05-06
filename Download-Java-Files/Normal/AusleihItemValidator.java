package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.AusleihItem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class AusleihItemValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AusleihItem.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AusleihItem item = (AusleihItem) target;

		validateTitel(item, errors);
		validateBeschreibung(item, errors);
		validateKaufItem(item, errors);
		validateTagessatz(item, errors);
		validateAvailableFromAndAvailableTill(item, errors);
		validateAbholort(errors, item);
	}

	private void validateAbholort(Errors errors, AusleihItem item) {
		if (item.getAbholort() == null) {
			ValidationUtils.rejectIfEmpty(errors, "abholort", Messages.notEmpty);
		}
	}

	private void validateAvailableFromAndAvailableTill(AusleihItem item, Errors errors) {
		if (item.getAvailableFrom() != null && item.getAvailableTill() != null) {
			validateInvalidPeriod(item, errors);
			validateInvalidAvailableFrom(item, errors);
		} else {
			errors.rejectValue("availableFrom", Messages.notEmpty);
			errors.rejectValue("availableTill", Messages.notEmpty);
		}
	}

	private void validateInvalidAvailableFrom(AusleihItem item, Errors errors) {
		if (item.getAvailableFrom().isBefore(LocalDate.now())) {
			errors.rejectValue("availableFrom", Messages.invalidAvailableFrom);
		}
	}

	private void validateInvalidPeriod(AusleihItem item, Errors errors) {
		if (item.getAvailableFrom().isAfter(item.getAvailableTill())) {
			errors.rejectValue("availableFrom", Messages.invalidPeriod);
		}
	}

	private void validateTagessatz(AusleihItem item, Errors errors) {
		if (item.getTagessatz() != null) {
			validateNegativeTagessatz(item, errors);
		} else {
			errors.rejectValue("tagessatz", Messages.notEmpty);
		}
	}

	private void validateNegativeTagessatz(AusleihItem item, Errors errors) {
		if (item.getTagessatz() <= 0) {
			errors.rejectValue("tagessatz", Messages.negativeValue);
		}
	}

	private void validateKaufItem(AusleihItem item, Errors errors) {
		if (item.getKautionswert() != null) {
			if (item.getKautionswert() <= 0) {
				errors.rejectValue("kautionswert", Messages.negativeValue);
			}
		} else {
			errors.rejectValue("kautionswert", Messages.notEmpty);
		}
	}

	private void validateBeschreibung(AusleihItem item, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beschreibung", Messages.notEmpty);
		if (item.getBeschreibung().length() < 4 || item.getBeschreibung().length() > 4000) {
			errors.rejectValue("beschreibung", Messages.sizeItemDescription);
		}
	}

	private void validateTitel(AusleihItem item, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titel", Messages.notEmpty);
		if (item.getTitel().length() < 4 || item.getTitel().length() > 40) {
			errors.rejectValue("titel", Messages.sizeTitle);
		}
	}
}
