package com.kratonsolution.cis.dm;

public enum Religion
{
	ISLAM,KATOLIK,KRISTEN,HINDU,BUDHA;
	
	public String dispalay()
	{
		switch (this)
		{
		case ISLAM:
			return "Islam";
		case KATOLIK:
			return "Katolik";
		case KRISTEN:
			return "Kristen";
		case HINDU:
			return "Hindu";
		case BUDHA:
			return "Budha";

		default:
			return "Islam";
		}
	}
}
