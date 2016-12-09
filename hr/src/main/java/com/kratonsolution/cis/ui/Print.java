package com.kratonsolution.cis.ui;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;

public class Print extends Div
{
	private static final long serialVersionUID = 1L;

	public Print(String uri,DesktopContainer desktop)
	{
		Iframe frame = new Iframe(uri);
		frame.setHflex("1");
		frame.setVflex("1");
		
		setHflex("1");
		setVflex("1");
		
		A a = new A();
		a.setIconSclass("z-icon-remove");
		a.setStyle("float:right;top:0;");
		a.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{

			@Override
			public void onEvent(Event arg0) throws Exception
			{
				desktop.setWidth("100%");
				desktop.setPageBefore(getPage(),Print.this);
				Print.this.detach();
			}
		});
		
		appendChild(a);
		appendChild(frame);
	}
}
