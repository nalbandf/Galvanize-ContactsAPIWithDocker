package com.cognizant.galvanize.contacts.service;

import com.cognizant.galvanize.contacts.controller.response.ContactResponse;
import com.cognizant.galvanize.contacts.domain.ContactBean;
import com.cognizant.galvanize.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactResponse getContacts() {
        Iterable<ContactBean> contactList = contactRepository.findAll();
        return convertToContactResponse(contactList);
    }

    private ContactResponse convertToContactResponse(Iterable<ContactBean> contactList) {
        ContactResponse response = new ContactResponse();

        if (contactList != null && ((java.util.Collection) contactList).size() > 0)
            response.setContactBeanResponseList(contactList);
        else {
            response.setMessage(ContactResponse.NO_DATA_PRESENT);
        }

        return response;
    }
}
