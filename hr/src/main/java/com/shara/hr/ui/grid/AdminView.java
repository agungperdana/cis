/**
 * 
 */
package com.shara.hr.ui.grid;

import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.A;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import com.shara.hr.dm.Admin;
import com.shara.hr.svc.AdminService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.DesktopContainer;
import com.shara.hr.ui.Print;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.Util;
import com.shara.hr.ui.dashboard.AdminDashboard;
import com.shara.hr.ui.form.AdminZoom;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public class AdminView extends Vlayout
{
	private static final long serialVersionUID = 1L;

	private AdminService service = Springs.get(AdminService.class);
	
	private Util util = Springs.get(Util.class);
	
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	private Hbox hbox = new Hbox();
	
	private Textbox filter = new Textbox();
	
	private Grid grid = new Grid();
	
	public AdminView()
	{
		super();
		
		setHflex("1");
		setVflex("1");
		
		Label title = new Label("STAFF ADMINISTRASI");
		title.setStyle("color:orange;font-weight:bolder;font-size:18px;padding-bottom:25px;");
		
		filter.setHeight("30px");
		filter.setHflex("1");
		filter.setPlaceholder("Masukan kata untuk mencari");
		filter.addEventListener(Events.ON_CHANGING, new EventListener<InputEvent>()
		{
			@Override
			public void onEvent(InputEvent input) throws Exception
			{
				initGrid(input.getValue());
			}
		});
		
		hbox.setHflex("1");
		hbox.setHeight("30px");
		hbox.setAlign("center");
		hbox.setSpacing("10px");
		
		grid.setWidth("100%");
		grid.setHeight("100%");
		
		appendChild(title);
		appendChild(hbox);
		appendChild(grid);
		
		initToolbar();
		initGrid(filter.getText());
	}
	
	private void initToolbar()
	{
		A create = new A();
		create.setIconSclass("z-icon-plus-square z-icon-2x");
		create.setStyle("color:limegreen;");
		create.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Admin admin = new Admin();
				admin.setId("0");
				
				AdminDashboard form = new AdminDashboard(admin);
				form.setPage(getPage());
				form.doModal();
			}
		});
		
		A trash = new A();
		trash.setIconSclass("z-icon-trash z-icon-2x");
		trash.setStyle("color:limegreen;");
		trash.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Messagebox.show("Yakin akan menghapus data?","Warning",Messagebox.CANCEL|Messagebox.OK, Messagebox.QUESTION,new EventListener<Event>()
				{
					@Override
					public void onEvent(Event event) throws Exception
					{
						if(event.getName().equals("onOK"))
						{
							for(Object object:grid.getRows().getChildren())
							{
								Row row = (Row)object;
								
								if(row.getFirstChild() instanceof Checkbox)
								{
									Checkbox check = (Checkbox)row.getFirstChild();
									if(check.isChecked())
									{
										Label label = (Label)row.getLastChild();
										service.delete(label.getValue());
									}
								}
							}
							
							initGrid(filter.getValue());
						}
					}
				});
			}
		});
		
		A show = new A();
		A hide = new A();
		
		show.setIconSclass("z-icon-search-plus z-icon-2x");
		show.setStyle("color:limegreen;");
		show.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				hbox.removeChild(show);
				hbox.appendChild(hide);
				hbox.appendChild(filter);
			}
		});
		
		
		hide.setIconSclass("z-icon-search-minus z-icon-2x");
		hide.setStyle("color:limegreen;");
		hide.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				hbox.removeChild(hide);
				hbox.appendChild(show);
				hbox.removeChild(filter);
			}
		});
	
		if(util.isLogedIn())
		{
			hbox.appendChild(create);
			hbox.appendChild(trash);
		}
		
		hbox.appendChild(show);
	}
	
	private void initGrid(String key)
	{
		grid.getChildren().clear();
		
		grid.appendChild(new Rows());
		grid.appendChild(new Columns());
		grid.setEmptyMessage("Data tidak tersedia");
		grid.setSpan("1");		
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column("NAMA",null,"25px"));
		grid.getColumns().appendChild(new Column("NIP",null,"100px"));
		grid.getColumns().appendChild(new Column("GOLONGAN (TMT)",null,"150px"));
		grid.getColumns().appendChild(new Column("JABATAN (TMT)",null,"150px"));
		grid.getColumns().appendChild(new Column("MASA KERJA",null,"120px"));
		grid.getColumns().appendChild(new Column());
		grid.getColumns().getLastChild().setVisible(false);
		grid.setSpan("2");
		
		Foot foot = new Foot();
		
		Footer footer = new Footer();
		footer.setSpan(7);
		footer.setHeight("25px");
		
		foot.appendChild(footer);
		
		grid.appendChild(foot);
		
		for(Admin admin:service.findAll(key))
		{
			Row row = new Row();
			row.setStyle("background-color:snow;");
			row.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
			{
				@Override
				public void onEvent(Event arg0) throws Exception
				{
					if(util.isLogedIn())
					{
						AdminDashboard form = new AdminDashboard(admin);
						form.setPage(getPage());
						form.doOverlapped();
					}
				}
			});

			if(util.isLogedIn())
				row.appendChild(Components.checkbox(false));
			else
			{
				A a = new A();
				a.setIconSclass("z-icon-external-link");
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>()
				{
					@Override
					public void onEvent(Event arg0) throws Exception
					{
						AdminZoom zoom = new AdminZoom(admin);
						zoom.setPage(getPage());
						zoom.doOverlapped();
					}
				});
				
				row.appendChild(a);
			}
			
			A print = new A();
			print.setIconSclass("z-icon-print");
			print.addEventListener(Events.ON_CLICK, new EventListener<Event>()
			{
				@Override
				public void onEvent(Event arg0) throws Exception
				{
					Component desktop = util.getDesktop(getPage());
					Print print = new Print("/adminprint?id="+admin.getId(), (DesktopContainer)desktop);
					print.setPageBefore(getPage(),desktop);
					desktop.detach();
				}
			});
			
			row.appendChild(print);
			row.appendChild(new Label(admin.getNama()));
			row.appendChild(new Label(admin.getNip()));
			row.appendChild(new Label(admin.getStep()!=null?admin.getStep().getNote()+" ("+format.format(admin.getStep().getStart())+")":""));
			row.appendChild(new Label(admin.getPath()!=null?admin.getPath().getNote()+" ("+format.format(admin.getPath().getStart())+")":""));
			row.appendChild(new Label((admin.getYearExp()>0?admin.getYearExp()+" Thn ":"")+(admin.getMonthExp()>0?admin.getMonthExp()+" Bln":"")));
			row.appendChild(new Label(admin.getId()));
			
			grid.getRows().appendChild(row);
		}
	}
}
