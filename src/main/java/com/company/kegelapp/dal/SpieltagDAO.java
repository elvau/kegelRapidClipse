
package com.company.kegelapp.dal;

import java.time.LocalDate;
import java.util.Collection;

import com.company.kegelapp.domain.Spieltag;
import com.company.kegelapp.storage.DB;


public class SpieltagDAO
{
	public static Collection<Spieltag> findAll()
	{
		return DB.root.getSpieltag();
	}
	
	public static void insert(final Spieltag spieltag)
	{
		SpieltagDAO.findAll().add(spieltag);
		DB.storageManager.store(SpieltagDAO.findAll());
	}

	public static void update(final Spieltag spieltag)
	{
		DB.storageManager.store(spieltag);
	}

	public static Spieltag findCurrent(final LocalDate localDate)
	{
		final Collection<Spieltag> spieltags = SpieltagDAO.findAll();
		for(final Spieltag spieltag : spieltags)
		{
			if(spieltag.getDatum().equals(localDate))
			{
				return spieltag;
			}
		}
		return null;
	}

	public static void delete(final Spieltag spieltag)
	{
		SpieltagDAO.findAll().remove(spieltag);
		DB.storageManager.store(SpieltagDAO.findAll());
	}

}
