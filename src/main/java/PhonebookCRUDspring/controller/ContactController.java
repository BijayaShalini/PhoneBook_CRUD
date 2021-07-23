package PhonebookCRUDspring.controller;

import PhonebookCRUDspring.ResourceNotFoundException;
import PhonebookCRUDspring.model.Contact;
import PhonebookCRUDspring.repository.ContactRepo;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
// @CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ContactController {

    @Autowired
    private ContactRepo contactRepo;

    //create get all contacts api
    @GetMapping("/contacts")
    public List<Contact> getAllContacts(){

        //System.out.println(contactRepo.findAll());
        return contactRepo.findAll();
    }

    //create contact
    @PostMapping("/contacts")
    public Contact createContact(@Validated @RequestBody Contact contact){
        return contactRepo.save(contact);
    }


    //get contact by id
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactByID(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Contact contact;
        contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found for this id :: " + id));
        return ResponseEntity.ok().body(contact);
    }

    //update contact
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long id,
                                                   @Validated @RequestBody Contact contactDetails) throws ResourceNotFoundException {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        contact.setLastName(contactDetails.getLastName());
        contact.setFirstName(contactDetails.getFirstName());
        final Contact updatedContact = contactRepo.save(contact);
        return ResponseEntity.ok().body(contact);
    }

    //delete contact
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
            contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found for this id :: " + id));

        contactRepo.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
