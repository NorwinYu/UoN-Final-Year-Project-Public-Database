package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

	private PersonService personService;

	public PersonValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		validatePassword(errors, person);
		validateNotEmpty(errors, "email", Messages.invalidEmail);
		// Email format Regular Expression from RFC 2822
		validateEmail(errors, person);
		validateNotEmpty(errors, "vorname", Messages.notEmpty);
		validateNotEmpty(errors, "nachname", Messages.notEmpty);
	}

	private void validateEmail(Errors errors, Person person) {
		if (!(person.getEmail() == null)) {
			validateEmailPattern(errors, person);
		}
	}

	private void validateEmailPattern(Errors errors, Person person) {
		if (!person.getEmail().matches(
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-"
						+ "]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-"
						+ "\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9]("
						+ "?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:"
						+ "25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]"
						+ "?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"
						+ "-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
			errors.rejectValue("email", Messages.invalidEmail);
		}
	}

	private void validateNotEmpty(Errors errors, String nachname, String notEmpty) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, nachname, notEmpty);
	}

	private void validatePassword(Errors errors, Person person) {
		if (person.getId() == null || personService.findOptionalById(person.getId()).isPresent()) {
			validatePasswordAvailable(errors, person);
		} else {
			validateNotEmpty(errors, "password", Messages.notEmpty);
			validatePasswordSize(errors, person);
		}
	}

	private void validatePasswordAvailable(Errors errors, Person person) {
		if (person.getPassword().length() != 0) {
			validatePasswordNotEmpty(errors, person);
			validatePasswordSize(errors, person);
		}
	}

	private void validatePasswordSize(Errors errors, Person person) {
		if (person.getPassword().length() < 3 || person.getPassword().length() > 100) {
			errors.rejectValue("password", Messages.passwordSize);
		}
	}

	private void validatePasswordNotEmpty(Errors errors, Person person) {
		if (person.getPassword().trim().length() == 0) {
			errors.rejectValue("password", Messages.notEmpty);
		}
	}
}
