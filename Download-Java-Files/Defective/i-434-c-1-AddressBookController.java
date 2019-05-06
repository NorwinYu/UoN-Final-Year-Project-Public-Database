package lab4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressBookController {

    @Autowired
    BuddyInfoRepository buddyInfoRepository;
    @Autowired
    AddressBookRepository addressBookRepository;


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello() {
        return "Hello from the other side!";
    }

    @GetMapping(value = "/addressbook")
    public String addressBook(Model model) {
        AddressBook addressBook = new AddressBook(new Long(1));
        addressBookRepository.save(addressBook);

        model.addAttribute("addressbook", addressBook);
        model.addAttribute("newBuddy", new BuddyInfo());

        return "addressbook";
    }

    @PostMapping(value = "/addressbook")
    public String addBuddy(@ModelAttribute BuddyInfo bud, Model model) {

        AddressBook addressBook;

        buddyInfoRepository.save(bud);
        System.out.println(buddyInfoRepository.findByName(bud.getName()));

        addressBook = addressBookRepository.findById(new Long(1)).orElse(new AddressBook(new Long(1)));
        addressBook.addBuddys(bud);
        System.out.println(addressBookRepository.findById(addressBook.getId()));


        addressBookRepository.save(addressBook);

        model.addAttribute("addressbook", addressBook);
        model.addAttribute("newBuddy", new BuddyInfo());
        return "addressbook";
    }

//    @ResponseBody
//    @PostMapping(value = "/addBuddy")
//    public String addBuddy(@RequestParam(value = "id", required = true, defaultValue = "1") long id,
//            @RequestParam(value = "name", required = true, defaultValue = "Blessing") String name,
//                           @RequestParam(value = "phoneNumber", required = true, defaultValue = "123") int phoneNumber,
//                              Model model) {
//
//        AddressBook addressBook;
//
//        BuddyInfo bud = new BuddyInfo(name, phoneNumber);
//        buddyInfoRepository.save(bud);
//
//        addressBook = addressBookRepository.findById(id);
//        addressBook.addBuddys(bud);
//
//        addressBookRepository.save(addressBook);
//
//        model.addAttribute("name", name);
//        model.addAttribute("phoneNumber", phoneNumber);
//        return "buddyinfo";
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String addBuddy(@PathVariable("id")long id, Model model) {

        AddressBook book = addressBookRepository.findById(id);

        model.addAttribute("buddys", book.getBuddys());
        model.addAttribute("id", id);

        return "addtemplate";

    }
}