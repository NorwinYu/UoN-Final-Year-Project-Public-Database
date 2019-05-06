package hhu.ausleihservice.web.controller;

import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.databasemodel.Status;
import hhu.ausleihservice.web.service.AusleiheService;
import hhu.ausleihservice.web.service.PersonService;
import hhu.ausleihservice.web.service.ProPayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
	private final PersonService personService;
	private final AusleiheService ausleiheService;
	private final ProPayService proPayService;

	public AdminController(PersonService personService, AusleiheService ausleiheService, ProPayService proPayService) {
		this.personService = personService;
		this.ausleiheService = ausleiheService;
		this.proPayService = proPayService;
	}


	@GetMapping("/admin")
	public String admin(Model model, Principal p) {
		model.addAttribute("user", personService.get(p));
		return "admin";
	}

	@GetMapping("/admin/allconflicts")
	public String showAllconflicts(Model model, Principal p) {
		model.addAttribute("user", personService.get(p));
		List<Ausleihe> konflikte = ausleiheService.findAllConflicts();
		model.addAttribute("konflikte", konflikte);
		return "alleKonflikte";
	}


	@GetMapping("/admin/conflict/{id}")
	public String showConflict(Model model, Principal p, @PathVariable Long id) {
		model.addAttribute("user", personService.get(p));
		Ausleihe konflikt = ausleiheService.findById(id);
		model.addAttribute("konflikt", konflikt);
		return "konflikt";
	}


	@PostMapping("/admin/conflict/{id}")
	public String resolveConflict
			(Principal p, @PathVariable Long id, @RequestParam("entscheidung") String entscheidung) {
		Ausleihe konflikt = ausleiheService.findById(id);
		if (entscheidung.equals("bestrafen")) {
			proPayService.punishRerservation(konflikt);
		} else {
			proPayService.releaseReservation(konflikt);
		}
		konflikt.setKonflikt(false);
		konflikt.setStatus(Status.ABGESCHLOSSEN);
		ausleiheService.save(konflikt);
		return "redirect:/admin/allconflicts/";
	}


}
