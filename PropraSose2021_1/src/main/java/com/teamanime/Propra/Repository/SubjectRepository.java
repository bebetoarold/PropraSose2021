package com.teamanime.Propra.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Entities.Tutor;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	
	@Query(value = "SELECT s FROM Subject s WHERE s.label LIKE %:label%")
	List<Subject> findWithLabel(String label);
	
	@Query(value = "SELECT s FROM Subject s WHERE s.id LIKE :id")
	List<Subject> findWithID(Long id);
	
	

}
