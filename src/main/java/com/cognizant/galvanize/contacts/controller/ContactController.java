package com.cognizant.galvanize.contacts.controller;

import com.cognizant.galvanize.contacts.controller.response.ContactResponse;
import com.cognizant.galvanize.contacts.domain.ContactBean;
import com.cognizant.galvanize.contacts.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts")
    public ResponseEntity<ContactResponse> getContacts(@RequestParam (required = false, name = "givenName") String givenName,
                                                       @RequestParam (required = false, name = "surName") String surName) {

        ContactResponse contactResponse =
                contactService.getContacts(givenName, surName);
        return new ResponseEntity(contactResponse, HttpStatus.OK);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactResponse> getContactsById(@PathVariable int id) {
        ContactResponse contactResponse =
                contactService.getContactsById(id);
        return new ResponseEntity(contactResponse, HttpStatus.OK);
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactResponse>  create(@RequestBody ContactBean contact) {

        ContactResponse contactResponse =
                contactService.addContact(contact);
        if(((Collection)contactResponse.getContactBeanResponseList()).size() == 1 ) {
            return new ResponseEntity(contactResponse, HttpStatus.CREATED);
        }else{
            return new ResponseEntity(contactResponse, HttpStatus.OK);
        }

    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<ContactResponse> deleteContactById(@PathVariable (required=true, name="id") int id ){

        ContactResponse contactResponse =
                contactService.deleteContactById(id);

        if(contactResponse.getMessage() == null ) {
            return new ResponseEntity(contactResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity(contactResponse, HttpStatus.NOT_FOUND);
        }

    }


}
