package com.cognizant.galvanize.contacts.repository;

import com.cognizant.galvanize.contacts.domain.ContactBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactBean, String> {
}
