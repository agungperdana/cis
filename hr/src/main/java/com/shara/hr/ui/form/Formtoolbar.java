package com.shara.hr.ui.form;

import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import lombok.Getter;

@Getter
public class Formtoolbar extends Toolbar
{
	private static final long serialVersionUID = 1L;

	private Toolbarbutton form = new Toolbarbutton("FORM","/icons/form.png");

	private Toolbarbutton family = new Toolbarbutton("KELUARGA","/icons/family.png");

	private Toolbarbutton golongan = new Toolbarbutton("GOLONGAN","/icons/step.png");

	private Toolbarbutton jabatan = new Toolbarbutton("JABATAN","/icons/path.png");

	private Toolbarbutton sk = new Toolbarbutton("SK","/icons/decree.png");

	private Toolbarbutton education = new Toolbarbutton("PENDIDIKAN","/icons/education.png");

	private Toolbarbutton publication = new Toolbarbutton("PUBLIKASI","/icons/publication.png");

	private Toolbarbutton research = new Toolbarbutton("PENELITIAN","/icons/research.png");

	public Formtoolbar()
	{
		super("vertical");
	
		setStyle("background-color:blackeel;font-size:10px;");
		setWidth("70px");
		setVflex("1");
		appendChild(form);
		appendChild(family);
		appendChild(golongan);
		appendChild(jabatan);
		appendChild(sk);
		appendChild(education);
		appendChild(publication);
		appendChild(research);
		
		form.setOrient("vertical");
		form.setStyle("text-align:center;font-size:9px;");
		
		family.setOrient("vertical");
		family.setStyle("text-align:center;font-size:9px;");
		
		golongan.setOrient("vertical");
		golongan.setStyle("text-align:center;font-size:9px;");
		
		jabatan.setOrient("vertical");
		jabatan.setStyle("text-align:center;font-size:9px;");
		
		sk.setOrient("vertical");
		sk.setStyle("text-align:center;font-size:9px;");
		
		education.setOrient("vertical");
		education.setStyle("text-align:center;font-size:9px;");
		
		publication.setOrient("vertical");
		publication.setStyle("text-align:center;font-size:9px;");
		
		research.setOrient("vertical");
		research.setStyle("text-align:center;font-size:9px;");
	}
}
