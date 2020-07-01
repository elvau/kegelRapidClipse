
package com.company.kegelapp.domain;

public class Strafe
{
	private String  bezeichnung;
	private Double  betrag;
	private Boolean active;
	
	public Strafe(final String name, final Double betrag, final Boolean active)
	{
		super();
		this.bezeichnung = name;
		this.betrag      = betrag;
		this.active      = active;
	}

	public Strafe()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString()
	{
		return "Strafe [bezeichnung=" + this.bezeichnung + ", betrag=" + this.betrag + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime  = 31;
		int       result = 1;
		result = prime * result + ((this.bezeichnung == null) ? 0 : this.bezeichnung.hashCode());
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
		final Strafe other = (Strafe)obj;
		if(this.bezeichnung == null)
		{
			if(other.bezeichnung != null)
			{
				return false;
			}
		}
		else if(!this.bezeichnung.equals(other.bezeichnung))
		{
			return false;
		}
		return true;
	}

	public String getName()
	{
		return this.bezeichnung;
	}

	public void setName(final String name)
	{
		this.bezeichnung = name;
	}

	public Double getBetrag()
	{
		return this.betrag;
	}

	public void setBetrag(final Double betrag)
	{
		this.betrag = betrag;
	}

	public Boolean getActive()
	{
		return this.active;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
	}

}
