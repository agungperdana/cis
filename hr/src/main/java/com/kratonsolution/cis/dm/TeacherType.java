/**
 * 
 */
package com.kratonsolution.cis.dm;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public enum TeacherType
{
	ALL,
	DOSEN_HUKUM_TATA_NEGARA,
	DOSEN_HUKUM_EKONOMI,
	DOSEN_HUKUM_PIDANA,
	DOSEN_HUKUM_PERDATA,
	DOSEN_HUKUM_INTERNASIONAL;
	
	public String getDisplay()
	{
		switch (this)
		{
			case ALL:
				return "DOSEN (KESELURUHAN)";
			case DOSEN_HUKUM_EKONOMI:
				return "Dosen Hukum Ekonomi";
			case DOSEN_HUKUM_INTERNASIONAL:
				return "Dosen Hukum Internasional";
			case DOSEN_HUKUM_PERDATA:
				return "Dosen Hukum Perdata";
			case DOSEN_HUKUM_PIDANA:
				return "Dosen Hukum Pidana";
			case DOSEN_HUKUM_TATA_NEGARA:
				return "Dosen Hukum Tata Negara";
		}
		
		return "";
	}
}
