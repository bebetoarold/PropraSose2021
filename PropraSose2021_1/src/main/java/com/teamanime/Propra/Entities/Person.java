package com.teamanime.Propra.Entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

import com.teamanime.Propra.Constraint.PasswordValueMatch;
import com.teamanime.Propra.Constraint.ValidPassword;


@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "passwordConfirmation",
                message = "Passwords do not match!"
        )
})
@Entity
@Table
@Inheritance( strategy = InheritanceType.JOINED)
public class Person implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min=4)
	protected String firstname;
	@Column(unique = true)
	@NotBlank
	@Size(min=4)
	protected String lastname;
	@NotBlank
	@Size(min=4)
	protected String contact;
	
	@ValidPassword
	@NotBlank
	@NotNull
	@Size(min=4)
	protected String password;
	
	
	
	@NotBlank
	@NotNull
	@Size(min=4)
	protected String passwordConfirmation;
	
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp="^([a-z0-9._-]+)@([a-z0-9._-]+)\\.([a-z]{2,6})$",message="please enter a correct email!") 
	protected String mailAddress;
	
	@Pattern(regexp="^([A-Z]{2,})[0-9]{20}$",message = "the IBAN muss have 20 Digits and start with capital letters")
	@NotBlank
	private String bankData;
	
	@ColumnDefault(value = "0")
	protected boolean passChanged;
	
	@ColumnDefault(value = "1")
	 protected boolean enabled=true;
	
	@ManyToMany(targetEntity =Role.class,fetch = FetchType.EAGER)
	protected List<Role> roles;
	
	
	/* Join with Entity address
	 * 
	 * */
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	protected Address address;
	
	

	public Person() {
		super();
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getContact() {
		return contact;
	}
	
	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	

	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public boolean isPassChanged() {
		return passChanged;
	}


	public void setPassChanged(boolean passChanged) {
		this.passChanged = passChanged;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	public String getBankData() {
		return bankData;
	}


	public void setBankData(String bankData) {
		this.bankData = bankData;
	}

	
}
