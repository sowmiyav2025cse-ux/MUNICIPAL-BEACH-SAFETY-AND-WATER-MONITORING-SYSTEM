package com.example.beachsafety.repository;

import com.example.beachsafety.entity.Contact;
import com.example.beachsafety.entity.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByContactType(ContactType contactType);
    List<Contact> findByActiveTrue();
}
