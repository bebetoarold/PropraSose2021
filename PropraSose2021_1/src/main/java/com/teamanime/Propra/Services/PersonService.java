package com.teamanime.Propra.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Person;
import com.teamanime.Propra.Repository.PersonRepository;

@Transactional
@Service
public class PersonService implements UserDetailsService{

	@Autowired
	private PersonRepository personRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Person user = personRepository.findByMailAddress(username);
     
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find admin");
        }
        else {
        	
        	System.err.println(user.getMailAddress());
        	System.err.println(user.getPassword());
        	
        }
         
        return new PersonDetails(user);
    
	}
	

	


}
