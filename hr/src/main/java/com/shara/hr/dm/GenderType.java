package com.shara.hr.dm;

public enum GenderType
{
	PRIA,WANITA;
	
	public String display()
	{
		switch (this)
		{
		case PRIA:
			return "Pria";
		case WANITA:
			return "Wanita";
		default:
			return "Pria";
		}
	}
}
