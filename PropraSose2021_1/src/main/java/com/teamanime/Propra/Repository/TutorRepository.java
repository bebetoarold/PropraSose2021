package com.teamanime.Propra.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Entities.Tutor;


public interface TutorRepository extends JpaRepository<Tutor, Long> {
	
	 Tutor findByMailAddress(String mailAddress);
	 List<Tutor> findByMySubjectsLabel(String label);
	
	@Query(value = "SELECT t FROM Tutor t WHERE t.mailAddress LIKE %:mailAddress%")
	List<Tutor> findWithMail(String mailAddress);
	
	@Query(value = "SELECT t FROM Tutor t WHERE t.tutorLabel LIKE %:tutorLabel%")
	List<Tutor> findWithLabel(String tutorLabel);
	
	@Query(value = "SELECT t FROM Tutor t WHERE t.id LIKE :id")
	List<Tutor> findWithID(Long id);
	
	
		
}
