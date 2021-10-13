package com.teamanime.Propra.Entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tutors")
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Tutor extends Person	{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(unique=true)
	@NotBlank
	@Size(min=4)
	private String tutorLabel;
	
	//Pattern(regexp="\\d+", message="please enter an correct integer")
	@DecimalMin("1")
	private int wage;
	
	
	/* Join with Entity Subjects
	 * */
	@ManyToMany(targetEntity=Subject.class)
	@JoinTable(name = "subjects_tutors",
    joinColumns = { @JoinColumn(name = "fk_tutor") },
    inverseJoinColumns = { @JoinColumn(name = "fk_subject") })

	private List<Subject> mySubjects;
	

	
	/*
	 * join with session
	 */
	@OneToMany(targetEntity =Session.class, mappedBy = "holder")
	private List<Session> mySessions;
	
	/*
	 * join with salary
	 */
	@OneToMany(targetEntity =Salary.class, mappedBy = "owner")
	private List<Salary> fees ;

	
	
	
	
	
	
	
	
	/**
	 * Constructors
	 */
	
	public Tutor() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getTutorLabel() {
		return tutorLabel;
	}


	public void setTutorLabel(String tutorLabel) {
		this.tutorLabel = tutorLabel;
	}



	public int getWage() {
		return wage;
	}


	public void setWage(int wage) {
		this.wage = wage;
	}

	


	public List<Subject> getMySubjects() {
		return mySubjects;
	}


	public void setMySubjects(List<Subject> mySubjects) {
		this.mySubjects = mySubjects;
		for (Subject subject : mySubjects) {
			if(!(subject.getTeaching().contains(this))) {
				
				subject.getTeaching().add(this);
			}
		}
	}




	public List<Session> getMySessions() {
		return mySessions;
	}


	public void setMySessions(List<Session> mySessions) {
		this.mySessions = mySessions;
	}


	public List<Salary> getFees() {
		return fees;
	}


	public void setFees(List<Salary> fees) {
		this.fees = fees;
	}


	


	


	
	
	


	
	
	

}
