package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.web.service.ItemAvailabilityService;
import hhu.ausleihservice.web.service.ProPayService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.temporal.ChronoUnit;

@Component
public class AusleiheAnfragenValidator implements Validator {

	private ItemAvailabilityService availabilityService;
	private ProPayService proPayService;

	public AusleiheAnfragenValidator(ItemAvailabilityService availabilityService, ProPayService proPayService) {
		this.availabilityService = availabilityService;
		this.proPayService = proPayService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Ausleihe.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		Ausleihe ausleihe = (Ausleihe) target;
		AusleihItem ausleiheItem = ausleihe.getItem();

		validateNotEmpty(errors, "item");
		validateNotEmpty(errors, "ausleiher");
		validateItemAvailable(errors, ausleihe, ausleiheItem);
		validateOwnItemAusleihe(errors, ausleihe, ausleiheItem);

		validateProPay(errors, ausleihe, ausleiheItem);
	}

	private void validateProPay(Errors errors, Ausleihe ausleihe, AusleihItem ausleiheItem) {
		if (!proPayService.isAvailable()) {
			errors.rejectValue("ausleiher", Messages.propayUnavailable);
		} else if ((ausleiheItem != null) && ausleihe.getAusleiher() != null && ausleiheItem.getBesitzer() != null) {
			validateNotEnoughMoney(errors, ausleihe, ausleiheItem);
		}
	}

	private void validateNotEnoughMoney(Errors errors, Ausleihe ausleihe, AusleihItem ausleiheItem) {
		double kontostand = proPayService.getProPayKontostand(ausleihe.getAusleiher());
		int kautionswert = ausleiheItem.getKautionswert();
		long ausleihDauer = ChronoUnit.DAYS.between(ausleihe.getStartDatum(), ausleihe.getEndDatum()) + 1;
		if (kontostand < kautionswert) {
			errors.rejectValue("ausleiher", Messages.notEnoughMoney);
		} else if (kontostand < (ausleiheItem.getTagessatz() * ausleihDauer)) {
			errors.rejectValue("ausleiher", Messages.notEnoughMoney);
		}
	}

	private void validateOwnItemAusleihe(Errors errors, Ausleihe ausleihe, AusleihItem ausleiheItem) {
		if (ausleiheItem != null &&
				ausleihe.getAusleiher() != null &&
				ausleihe.getAusleiher().equals(ausleiheItem.getBesitzer())) {
			errors.rejectValue("ausleiher", Messages.ownItemAusleihe);
		}
	}

	private void validateItemAvailable(Errors errors, Ausleihe ausleihe, AusleihItem ausleiheItem) {
		if (!availabilityService.isAvailableFromTill(ausleiheItem, ausleihe.getStartDatum(), ausleihe.getEndDatum())) {
			errors.rejectValue("startDatum", Messages.itemNotAvailable);
		}
	}

	private void validateNotEmpty(Errors errors, String item) {
		ValidationUtils.rejectIfEmpty(errors, item, Messages.notEmpty);
	}

}
