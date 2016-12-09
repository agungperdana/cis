package com.kratonsolution.cis.ui;

import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Style;

public class Desktop extends GenericRichlet
{
	@Override
	public void service(Page page) throws Exception
	{
		Style style = new Style("/css/desktop.css");
		style.setPage(page);
		
		DesktopContainer con = new DesktopContainer();
		con.setPage(page);
		con.setWidth("100.75%");
	}
}