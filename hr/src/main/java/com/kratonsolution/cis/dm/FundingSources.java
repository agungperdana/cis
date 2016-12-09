package com.kratonsolution.cis.dm;

public enum FundingSources
{
	DIKNAS,
	INSTITUSI_LUAR_DIKNAS,
	INSTITUSI_LUAR_NEGRERI,
	BIAYA_SENDIRI;
	
	public String display()
	{
		switch (this)
		{
			case DIKNAS:
				return "Diknas";
			case INSTITUSI_LUAR_DIKNAS:
				return "Institusi Luar Diknas";
			case INSTITUSI_LUAR_NEGRERI:
				return "Institusi Luar Negeri";
			case BIAYA_SENDIRI:
				return "Biaya Sendiri";
			default:
				return "Diknas";
		}
	}
}
