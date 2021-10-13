package com.teamanime.Propra.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Learner;
import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.SubjectRepository;
import com.teamanime.Propra.Utilitary.FindObject;

@Transactional
@Service
public class SubjectService implements SubjectInterface {
	
	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public List<Subject> listSubjects() throws PropraException {
		
		// TODO Auto-generated method stub
		try {
		return subjectRepository.findAll();
		}catch(Exception e) {
			
			throw new PropraException(e);	
		}
	}

	@Override
	public Subject addSubject(Subject s) throws PropraException {
		// TODO Auto-generated method stub
		
		try {
	 		
			return subjectRepository.save(s);
			
		}catch(Exception e) {
				
				throw new PropraException(e);
				
				
			}
	
	}

	@Override
	public Subject findSubject(Long id) throws PropraException{
		// TODO Auto-generated method stub
		
		try {
		return subjectRepository.getById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
		}
	
	}

	@Override
	public List<Subject> findSubjects(FindObject ft) throws PropraException {
		// TODO Auto-generated method stub
		
	try {
		if(ft.getCategorie().equals("label"))
		{
		
			System.err.println(ft.getSearch());
			return subjectRepository.findWithLabel(ft.getSearch());
		}
		
		else if(ft.getCategorie().equals("id"))
		{
			System.err.println(ft.getSearch());
			return subjectRepository.findWithID((Long.valueOf(ft.getSearch())));
		}
		}catch(Exception e) 
	    {
			throw new PropraException(e);
	    }
		
		return null;
	}

	@Override
	public void removeSubject(Long id) throws PropraException {
		// TODO Auto-generated method stub
		try {
			
		Subject indb=subjectRepository.getById(id);
		for(Session session: indb.getMySessions()) {
			session.setSubject(null);
			
		}
		for(Tutor tutor: indb.getTeaching()) {
			tutor.getMySubjects().remove(indb);
			
		}
		for(Learner learner: indb.getLearning()) {
			learner.getMySubjects().remove(indb);
			
		}
		
		subjectRepository.deleteById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
			
			
		}
		
	}

	@Override
	public Subject updateSubject(Subject subject) throws PropraException {
		// TODO Auto-generated method stub
		try {
			Subject indb= subjectRepository.getById(subject.getId());
			indb.setLabel(subject.getLabel());
			indb.setLimitHours(subject.getLimitHours());
			indb.setSubjectCost(subject.getSubjectCost());
			Subject updated=subjectRepository.save(indb);
			return updated;
			}catch(Exception e) {
				
				throw new PropraException(e);
				
				
			}
	}

	
	
}
