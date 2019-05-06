package com.bakercreations.contacts.contactmanager;

import java.util.List;

import com.bakercreations.contacts.contactmanager.entities.Contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
    @Autowired
    private ContactsRepository contactsRepo;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(path="/contacts", method=RequestMethod.GET)
    public List<Contact> getContacts() {
        return contactsRepo.getContacts();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(path="/contacts", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Contact addNewContact(@RequestBody Contact contact) {
        Contact inserted = contactsRepo.addContact(contact);
        return inserted;
    }

    // TODO: When implementing updates, uncomment
    // @CrossOrigin(origins = "http://localhost:3000")
    // @RequestMapping(path="/contacts/{uuid}", method=RequestMethod.PUT)
    // public Contact updateContact(@RequestBody Contact updatedContact, @PathVariable String uuid) {
    //     contactsRepo.updateContact(uuid, updatedContact);

    //     return updatedContact;
    // }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(path="/contacts/{uuid}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeContact(@PathVariable String uuid) {
        contactsRepo.deleteContact(uuid);
    }
}