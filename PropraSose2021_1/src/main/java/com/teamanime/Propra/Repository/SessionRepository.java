package com.teamanime.Propra.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamanime.Propra.Entities.Session;


public interface SessionRepository extends JpaRepository<Session, Long> {
	
	
	//Query(value = "SELECT s FROM Session s WHERE s.holder.id =:id")
	List<Session> findByHolderId(Long id);
	
	@Query( value = "SELECT s.* FROM sessions s WHERE s.fk_holder=:id AND  MONTH(s.start)=:month AND  MONTH(s.end)=:month AND s.took_place=1 ",nativeQuery = true)
	List<Session> findSessionOfMonth(int month, Long id);
	
	@Query( value = "SELECT s.* FROM sessions s JOIN sessions_learners sl ON(s.id=sl.fk_sessions) JOIN learners l ON(l.id=sl.fk_learner) WHERE l.id=:id "
			+ "AND MONTH(s.start)=:month AND MONTH(s.end)=:month AND s.took_place=1" ,nativeQuery = true)
	List<Session> findSessionOfMonthLearner(int month, Long id);
	
	@Query(value="Select  subjects.label , subjects.limit_hours,SUM(duration),sessions.start,sessions.end,learners.id,sessions.took_place,subjects.subject_cost "
			+ "from learners JOIN sessions_learners ON(learners.id=sessions_learners.fk_learner) JOIN sessions ON(sessions_learners.fk_sessions=sessions.id) JOIN "
			+ "subjects On(sessions.fk_subject=subjects.id) GROUP BY subjects.label,learners.id  HAVING MONTH(sessions.start)=:month AND MONTH(sessions.end)=:month AND sessions.took_place=1 AND learners.id=:id" ,nativeQuery = true)
	 List<Object[]> findSubventioned(int month,Long id);
	//not used now
	List<Session> findByValid(boolean valid);
	List<Session> findByParticipantsId(Long id);
	
	

}
