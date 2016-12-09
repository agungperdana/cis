package com.shara.hr.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shara.hr.svc.EmployeeService;

@Controller
public class PrintView
{
	@Autowired
	private EmployeeService service;
	
	@RequestMapping("/adminprint")
	public String adminprint(Model model,@RequestParam("id")String id)
	{
		model.addAttribute("emp",service.find(id));
		
		return "adminprint";
	}
	
	@RequestMapping("/dosenprint")
	public String dosenprint(Model model,@RequestParam("id")String id)
	{
		model.addAttribute("emp",service.find(id));
		System.out.println("print dosen......");
		return "dosenprint";
	}
}
