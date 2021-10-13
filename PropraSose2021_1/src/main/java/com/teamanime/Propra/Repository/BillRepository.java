package com.teamanime.Propra.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamanime.Propra.Entities.Bill;


public interface BillRepository extends JpaRepository<Bill, Long> {
	
	
	List<Bill> findByOwnerMailAddress(String mailAddress);
	
	
	List<Bill> findByBillNumber(String label);
	
	
	

}
