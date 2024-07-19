package com.scm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
public class SocialLink {
	@Id
	private Long id;
	private String link;
	private String title;
	@ManyToOne
	private Contact contact;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public SocialLink(Long id, String link, String title, Contact contact) {
		super();
		this.id = id;
		this.link = link;
		this.title = title;
		this.contact = contact;
	}
	public SocialLink() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
