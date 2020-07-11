
package com.company.kegelapp.domain;

import java.time.LocalDate;


public class Spieler
{
	private String    name;
	private String    nachName;
	private LocalDate birthday;
	private Boolean   active = false;
	
	public Spieler(final String name, final String nachName, final LocalDate birthday, final Boolean active)
	{
		super();
		this.setName(name);
		this.setNachName(nachName);
		this.birthday = birthday;
		this.setActive(active);
	}

	public Spieler()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString()
	{
		return "Spieler [name=" + this.name + ", nachName=" + this.nachName + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime  = 31;
		int       result = 1;
		result = prime * result + ((this.nachName == null) ? 0 : this.nachName.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		final Spieler other = (Spieler)obj;
		if(this.nachName == null)
		{
			if(other.nachName != null)
			{
				return false;
			}
		}
		else if(!this.nachName.equals(other.nachName))
		{
			return false;
		}
		if(this.name == null)
		{
			if(other.name != null)
			{
				return false;
			}
		}
		else if(!this.name.equals(other.name))
		{
			return false;
		}
		return true;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public LocalDate getBirthday()
	{
		return this.birthday;
	}

	public void setBirthday(final LocalDate birthday)
	{
		this.birthday = birthday;
	}

	public Boolean getActive()
	{
		return this.active;
	}

	public void setActive(final Boolean active)
	{
		
		if(active != null)
		{
			this.active = active;
		}
	}

	public String getNachName()
	{
		return this.nachName;
	}

	public void setNachName(final String nachName)
	{
		this.nachName = nachName;
	}

}
