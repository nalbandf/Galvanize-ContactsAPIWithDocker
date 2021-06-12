package com.cognizant.galvanize.contacts.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class ContactBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String givenName;

    private String surName;

    private String phoneNumber;


}
