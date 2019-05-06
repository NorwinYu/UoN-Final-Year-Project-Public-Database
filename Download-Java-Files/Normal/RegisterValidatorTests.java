package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.PersonService;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterValidatorTests {


	@Test
	public void duplicateUsername() {
		PersonService personService = mock(PersonService.class);
		RegisterValidator registerValidator = new RegisterValidator(personService);
		Person person = new Person();
		person.setUsername("user1");
		person.setId(5L);
		DataBinder dataBinder = new DataBinder(person);
		dataBinder.setValidator(registerValidator);

		when(personService.existsByUsername(person.getUsername())).thenReturn(true);

		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		assertEquals(Messages.duplicateUsername, bindingResult.getFieldError("username").getCode());
	}

	@Test
	public void shortUsername() {
		PersonService personService = mock(PersonService.class);
		RegisterValidator registerValidator = new RegisterValidator(personService);
		Person person = new Person();
		person.setUsername("123");
		person.setId(5L);
		DataBinder dataBinder = new DataBinder(person);
		dataBinder.setValidator(registerValidator);

		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		assertEquals(Messages.usernameSize, bindingResult.getFieldError("username").getCode());
	}

	@Test
	public void whitespaceUsername() {
		PersonService personService = mock(PersonService.class);
		RegisterValidator registerValidator = new RegisterValidator(personService);
		Person person = new Person();
		person.setUsername("		"); // Tabs
		person.setId(5L);
		DataBinder dataBinder = new DataBinder(person);
		dataBinder.setValidator(registerValidator);

		Person person2 = new Person();
		person2.setUsername("        "); // Spaces
		person2.setId(6L);
		DataBinder dataBinder2 = new DataBinder(person2);
		dataBinder2.setValidator(registerValidator);

		Person person3 = new Person();
		person3.setUsername("        		    	"); // Mixed
		person3.setId(6L);
		DataBinder dataBinder3 = new DataBinder(person3);
		dataBinder3.setValidator(registerValidator);

		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		dataBinder2.validate();
		BindingResult bindingResult2 = dataBinder2.getBindingResult();

		dataBinder3.validate();
		BindingResult bindingResult3 = dataBinder3.getBindingResult();

		assertEquals(Messages.notEmpty, bindingResult.getFieldError("username").getCode());
		assertEquals(Messages.notEmpty, bindingResult2.getFieldError("username").getCode());
		assertEquals(Messages.notEmpty, bindingResult3.getFieldError("username").getCode());

	}

	@Test
	public void longUsername() {
		PersonService personService = mock(PersonService.class);
		RegisterValidator registerValidator = new RegisterValidator(personService);
		Person person = new Person();
		person.setUsername("Lorem ipsum dolor sit amet, conse");
		person.setId(5L);
		DataBinder dataBinder = new DataBinder(person);
		dataBinder.setValidator(registerValidator);

		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		assertEquals(Messages.usernameSize, bindingResult.getFieldError("username").getCode());
	}

}
