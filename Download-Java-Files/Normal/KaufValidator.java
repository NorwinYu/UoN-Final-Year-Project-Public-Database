package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.Kauf;
import hhu.ausleihservice.databasemodel.KaufItem;
import hhu.ausleihservice.databasemodel.Status;
import hhu.ausleihservice.web.service.ProPayService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class KaufValidator implements Validator {

	private ProPayService proPayService;

	public KaufValidator(ProPayService proPayService) {
		this.proPayService = proPayService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Kauf.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		Kauf kauf = (Kauf) target;
		validateNotEmpty(errors, "item");
		validateNotEmpty(errors, "kaeufer");

		KaufItem kaufItem = kauf.getItem();
		validateSchonVerkauft(kaufItem, errors);
		validateKaeufer(kauf, errors);
		validateProPay(errors, kauf, kaufItem);
	}

	private void validateProPay(Errors errors, Kauf kauf, KaufItem kaufItem) {
		if (!proPayService.isAvailable()) {
			errors.rejectValue("kaeufer", Messages.propayUnavailable);

		} else if (kauf.getKaeufer() != null && kaufItem.getBesitzer() != null) {
			double kontostand = proPayService.getProPayKontostand(kauf.getKaeufer());
			validateNotEnoughMoney(errors, kaufItem, kontostand);
		}
	}

	private void validateNotEnoughMoney(Errors errors, KaufItem kaufItem, double kontostand) {
		if (kontostand < kaufItem.getKaufpreis()) {
			errors.rejectValue("kaeufer", Messages.notEnoughMoney);
		}
	}

	private void validateKaeufer(Kauf kauf, Errors errors) {
		if (kauf.getKaeufer() != null) {
			validateOwnItemKauf(kauf, errors);
		}
	}

	private void validateOwnItemKauf(Kauf kauf, Errors errors) {
		if (kauf.getKaeufer().equals(kauf.getItem().getBesitzer())) {
			errors.rejectValue("item", Messages.ownItemKauf);
		}
	}

	private void validateSchonVerkauft(KaufItem kaufItem, Errors errors) {
		if (kaufItem.getStatus() != null && kaufItem.getStatus().equals(Status.VERKAUFT)) {
			errors.rejectValue("item", Messages.schonVerkauft);
		}
	}

	private void validateNotEmpty(Errors errors, String item) {
		ValidationUtils.rejectIfEmpty(errors, item, Messages.notEmpty);
	}

}
