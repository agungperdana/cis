/**
 * 
 */
package com.kratonsolution.cis.ui.grid;

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

import com.kratonsolution.cis.dm.Research;
import com.kratonsolution.cis.dm.Teacher;
import com.kratonsolution.cis.svc.TeacherService;
import com.kratonsolution.cis.ui.Components;
import com.kratonsolution.cis.ui.ImageGaleri;
import com.kratonsolution.cis.ui.RowUtils;
import com.kratonsolution.cis.ui.Springs;
import com.kratonsolution.cis.ui.dashboard.DashboardContent;
import com.kratonsolution.cis.ui.form.ResearchForm;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public class ResearchView extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private TeacherService service = Springs.get(TeacherService.class);
	
	private Toolbar toolbar = new Toolbar();
	
	private Grid grid = new Grid();
	
	public ResearchView(Teacher teacher)
	{
		super();
		
		setTitle("PENELITIAN");
		
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
				
				Research publication = new Research();
				publication.setId("0");
				publication.setEmployee(teacher);
				
				canvas.getChildren().clear();
				
				ResearchForm form = new ResearchForm(publication);
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
											Iterator<Research> iterator = on.getResearchs().iterator();
											while (iterator.hasNext())
											{
												Research research = (Research) iterator.next();
												if(research.getId().equals(RowUtils.id(row)))
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
		grid.getColumns().appendChild(new Column("KEANGGOTAAN",null,"150px"));
		grid.getColumns().appendChild(new Column("SUMBER DANA",null,"125px"));
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column());
		grid.getColumns().getLastChild().setVisible(false);
		grid.setSpan("1");
		
		for(Research fam:service.find(teacher.getId()).getResearchs())
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
					galeri.enableClose();
					canvas.appendChild(galeri);
				}
			});
			
			Row row = new Row();
			row.appendChild(Components.checkbox(false));
			row.appendChild(new Label(fam.getTitle()));
			row.appendChild(new Label(fam.getMembership()));
			row.appendChild(new Label(fam.getFunders().display()));
			row.appendChild(com);
			row.appendChild(new Label(fam.getId()));
			row.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
			{
				@Override
				public void onEvent(Event arg0) throws Exception
				{
					canvas.getChildren().clear();
					ResearchForm form = new ResearchForm(fam);
					form.setCanvas(canvas);
					
					canvas.appendChild(form);
				}
			});
			
			grid.getRows().appendChild(row);
		}
	}
}
