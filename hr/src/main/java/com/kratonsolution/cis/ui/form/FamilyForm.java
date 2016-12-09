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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Employee;
import com.kratonsolution.cis.dm.Family;
import com.kratonsolution.cis.dm.FamilyType;
import com.kratonsolution.cis.svc.EmployeeService;
import com.kratonsolution.cis.ui.Components;
import com.kratonsolution.cis.ui.DateTimes;
import com.kratonsolution.cis.ui.Springs;
import com.kratonsolution.cis.ui.dashboard.DashboardContent;
import com.kratonsolution.cis.ui.grid.FamilyView;

public class FamilyForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private EmployeeService service = Springs.get(EmployeeService.class);

	private Toolbar toolbar = new Toolbar();

	private Listbox tipe = Components.newSelect();

	private Textbox nama = Components.mandatoryTextBox(false);

	private Textbox tempat = new Textbox();

	private Datebox tanggal = Components.datebox();

	private Grid grid = new Grid();

	public FamilyForm(Family family)
	{
		super();
		setTitle("KELUARGA");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(family);
		initGrid(family);
	}

	private void initToolbar(Family family)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Employee employee = service.find(family.getEmployee().getId());
				if(employee != null)
				{
					if(family.getId().equals("0"))
					{
						family.setId(UUID.randomUUID().toString());
						family.setName(nama.getText());
						family.setBirthPlace(tempat.getText());
						family.setBirthDate(DateTimes.sql(tanggal.getValue()));
						family.setType(FamilyType.valueOf(tipe.getSelectedItem().getValue().toString()));
						family.setEmployee(employee);
						
						employee.getFamilys().add(family);
					}
					else
					{
						for(Family fam:employee.getFamilys())
						{
							if(fam.getId().equals(family.getId()))
							{
								fam.setName(nama.getText());
								fam.setBirthPlace(tempat.getText());
								fam.setBirthDate(DateTimes.sql(tanggal.getValue()));
								fam.setType(FamilyType.valueOf(tipe.getSelectedItem().getValue().toString()));
							}
						}
					}
					
					service.edit(employee);
				}

				canvas.getChildren().clear();
				
				FamilyView view = new FamilyView(employee);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(Family family)
	{
		tipe.setMold("select");
		tipe.setWidth("200px");

		for(FamilyType type:FamilyType.values())
		{
			Listitem listitem = tipe.appendItem(type.name(), type.name());
			if(type.equals(family.getType()))
				tipe.setSelectedItem(listitem);
		}
		
		if(!Strings.isNullOrEmpty(family.getName()))
			nama.setText(family.getName());

		tempat.setWidth("200px");
		tempat.setText(family.getBirthPlace());

		if(family.getBirthDate() != null)
			tanggal.setValue(family.getBirthDate());

		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"150px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row0 = new Row();
		row0.appendChild(new Label("TIPE"));
		row0.appendChild(tipe);

		Row row1 = new Row();
		row1.appendChild(new Label("NAMA"));
		row1.appendChild(nama);

		Row row2 = new Row();
		row2.appendChild(new Label("TEMPAT LAHIR"));
		row2.appendChild(tempat);

		Row row3 = new Row();
		row3.appendChild(new Label("TANGGAL LAHIR"));
		row3.appendChild(tanggal);

		grid.getRows().appendChild(row0);
		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row2);
		grid.getRows().appendChild(row3);
	}
}
