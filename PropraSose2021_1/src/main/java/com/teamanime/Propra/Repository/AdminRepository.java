package com.teamanime.Propra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamanime.Propra.Entities.Admin;


public interface AdminRepository extends JpaRepository<Admin, Long> {
		
	Admin findByMailAddress(String mailAddress);
	
		
	
}
