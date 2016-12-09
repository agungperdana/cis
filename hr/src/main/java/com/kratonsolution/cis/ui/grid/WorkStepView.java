/**
 * 
 */
package com.kratonsolution.cis.ui.grid;

import java.text.SimpleDateFormat;
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

import com.kratonsolution.cis.dm.Employee;
import com.kratonsolution.cis.dm.WorkStep;
import com.kratonsolution.cis.svc.EmployeeService;
import com.kratonsolution.cis.ui.Components;
import com.kratonsolution.cis.ui.ImageGaleri;
import com.kratonsolution.cis.ui.RowUtils;
import com.kratonsolution.cis.ui.Springs;
import com.kratonsolution.cis.ui.dashboard.DashboardContent;
import com.kratonsolution.cis.ui.form.WorkStepForm;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public class WorkStepView extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private EmployeeService service = Springs.get(EmployeeService.class);
	
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	private Toolbar toolbar = new Toolbar();
	
	private Grid grid = new Grid();
	
	public WorkStepView(Employee employee)
	{
		super();
		
		setTitle("GOLONGAN");
		
		setHflex("1");
		setVflex("1");

		toolbar.setHflex("1");
		
		grid.setWidth("100%");
		grid.setHeight("100%");
		
		appendChild(toolbar);
		appendChild(grid);
		
		initToolbar(employee);
		initGrid(employee);
	}
	
	private void initToolbar(Employee employee)
	{
		Toolbarbutton create = new Toolbarbutton("Tambah Data");
		create.setIconSclass("z-icon-plus-square z-icon-2x");
		create.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				if(service.find(employee.getId()) == null)
				{
					Clients.showNotification("Simpan Data dosen terlebih dahulu");
					return;
				}
				
				WorkStep step = new WorkStep();
				step.setId("0");
				step.setEmployee(employee);
				
				canvas.getChildren().clear();
				
				WorkStepForm form = new WorkStepForm(step);
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
										Employee on = service.find(employee.getId());
										if(on != null)
										{
											if(on.getStep().getId().equals(RowUtils.id(row)))
												on.setStep(null);
											
											WorkStep last = null;
											
											Iterator<WorkStep> iterator = on.getWorkSteps().iterator();
											while (iterator.hasNext())
											{
												WorkStep step = (WorkStep) iterator.next();
												if(step.getId().equals(RowUtils.id(row)))
													iterator.remove();
												else
													last = step;
											}
											
											on.setStep(last);
											
											service.edit(on);
										}
									}
								}
							}
							
							initGrid(employee);
						}
					}
				});
			}
		});
		
		toolbar.appendChild(create);
		toolbar.appendChild(trash);
	}
	
	private void initGrid(Employee employee)
	{
		grid.getChildren().clear();
		
		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column("GOLONGAN",null,"150px"));
		grid.getColumns().appendChild(new Column("TMT",null,"125px"));
		grid.getColumns().appendChild(new Column(null,null,"25px"));
		grid.getColumns().appendChild(new Column());
		grid.getColumns().getLastChild().setVisible(false);
		grid.setSpan("1");
		
		for(WorkStep fam:service.find(employee.getId()).getWorkSteps())
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
			row.appendChild(new Label(fam.getNote()));
			row.appendChild(new Label(format.format(fam.getStart())));
			row.appendChild(com);
			row.appendChild(new Label(fam.getId()));
			row.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
			{
				@Override
				public void onEvent(Event arg0) throws Exception
				{
					canvas.getChildren().clear();
					WorkStepForm form = new WorkStepForm(fam);
					form.setCanvas(canvas);
					
					canvas.appendChild(form);
				}
			});
			
			grid.getRows().appendChild(row);
		}
	}
}
