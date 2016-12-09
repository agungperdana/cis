package com.shara.hr.ui.form;

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
import com.shara.hr.dm.Employee;
import com.shara.hr.dm.WorkStep;
import com.shara.hr.svc.EmployeeService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.DateTimes;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.dashboard.DashboardContent;
import com.shara.hr.ui.grid.WorkStepView;

public class WorkStepForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private EmployeeService service = Springs.get(EmployeeService.class);

	private Toolbar toolbar = new Toolbar();

	private Textbox nama = Components.mandatoryTextBox(false);

	private Datebox tanggal = Components.datebox();

	private Grid grid = new Grid();

	public WorkStepForm(WorkStep step)
	{
		super();
		setTitle("GOLONGAN");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(step);
		initGrid(step);
	}

	private void initToolbar(WorkStep step)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Employee teacher = service.find(step.getEmployee().getId());
				if(teacher != null)
				{
					if(step.getId().equals("0"))
					{
						step.setId(UUID.randomUUID().toString());
						step.setNote(nama.getText());
						step.setStart(DateTimes.sql(tanggal.getValue()));
						step.setEmployee(teacher);
						
						teacher.getWorkSteps().add(step);
					
						if(teacher.getStep() == null || teacher.getStep().getStart().compareTo(step.getStart()) < 0)
							teacher.setStep(step);
					}
					else
					{
						for(WorkStep fam:teacher.getWorkSteps())
						{
							if(fam.getId().equals(step.getId()))
							{
								fam.setNote(nama.getText());
								fam.setStart(DateTimes.sql(tanggal.getValue()));
								
								if(teacher.getStep() == null || teacher.getStep().getStart().compareTo(fam.getStart()) < 0)
									teacher.setStep(fam);
								
								break;
							}
						}
					}
					
					service.edit(teacher);
				}

				canvas.getChildren().clear();
				
				WorkStepView view = new WorkStepView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(WorkStep step)
	{
		
		if(!Strings.isNullOrEmpty(step.getNote()))
			nama.setText(step.getNote());

		if(step.getStart() != null)
			tanggal.setValue(step.getStart());

		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"150px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row1 = new Row();
		row1.appendChild(new Label("GOLONGAN"));
		row1.appendChild(nama);

		Row row3 = new Row();
		row3.appendChild(new Label("TMT"));
		row3.appendChild(tanggal);

		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row3);
	}
}
