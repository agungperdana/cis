package com.shara.hr.ui.form;

import java.util.UUID;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import com.google.common.base.Strings;
import com.shara.hr.dm.Education;
import com.shara.hr.dm.EducationLevel;
import com.shara.hr.dm.Employee;
import com.shara.hr.svc.EmployeeService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.dashboard.DashboardContent;
import com.shara.hr.ui.grid.EducationView;

public class EducationForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private EmployeeService service = Springs.get(EmployeeService.class);

	private Toolbar toolbar = new Toolbar();

	private Listbox jenis = Components.newSelect();
	
	private Textbox nama = Components.mandatoryTextBox(false);

	private Intbox start = new Intbox();
	
	private Intbox end = new Intbox();

	private Grid grid = new Grid();

	public EducationForm(Education education)
	{
		super();
		setTitle("PENDIDIKAN");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(education);
		initGrid(education);
	}

	private void initToolbar(Education education)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Employee teacher = service.find(education.getEmployee().getId());
				if(teacher != null)
				{
					if(education.getId().equals("0"))
					{
						education.setId(UUID.randomUUID().toString());
						education.setName(nama.getText());
						education.setLevel(EducationLevel.valueOf(jenis.getSelectedItem().getValue().toString()));
						education.setYearStart(start.getValue());
						education.setYearEnd(end.getValue());
						education.setEmployee(teacher);
						
						teacher.getEducations().add(education);
					
						if(teacher.getEducation() == null || teacher.getEducation().getYearStart().compareTo(education.getYearStart()) < 0)
							teacher.setEducation(education);
					}
					else
					{
						for(Education fam:teacher.getEducations())
						{
							if(fam.getId().equals(education.getId()))
							{
								fam.setName(nama.getText());
								fam.setLevel(EducationLevel.valueOf(jenis.getSelectedItem().getValue().toString()));
								fam.setYearStart(start.getValue());
								fam.setYearEnd(end.getValue());
								
								if(teacher.getEducation() == null || teacher.getEducation().getYearStart().compareTo(fam.getYearStart()) < 0)
									teacher.setEducation(fam);
								
								break;
							}
						}
					}
					
					service.edit(teacher);
				}

				canvas.getChildren().clear();
				
				EducationView view = new EducationView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(Education education)
	{
		
		for(EducationLevel level:EducationLevel.values())
		{
			Listitem listitem = jenis.appendItem(level.name(), level.name());
			if(education.getLevel().equals(level))
				jenis.setSelectedItem(listitem);
		}
		
		if(!Strings.isNullOrEmpty(education.getName()))
			nama.setText(education.getNote());
		
		if(education.getYearStart() != null)
			start.setValue(education.getYearStart());
		
		if(education.getYearEnd() != null)
			start.setValue(education.getYearEnd());

		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"150px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row1 = new Row();
		row1.appendChild(new Label("JENIS"));
		row1.appendChild(jenis);

		Row row2 = new Row();
		row2.appendChild(new Label("PERGURUAN TINGGI"));
		row2.appendChild(nama);
		
		Row row3 = new Row();
		row3.appendChild(new Label("TAHUN MULAI"));
		row3.appendChild(start);
		
		Row row4 = new Row();
		row4.appendChild(new Label("TAHUN SELESAI"));
		row4.appendChild(end);

		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row2);
		grid.getRows().appendChild(row3);
		grid.getRows().appendChild(row4);
	}
}
