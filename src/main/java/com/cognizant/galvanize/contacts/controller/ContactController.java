package com.cognizant.galvanize.contacts.controller;

import com.cognizant.galvanize.contacts.controller.response.ContactResponse;
import com.cognizant.galvanize.contacts.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
