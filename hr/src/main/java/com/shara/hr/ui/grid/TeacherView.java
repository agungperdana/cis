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

import com.shara.hr.dm.Teacher;
import com.shara.hr.dm.TeacherType;
import com.shara.hr.svc.TeacherService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.DesktopContainer;
import com.shara.hr.ui.Print;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.Util;
import com.shara.hr.ui.dashboard.TeacherDashboard;
import com.shara.hr.ui.form.TeacherZoom;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public class TeacherView extends Vlayout
{
	private static final long serialVersionUID = 1L;

	private TeacherService service = Springs.get(TeacherService.class);
	
	private Util util = Springs.get(Util.class);
	
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	private Hbox hbox = new Hbox();
	
	private Textbox filter = new Textbox();
	
	private Grid grid = new Grid();
	
	public TeacherView(TeacherType type)
	{
		super();
		
		setHflex("1");
		setVflex("1");
		
		Label title = new Label(type.getDisplay());
		title.setStyle("color:orange;font-weight:bolder;font-size:18px;padding-bottom:25px;");
		
		filter.setHeight("30px");
		filter.setHflex("1");
		filter.setPlaceholder("Masukan kata untuk mencari");
		filter.addEventListener(Events.ON_CHANGING, new EventListener<InputEvent>()
		{
			@Override
			public void onEvent(InputEvent input) throws Exception
			{
				initGrid(type, input.getValue());
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
		
		initToolbar(type);
		initGrid(type,filter.getText());
	}
	
	private void initToolbar(TeacherType type)
	{
		A create = new A();
		create.setIconSclass("z-icon-plus-square z-icon-2x");
		create.setStyle("color:limegreen;");
		create.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Teacher teacher = new Teacher();
				teacher.setId("0");
				teacher.setType(type);
				
				TeacherDashboard form = new TeacherDashboard(teacher);
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
							
							initGrid(type, filter.getValue());
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
	
	private void initGrid(TeacherType type,String key)
	{
		grid.getChildren().clear();
		
		grid.appendChild(new Rows());
		grid.appendChild(new Columns());
		grid.setEmptyMessage("Data tidak tersedia");
		grid.setSpan("1");		
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column("NAMA",null,"200px"));
		grid.getColumns().appendChild(new Column("NIDN",null,"85px"));
		grid.getColumns().appendChild(new Column("NIP",null,"85px"));
		grid.getColumns().appendChild(new Column("GOLONGAN (TMT)",null,"150px"));
		grid.getColumns().appendChild(new Column("JABATAN (TMT)",null,"150px"));
		grid.getColumns().appendChild(new Column("MASA KERJA",null,"125px"));
		grid.getColumns().appendChild(new Column());
		grid.getColumns().getLastChild().setVisible(false);
		grid.setSpan("2");
		
		Foot foot = new Foot();
		
		Footer footer = new Footer();
		footer.setSpan(7);
		footer.setHeight("25px");
		
		foot.appendChild(footer);
		
		grid.appendChild(foot);
		
		for(Teacher teacher:service.findAll(type.equals(TeacherType.ALL)?null:type, key))
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
						TeacherDashboard form = new TeacherDashboard(teacher);
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
						TeacherZoom zoom = new TeacherZoom(teacher);
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
					Print print = new Print("/dosenprint?id="+teacher.getId(), (DesktopContainer)desktop);
					print.setPageBefore(getPage(),desktop);
					desktop.detach();
				}
			});
			
			row.appendChild(print);
			row.appendChild(new Label(teacher.getNama()));
			row.appendChild(new Label(teacher.getNidn()));
			row.appendChild(new Label(teacher.getNip()));
			row.appendChild(new Label(teacher.getStep()!=null?teacher.getStep().getNote()+" ("+format.format(teacher.getStep().getStart())+")":""));
			row.appendChild(new Label(teacher.getPath()!=null?teacher.getPath().getNote()+" ("+format.format(teacher.getPath().getStart())+")":""));
			row.appendChild(new Label((teacher.getYearExp()>0?teacher.getYearExp()+" Thn ":"")+(teacher.getMonthExp()>0?teacher.getMonthExp()+" Bln":"")));
			row.appendChild(new Label(teacher.getId()));
			
			grid.getRows().appendChild(row);
		}
	}
}
