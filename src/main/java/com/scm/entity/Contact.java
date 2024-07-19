package com.scm.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity

public class Contact {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
   private String id;
   private String email;
   private String phone;
   private String name;
   private String address;
   private String picture;
   @Column(length = 1000)
   private String descr;
   private Boolean favBoolean=false;
   private String websiteLink;
   private String linkLink;
   private String cloudId;
   
   @ManyToOne
   @JsonIgnore
   private  User user;
   
   @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER,cascade = CascadeType.ALL ,orphanRemoval = true)
   private java.util.List<SocialLink> links = new ArrayList<>();

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
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

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getPicture() {
	return picture;
}

public void setPicture(String picture) {
	this.picture = picture;
}

public String getDescr() {
	return descr;
}

public void setDescr(String descr) {
	this.descr = descr;
}

public Boolean getFavBoolean() {
	return favBoolean;
}

public void setFavBoolean(Boolean favBoolean) {
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

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public java.util.List<SocialLink> getLinks() {
	return links;
}

public void setLinks(java.util.List<SocialLink> links) {
	this.links = links;
}

public String getCloudId() {
	return cloudId;
}

public void setCloudId(String cloudId) {
	this.cloudId = cloudId;
}

   
   
}
