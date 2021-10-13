package com.teamanime.Propra.Entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "subjects")
public class Subject {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	@NotBlank
	@Size(min=4)
	private String label;
	
	@NotNull
	@DecimalMin("35")
	private int limitHours; //for learner subventioned , this is the total hours pro subjects.
	
	@NotNull
	@DecimalMin("0")
	private int subjectCost;
	
	@OneToMany(targetEntity = Session.class ,mappedBy ="subject" )
	private List<Session> mySessions;
	

	@ManyToMany(targetEntity = Learner.class, mappedBy = "mySubjects")
	private Set<Learner> learning=new HashSet<>();

	@ManyToMany(targetEntity = Tutor.class, mappedBy = "mySubjects")
	private Set<Tutor> teaching =new HashSet<>();
	
	
	public Set<Tutor> getTeaching() {
		return teaching;
	}


	public void setTeaching(Set<Tutor> teaching) {
		this.teaching = teaching;
	}


	public Set<Learner> getLearning() {
		return learning;
	}


	public void setLearning(Set<Learner> learning) {
		this.learning = learning;
	}


	

	public Subject() {
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


	public int getLimitHours() {
		return limitHours;
	}


	public void setLimitHours(int limitHours) {
		this.limitHours = limitHours;
	}


	public int getSubjectCost() {
		return subjectCost;
	}


	public void setSubjectCost(int subjectCost) {
		this.subjectCost = subjectCost;
	}

	public List<Session> getMySessions() {
		return mySessions;
	}


	public void setMySessions(List<Session> mySessions) {
		this.mySessions = mySessions;
	}


	

	
	
	
}
