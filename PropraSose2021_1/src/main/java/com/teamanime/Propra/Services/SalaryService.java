package com.teamanime.Propra.Services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Salary;
import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.SalaryRepository;
import com.teamanime.Propra.Repository.SessionRepository;
import com.teamanime.Propra.Utilitary.FindObject;

@Transactional
@Service
public class SalaryService implements SalaryInterface {
	
	@Autowired
	private SalaryRepository salaryRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public Salary addSalary(Salary s) throws PropraException {
		// TODO Auto-generated method stub
		
		try {
	 		
			return salaryRepository.save(s);
			
		}catch(Exception e) {
				
				throw new PropraException(e);
				
				
			}
	
	}

	@Override
	public Salary findSalary(Long id) throws PropraException{
		// TODO Auto-generated method stub
		
		try {
		return salaryRepository.getById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
		}
	
	}

	

	@Override
	public void removeSalary(Long id) throws PropraException {
		// TODO Auto-generated method stub
		try {
			
		Salary indb=salaryRepository.getById(id);
		for(Session session: indb.getSessions()) {
			session.setPayment(null);
			
		}
	
		
		salaryRepository.deleteById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
			
			
		}
		
	}
	
	@Override
	public List<Salary> findSalaries(FindObject ft) throws PropraException {
		// TODO Auto-generated method stub
		
	try {
		if(ft.getCategorie().equals("label"))
		{
		
			return salaryRepository.findByLabelLike(ft.getSearch());
		}
		
		else if(ft.getCategorie().equals("tutorLabel"))
		{
			
			return salaryRepository.findByOwnerTutorLabel(ft.getSearch());
		}
		}catch(Exception e) 
	    {
			throw new PropraException(e);
	    }
		
		return null;
	}

	@Override
	public Salary updateSalary(Salary salary) throws PropraException {
		// TODO Auto-generated method stub
		try {
		Salary indb=salaryRepository.getById(salary.getId());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(indb.getSince());
		for(Session session: sessionRepository.findSessionOfMonth((calendar.get(Calendar.MONTH)), indb.getOwner().getId())) {
			session.setPayment(indb);
			indb.getSessions().add(session);
		}
		return salaryRepository.save(indb);
		
		}catch(Exception e) {
			throw new  PropraException(e);
		}
	}
	

	
	
}
