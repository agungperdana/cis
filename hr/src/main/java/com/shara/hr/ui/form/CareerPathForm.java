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
import com.shara.hr.dm.CareerPath;
import com.shara.hr.dm.Employee;
import com.shara.hr.svc.EmployeeService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.DateTimes;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.dashboard.DashboardContent;
import com.shara.hr.ui.grid.CareerPathView;

public class CareerPathForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private EmployeeService service = Springs.get(EmployeeService.class);

	private Toolbar toolbar = new Toolbar();

	private Textbox nama = Components.mandatoryTextBox(false);

	private Datebox tanggal = Components.datebox();

	private Grid grid = new Grid();

	public CareerPathForm(CareerPath path)
	{
		super();
		setTitle("JABATAN");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(path);
		initGrid(path);
	}

	private void initToolbar(CareerPath path)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Employee teacher = service.find(path.getEmployee().getId());
				if(teacher != null)
				{
					if(path.getId().equals("0"))
					{
						path.setId(UUID.randomUUID().toString());
						path.setNote(nama.getText());
						path.setStart(DateTimes.sql(tanggal.getValue()));
						path.setEmployee(teacher);
						
						teacher.getCareerPaths().add(path);
					
						if(teacher.getPath() == null || teacher.getPath().getStart().compareTo(path.getStart()) < 0)
							teacher.setPath(path);
					}
					else
					{
						for(CareerPath fam:teacher.getCareerPaths())
						{
							if(fam.getId().equals(path.getId()))
							{
								fam.setNote(nama.getText());
								fam.setStart(DateTimes.sql(tanggal.getValue()));
								
								if(teacher.getPath() == null || teacher.getPath().getStart().compareTo(fam.getStart()) < 0)
									teacher.setPath(fam);
								
								break;
							}
						}
					}
					
					service.edit(teacher);
				}

				canvas.getChildren().clear();
				
				CareerPathView view = new CareerPathView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(CareerPath path)
	{
		
		if(!Strings.isNullOrEmpty(path.getNote()))
			nama.setText(path.getNote());

		if(path.getStart() != null)
			tanggal.setValue(path.getStart());

		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"150px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row1 = new Row();
		row1.appendChild(new Label("JABATAN"));
		row1.appendChild(nama);

		Row row3 = new Row();
		row3.appendChild(new Label("TMT"));
		row3.appendChild(tanggal);

		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row3);
	}
}
