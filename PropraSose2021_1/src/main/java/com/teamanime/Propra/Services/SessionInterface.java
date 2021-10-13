package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Exceptions.PropraException;

public interface SessionInterface {
	
	List<Session> listSessions() throws PropraException;
	//List<Session> listMySessions() throws PropraException;
	Session addSession(Session session)throws PropraException;
	Session findSession(Long id) throws PropraException;
	void removeSession(Long id) throws PropraException;
	Session updateSession(Session session) throws PropraException;
	Session confirmSession(Session session) throws PropraException;
	
}
