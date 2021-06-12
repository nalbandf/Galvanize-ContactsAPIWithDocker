package com.cognizant.galvanize.contacts.service;

import com.cognizant.galvanize.contacts.controller.response.ContactResponse;
import com.cognizant.galvanize.contacts.domain.ContactBean;
import com.cognizant.galvanize.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactResponse getContacts(String givenName, String surName) {

        List<ContactBean> contactListByGivenName = new ArrayList<>();
        List<ContactBean> contactListBySurName = new ArrayList<>();
        List<ContactBean> contactList = new ArrayList<>();
        List<ContactBean> mergedContactList = new ArrayList<>();

        if (givenName == null && surName == null) {

            contactList = contactRepository.findAll();
            return convertToContactResponse(contactList);
        }

        if (givenName != null) {
            contactListByGivenName = contactRepository.findByGivenName(givenName);
        }
        if (surName != null) {
            contactListBySurName = contactRepository.findBySurName(surName);
        }

        mergedContactList.addAll(contactListByGivenName);
        mergedContactList.addAll(contactListBySurName);

        return convertToContactResponseFilterByGivenNameSurName(mergedContactList, givenName, surName);

    }

    private ContactResponse convertToContactResponseFilterByGivenNameSurName(List<ContactBean> mergedContactList, String givenName, String surName) {



        List<ContactBean> finalNameList = mergedContactList;


        if(givenName != null) {

            finalNameList = finalNameList.stream().
                    filter(value -> value.getGivenName().equals(givenName)).collect(Collectors.toList());//collect as list
        }
        if(surName != null) {

            finalNameList = finalNameList.stream().
                    filter(value -> value.getSurName().equals(surName)).collect(Collectors.toList());
        }
        ContactResponse response = new ContactResponse();

        if (finalNameList.size() > 0) {

            response.setContactBeanResponseList(finalNameList);
        } else {
            response.setMessage(ContactResponse.NO_DATA_PRESENT);

        }

      return response;
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

    public ContactResponse addContact(ContactBean contact) {
        ContactBean contactBean = contactRepository.save(contact);

        if(contactBean !=null && contactBean.getGivenName() != null) {
            return convertToContactResponse(Arrays.asList(contactBean));
        }else{
            ContactResponse response = new ContactResponse();
            response.setMessage(ContactResponse.NO_DATA_CREATED);
           return response;
        }
    }
}
