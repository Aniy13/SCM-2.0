package com.scm.controller;

import java.awt.RenderingHints.Key;
import java.util.List;
import java.util.UUID;

import org.hibernate.sql.results.graph.instantiation.internal.ArgumentDomainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;import com.nimbusds.jose.shaded.gson.FieldNamingStrategy;
import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.AppConstants;
import com.scm.helper.HelperLogin;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.service.ContactService;
import com.scm.service.ImageService;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
@RequestMapping("user/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService UserService;
    @Autowired
    private ImageService imageService;

    Logger logger=LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/add")
    public String addContact(Model model){
        ContactForm contactForm = new ContactForm();
        // form.setName("AC");
        // form.setFavBoolean(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }
@PostMapping("/add")
public String SaveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Model model, Authentication authentication,HttpSession session) {
//    System.out.println(form);
Message message = new Message();
    if(result.hasErrors()){
        result.getAllErrors().forEach(er->logger.info(er.toString()));
        message.setContents("Please correct the following fields");
        message.setType(MessageType.red); 
      session.setAttribute("message",message);
       return "user/add_contact";
    }

   String username=HelperLogin.getEmailOfLoggedInUser(authentication);
   User user=UserService.getUserByEmail(username);
   Contact contact = new Contact();


   //image
   logger.info("File information :{}",contactForm.getPictureImg().getOriginalFilename());
  //file upload wala code   
   String fileName=UUID.randomUUID().toString();
    String imageUrl = imageService.uploadImage(contactForm.getPictureImg(),fileName);


   //data
   contact.setName(contactForm.getName());
   contact.setEmail(contactForm.getEmail());
   contact.setPhone(contactForm.getPhone());
   contact.setAddress(contactForm.getAddress());
   contact.setDescr(contactForm.getDescr());
   contact.setUser(user);
   contact.setWebsiteLink(contactForm.getWebsiteLink());
   contact.setLinkLink(contactForm.getLinkLink());
   contact.setFavBoolean(contactForm.isFavBoolean());
   contact.setPicture(imageUrl);
   contact.setCloudId(fileName);
   contactService.save(contact);
   
  
   message.setContents("Registration successful!");
		  message.setType(MessageType.green); 
		session.setAttribute("message",message);

    return  "redirect:/user/contact/add";
}
@RequestMapping("/contacts")
public String viewContacts(
		@RequestParam(value = "page",defaultValue = "0") int page,
		@RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
		@RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
		@RequestParam(value = "dir",defaultValue = "asc") String dir
		,Authentication authentication, Model model) {

    // load all the user contacts
      String username = HelperLogin.getEmailOfLoggedInUser(authentication);
      User user = UserService.getUserByEmail(username);

      Page<Contact> pageContacts = contactService.getByUser(user,page,size,sortBy,dir);
      System.out.println(pageContacts);
      
      model.addAttribute("pageContacts", pageContacts);
      model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
      model.addAttribute("contactSearchForm",   new ContactSearchForm());
//
//    model.addAttribute("contactSearchForm", new ContactSearchForm());

    return "user/contacts";
}
         //search handler
          @RequestMapping("/search")
         public String searchHandler(
          @ModelAttribute ContactSearchForm contactSearchForm,
          @RequestParam(value = "page",defaultValue = "0") int page,
          @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
          @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
  		  @RequestParam(value = "dir",defaultValue = "asc") String dir,
  		  Model model , Authentication authentication
          
        ){
        	  
        	  String username = HelperLogin.getEmailOfLoggedInUser(authentication);
              User user = UserService.getUserByEmail(username);
 
        	 logger.info("field : "+ contactSearchForm.getField() +" and "+ "keyword : "+contactSearchForm.getValue());
        	 Page<Contact> pageContacts=null;
        	 if(contactSearchForm.getField().equalsIgnoreCase("name")) {
        		pageContacts =contactService.searchByName(contactSearchForm.getValue(), page, size, sortBy, dir, user);
        	 }else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
        		 pageContacts =contactService.searchByEmail(contactSearchForm.getValue(), page, size, sortBy, dir, user);
			}else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
				pageContacts =contactService.searchByPhone(contactSearchForm.getValue(), page, size, sortBy, dir, user);
			}
        	 logger.info("PageContact {}", pageContacts);
        	 model.addAttribute("pageContacts", pageContacts);
        	 model.addAttribute("contactSearchForm", contactSearchForm);
        	 model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        	 return "user/search";
         }
          @RequestMapping("/contacts/delete/{id}")
          public String deleteContact(@PathVariable String id, HttpSession session) {
        	  contactService.delete(id);
        	  logger.info("Contact {} deleted",id);
        	  Message message = new Message();
    		  message.setContents("Contact deleted successfully!");
    		  message.setType(MessageType.green); 
    		session.setAttribute("message",message);
        	  return "redirect:/user/contact/contacts";
          }
          
          @RequestMapping("/contacts/view/{id}")
          public String updateViewContact(@PathVariable String id,Model model) {
        	  
        	  Contact contact=contactService.getById(id);
        	  ContactForm contactForm= new ContactForm();
        	  contactForm.setName(contact.getName());
              contactForm.setEmail(contact.getEmail());
              contactForm.setPhone(contact.getPhone());
              contactForm.setAddress(contact.getAddress());
              contactForm.setDescr(contact.getDescr());
              contactForm.setFavBoolean(contact.getFavBoolean());
              contactForm.setWebsiteLink(contact.getWebsiteLink());
              contactForm.setLinkLink(contact.getLinkLink());
              contactForm.setPicture(contact.getPicture());
              model.addAttribute("contactForm", contactForm);
              model.addAttribute("id", id);
        	  logger.info("Contact {} updated",id);
        	  Message message = new Message();
//    		  message.setContents("Contact updated successfully!");
//    		  message.setType(MessageType.green); 
//    		session.setAttribute("message",message);
        	  return "user/update_view";
          }
          @PostMapping("/update/{id}")
          public String updateContact(@PathVariable String id, @ModelAttribute ContactForm contactForm,Model model,HttpSession session ,BindingResult result) {
              
        	  if(result.hasErrors()) {
        		  return "/user/update_view";
        	  }
        	  
        	  Contact con= contactService.getById(id);
              con.setId(id);
              con.setName(contactForm.getName());
              con.setEmail(contactForm.getEmail());
              con.setPhone(contactForm.getPhone());
              con.setAddress(contactForm.getAddress());
              con.setDescr(contactForm.getDescr());
              con.setFavBoolean(contactForm.isFavBoolean());
              con.setWebsiteLink(contactForm.getWebsiteLink());
              con.setLinkLink(contactForm.getLinkLink());
              //image process if change
              
              
              if (contactForm.getPictureImg() != null && !contactForm.getPictureImg().isEmpty()) {
                  logger.info("file is not empty");
                  String fileName = UUID.randomUUID().toString();
                  String imageUrl = imageService.uploadImage(contactForm.getPictureImg(), fileName);
                  con.setCloudId(fileName);
                  con.setPicture(imageUrl);
                  contactForm.setPicture(imageUrl);

              } else {
                  logger.info("file is empty");
              }
              
              
              Contact contact=contactService.update(con);
              logger.info("updatedcon {}",contact);
              
              
              
              
              Message message = new Message();
    		  message.setContents("Contact updated successfully!");
    		  message.setType(MessageType.green); 
    		  session.setAttribute("message",message);
              
              return "redirect:/user/contact/contacts/view/"+id;
          }
          
       

   
}
