package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Bill;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Utilitary.FindObject;

public interface BillInterface {
	
	//List<Salary> listSalaries() throws PropraException;
	Bill addBill(Bill bill)throws PropraException;
	Bill findBill(Long id) throws PropraException;
	List<Bill> findBills(FindObject ft) throws PropraException;
	void removeBill(Long id) throws PropraException;
	//Bill updateBill(Bill bill) throws PropraException;
	
}
