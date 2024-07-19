package com.scm.service.impl;



import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repository.ContactRepo;
import com.scm.service.ContactService;


@Service
public class ContactServiceImpl  implements  ContactService{
    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String id=UUID.randomUUID().toString();
        contact.setId(id);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        Contact oldContact = contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("Contact not found"));
        oldContact.setName(contact.getName());
        oldContact.setEmail(contact.getEmail());
        oldContact.setPhone(contact.getPhone());
        oldContact.setAddress(contact.getAddress());
        oldContact.setDescr(contact.getDescr());
        oldContact.setPicture(contact.getPicture());
        oldContact.setFavBoolean(contact.getFavBoolean());
        oldContact.setWebsiteLink(contact.getWebsiteLink());
        oldContact.setLinkLink(contact.getLinkLink());
        oldContact.setCloudId(contact.getCloudId());
        
        
        return contactRepo.save(oldContact);
        
        
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found for given id"+id));
    }

    @Override
    public void delete(String id) {
       Contact con= contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found for given id"+id));
       contactRepo.delete(con);
    }

    @Override
    public List<Contact> getByUserId(String id) {
           return  contactRepo.findByUserId(id);
    }

    @Override
    public List<Contact> getAll() {
        return   contactRepo.findAll();
    }

	@Override
	public Page<Contact> getByUser(User user, int page , int size,String sortBy, String dir) {

        Sort sort=dir.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageble = PageRequest.of(page, size,sort);
		return contactRepo.findByUser(user,pageble);
	}

	@Override
	public Page<Contact> searchByName(String nameKeyword, int page , int size,String sortBy, String dir,User user) {
		Sort sort=dir.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageble=PageRequest.of(page, size,sort);
		 return contactRepo.findByUserAndNameContaining(user,nameKeyword, pageble);
	}

	@Override
	public Page<Contact> searchByEmail(String emailKeyword,int page , int size,String sortBy, String dir,User user) {
		Sort sort=dir.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageble=PageRequest.of(page, size,sort);
		 return contactRepo.findByUserAndEmailContaining(user,emailKeyword, pageble);
	}

	@Override
	public Page<Contact> searchByPhone(String phoneKeyword,int page , int size,String sortBy, String dir,User user) {
		Sort sort=dir.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageble=PageRequest.of(page, size,sort);
		 return contactRepo.findByUserAndPhoneContaining(user,phoneKeyword, pageble);
	}

}
