package com.teamanime.Propra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamanime.Propra.Entities.Person;


public interface PersonRepository extends JpaRepository<Person, Long> {
	
	
	
	Person findByMailAddress(String mailAddress);
	
		
	
}
