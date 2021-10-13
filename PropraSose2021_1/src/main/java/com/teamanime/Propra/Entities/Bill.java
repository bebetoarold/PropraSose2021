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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    @Column(unique=true,nullable=true)
    private String billNumber;   
    
    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(iso=ISO.DATE)
    private Date billDate;
    
    private double total;
    
    /*
     * Join with learner
     */
   @ManyToOne(targetEntity = Learner.class)
   @JoinColumn(name = "fk_owner")
    private Learner owner;
    /*
     * join with session
     */
   @OneToMany(targetEntity=Session.class, mappedBy = "bill")
   	private List<Session> sessions;
   
   
public Bill() {
	super();
	// TODO Auto-generated constructor stub
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getBillNumber() {
	return billNumber;
}


public void setBillNumber(String billNumber) {
	this.billNumber = billNumber;
}


public Date getBillDate() {
	return billDate;
}


public void setBillDate(Date billDate) {
	this.billDate = billDate;
}


public Learner getOwner() {
	return owner;
}


public void setOwner(Learner owner) {
	this.owner = owner;
}


public List<Session> getSessions() {
	return sessions;
	
}


public void setSessions(List<Session> sessions) {
	this.sessions = sessions;
	for (Session session : sessions) {
		session.setBill(this);
	}
}
    
   



   
   public double getTotal() {
	return total;
}


public void setTotal(double total) {
	this.total = total;
}


@PostPersist
   public void updateBillNumber() {
   	
   	this.setBillNumber("BIB"+this.getId());
   	
   }

	
	
}
