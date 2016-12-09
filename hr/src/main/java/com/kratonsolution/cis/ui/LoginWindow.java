package com.kratonsolution.cis.ui;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Admin;
import com.kratonsolution.cis.svc.AdminService;

public class LoginWindow extends Window
{
	private static final long serialVersionUID = 1L;

	public LoginWindow()
	{
		Caption caption = new Caption("Login Admin");
		caption.setIconSclass("z-icon-key z-icon-2x");
		
		appendChild(caption);
		
		setClosable(true);
		setMaximizable(false);
		setMinimizable(false);
		setSizable(false);
		setPosition("center");
		
		setWidth("400px");
		setHeight("180px");
		
		init();
	}
	
	private void init()
	{
		Vbox vbox = new Vbox();
		vbox.setHflex("1");
		vbox.setVflex("1");
		
		Textbox user = new Textbox();
		user.setPlaceholder("Nama Login");
		user.setHflex("1");
		user.setHeight("40px");
		
		Textbox pass = new Textbox();
		pass.setPlaceholder("Kata kunci");
		pass.setHflex("1");
		pass.setType("password");
		pass.setHeight("40px");
		
		Button button = new Button("Login");
		button.setHflex("1");
		button.setIconSclass("z-icon-cog z-icon-spin");
		button.setHeight("40px");
		button.setStyle("background-color:green;font-weight:bolder;color:orange;");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				if(Strings.isNullOrEmpty(user.getText()))
					throw new WrongValueException(user, "Nama login tidak boleh kosong");
				
				if(Strings.isNullOrEmpty(pass.getText()))
					throw new WrongValueException(pass, "Kata kunci tidak boleh kosong");
				
				AdminService service = Springs.get(AdminService.class);
				
				Admin admin = service.authenticate(user.getText(), pass.getText());
				if(admin != null)
				{
					Util util = Springs.get(Util.class);
					util.setLogedIn(true);
					util.setUsername(admin.getNama());
					
					LoginWindow.this.detach();
					
					Executions.sendRedirect("/");
				}
			}
		});
		
		
		vbox.appendChild(user);
		vbox.appendChild(pass);
		vbox.appendChild(button);
		
		appendChild(vbox);
	}
}
