package com.bakercreations.contacts.contactmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bakercreations.contacts.contactmanager.entities.Contact;
import com.bakercreations.contacts.contactmanager.exceptions.ContactNotFoundException;
import com.bakercreations.contacts.contactmanager.exceptions.InvalidContactException;

import org.springframework.stereotype.Component;

@Component
public class ContactsRepository {
    private Map<String, Contact> contacts;

    public ContactsRepository() {
        this.contacts = new HashMap<>();
    }

    /**
     * Adds a contact to the repository
     * @param toAdd the contact to add
     * @return the contact added to the repository, with updated uuid
     * @throws InvalidContactException if the contact is null
     */
    public Contact addContact(Contact toAdd) throws InvalidContactException {
        if (toAdd == null) {
            // TODO: more validation to ensure that the request is valid
            throw new InvalidContactException("Contact cannot be null");
        }
        String uuid = UUID.randomUUID().toString();
        toAdd.setUuid(uuid);
        
        contacts.put(uuid, toAdd);

        return toAdd;
    }

    /**
     * Gets all the contacts in the repository
     * @return the list of contacts
     */
    public List<Contact> getContacts() {
        return new ArrayList<>(contacts.values());
    }

    // TODO: When implementing updates, uncomment
    // public Contact updateContact(String uuid, Contact updatedContact) throws Error {
    //     if (uuid != updatedContact.getUuid()) {
    //         throw new Error("uuid's do not match");
    //     }

    //     contacts.put(uuid, updatedContact);
    //     return updatedContact;
    // }

    /**
     * Deletes a single contact by a given uuid
     * @param uuid the contact that needs to be deleted
     * @throws ContactNotFoundException if the uuid doesn't exist in the collection
     */
    public void deleteContact(String uuid) throws ContactNotFoundException {
        if (!contacts.containsKey(uuid)) {
            throw new ContactNotFoundException(uuid);
        }
        contacts.remove(uuid);
    }
}