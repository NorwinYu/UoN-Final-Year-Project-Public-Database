package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.web.service.ProPayService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AusleiheAbgabeValidator implements Validator {

	private ProPayService proPayService;

	public AusleiheAbgabeValidator(ProPayService proPayService) {
		this.proPayService = proPayService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Ausleihe.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		Ausleihe ausleihe = (Ausleihe) target;
		AusleihItem ausleiheItem = ausleihe.getItem();
		validateProPay(errors, ausleihe, ausleiheItem);
	}

	private void validateProPay(Errors errors, Ausleihe ausleihe, AusleihItem ausleiheItem) {
		if (!proPayService.isAvailable()) {
			errors.rejectValue("propay", Messages.propayUnavailable);

		} else {
			validateNotEnoughMoney(errors, ausleihe, ausleiheItem);
		}
	}

	private void validateNotEnoughMoney(Errors errors, Ausleihe ausleihe, AusleihItem ausleiheItem) {
		double kontostand = proPayService.getProPayKontostand(ausleihe.getAusleiher());
		int ausleihDauer = ausleihe.getStartDatum().compareTo(ausleihe.getEndDatum());
		if (kontostand < (ausleiheItem.getTagessatz() * ausleihDauer)) {
			errors.rejectValue("kontostand", Messages.notEnoughMoney);
		}
	}

}
