package com.kratonsolution.cis.ui.dashboard;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.kratonsolution.cis.dm.Admin;
import com.kratonsolution.cis.svc.AdminService;
import com.kratonsolution.cis.ui.Springs;
import com.kratonsolution.cis.ui.Util;
import com.kratonsolution.cis.ui.form.AdminForm;
import com.kratonsolution.cis.ui.grid.AdminView;
import com.kratonsolution.cis.ui.grid.CareerPathView;
import com.kratonsolution.cis.ui.grid.DecreeView;
import com.kratonsolution.cis.ui.grid.EducationView;
import com.kratonsolution.cis.ui.grid.FamilyView;
import com.kratonsolution.cis.ui.grid.WorkStepView;

public class AdminDashboard extends Window
{
	private static final long serialVersionUID = 1L;
	
	private AdminService service = Springs.get(AdminService.class);
	
	private Util util = Springs.get(Util.class);
	
	private Hlayout layout = new Hlayout();
	
	private Vlayout canvas = new Vlayout();
	
	private Toolbar toolbar = new Toolbar("vertical");
	
	private Toolbarbutton form = new Toolbarbutton("FORM","/icons/form.png");
	
	private Toolbarbutton family = new Toolbarbutton("KELUARGA","/icons/family.png");
	
	private Toolbarbutton golongan = new Toolbarbutton("GOLONGAN","/icons/step.png");
	
	private Toolbarbutton jabatan = new Toolbarbutton("JABATAN","/icons/path.png");
	
	private Toolbarbutton sk = new Toolbarbutton("SK","/icons/decree.png");
	
	private Toolbarbutton education = new Toolbarbutton("PENDIDIKAN","/icons/education.png");
	
	private Toolbarbutton publication = new Toolbarbutton("PUBLIKASI","/icons/publication.png");
	
	private Toolbarbutton research = new Toolbarbutton("PENELITIAN","/icons/research.png");
	
	public AdminDashboard(Admin admin)
	{
		Caption caption = new Caption("STAF ADMINISTRASI ");
		caption.setIconSclass("z-icon-user-plus z-icon-2x");
		
		if(service.find(admin.getId()) == null)
			caption.setLabel(caption.getLabel()+" (DATA BARU)");
		else
			caption.setLabel(caption.getLabel()+" ("+admin.getNama().toUpperCase()+")");
		
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
				util.display(new AdminView());
			}
		});
		
		toolbar.setStyle("background-color:blackeel;font-size:10px;");
		toolbar.setWidth("70px");
		toolbar.setVflex("1");
		toolbar.appendChild(form);
		toolbar.appendChild(family);
		toolbar.appendChild(golongan);
		toolbar.appendChild(jabatan);
		toolbar.appendChild(sk);
		toolbar.appendChild(education);
		
		form.setOrient("vertical");
		form.setStyle("text-align:center;font-size:9px;");
		
		family.setOrient("vertical");
		family.setStyle("text-align:center;font-size:9px;");
		
		golongan.setOrient("vertical");
		golongan.setStyle("text-align:center;font-size:9px;");
		
		jabatan.setOrient("vertical");
		jabatan.setStyle("text-align:center;font-size:9px;");
		
		sk.setOrient("vertical");
		sk.setStyle("text-align:center;font-size:9px;");
		
		education.setOrient("vertical");
		education.setStyle("text-align:center;font-size:9px;");
		
		publication.setOrient("vertical");
		publication.setStyle("text-align:center;font-size:9px;");
		
		research.setOrient("vertical");
		research.setStyle("text-align:center;font-size:9px;");
		
		layout.setHflex("1");
		layout.setVflex("1");
		layout.appendChild(toolbar);
		layout.appendChild(canvas);
		
		canvas.setHflex("1");
		canvas.setVflex("1");
		canvas.setStyle("overflow:auto;");
		canvas.appendChild(new AdminForm(admin));
		
		appendChild(caption);
		appendChild(layout);
		
		initToolbar(admin);
	}
	
	private void initToolbar(Admin admin)
	{
		form.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				
				AdminForm form = new AdminForm(admin);
				form.setCanvas(canvas);
				form.setTitle("FORM");
				
				canvas.appendChild(form);
			}
		});
		
		family.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				
				FamilyView view = new FamilyView(admin);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		golongan.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				
				WorkStepView view = new WorkStepView(admin);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		jabatan.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				CareerPathView view = new CareerPathView(admin);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		sk.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				DecreeView view = new DecreeView(admin);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
		
		education.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				canvas.getChildren().clear();
				EducationView view = new EducationView(admin);
				view.setCanvas(canvas);
				
				canvas.appendChild(view);
			}
		});
	}
}
