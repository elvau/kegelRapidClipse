
package com.company.kegelapp.dal;

import java.util.Collection;

import com.company.kegelapp.domain.Strafe;
import com.company.kegelapp.storage.DB;


public class StrafeDAO
{
	public static Collection<Strafe> findAll()
	{
		return DB.root.getStrafen();
	}
	
	public static void insert(final Strafe strafe)
	{
		StrafeDAO.findAll().add(strafe);
		DB.storageManager.store(StrafeDAO.findAll());
	}

	public static void update(final Strafe strafe)
	{
		DB.storageManager.store(strafe);
	}
}
