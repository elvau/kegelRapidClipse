
package com.company.kegelapp.dal;

import java.util.Collection;

import com.company.kegelapp.domain.Spieler;
import com.company.kegelapp.storage.DB;


public class SpielerDAO
{
	public static Collection<Spieler> findAll()
	{
		return DB.root.getSpieler();
	}
	
	public static void insert(final Spieler spieler)
	{
		SpielerDAO.findAll().add(spieler);
		DB.storageManager.store(SpielerDAO.findAll());
	}

	public static void update(final Spieler spieler)
	{
		DB.storageManager.store(spieler);
	}
	
	public static void delete(final Spieler spieler)
	{
		SpielerDAO.findAll().remove(spieler);
		DB.storageManager.store(SpielerDAO.findAll());
	}
}
