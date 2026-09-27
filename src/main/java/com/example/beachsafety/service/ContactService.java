package com.example.beachsafety.service;

import com.example.beachsafety.entity.Contact;
import com.example.beachsafety.entity.ContactType;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> getContactsByType(ContactType type) {
        return contactRepository.findByContactType(type);
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with ID: " + id));
    }

    public Contact createContact(Contact contact) {
        if (contact.getActive() == null) {
            contact.setActive(true);
        }
        return contactRepository.save(contact);
    }

    public Contact updateContact(Long id, Contact updatedContact) {
        Contact existing = getContactById(id);
        existing.setName(updatedContact.getName());
        existing.setContactType(updatedContact.getContactType());
        existing.setEmail(updatedContact.getEmail());
        existing.setPhone(updatedContact.getPhone());
        existing.setAddress(updatedContact.getAddress());
        if (updatedContact.getActive() != null) {
            existing.setActive(updatedContact.getActive());
        }
        return contactRepository.save(existing);
    }

    public void deleteContact(Long id) {
        Contact existing = getContactById(id);
        contactRepository.delete(existing);
    }
}
