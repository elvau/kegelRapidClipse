
package com.company.kegelapp.domain;

import java.time.LocalDate;
import java.util.Map;


public class Spieltag
{
	
	private LocalDate                          datum;
	private Map<Spieler, Map<Strafe, Integer>> spieltagMap;
	
	public LocalDate getDatum()
	{
		return this.datum;
	}
	
	public Spieltag(final LocalDate datum)
	{
		super();
		this.datum = datum;

	}

	public void setDatum(final LocalDate date)
	{
		this.datum = date;
	}
	
	public Map<Spieler, Map<Strafe, Integer>> getSpieltagMap()
	{
		return this.spieltagMap;
	}
	
	public void setSpieltagMap(final Map<Spieler, Map<Strafe, Integer>> spieltagMap)
	{
		this.spieltagMap = spieltagMap;
	}
	
	@Override
	public int hashCode()
	{
		final int prime  = 31;
		int       result = 1;
		result = prime * result + ((this.datum == null) ? 0 : this.datum.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(this.getClass() != obj.getClass())
		{
			return false;
		}
		final Spieltag other = (Spieltag)obj;
		if(this.datum == null)
		{
			if(other.datum != null)
			{
				return false;
			}
		}
		else if(!this.datum.equals(other.datum))
		{
			return false;
		}
		return true;
	}

	public Spieltag(final LocalDate datum, final Map<Spieler, Map<Strafe, Integer>> spieltagMap)
	{
		super();
		this.datum       = datum;
		this.spieltagMap = spieltagMap;
	}

	public Spieltag()
	{
		
	}
	
	@Override
	public String toString()
	{
		return "Spieltag [datum=" + this.datum + ", spieltagMap=" + this.spieltagMap + "]";
	}
	
}
