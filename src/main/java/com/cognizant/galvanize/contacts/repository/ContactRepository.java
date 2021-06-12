package com.cognizant.galvanize.contacts.repository;

import com.cognizant.galvanize.contacts.domain.ContactBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactBean, Integer> {

    public List<ContactBean> findByGivenName(String givenName);
    public List<ContactBean> findBySurName(String surName);

}
