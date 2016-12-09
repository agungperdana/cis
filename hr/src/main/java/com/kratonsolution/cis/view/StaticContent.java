/**
 * 
 */
package com.kratonsolution.cis.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
@Controller
public class StaticContent
{
	@RequestMapping("/")
	public String root()
	{
		return "redirect:/svc/desktop";
	}
}
