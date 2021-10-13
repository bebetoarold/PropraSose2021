package com.teamanime.Propra.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamanime.Propra.Entities.Salary;


public interface SalaryRepository extends JpaRepository<Salary, Long> {
	
	
	List<Salary> findByOwnerTutorLabel(String tutorLabel);
	
	
	List<Salary> findByLabelLike(String label);
	
	
	

}
