package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Salary;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Utilitary.FindObject;

public interface SalaryInterface {
	
	//List<Salary> listSalaries() throws PropraException;
	Salary addSalary(Salary salary)throws PropraException;
	Salary findSalary(Long id) throws PropraException;
	List<Salary> findSalaries(FindObject ft) throws PropraException;
	void removeSalary(Long id) throws PropraException;
	Salary updateSalary(Salary salary) throws PropraException;
	
}
