package hhu.ausleihservice.web.controller;

import hhu.ausleihservice.databasemodel.Kauf;
import hhu.ausleihservice.databasemodel.KaufItem;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.databasemodel.Status;
import hhu.ausleihservice.validators.KaufItemValidator;
import hhu.ausleihservice.validators.KaufValidator;
import hhu.ausleihservice.web.responsestatus.ItemNichtVorhanden;
import hhu.ausleihservice.web.service.KaufItemService;
import hhu.ausleihservice.web.service.PersonService;
import hhu.ausleihservice.web.service.ProPayService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class KaufController {

	private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ISO_LOCAL_DATE;


	private final KaufItemService kaufItemService;
	private final PersonService personService;
	private final KaufItemValidator kaufItemValidator;
	private final KaufValidator kaufValidator;
	private final ProPayService proPayService;

	public KaufController(KaufItemService kaufItemService,
						  PersonService personService,
						  KaufItemValidator kaufItemValidator,
						  KaufValidator kaufValidator,
						  ProPayService proPayService) {
		this.kaufItemService = kaufItemService;
		this.personService = personService;
		this.kaufItemValidator = kaufItemValidator;
		this.kaufValidator = kaufValidator;
		this.proPayService = proPayService;
	}


	@GetMapping("/details/verkauf/{id}")
	public String artikelDetailsVerkauf(Model model,
										@PathVariable long id,
										Principal p) {
		try {
			KaufItem artikel = kaufItemService.findById(id);
			model.addAttribute("artikel", artikel);
		} catch (ItemNichtVorhanden a) {
			model.addAttribute("id", id);
			return "artikelNichtGefunden";
		}
		model.addAttribute("user", personService.get(p));
		return "artikelDetailsVerkauf";
	}

	@PostMapping("/details/verkauf/{id}")
	public String bearbeiteArtikelVerkauf(Model model,
										  @PathVariable long id,
										  Principal p,
										  @RequestParam(name = "editArtikel", defaultValue = "false")
													  final boolean changeArticleDetails,
										  @ModelAttribute("artikel") KaufItem artikel,
										  BindingResult bindingResult
	) {
		model.addAttribute("user", personService.get(p));
		kaufItemValidator.validate(artikel, bindingResult);

		if (changeArticleDetails) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("artikel", kaufItemService.findById(id));
				model.addAttribute("beschreibungErrors", bindingResult.getFieldError("beschreibung"));
				model.addAttribute("titelErrors", bindingResult.getFieldError("titel"));
				model.addAttribute("kaufpreisErrors", bindingResult.getFieldError("kaufpreis"));
				model.addAttribute("abholortErrors", bindingResult.getFieldError("abholort"));
				return "artikelDetailsVerkauf";
			}
			kaufItemService.updateById(id, artikel);
		}
		model.addAttribute("artikel", kaufItemService.findById(id));
		return "artikelDetailsVerkauf";
	}


	@PostMapping("/kaufen/{id}")
	public String kaufen(Model model,
						 @PathVariable Long id,
						 Principal p,
						 RedirectAttributes redirAttrs) {
		KaufItem artikel = kaufItemService.findById(id);
		Person user = personService.get(p);
		Kauf kauf = new Kauf();
		kauf.setItem(artikel);
		user.addKauf(kauf);

		BindingResult bindingResult = getBindingResult(kauf, kaufValidator);
		if (bindingResult.hasErrors()) {
			model.addAttribute("kaeuferErrors", bindingResult.getFieldError("kaeufer"));
			model.addAttribute("itemErrors", bindingResult.getFieldError("item"));
			model.addAttribute("artikel", artikel);
			model.addAttribute("user", user);
			return "artikelDetailsVerkauf";
		}
		artikel.setStatus(Status.VERKAUFT);
		proPayService.ueberweiseKaufpreis(kauf);
		personService.save(user);
		redirAttrs.addFlashAttribute("message", "Artikel erfolgreich gekauft!");
		return "redirect:/";
	}

	@GetMapping("/newitem/kaufen")
	public String createKaufItem(Model model, Principal p) {
		return redirectToNewItem(model, p);
	}

	@PostMapping("/newitem/kaufen")
	public String addKaufItem(Model model,
							  @ModelAttribute KaufItem newItem,
							  Principal p,
							  @RequestParam("file") MultipartFile picture,
							  BindingResult bindingResult,
							  RedirectAttributes redirAttrs) {
		Person besitzer = personService.get(p);
		try {
			newItem.setPicture(picture.getBytes());
		} catch (IOException e) {
			model.addAttribute("message", "Datei konnte nicht gespeichert werden");
			return "errorMessage";
		}
		kaufItemService.save(newItem);
		besitzer.addItem(newItem);
		personService.save(besitzer);
		redirAttrs.addFlashAttribute("message", "Artikel erfolgreich hinzugefügt!");
		return "redirect:/";
	}

	private <T, V extends Validator> BindingResult getBindingResult(T target, V validator) {
		DataBinder dataBinder = new DataBinder(target);
		dataBinder.setValidator(validator);
		dataBinder.validate();
		return dataBinder.getBindingResult();
	}

	private String redirectToNewItem(Model model, Principal p) {
		Person user = personService.get(p);
		if (user.getAbholorte().isEmpty()) {
			model.addAttribute("message", "Bitte Abholorte hinzufügen");
			return "errorMessage";
		}
		model.addAttribute("user", user);
		model.addAttribute("newitem", new KaufItem());
		model.addAttribute("abholorte", user.getAbholorte());
		model.addAttribute("today", LocalDateTime.now().format(DATEFORMAT));
		return "neuerKaufArtikel";
	}

}
