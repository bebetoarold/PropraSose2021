package com.teamanime.Propra.Entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.joda.time.Period;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "sessions")
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min=4)
	private String label;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private LocalDateTime start;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private LocalDateTime end;
	
	private int duration;
	
	private boolean tookPlace;
	private boolean valid; //not used now for features
	
	/*
	 * reference to Salary
	 */
	@ManyToOne(targetEntity = Salary.class)
	@JoinColumn(name = "fk_payment")
	private Salary payment;
	
	
	/*
	 *  Reference to learner
	 */
	@ManyToMany(targetEntity=Learner.class)
	@JoinTable(name = "sessions_learners",
    joinColumns = { @JoinColumn(name = "fk_sessions") },
    inverseJoinColumns = { @JoinColumn(name = "fk_learner") })
	private Set<Learner> participants=new HashSet<>();
	/*
	 * reference to subject
	 * 
	 */
	@ManyToOne
	 @JoinColumn(name = "fk_subject")
	private Subject subject ;
	
	/*
	 * reference to bill
	 * 
	 */
	@ManyToOne(targetEntity = Bill.class)
	@JoinColumn(name = "fk_bill")
	private Bill bill ;
	/*
	 * reference to tutor
	 */
	@ManyToOne(targetEntity = Tutor.class)
	 @JoinColumn(name = "fk_holder")
	private Tutor holder; //holder of the session
	
	
	
	
	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}







	public LocalDateTime getStart() {
		return start;
	}




	public void setStart(LocalDateTime start) {
		this.start = start;
	}




	public LocalDateTime getEnd() {
		return end;
	}




	public void setEnd(LocalDateTime end) {
		this.end = end;
	}




	public boolean isTookPlace() {
		return tookPlace;
	}




	public void setTookPlace(boolean tookPlace) {
		this.tookPlace = tookPlace;
	}





	public Set<Learner> getParticipants() {
		return participants;
	}




	public void setParticipants(Set<Learner> participants) {
		this.participants = participants;
		for (Learner learner : participants) {
			if(!(learner.getMySessions().contains(this))) {
				learner.getMySessions().add(this);
			}
		}
	}




	public Subject getSubject() {
		return subject;
	}




	public void setSubject(Subject subject) {
		this.subject = subject;
		subject.getMySessions().add(this);
	}




	public Bill getBill() {
		return bill;
	}




	public void setBill(Bill bill) {
		this.bill = bill;
	}




	public Tutor getHolder() {
		return holder;
	}



	public void setHolder(Tutor holder) {
		this.holder = holder;
		holder.getMySessions().add(this);
	}




	public boolean isValid() {
		return valid;
	}




	public void setValid(boolean valid) {
		this.valid = valid;
	}




	public String getLabel() {
		return label;
	}




	public void setLabel(String label) {
		this.label = label;
	}




	public Salary getPayment() {
		return payment;
	}




	public void setPayment(Salary payment) {
		this.payment = payment;
	}




	public int getDuration() {
		return duration;
	}




	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
		@PostPersist
		@PreUpdate
		public void updateDuration() {
			
			System.err.println(">>>>>>>>>>>starting the duration initialisation<<<<<<<<<<<<<<<<<<<<<<");
			java.time.LocalDateTime java8LocalDateTime = this.getStart();
			java.time.ZonedDateTime java8ZonedDateTime = java8LocalDateTime.atZone(ZoneId.systemDefault());
			java.time.Instant java8Instant = java8ZonedDateTime.toInstant();
			long millis = java8Instant.toEpochMilli();
			org.joda.time.LocalDateTime myStart = new org.joda.time.LocalDateTime(millis);
			
			java.time.LocalDateTime java8LocalDateTime2 = this.getEnd();
			java.time.ZonedDateTime java8ZonedDateTime2 = java8LocalDateTime2.atZone(ZoneId.systemDefault());
			java.time.Instant java8Instant2 = java8ZonedDateTime2.toInstant();
			long millis2 = java8Instant2.toEpochMilli();
			org.joda.time.LocalDateTime myEnd = new org.joda.time.LocalDateTime(millis2);
			
			
			Period p=new Period(myStart,myEnd);
			this.setDuration(p.getHours());			
		}
		
		
}
