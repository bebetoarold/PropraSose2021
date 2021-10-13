package com.teamanime.Propra.Entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "learners")
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Learner extends Person{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@ColumnDefault(value="false")
	private boolean subventioned; //learner pays for his courses itself
	
	/*
	 * Join with Address for the bill
	 */
	
	@OneToOne(cascade = CascadeType.ALL)
	protected Address billAddress;
	
	
	
	/*
	 * join with session
	 */
	@ManyToMany(targetEntity=Session.class, mappedBy = "participants")
	protected Set<Session> mySessions;


	/* Join with Entity Subjects
	 * */
	@ManyToMany(targetEntity=Subject.class)
	@JoinTable(name = "subjects_learners",
    joinColumns = { @JoinColumn(name = "fk_learner") },
    inverseJoinColumns = { @JoinColumn(name = "fk_subject") })
	private List<Subject> mySubjects;
	
	/*
	 * join with bill
	 */
	@OneToMany(targetEntity =Bill.class, mappedBy = "owner")
	private List<Bill> bills ;



	/*
	 * constructors
	 */


	public Learner() {
		super();
		// TODO Auto-generated constructor stub
	}



	public boolean isSubventioned() {
		return subventioned;
	}


	public void setSubventioned(boolean subventioned) {
		this.subventioned = subventioned;
	}


	public Address getBillAddress() {
		return billAddress;
	}





	public void setBillAddress(Address billAddress) {
		this.billAddress = billAddress;
	}



	public Set<Session> getMySessions() {
		return mySessions;
	}



	public List<Subject> getMySubjects() {
		return mySubjects;
	}


	public void setMySubjects(List<Subject> mySubjects) {
		this.mySubjects = mySubjects;
		for (Subject subject : mySubjects) {
			if(!(subject.getLearning().contains(this))) {
				
				subject.getLearning().add(this);
			}
		}
	}



	public List<Bill> getBills() {
		return bills;
	}



	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}



	public void setMySessions(Set<Session> mySessions) {
		this.mySessions = mySessions;
	}
	



	
}
