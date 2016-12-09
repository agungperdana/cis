/**
 * 
 */
package com.kratonsolution.cis.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kratonsolution.cis.dm.CurveRepository;
import com.kratonsolution.cis.dm.GenderRepository;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
@Controller
public class CurveView
{
	@Autowired
	private CurveRepository curveRepo;
	
	@Autowired
	private GenderRepository genderRepo;
	
	@RequestMapping("/gender")
	public String gender(Model model)
	{
		model.addAttribute("gender", genderRepo.findOne("00000"));
		return "gender";
	}
	
	@RequestMapping("/pendidikan")
	public String root(Model model)
	{
		model.addAttribute("grade", curveRepo.findOne("00000"));
		return "pendidikan";
	}
}
