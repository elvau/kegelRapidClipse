
package com.company.kegelapp.storage;

import java.util.HashSet;
import java.util.Set;

import com.company.kegelapp.domain.Spieler;
import com.company.kegelapp.domain.Spieltag;
import com.company.kegelapp.domain.Strafe;


public class DataRoot
{
	private Set<Spieler>  spieler  = new HashSet<>();
	private Set<Strafe>   strafen  = new HashSet<>();
	private Set<Spieltag> spieltag = new HashSet<>();

	public DataRoot()
	{
		super();
	}

	public Set<Spieler> getSpieler()
	{
		return this.spieler;
	}

	public void setSpieler(final Set<Spieler> content)
	{
		this.spieler = content;
	}

	@Override
	public String toString()
	{
		return "Root: " + this.spieler;
	}
	
	public Set<Strafe> getStrafen()
	{
		return this.strafen;
	}
	
	public void setStrafen(final Set<Strafe> strafen)
	{
		this.strafen = strafen;
	}
	
	public Set<Spieltag> getSpieltag()
	{
		return this.spieltag;
	}
	
	public void setSpieltag(final Set<Spieltag> spieltag)
	{
		this.spieltag = spieltag;
	}
}
