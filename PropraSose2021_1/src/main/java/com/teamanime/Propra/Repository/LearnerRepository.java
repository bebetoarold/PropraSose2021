package com.teamanime.Propra.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamanime.Propra.Entities.Learner;


public interface LearnerRepository extends JpaRepository<Learner, Long> {
	
	 Learner findByMailAddress(String mailAddress);
	
	@Query(value = "SELECT t FROM Learner t WHERE t.mailAddress LIKE %:mailAddress%")
	List<Learner> findWithMail(String mailAddress);
	
	
	
	@Query(value = "SELECT t FROM Learner t WHERE t.id LIKE :id")
	List<Learner> findWithID(Long id);

}
