package com.teamanime.Propra.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.SessionRepository;

@Transactional
@Service
public class SessionService implements SessionInterface {
	
	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public List<Session> listSessions() throws PropraException {
		
		// TODO Auto-generated method stub
		try {
		return sessionRepository.findAll();
		}catch(Exception e) {
			
			throw new PropraException(e);	
		}
	}

	@Override
	public Session addSession(Session s) throws PropraException {
		// TODO Auto-generated method stub
		
		try {
	 		
			return sessionRepository.save(s);
			
		}catch(Exception e) {
				
				throw new PropraException(e);
				
				
			}
	
	}

	@Override
	public Session findSession(Long id) throws PropraException{
		// TODO Auto-generated method stub
		
		try {
		return sessionRepository.getById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
		}
	
	}

	
	@Override
	public void removeSession(Long id) throws PropraException {
		// TODO Auto-generated method stub
		try {
		sessionRepository.deleteById(id);
		}catch(Exception e) {
			throw new PropraException(e);
			
			
		}
		
	}
	
	

	@Override
	public Session confirmSession (Session session) throws PropraException {
		// TODO Auto-generated method stub
		try {
			Session indb= sessionRepository.getById(session.getId());	
			indb.setTookPlace(true);
			indb.setParticipants(session.getParticipants());
			Session  updated=sessionRepository.save(indb);
			return updated;
		}catch(Exception e) {
		
			throw new PropraException(e);
			
			
		}
		
	}

	@Override
	public Session updateSession(Session session) throws PropraException {
		// TODO Auto-generated method stub
		try {
			Session indb= sessionRepository.getById(session.getId());
			indb.setStart(session.getStart());
			indb.setEnd(session.getEnd());
			indb.setLabel(session.getLabel());
			indb.setSubject(session.getSubject());
			Session updated=sessionRepository.save(indb);
			return updated;
			}catch(Exception e) {
				
				throw new PropraException(e);
				
				
			}
	}

	
	
}
