package com.scm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entity.Contact;
import com.scm.entity.User;


public interface ContactService {

    Contact save(Contact contact);


    Contact update(Contact contact);

    List<Contact> getAll();


    Contact getById(String id);


    void delete(String id);


    Page<Contact>  searchByName(String nameKeyword,int page , int size,String sortBy, String dir,User user);
    Page<Contact>  searchByEmail(String emailKeyword,int page , int size,String sortBy, String dir,User user);
    Page<Contact>  searchByPhone(String phoneKeyword,int page , int size,String sortBy, String dir,User user);


    List<Contact> getByUserId(String id);
    
    Page<Contact> getByUser(User user,int page , int size,String sortBy, String dir);

}
