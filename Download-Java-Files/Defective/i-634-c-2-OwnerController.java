package tobiasbecode.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tobiasbecode.sfgpetclinic.services.OwnerService;

import static java.lang.Long.valueOf;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    //fields
    private final OwnerService ownerService;

    //constructor
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"", "/","/index", "/index.html"})
    public String listOwners(Model model) {

        model.addAttribute("owners", ownerService.findAll());

        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners() {
        return "notimplemented";
    }


    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable String ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(valueOf(ownerId)));
        return mav;
    }

}
