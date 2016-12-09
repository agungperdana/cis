package com.shara.hr.ui.form;

import java.util.UUID;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
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
import com.shara.hr.dm.FundingSources;
import com.shara.hr.dm.Research;
import com.shara.hr.dm.Teacher;
import com.shara.hr.svc.TeacherService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.dashboard.DashboardContent;
import com.shara.hr.ui.grid.ResearchView;

public class ResearchForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private TeacherService service = Springs.get(TeacherService.class);

	private Toolbar toolbar = new Toolbar();

	private Textbox title = Components.mandatoryTextBox(false);
	
	private Textbox membership = Components.mandatoryTextBox(false);

	private Listbox funder = Components.newSelect();
	
	private Textbox tahun = Components.mandatoryTextBox(false);
	
	private Grid grid = new Grid();

	public ResearchForm(Research research)
	{
		super();
		setTitle("PENELITIAN");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(research);
		initGrid(research);
	}

	private void initToolbar(Research research)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Teacher teacher = service.find(research.getEmployee().getId());
				if(teacher != null)
				{
					if(research.getId().equals("0"))
					{
						research.setId(UUID.randomUUID().toString());
						research.setTitle(title.getText());
						research.setMembership(membership.getText());
						research.setPeriod(tahun.getText());
						research.setFunders(FundingSources.valueOf(funder.getSelectedItem().getValue().toString()));
						research.setEmployee(teacher);
						
						teacher.setResearch(research);
						teacher.getResearchs().add(research);
						
					}
					else
					{
						for(Research fam:teacher.getResearchs())
						{
							if(fam.getId().equals(research.getId()))
							{
								fam.setTitle(title.getText());
								fam.setMembership(membership.getText());
								fam.setPeriod(tahun.getText());
								fam.setFunders(FundingSources.valueOf(funder.getSelectedItem().getValue().toString()));

								if(teacher.getResearch() == null)
									teacher.setResearch(fam);
								
								break;
							}
						}
					}
					
					service.edit(teacher);
				}

				canvas.getChildren().clear();
				
				ResearchView view = new ResearchView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(Research research)
	{
		for(FundingSources sources:FundingSources.values())
		{
			Listitem listitem = funder.appendItem(sources.display(), sources.name());
			if(sources.equals(research.getFunders()))
				funder.setSelectedItem(listitem);
		}
				
		if(!Strings.isNullOrEmpty(research.getTitle()))
			title.setText(research.getTitle());
		
		if(!Strings.isNullOrEmpty(research.getMembership()))
			membership.setText(research.getMembership());
		
		if(!Strings.isNullOrEmpty(research.getPeriod()))
			tahun.setText(research.getPeriod());

		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"150px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row1 = new Row();
		row1.appendChild(new Label("JUDUL"));
		row1.appendChild(title);

		Row row2 = new Row();
		row2.appendChild(new Label("KEANGGOTAAN"));
		row2.appendChild(membership);
		
		Row row3 = new Row();
		row3.appendChild(new Label("SUMBER DANA"));
		row3.appendChild(funder);
		
		Row row4 = new Row();
		row4.appendChild(new Label("TAHUN"));
		row4.appendChild(tahun);

		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row2);
		grid.getRows().appendChild(row3);
		grid.getRows().appendChild(row4);
	}
}
