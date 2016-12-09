package com.shara.hr.ui.dashboard;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.shara.hr.dm.Teacher;
import com.shara.hr.svc.TeacherService;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.Util;
import com.shara.hr.ui.form.Formtoolbar;
import com.shara.hr.ui.form.TeacherForm;
import com.shara.hr.ui.grid.CareerPathView;
import com.shara.hr.ui.grid.DecreeView;
import com.shara.hr.ui.grid.EducationView;
import com.shara.hr.ui.grid.FamilyView;
import com.shara.hr.ui.grid.PublicationView;
import com.shara.hr.ui.grid.ResearchView;
import com.shara.hr.ui.grid.TeacherView;
import com.shara.hr.ui.grid.WorkStepView;

public class TeacherDashboard extends Window
{
	private static final long serialVersionUID = 1L;
	
	private TeacherService service = Springs.get(TeacherService.class);
	
	private Util util = Springs.get(Util.class);
	
	private Hlayout layout = new Hlayout();
	
	private Vlayout canvas = new Vlayout();
	
	private Formtoolbar toolbar = new Formtoolbar();
	
	public TeacherDashboard(Teacher teacher)
	{
		Caption caption = new Caption(teacher.getType().getDisplay().toUpperCase());
		caption.setIconSclass("z-icon-user-plus z-icon-2x");
		
		if(service.find(teacher.getId()) == null)
			caption.setLabel(caption.getLabel()+" (DATA BARU)");
		else
			caption.setLabel(caption.getLabel()+" ("+teacher.getNama().toUpperCase()+")");
		
		setWidth("800px");
		setHeight("600px");
		setClosable(true);
		setMaximizable(true);
		setMinimizable(true);
		setSizable(true);
		setPosition("center");
		
		addEventListener(Events.ON_CLOSE, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				detach();
				util.display(new TeacherView(teacher.getType()));
			}
		});
		
		toolbar.getFamily().setDisabled(teacher.getId().equals("0"));
		toolbar.getGolongan().setDisabled(teacher.getId().equals("0"));
		toolbar.getJabatan().setDisabled(teacher.getId().equals("0"));
		toolbar.getSk().setDisabled(teacher.getId().equals("0"));
		toolbar.getEducation().setDisabled(teacher.getId().equals("0"));
		toolbar.getPublication().setDisabled(teacher.getId().equals("0"));
		toolbar.getResearch().setDisabled(teacher.getId().equals("0"));
		
		layout.setHflex("1");
		layout.setVflex("1");
		layout.appendChild(toolbar);
		layout.appendChild(canvas);
		
		canvas.setHflex("1");
		canvas.setVflex("1");
		canvas.setStyle("overflow:auto;");
		canvas.appendChild(new TeacherForm(teacher,toolbar));
		
		appendChild(caption);
		appendChild(layout);
		
		initToolbar(teacher);
	}
	
	private void initToolbar(Teacher teacher)
	{
		toolbar.getForm().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				
				TeacherForm form = new TeacherForm(teacher,toolbar);
				form.setCanvas(canvas);
				form.setTitle("FORM");
				
				canvas.appendChild(form);
			}
		});
		
		toolbar.getFamily().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				
				FamilyView view = new FamilyView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		toolbar.getGolongan().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				
				WorkStepView view = new WorkStepView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		toolbar.getJabatan().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				CareerPathView view = new CareerPathView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		toolbar.getSk().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				DecreeView view = new DecreeView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		toolbar.getEducation().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				EducationView view = new EducationView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		toolbar.getPublication().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				PublicationView view = new PublicationView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		toolbar.getResearch().addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				ResearchView view = new ResearchView(teacher);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
	}
}
