package com.teamanime.Propra.Entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.ColumnDefault;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Admin extends Person	{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ColumnDefault(value = "true")
	protected boolean disponible;
	

}
