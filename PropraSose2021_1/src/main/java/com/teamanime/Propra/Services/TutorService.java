package com.teamanime.Propra.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.PersonRepository;
import com.teamanime.Propra.Repository.TutorRepository;
import com.teamanime.Propra.Utilitary.FindObject;

@Transactional
@Service
public class TutorService implements TutorInterface {

	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
    private PersonRepository personRepository;

	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Tutor> listTutors() throws PropraException{


		try {
			return tutorRepository.findAll();

		}catch(Exception e){
			throw new PropraException(e);

		}

	}


	//should return the entity stored for the view the entity now has an id created during the storage process
	@Override
	public Tutor addTutor(Tutor tutor) throws PropraException{

		try {
			
			String pass=tutor.getPassword();
			String passE=passwordEncoder.encode(pass);
			tutor.setPassword(passE);
		    tutor.setPasswordConfirmation(passE);
			return tutorRepository.save(tutor);
		}catch(Exception e){
			throw new PropraException(e);

		}

	}




	@Override
	public Tutor findTutor(Long id) {
		// TODO Auto-generated method stub
		try {
			Tutor t= tutorRepository.getById(id) ;
			return t;
		}catch(Exception e) {
			throw new PropraException(e);
		}
	}


	@Override
	public List<Tutor> findTutors(FindObject ft) throws PropraException{
		// TODO Auto-generated method stub
		//check the categorie of the object and the value

		try {
			if(ft.getCategorie().equals("mailAddress"))
			{

				System.err.println(ft.getSearch());
				return tutorRepository.findWithMail(ft.getSearch());
			}
			else if(ft.getCategorie().equals("tutorLabel"))
			{	
				System.err.println(ft.getSearch());
				return tutorRepository.findWithLabel(ft.getSearch());
			}
			else if(ft.getCategorie().equals("id"))
			{
				System.err.println(ft.getSearch());
				return tutorRepository.findWithID((Long.valueOf(ft.getSearch())));
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
	public void removeTutor(Long id) throws PropraException{
		// TODO Auto-generated method stub

		try {
			Tutor indb= tutorRepository.getById(id);	
			
			
			for(Session session: indb.getMySessions()) {
				
				session.setHolder(null);
			}
		
			tutorRepository.deleteById(id);
			//personRepository.deleteById(id);
		}catch(Exception e) {

			throw new PropraException(e);


		}
	}


	@Override
	public Tutor updateTutor(Tutor tutor) throws PropraException {
		try {
			Tutor indb= tutorRepository.getById(tutor.getId());
			indb.setFirstname(tutor.getFirstname());
			indb.setLastname(tutor.getLastname());
			indb.setBankData(tutor.getBankData());
			indb.setContact(tutor.getContact());
			indb.setMailAddress(tutor.getMailAddress());
			String passE=passwordEncoder.encode(tutor.getPassword());
			indb.setPassword(passE);
			indb.setPasswordConfirmation(passE);
			indb.setTutorLabel(tutor.getTutorLabel());
			indb.setWage(tutor.getWage());
			indb.setAddress(tutor.getAddress());
			Tutor updated=tutorRepository.save(indb);
			return updated;
		}
		catch(Exception e) {
		
			throw new PropraException(e);


		}
	}


	@Override
	public Tutor linkWithSubject(Tutor tutor) throws PropraException {
		// TODO Auto-generated method stub
			
		
		try {
			Tutor indb= tutorRepository.getById(tutor.getId()) ;
			indb.setMySubjects(tutor.getMySubjects());
			return tutorRepository.save(indb);
			
			
		}catch(Exception e) {
			throw new PropraException(e);
		}
	}
	
	@Override
	public Tutor linkWithRole(Tutor tutor) throws PropraException {
		// TODO Auto-generated method stub

		try {
			Tutor indb= tutorRepository.getById(tutor.getId()) ;
			indb.setRoles(tutor.getRoles());
			return tutorRepository.save(indb);
			
			
		}catch(Exception e) {
			throw new PropraException(e);
		}
	}


	







}
