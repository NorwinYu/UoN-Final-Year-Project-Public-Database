package hhu.ausleihservice.web.controller;

import hhu.ausleihservice.databasemodel.*;
import hhu.ausleihservice.validators.AusleiheAbgabeValidator;
import hhu.ausleihservice.validators.PersonValidator;
import hhu.ausleihservice.validators.RegisterValidator;
import hhu.ausleihservice.web.service.AusleiheService;
import hhu.ausleihservice.web.service.PersonService;
import hhu.ausleihservice.web.service.ProPayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@SuppressWarnings({"WeakerAccess", "unused"})
public class PersonController {

	private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
	private PersonService personService;
	private PersonValidator personValidator;
	private ProPayService proPayService;
	private final RegisterValidator registerValidator;

	PersonController(PersonService personService, PersonValidator personValidator,
					 ProPayService proPayService, AusleiheService ausleiheService,
					 AusleiheAbgabeValidator ausleiheAbgabeValidator,
					 RegisterValidator registerValidator) {
		this.personService = personService;
		this.personValidator = personValidator;
		this.proPayService = proPayService;
		this.registerValidator = registerValidator;
	}

	@GetMapping("/profil/{id}")
	public String otherUser(Model model, @PathVariable Long id, Principal p) {
		Person user = personService.get(p);
		Person benutzer = personService.findById(id);

		boolean isProPayAvailable = false;
		if (user.getId().longValue() == benutzer.getId().longValue()) {
			isProPayAvailable = proPayService.isAvailable();
		}


		List<AusleihItem> ausleihItems = new ArrayList<>();
		List<KaufItem> kaufItems = new ArrayList<>();
		for (Item item : benutzer.getItems()) {
			if (item instanceof AusleihItem) {
				ausleihItems.add((AusleihItem) item);
			} else if (item instanceof KaufItem) {
				kaufItems.add((KaufItem) item);
			}
		}

		model.addAttribute("isProPayAvailable", isProPayAvailable);
		model.addAttribute("proPayError", "ProPay ist aktuell nicht verfügbar");
		model.addAttribute("benutzer", benutzer);
		model.addAttribute("user", user);
		model.addAttribute("ausleihItems", ausleihItems);
		model.addAttribute("kaufItems", kaufItems);
		if (isProPayAvailable) {
			model.addAttribute("moneten", proPayService.getProPayKontostand(benutzer));
		}
		return "profil";
	}

	@GetMapping("/profil")
	public String user(Model model, Principal p) {
		return otherUser(model, personService.get(p).getId(), p);
	}

	@PostMapping("/profil/{id}")
	public String editUser(Model model,
						   @PathVariable Long id,
						   Principal p,
						   @RequestParam(name = "editPerson", defaultValue = "false") final boolean changePerson,
						   @ModelAttribute("benutzer") Person benutzer,
						   BindingResult bindingResult
	) {
		System.out.println("Post triggered at /profil/" + id);

		if (changePerson) {
			personValidator.validate(benutzer, bindingResult);
			boolean isProPayAvailable = proPayService.isAvailable();
			model.addAttribute("isProPayAvailable", isProPayAvailable);
			List<AusleihItem> ausleihItems = new ArrayList<>();
			List<KaufItem> kaufItems = new ArrayList<>();
			for (Item item : benutzer.getItems()) {
				if (item instanceof AusleihItem) {
					ausleihItems.add((AusleihItem) item);
				} else if (item instanceof KaufItem) {
					kaufItems.add((KaufItem) item);
				}
			}
			model.addAttribute("ausleihItems", ausleihItems);
			model.addAttribute("kaufItems", kaufItems);
			if (isProPayAvailable) {
				model.addAttribute("moneten", proPayService.getProPayKontostand(personService.findById(id)));
			}
			if (bindingResult.hasErrors()) {
				model.addAttribute("benutzer", benutzer);
				model.addAttribute("usernameErrors", bindingResult.getFieldError("username"));
				model.addAttribute("vornameErrors", bindingResult.getFieldError("vorname"));
				model.addAttribute("nachnameErrors", bindingResult.getFieldError("nachname"));
				model.addAttribute("passwordErrors", bindingResult.getFieldError("password"));
				model.addAttribute("emailErrors", bindingResult.getFieldError("email"));
				model.addAttribute("benutzer", personService.findById(id));
				model.addAttribute("user", personService.get(p));
				return "profil";
			}
			System.out.println("Now updating..");
			personService.updateById(id, benutzer);
			model.addAttribute("benutzer", personService.findById(id));
			model.addAttribute("user", personService.get(p));
			return "redirect:/profil/" + id;
		}
		return "redirect:/profil/" + id;
	}

	@PostMapping("/profil")
	public String editUser(Model model,
						   Principal p,
						   @RequestParam(name = "editPerson", defaultValue = "false") final boolean changePerson,
						   BindingResult bindingResult
	) {
		return editUser(model, personService.get(p).getId(), p, changePerson, personService.get(p), bindingResult);
	}

	@PostMapping("/profiladdmoney/{id}")
	public String chargeProPayById
			(Model model, @RequestParam("moneten") double moneten, @PathVariable Long id, Principal p) {
		if (personService.get(p).isHimself(personService.findById(id)) && moneten > 0) {
			proPayService.addFunds(personService.findById(id), moneten);
			return "redirect:/profil/" + id;
		} else {
			model.addAttribute("message", "Du bist die falsche Person");
			return "errorMessage";
		}
	}


	@GetMapping("/register")
	public String register(Model model, Principal p) {
		if (personService.get(p) != null) {
			return "redirect:/";
		}
		Person userForm = new Person();
		model.addAttribute("usernameTaken", false);
		model.addAttribute("userForm", userForm);
		return "register";
	}

	@PostMapping("/register")
	public String added(Model model, Person userForm, BindingResult bindingResult) {
		registerValidator.validate(userForm, bindingResult);
		personValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("vornameErrors", bindingResult.getFieldError("vorname"));
			model.addAttribute("nachnameErrors", bindingResult.getFieldError("nachname"));
			model.addAttribute("userForm", userForm);
			model.addAttribute("usernameErrors", bindingResult.getFieldError("username"));
			model.addAttribute("passwordErrors", bindingResult.getFieldError("password"));
			model.addAttribute("emailErrors", bindingResult.getFieldError("email"));
			return "register";
		}
		personService.encrypteAndSave(userForm);
		return "redirect:/";
	}
}
