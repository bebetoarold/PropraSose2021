package com.teamanime.Propra.Entities;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "payments")
public class Salary {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    
    @Column(unique=true)
    private String label;   //post persist bill_numer=ID+:JJMM_MM
    
    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(iso=ISO.DATE)
    private Date since;
    
    private double total;
    
    /*
     * Join with tutor
     */
   @ManyToOne(targetEntity = Tutor.class)
   @JoinColumn(name = "fk_owner")
    private Tutor  owner;
    /*
     * join with Session
     */
   @OneToMany(targetEntity=Session.class ,mappedBy = "payment")
   	private List<Session> sessions;
   
   
public Salary() {
	super();
	// TODO Auto-generated constructor stub
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getLabel() {
	return label;
}

public void setLabel(String label) {
	this.label = label;
}


public Date getSince() {
	return since;
}


public void setSince(Date since) {
	this.since = since;
}


public Tutor getOwner() {
	return owner;
}


public void setOwner(Tutor owner) {
	this.owner = owner;
}


public List<Session> getSessions() {
	return sessions;
}


public void setSessions(List<Session> sessions) {
	this.sessions = sessions;
	for (Session session : sessions) {
		session.setPayment(this);
	}
}


public double getTotal() {
	return total;
}


public void setTotal(double total) {
	this.total = total;
}
   

@PostPersist
public void updatelabel() {
	
	this.setLabel("BI"+(this.getId()));
	
}

   
   
    
   
 
  
	
	
}
