package com.teamanime.Propra.Services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Learner;
import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.LearnerRepository;
import com.teamanime.Propra.Repository.PersonRepository;
import com.teamanime.Propra.Repository.TutorRepository;
import com.teamanime.Propra.Utilitary.FindObject;

@Transactional
@Service
public class LearnerService implements LearnerInterface {

	@Autowired
    private LearnerRepository learnerRepository;
	
	@Autowired
    private PersonRepository personRepository;

	@Autowired
    private TutorRepository tutorRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<Learner> listLearners() throws PropraException {
		try {
			return learnerRepository.findAll();

		}catch(Exception e){
			throw new PropraException(e);

		}
	}

	@Override
	public Learner addLearner(Learner learner) throws PropraException {
  try {
			
			String pass=learner.getPassword();
			String passE=passwordEncoder.encode(pass);
			learner.setPassword(passE);
		    learner.setPasswordConfirmation(passE);
			return learnerRepository.save(learner);
		}catch(Exception e){
			throw new PropraException(e);

		}
	}

	@Override
	public Learner findLearner(Long id) throws PropraException {
		try {
			Learner l= learnerRepository.getById(id) ;
			return l;
		}catch(Exception e) {
			throw new PropraException(e);
		}
	}

	@Override
	public List<Learner> findLearners(FindObject ft) throws PropraException {
		try {
			if(ft.getCategorie().equals("mailAddress"))
			{

				System.err.println(ft.getSearch());
				return learnerRepository.findWithMail(ft.getSearch());
			}
			
			else if(ft.getCategorie().equals("id"))
			{
				System.err.println(ft.getSearch());
				return learnerRepository.findWithID((Long.valueOf(ft.getSearch())));
			}
			else if(ft.getCategorie().equals("subject"))
			{
				System.err.println(ft.getSearch());
				System.err.println("feature not implemented yet");
			}
		}catch(Exception e) 
		{
			throw new PropraException(e);

		}
		return null;
	}

	@Override
	public void removeLearner(Long id) throws PropraException {
		// TODO Auto-generated method stub
		try {
			Learner indb= learnerRepository.getById(id);
			
			for (Session session: indb.getMySessions()) {
				
				session.getParticipants().remove(indb);
			}
			
			

			
			
			learnerRepository.deleteById(id);
			//personRepository.deleteById(id);
			
		}catch(Exception e) {

			throw new PropraException(e);


		}
	}

	@Override
	public Learner updateLearner(Learner learner) throws PropraException {
		// TODO Auto-generated method stub
		try {
			Learner indb= learnerRepository.getById(learner.getId());
			indb.setFirstname(learner.getFirstname());
			indb.setLastname(learner.getLastname());
			indb.setBankData(learner.getBankData());
			indb.setContact(learner.getContact());
			indb.setMailAddress(learner.getMailAddress());
			String passE=passwordEncoder.encode(learner.getPassword());
			indb.setPassword(passE);
			indb.setPasswordConfirmation(passE);
			indb.setAddress(learner.getAddress());
			indb.setBillAddress(learner.getBillAddress());
			indb.setSubventioned(learner.isSubventioned());
           
			Learner updated=learnerRepository.save(indb);
			return updated;
		}
		catch(Exception e) {
		
			throw new PropraException(e);


		}
	}

	@Override
	public Learner linkWithSubject(Learner learner) throws PropraException {
		try {
			Learner indb= learnerRepository.getById(learner.getId()) ;
			indb.setMySubjects(learner.getMySubjects());
		
		
			return learnerRepository.save(indb);
			
			
		}catch(Exception e) {
			throw new PropraException(e);
		}
	}

	@Override
	public Learner linkWithRole(Learner learner) throws PropraException {
		try {
			Learner indb= learnerRepository.getById(learner.getId()) ;
			indb.setRoles(learner.getRoles());
			return learnerRepository.save(indb);
			
			
		}catch(Exception e) {
			throw new PropraException(e);
		}
	}

	


}
