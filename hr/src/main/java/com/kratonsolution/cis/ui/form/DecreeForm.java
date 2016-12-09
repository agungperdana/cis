package com.kratonsolution.cis.ui.form;

import java.util.UUID;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Decree;
import com.kratonsolution.cis.dm.Employee;
import com.kratonsolution.cis.svc.EmployeeService;
import com.kratonsolution.cis.ui.Components;
import com.kratonsolution.cis.ui.DateTimes;
import com.kratonsolution.cis.ui.Springs;
import com.kratonsolution.cis.ui.dashboard.DashboardContent;
import com.kratonsolution.cis.ui.grid.DecreeView;

public class DecreeForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private EmployeeService service = Springs.get(EmployeeService.class);

	private Toolbar toolbar = new Toolbar();

	private Textbox nama = Components.mandatoryTextBox(false);

	private Datebox tanggal = Components.datebox();

	private Grid grid = new Grid();

	public DecreeForm(Decree decree)
	{
		super();
		setTitle("SK");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(decree);
		initGrid(decree);
	}

	private void initToolbar(Decree decree)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Employee teacher = service.find(decree.getEmployee().getId());
				if(teacher != null)
				{
					if(decree.getId().equals("0"))
					{
						decree.setId(UUID.randomUUID().toString());
						decree.setNote(nama.getText());
						decree.setStart(DateTimes.sql(tanggal.getValue()));
						decree.setEmployee(teacher);
						
						teacher.getDecrees().add(decree);
					
						if(teacher.getDecree() == null || teacher.getDecree().getStart().compareTo(decree.getStart()) < 0)
							teacher.setDecree(decree);
					}
					else
					{
						for(Decree fam:teacher.getDecrees())
						{
							if(fam.getId().equals(decree.getId()))
							{
								fam.setNote(nama.getText());
								fam.setStart(DateTimes.sql(tanggal.getValue()));
								
								if(teacher.getDecree() == null || teacher.getDecree().getStart().compareTo(fam.getStart()) < 0)
									teacher.setDecree(fam);
								
								break;
							}
						}
					}
					
					service.edit(teacher);
				}

				canvas.getChildren().clear();
				
				DecreeView view = new DecreeView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(Decree decree)
	{
		
		if(!Strings.isNullOrEmpty(decree.getNote()))
			nama.setText(decree.getNote());

		if(decree.getStart() != null)
			tanggal.setValue(decree.getStart());

		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"150px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row1 = new Row();
		row1.appendChild(new Label("DESKRIPSI"));
		row1.appendChild(nama);

		Row row3 = new Row();
		row3.appendChild(new Label("TMT"));
		row3.appendChild(tanggal);

		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row3);
	}
}
