package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactForm {
    @NotBlank
    private String name;

    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Size(min=8,max=12,message="Invalid phone number")
    private String phone;

    @NotBlank
    private String address;
    private String descr;
    private boolean favBoolean;
    private String websiteLink;
    private String linkLink;
    //anootayion,size,resolution
    @ValidFile
    private MultipartFile pictureImg;
    
    private String picture;

    public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public ContactForm() {
    }

    public ContactForm(String address, String descr, String email, boolean favBoolean, String linkLink, String name, String phone, MultipartFile pictureImg, String websiteLink) {
        this.address = address;
        this.descr = descr;
        this.email = email;
        this.favBoolean = favBoolean;
        this.linkLink = linkLink;
        this.name = name;
        this.phone = phone;
        this.pictureImg = pictureImg;
        this.websiteLink = websiteLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isFavBoolean() {
        return favBoolean;
    }

    public void setFavBoolean(boolean favBoolean) {
        this.favBoolean = favBoolean;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getLinkLink() {
        return linkLink;
    }

    public void setLinkLink(String linkLink) {
        this.linkLink = linkLink;
    }

    public MultipartFile getPictureImg() {
        return pictureImg;
    }

    public void setPictureImg(MultipartFile pictureImg) {
        this.pictureImg = pictureImg;
    }

    @Override
    public String toString() {
        return "ContactForm [name=" + name + ", email=" + email + ", phone=" + phone + ", favBoolean=" + favBoolean
                + "]";
    }






}
