package com.teamanime.Propra.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Bill;
import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.BillRepository;
import com.teamanime.Propra.Utilitary.FindObject;

@Transactional
@Service
public class BillService implements BillInterface {
	
	@Autowired
	private BillRepository billRepository;

	

	@Override
	public Bill addBill(Bill s) throws PropraException {
		// TODO Auto-generated method stub
		
		try {
	 		
			return billRepository.save(s);
			
		}catch(Exception e) {
				
				throw new PropraException(e);
				
				
			}
	
	}

	@Override
	public Bill findBill(Long id) throws PropraException{
		// TODO Auto-generated method stub
		
		try {
		return billRepository.getById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
		}
	
	}

	

	@Override
	public void removeBill(Long id) throws PropraException {
		// TODO Auto-generated method stub
		try {
			
		Bill indb=billRepository.getById(id);
		for(Session session: indb.getSessions()) {
			session.setBill(null);
			
		}
	
		
		billRepository.deleteById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
			
			
		}
		
	}
	
	@Override
	public List<Bill> findBills(FindObject ft) throws PropraException {
		// TODO Auto-generated method stub
		
	try {
		if(ft.getCategorie().equals("mailAddress"))
		{
		
			return billRepository.findByOwnerMailAddress(ft.getSearch());
		}
		
		else if(ft.getCategorie().equals("billNumber"))
		{
			
			return billRepository.findByBillNumber(ft.getSearch());
		}
		}catch(Exception e) 
	    {
			throw new PropraException(e);
	    }
		
		return null;
	}

	
	
	

	
	
}
