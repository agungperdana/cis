/**
 * 
 */
package com.shara.hr.ui.grid;

import java.util.Iterator;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import com.shara.hr.dm.Publication;
import com.shara.hr.dm.Teacher;
import com.shara.hr.svc.TeacherService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.ImageGaleri;
import com.shara.hr.ui.RowUtils;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.dashboard.DashboardContent;
import com.shara.hr.ui.form.PublicationForm;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public class PublicationView extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private TeacherService service = Springs.get(TeacherService.class);
	
	private Toolbar toolbar = new Toolbar();
	
	private Grid grid = new Grid();
	
	public PublicationView(Teacher teacher)
	{
		super();
		
		setTitle("PUBLIKASI");
		
		setHflex("1");
		setVflex("1");

		toolbar.setHflex("1");
		
		grid.setWidth("100%");
		grid.setHeight("100%");
		
		appendChild(toolbar);
		appendChild(grid);
		
		initToolbar(teacher);
		initGrid(teacher);
	}
	
	private void initToolbar(Teacher teacher)
	{
		Toolbarbutton create = new Toolbarbutton("Tambah Data");
		create.setIconSclass("z-icon-plus-square z-icon-2x");
		create.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				if(service.find(teacher.getId()) == null)
				{
					Clients.showNotification("Simpan Data dosen terlebih dahulu");
					return;
				}
				
				Publication publication = new Publication();
				publication.setId("0");
				publication.setEmployee(teacher);
				
				canvas.getChildren().clear();
				
				PublicationForm form = new PublicationForm(publication);
				form.setCanvas(canvas);
				
				canvas.appendChild(form);
			}
		});
		
		Toolbarbutton trash = new Toolbarbutton("Hapus Data");
		trash.setIconSclass("z-icon-trash z-icon-2x");
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
										Teacher on = service.find(teacher.getId());
										if(on != null)
										{
											Iterator<Publication> iterator = on.getPublications().iterator();
											while (iterator.hasNext())
											{
												Publication pub = (Publication) iterator.next();
												if(pub.getId().equals(RowUtils.id(row)))
													iterator.remove();
											}
										
											service.edit(on);
										}
									}
								}
							}
							
							initGrid(teacher);
						}
					}
				});
			}
		});
		
		toolbar.appendChild(create);
		toolbar.appendChild(trash);
	}
	
	private void initGrid(Teacher teacher)
	{
		grid.getChildren().clear();
		
		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column("JUDUL",null,"250px"));
		grid.getColumns().appendChild(new Column("DIPUBLIKASIKAN PADA",null,"250px"));
		grid.getColumns().appendChild(new Column("TAHUN",null,"100px"));
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column());
		grid.getColumns().getLastChild().setVisible(false);
		grid.setSpan("1");
		
		for(Publication fam:service.find(teacher.getId()).getPublications())
		{
			A com = new A();
			com.setIconSclass("z-icon-camera");
			com.addEventListener(Events.ON_CLICK, new EventListener<Event>()
			{
				@Override
				public void onEvent(Event arg0) throws Exception
				{
					canvas.getChildren().clear();
					ImageGaleri galeri = new ImageGaleri(fam);
					galeri.setCanvas(canvas);
					canvas.appendChild(galeri);
				}
			});
			
			Row row = new Row();
			row.appendChild(Components.checkbox(false));
			row.appendChild(new Label(fam.getTitle()));
			row.appendChild(new Label(fam.getPublishedIn()));
			row.appendChild(new Label(fam.getPeriod()));
			row.appendChild(com);
			row.appendChild(new Label(fam.getId()));
			row.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
			{
				@Override
				public void onEvent(Event arg0) throws Exception
				{
					canvas.getChildren().clear();
					PublicationForm form = new PublicationForm(fam);
					form.setCanvas(canvas);
					
					canvas.appendChild(form);
				}
			});
			
			grid.getRows().appendChild(row);
		}
	}
}
