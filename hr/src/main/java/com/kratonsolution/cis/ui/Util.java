package com.kratonsolution.cis.ui;

import java.util.Iterator;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.google.common.base.Strings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
@SessionScope
public class Util
{
	private boolean logedIn = false;
	
	private String username;

	private Component content;
	
	private Textbox filter = new Textbox();
	
	private Iframe iframe = new Iframe();
	
	public Util()
	{
		iframe.setHflex("1");
		iframe.setVflex("1");
		
		filter.setPlaceholder("ketik kata untuk mencari");
		filter.setWidth("90%");
		filter.setHeight("40px");
	}
	
	public synchronized void display(String title,String url)
	{
		content.getChildren().clear();
		
		if(!Strings.isNullOrEmpty(url))
		{
			iframe.setSrc(url+"&key=");
			
			Label label = new Label(title);
			label.setStyle("color:white;font-weight:bolder;font-size=16px;text-align:center");
			label.setHeight("100px");
			label.setHflex("1");
			
			content.appendChild(label);
			content.appendChild(filter);
			content.appendChild(iframe);
			
			filter.addEventListener(Events.ON_CHANGING, new EventListener<InputEvent>()
			{
				@Override
				public void onEvent(InputEvent input) throws Exception
				{
					if(iframe != null)
						iframe.setSrc(url+"&key="+input.getValue());
				}
			});
		}
	}
	
	public synchronized void display(Component... coms)
	{
		content.getChildren().clear();
		
		for(Component com:coms)
			content.appendChild(com);
	}
	
	public DesktopContainer getDesktop(Page page)
	{
		if(page != null)
		{
			Iterator<Component> iterator = page.getRoots().iterator();
			while (iterator.hasNext())
			{
				Component com = (Component) iterator.next();
				if(com instanceof DesktopContainer)
					return (DesktopContainer)com;
			}
		}
		
		return null;
	}
}
