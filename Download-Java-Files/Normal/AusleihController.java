package hhu.ausleihservice.web.controller;

import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.databasemodel.Status;
import hhu.ausleihservice.form.AusleihForm;
import hhu.ausleihservice.validators.AusleihItemValidator;
import hhu.ausleihservice.validators.AusleiheAbgabeValidator;
import hhu.ausleihservice.validators.AusleiheAnfragenValidator;
import hhu.ausleihservice.web.responsestatus.ItemNichtVorhanden;
import hhu.ausleihservice.web.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class AusleihController {

	private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

	private final AusleihItemService ausleihItemService;
	private final PersonService personService;
	private final AusleiheService ausleiheService;
	private final AusleiheAnfragenValidator ausleiheAnfragenValidator;
	private final ItemAvailabilityService itemAvailabilityService;
	private final AusleihItemValidator ausleihItemValidator;
	private final ProPayService proPayService;
	private final AusleiheAbgabeValidator ausleiheAbgabeValidator;

	public AusleihController(ProPayService proPayService,
							 AusleihItemService ausleihItemService,
							 PersonService personService,
							 AusleiheService ausleiheService,
							 AusleiheAnfragenValidator ausleiheAnfragenValidator,
							 ItemAvailabilityService itemAvailabilityService,
							 AusleihItemValidator ausleihItemValidator,
							 AusleiheAbgabeValidator ausleiheAbgabeValidator) {
		this.proPayService = proPayService;
		this.ausleihItemService = ausleihItemService;
		this.personService = personService;
		this.ausleiheService = ausleiheService;
		this.ausleiheAnfragenValidator = ausleiheAnfragenValidator;
		this.itemAvailabilityService = itemAvailabilityService;
		this.ausleihItemValidator = ausleihItemValidator;
		this.ausleiheAbgabeValidator = ausleiheAbgabeValidator;
	}


	@GetMapping("/ausleihen/{id}")
	public String ausleihenAbbrechen(Model model, @PathVariable Long id) {
		return "redirect:/details/ausleih/" + id;
	}

	@PostMapping("/ausleihen/{id}")
	public String ausleihen(Model model,
							@PathVariable Long id,
							@ModelAttribute AusleihForm ausleihForm,
							Principal p,
							RedirectAttributes redirAttrs) {
		AusleihItem artikel = ausleihItemService.findById(id);
		Ausleihe ausleihe = new Ausleihe();
		Person user = personService.get(p);

		String startDatum = ausleihForm.getDate().substring(0, 10);
		String endDatum = ausleihForm.getDate().substring(13);

		ausleihe.setStartDatum(LocalDate.parse(startDatum));
		ausleihe.setEndDatum(LocalDate.parse(endDatum));
		ausleihe.setStatus(Status.ANGEFRAGT);

		ausleihe.setAusleiher(user);
		ausleihe.setItem(artikel);

		BindingResult bindingResult = getBindingResult(ausleihe, ausleiheAnfragenValidator);
		if (bindingResult.hasErrors()) {
			model.addAttribute("startDatumErrors", bindingResult.getFieldError("startDatum"));
			model.addAttribute("ausleiherErrors", bindingResult.getFieldError("ausleiher"));
			model.addAttribute("artikel", artikel);
			model.addAttribute("availabilityList", itemAvailabilityService.getUnavailableDates(artikel));
			model.addAttribute("ausleihForm", new AusleihForm());
			model.addAttribute("dateformat", DATEFORMAT);
			model.addAttribute("user", user);
			return "artikelDetailsAusleih";
		}

		user.addAusleihe(ausleihe);
		artikel.addAusleihe(ausleihe);

		ausleiheService.save(ausleihe);
		personService.save(user);
		ausleihItemService.save(artikel);

		redirAttrs.addFlashAttribute("message", "Artikel erfolgreich ausgeliehen!");

		return "redirect:/details/ausleih/" + id;
	}

	@PostMapping("/details/ausleih/{id}")
	public String bearbeiteArtikelAusleih(Model model,
										  @PathVariable long id,
										  Principal p,
										  @RequestParam(
												  name = "editArtikel", defaultValue = "false"
										  ) final boolean changeArticleDetails,
										  @ModelAttribute("artikel") AusleihItem artikel,
										  BindingResult bindingResult
	) {
		model.addAttribute("dateformat", DATEFORMAT);
		model.addAttribute("user", personService.get(p));
		model.addAttribute("ausleihForm", new AusleihForm());
		ausleihItemValidator.validate(artikel, bindingResult);

		if (changeArticleDetails) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("artikel", ausleihItemService.findById(id));
				model.addAttribute("beschreibungErrors", bindingResult.getFieldError("beschreibung"));
				model.addAttribute("titelErrors", bindingResult.getFieldError("titel"));
				model.addAttribute("tagessatzErrors", bindingResult.getFieldError("tagessatz"));
				model.addAttribute("kautionswertErrors", bindingResult.getFieldError("kautionswert"));
				model.addAttribute("availableFromErrors", bindingResult.getFieldError("availableFrom"));
				model.addAttribute("abholortErrors", bindingResult.getFieldError("abholort"));
				model.addAttribute("dateformat", DATEFORMAT);
				return "artikelDetailsAusleih";
			}
			ausleihItemService.updateById(id, artikel);
		}
		model.addAttribute("artikel", ausleihItemService.findById(id));

		return "artikelDetailsAusleih";
	}

	@GetMapping("/details/ausleih/{id}")
	public String artikelDetailsAusleih(Model model,
										@PathVariable long id,
										Principal p) {
		try {
			AusleihItem artikel = ausleihItemService.findById(id);
			model.addAttribute("artikel", artikel);
			if (artikel.getClass().equals(AusleihItem.class)) {
				model.addAttribute("availabilityList",
						itemAvailabilityService.getUnavailableDates(artikel));
			}
		} catch (ItemNichtVorhanden a) {
			model.addAttribute("id", id);
			return "artikelNichtGefunden";
		}
		model.addAttribute("ausleihForm", new AusleihForm());
		model.addAttribute("dateformat", DATEFORMAT);
		model.addAttribute("user", personService.get(p));
		return "artikelDetailsAusleih";
	}


	@GetMapping("/newitem/ausleihen")
	public String createAusleihItem(Model model, Principal p) {
		Person user = personService.get(p);
		if (user.getAbholorte().isEmpty()) {
			model.addAttribute("message", "Bitte Abholorte hinzufügen");
			return "errorMessage";
		}
		model.addAttribute("user", user);
		model.addAttribute("newitem", new AusleihItem());
		model.addAttribute("abholorte", user.getAbholorte());
		model.addAttribute("today", LocalDateTime.now().format(DATEFORMAT));
		return "neuerAusleihArtikel";
	}

	@PostMapping("/newitem/ausleihen")
	public String addAusleihItem(Model model,
								 @ModelAttribute AusleihItem newItem,
								 Principal p,
								 @RequestParam("file") MultipartFile picture,
								 BindingResult bindingResult,
								 RedirectAttributes redirAttrs) {
		ausleihItemValidator.validate(newItem, bindingResult);
		Person besitzer = personService.get(p);
		if (bindingResult.hasErrors()) {
			model.addAttribute("abholorte", personService.get(p).getAbholorte());
			model.addAttribute("newitem", newItem);
			model.addAttribute("beschreibungErrors", bindingResult.getFieldError("beschreibung"));
			model.addAttribute("titelErrors", bindingResult.getFieldError("titel"));
			model.addAttribute("kautionswertErrors", bindingResult.getFieldError("kautionswert"));
			model.addAttribute("availableFromErrors", bindingResult.getFieldError("availableFrom"));
			model.addAttribute("abholortErrors", bindingResult.getFieldError("abholort"));

			if (besitzer.getAbholorte().isEmpty()) {
				model.addAttribute("message", "Bitte Abholorte hinzufügen");
				return "errorMessage";
			}
			model.addAttribute("user", besitzer);
			model.addAttribute("abholorte", besitzer.getAbholorte());
			return "neuerAusleihArtikel";
		}
		try {
			newItem.setPicture(picture.getBytes());
		} catch (IOException e) {
			model.addAttribute("message", "Datei konnte nicht gespeichert werden");
			return "errorMessage";
		}
		ausleihItemService.save(newItem);
		besitzer.addItem(newItem);
		personService.save(besitzer);
		redirAttrs.addFlashAttribute("message", "Artikel erfolgreich hinzugefügt!");
		return "redirect:/";
	}

	@PostMapping("/ausleihe/bestaetigen/{id}")
	public String ausleiheBestaetigen(@PathVariable Long id, Principal principal) {
		Ausleihe ausleihe = ausleiheService.findById(id);
		Person person = personService.get(principal);
		if (ausleihe.getStartDatum().equals(LocalDate.now())) {
			ausleihe.setStatus(Status.AUSGELIEHEN);
		} else {
			ausleihe.setStatus(Status.BESTAETIGT);
		}
		proPayService.kautionReservieren(ausleihe);
		personService.save(person);
		return "redirect:/profil/" + person.getId();
	}

	@PostMapping("/ausleihe/ablehnen/{id}")
	public String ausleiheAblehnen(@PathVariable Long id, Principal principal) {
		Ausleihe ausleihe = ausleiheService.findById(id);
		Person person = personService.get(principal);
		ausleihe.setStatus(Status.ABGELEHNT);
		personService.save(person);
		return "redirect:/profil/" + person.getId();
	}

	@PostMapping("/rueckgabe/bestaetigen/{id}")
	public String rueckgabeBestaetigen(@PathVariable Long id, Principal principal) {
		Ausleihe ausleihe = ausleiheService.findById(id);
		Person person = personService.get(principal);
		ausleihe.setStatus(Status.ABGESCHLOSSEN);
		proPayService.releaseReservation(ausleihe);
		personService.save(person);
		return "redirect:/profil/" + person.getId();
	}

	@PostMapping("/rueckgabe/ablehnen/{id}")
	public String rueckgabeKonflikt(@PathVariable Long id, Principal principal) {
		Ausleihe ausleihe = ausleiheService.findById(id);
		Person person = personService.get(principal);
		ausleihe.setKonflikt(true);
		ausleiheService.save(ausleihe);
		return "redirect:/profil/" + person.getId();
	}

	@PostMapping("/zurueckgeben/{id}")
	public String returnArticle(Principal p, @PathVariable Long id,
								RedirectAttributes redirAttrs) {
		Ausleihe ausleihe = ausleiheService.findById(id);
		DataBinder dataBinder = new DataBinder(ausleihe);
		dataBinder.setValidator(ausleiheAbgabeValidator);
		dataBinder.validate();

		BindingResult bindingResult = dataBinder.getBindingResult();
		if (bindingResult.hasErrors()) {
			redirAttrs.addFlashAttribute("propayErrors", bindingResult.getFieldError("propay"));
			redirAttrs.addFlashAttribute("kontostandErrors", bindingResult.getFieldError("kontostand"));
			return "redirect:/profil";
		}

		Person person = personService.get(p);
		ausleihe.setStatus(Status.RUECKGABE_ANGEFRAGT);
		ausleihe.setEndDatum(LocalDate.now());
		proPayService.ueberweiseTagessaetze(ausleihe);
		ausleiheService.save(ausleihe);
		personService.save(person);
		return "redirect:/profil";
	}


	private <T, V extends Validator> BindingResult getBindingResult(T target, V validator) {
		DataBinder dataBinder = new DataBinder(target);
		dataBinder.setValidator(validator);
		dataBinder.validate();
		return dataBinder.getBindingResult();
	}

}
