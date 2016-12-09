package com.shara.hr.ui.dashboard;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

public class DashboardContent extends Vlayout
{
	private static final long serialVersionUID = 1L;
	
	protected Vlayout canvas;
	
	private Hbox hbox = new Hbox();
	
	private Label title = new Label();
	
	private A a = new A();
	
	public DashboardContent()
	{
		hbox.setHflex("1");
		hbox.setHeight("20px");
		
		a.setIconSclass("z-icon-remove");
		a.setStyle("color:orange;");
		a.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				detach();
			}
		});
		
		title.setStyle("color:orange;font-weight:bolder;font-size:10px;text-align:right;");
		title.setWidth("50px");
		
		hbox.appendChild(title);
		hbox.appendChild(a);
		hbox.setPack("end");
		hbox.setAlign("end");
		
		appendChild(hbox);
	}
	
	public void setCanvas(Vlayout vlayout)
	{
		this.canvas = vlayout;
	}
	
	public void setTitle(String title)
	{
		this.title.setValue(title);
	}
	
	public void disableClose()
	{
		a.setDisabled(true);
	}
	
	public void enableClose()
	{
		a.setDisabled(false);
	}
}
