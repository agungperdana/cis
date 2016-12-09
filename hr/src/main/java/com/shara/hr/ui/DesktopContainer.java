/**
 * 
 */
package com.shara.hr.ui;


import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.North;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;

import com.google.common.base.Strings;
import com.shara.hr.dm.TeacherType;
import com.shara.hr.ui.grid.AdminView;
import com.shara.hr.ui.grid.TeacherView;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public class DesktopContainer extends Div
{
	private static final long serialVersionUID = 1L;

	private Util util = Springs.get(Util.class);
	
	private Borderlayout borderlayout = new Borderlayout();

	private North north = new North();

	private West west = new West();

	private Center center = new Center();

	private String background = "background:#3e879f url(../images/bg.gif) repeat;margin:0;padding:0;color:white;";

	public DesktopContainer()
	{
		setWidth("101%");
		setVflex("1");
		setStyle("background:#3e879f url(../images/bg.gif) repeat;margin:0;padding:0;color:white;overflow:hidden;");
		
		borderlayout.setHflex("1");
		borderlayout.setVflex("1");
		borderlayout.setStyle(background);

		appendChild(borderlayout);

		initNorth();
		initWest();
		initContent();
		
		util.setContent(center.getFirstChild());
	}

	private void initNorth()
	{
		Hbox hbox = new Hbox();
		hbox.setPack("end");
		hbox.setAlign("end");
		hbox.setHeight("40px");
		hbox.setHflex("1");
		
		A icon = new A();
		icon.setIconSclass("z-icon-user");
		icon.setStyle("color:orange;font-size:14px;font-weight:bold;");
		
		Label user = new Label(Strings.isNullOrEmpty(util.getUsername())?"Umum":util.getUsername());
		user.setStyle("color:orange;font-size:14px;font-weight:bold;padding-right:5px;");
		
		A logout = new A("Keluar");
		logout.setIconSclass("z-icon-cog z-icon-spin");
		logout.setStyle("color:red;font-size:14px;font-weight:bold;padding-right:20px");
		logout.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.setLogedIn(false);
				util.setUsername(null);
				
				Executions.sendRedirect("/");
			}
		});
		
		hbox.appendChild(icon);
		hbox.appendChild(user);
		
		if(!Strings.isNullOrEmpty(util.getUsername()))
			hbox.appendChild(logout);
		
		Vbox vbox = new Vbox();
		vbox.setHflex("1");
		vbox.setVflex("1");
		vbox.setSpacing("0px");
		vbox.setStyle("background:url(../images/headers.jpg) repeat;background-size: 100% 250px;margin:0;padding:0;color:white;");
		vbox.setPack("start");
		vbox.setAlign("end");
		vbox.appendChild(hbox);
		
		north.setHflex("1");
		north.setSplittable(false);
		north.setCollapsible(false);;
		north.setBorder("none");
		north.setHeight("250px");
		north.setStyle("background:url(../images/headers.jpg) repeat;background-size: 100% 250px;margin:0;padding:0;color:white;");
		north.appendChild(vbox);

		borderlayout.appendChild(north);
	}

	private void initWest()
	{

		Vbox vbox = new Vbox();
		vbox.setHflex("1");
		vbox.setVflex("1");
		vbox.setSpacing("10px");
		vbox.setStyle("padding-left:15px;");
		vbox.appendChild(new A());

		Label title = new Label("DOSEN & STAFF PEGAWAI");
		title.setStyle("font-weight:bolder;font-size:16px;color:orange;");
		
		
		A all = new A(" DOSEN (KESELURUHAN)");
		all.setStyle("color:white;padding-left:5px;font-size:11px;");
		all.setIconSclass("z-icon-caret-square-o-right");
		all.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.display(new TeacherView(TeacherType.ALL));
			}
		});
		
		A ttn = new A(" DOSEN HUKUM TATA NEGARA");
		ttn.setStyle("color:white;padding-left:5px;font-size:11px;");
		ttn.setIconSclass("z-icon-caret-square-o-right");
		ttn.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.display(new TeacherView(TeacherType.DOSEN_HUKUM_TATA_NEGARA));
			}
		});
		
		A ekon = new A(" DOSEN HUKUM EKONOMI");
		ekon.setStyle("color:white;padding-left:5px;font-size:11px;");
		ekon.setIconSclass("z-icon-caret-square-o-right");
		ekon.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.display(new TeacherView(TeacherType.DOSEN_HUKUM_EKONOMI));
			}
		});
		
		A pidana = new A(" DOSEN HUKUM PIDANA");
		pidana.setStyle("color:white;padding-left:5px;font-size:11px;");
		pidana.setIconSclass("z-icon-caret-square-o-right");
		pidana.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.display(new TeacherView(TeacherType.DOSEN_HUKUM_PIDANA));
			}
		});
		
		A perdata = new A(" DOSEN HUKUM PERDATA");
		perdata.setStyle("color:white;padding-left:5px;font-size:11px;");
		perdata.setIconSclass("z-icon-caret-square-o-right");
		perdata.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.display(new TeacherView(TeacherType.DOSEN_HUKUM_PERDATA));
			}
		});
		
		A inter = new A(" DOSEN HUKUM INTERNATIONAL");
		inter.setStyle("color:white;padding-left:5px;font-size:11px;");
		inter.setIconSclass("z-icon-caret-square-o-right");
		inter.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.display(new TeacherView(TeacherType.DOSEN_HUKUM_INTERNASIONAL));
			}
		});
		
		Label admin = new Label("ADMINISTRASI");
		admin.setStyle("font-weight:bolder;font-size:16px;color:orange;");
		
		A iadmin = new A(" TENAGA ADMINISTRASI");
		iadmin.setStyle("color:white;padding-left:5px;font-size:11px;");
		iadmin.setIconSclass("z-icon-caret-square-o-right");
		iadmin.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				util.display(new AdminView());
			}
		});
		
		A login = new A(" LOGIN");
		login.setStyle("color:white;padding-left:5px;font-size:11px;");
		login.setIconSclass("z-icon-caret-square-o-right");
		login.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				LoginWindow log = new LoginWindow();
				log.setPage(getPage());
				log.doModal();
			}
		});
		
		Label curva = new Label("CURVA");
		curva.setStyle("font-weight:bolder;font-size:16px;color:orange;");
		
		A pendidik = new A(" PENDIDIKAN");
		pendidik.setStyle("color:white;padding-left:5px;font-size:11px;");
		pendidik.setIconSclass("z-icon-caret-square-o-right");
		pendidik.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Iframe frame = new Iframe("/pendidikan");
				frame.setHflex("1");
				frame.setVflex("1");
				
				util.display(frame);
			}
		});
		
		A gender = new A(" JENIS KELAMIN");
		gender.setStyle("color:white;padding-left:5px;font-size:11px;");
		gender.setIconSclass("z-icon-caret-square-o-right");
		gender.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				Iframe frame = new Iframe("/gender");
				frame.setHflex("1");
				frame.setVflex("1");
				
				util.display(frame);
			}
		});
		
		vbox.appendChild(title);
		vbox.appendChild(all);
		vbox.appendChild(ttn);
		vbox.appendChild(ekon);
		vbox.appendChild(pidana);
		vbox.appendChild(perdata);
		vbox.appendChild(inter);
		vbox.appendChild(admin);
		vbox.appendChild(iadmin);
		vbox.appendChild(login);
		vbox.appendChild(curva);
		vbox.appendChild(pendidik);
		vbox.appendChild(gender);

		west.setSplittable(false);
		west.setCollapsible(false);;
		west.setBorder("none");
		west.setWidth("20%");
		west.setStyle(background);
		west.appendChild(vbox);

		borderlayout.appendChild(west);
	}
	
	private void initContent()
	{
		Vlayout vlayout = new Vlayout();
		vlayout.setStyle("background:#3e879f url(../images/bg.gif) repeat;margin:0;padding:10px;color:white;");
		vlayout.setStyle("padding:10px");
		vlayout.setHflex("1");
		vlayout.setVflex("1");
		
		center.setHflex("1");
		center.setVflex("1");
		center.setBorder("none");
		center.setStyle(background);
		center.appendChild(vlayout);
		
		borderlayout.appendChild(center);
		
		util.setContent(vlayout);
	}
}
