package com.scm.forms;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


public class UserForm {
	@NotBlank(message="Name can't be blank")
	@Size(min=3,message="Atleast 3 characters required")
	private String name;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	@Size(min=4,max=8)  @NotBlank(message="4 characters required")
	private String password;
	@Size(min=8,max=12,message="Invalid phone number")
	private String phone;
	@NotBlank(message="about field is required")
	private String about;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone
				+ ", about=" + about + "]";
	}
	public UserForm(
			@NotBlank(message = "Name can't be blank") @Size(min = 3, message = "Atleast 3 characters required") String name,
			@Email(regexp = "\"^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$\"") String email,
			@Size(min = 4, max = 8) @NotBlank(message = "4 characters required") String password,
			@Size(min = 8, max = 12, message = "Invalid phone number") String phone,
			@NotBlank(message = "about field is required") String about) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.about = about;
	}
	public UserForm() {
		super();
		// TODO Auto-generated constructor stub
	}

  
}

