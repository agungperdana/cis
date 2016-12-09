package com.shara.hr.ui.form;

import java.util.UUID;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import com.google.common.base.Strings;
import com.shara.hr.dm.Publication;
import com.shara.hr.dm.Teacher;
import com.shara.hr.svc.TeacherService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.dashboard.DashboardContent;
import com.shara.hr.ui.grid.PublicationView;

public class PublicationForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private TeacherService service = Springs.get(TeacherService.class);

	private Toolbar toolbar = new Toolbar();

	private Textbox title = Components.mandatoryTextBox(false);
	
	private Textbox in = Components.mandatoryTextBox(false);

	private Textbox tahun = Components.mandatoryTextBox(false);
	
	private Grid grid = new Grid();

	public PublicationForm(Publication publication)
	{
		super();
		setTitle("PUBLIKASI");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(publication);
		initGrid(publication);
	}

	private void initToolbar(Publication publication)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Teacher teacher = service.find(publication.getEmployee().getId());
				if(teacher != null)
				{
					if(publication.getId().equals("0"))
					{
						publication.setId(UUID.randomUUID().toString());
						publication.setTitle(title.getText());
						publication.setPublishedIn(in.getText());
						publication.setPeriod(tahun.getText());
						publication.setEmployee(teacher);
						
						teacher.setPublication(publication);
						teacher.getPublications().add(publication);
						
					}
					else
					{
						for(Publication fam:teacher.getPublications())
						{
							if(fam.getId().equals(publication.getId()))
							{
								fam.setTitle(title.getText());
								fam.setPublishedIn(in.getText());
								fam.setPeriod(tahun.getText());

								if(teacher.getPublication() == null)
									teacher.setPublication(fam);
								
								break;
							}
						}
					}
					
					service.edit(teacher);
				}

				canvas.getChildren().clear();
				
				PublicationView view = new PublicationView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(Publication publication)
	{
				
		if(!Strings.isNullOrEmpty(publication.getTitle()))
			title.setText(publication.getTitle());
		
		if(!Strings.isNullOrEmpty(publication.getPublishedIn()))
			title.setText(publication.getTitle());
		
		if(!Strings.isNullOrEmpty(publication.getPeriod()))
			title.setText(publication.getTitle());

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
		row2.appendChild(new Label("DIPUBLIKASIKAN PADA"));
		row2.appendChild(in);
		
		Row row3 = new Row();
		row3.appendChild(new Label("TAHUN PUBLIKASI"));
		row3.appendChild(tahun);

		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row2);
		grid.getRows().appendChild(row3);
	}
}
