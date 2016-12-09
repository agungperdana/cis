package com.shara.hr.ui.form;

import java.util.UUID;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
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
import com.shara.hr.dm.Admin;
import com.shara.hr.dm.GenderType;
import com.shara.hr.dm.Religion;
import com.shara.hr.svc.AdminService;
import com.shara.hr.ui.Components;
import com.shara.hr.ui.DateTimes;
import com.shara.hr.ui.Springs;
import com.shara.hr.ui.dashboard.DashboardContent;

public class AdminForm extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private AdminService service = Springs.get(AdminService.class);

	private Toolbar toolbar = new Toolbar();

	private Textbox nama = Components.mandatoryTextBox(false);

	private Textbox nip = new Textbox();

	private Listbox agama = Components.newSelect();

	private Listbox gender = Components.newSelect();

	private Textbox bagian = new Textbox();

	private Textbox tempat = new Textbox();

	private Datebox tgl = Components.datebox();

	private Textbox alamat = new Textbox();

	private Textbox kontak = new Textbox();

	private Grid grid = new Grid();

	public AdminForm(Admin teacher)
	{
		super();
		setTitle("FORM");
		
		setHflex("1");
		setVflex("1");

		appendChild(toolbar);
		appendChild(grid);

		initToolbar(teacher);
		initGrid(teacher);
	}

	private void initToolbar(Admin teacher)
	{
		Toolbarbutton save = new Toolbarbutton("Simpan");
		save.setIconSclass("z-icon-save z-icon-2x");
		save.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				if(teacher.getId().equals("0"))
				{
					teacher.setId(UUID.randomUUID().toString());
					teacher.setAgama(Religion.valueOf(agama.getSelectedItem().getValue().toString()));
					teacher.setAlamat(alamat.getText());
					teacher.setBagian(bagian.getText());
					teacher.setKontak(kontak.getText());
					teacher.setGender(GenderType.valueOf(gender.getSelectedItem().getValue().toString()));
					teacher.setNama(nama.getText());
					teacher.setNip(nip.getText());
					teacher.setTempatLahir(tempat.getText());
					teacher.setTglLahir(DateTimes.sql(tgl.getValue()));

					service.add(teacher);
				}
				else
				{
					Admin on = service.find(teacher.getId());
					if(on != null)
					{
						on.setAgama(Religion.valueOf(agama.getSelectedItem().getValue().toString()));
						on.setAlamat(alamat.getText());
						on.setBagian(bagian.getText());
						on.setKontak(kontak.getText());
						on.setGender(GenderType.valueOf(gender.getSelectedItem().getValue().toString()));
						on.setNama(nama.getText());
						on.setNip(nip.getText());
						on.setTempatLahir(tempat.getText());
						on.setTglLahir(DateTimes.sql(tgl.getValue()));
					
						service.edit(on);
					}
				}
				
				Clients.showNotification("Data berhasil disimpan.");
			}
		});

		toolbar.appendChild(save);
	}

	private void initGrid(Admin teacher)
	{
		for(Religion religion:Religion.values())
		{
			Listitem listitem = agama.appendItem(religion.dispalay(), religion.name());
			if(religion.equals(teacher.getAgama()))
				agama.setSelectedItem(listitem);
		}

		for(GenderType type:GenderType.values())
		{
			Listitem listitem = gender.appendItem(type.display(), type.name());
			if(type.equals(teacher.getGender()))
				gender.setSelectedItem(listitem);
		}

		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"150px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row1 = new Row();
		row1.appendChild(new Label("NAMA"));
		row1.appendChild(nama);

		Row row3 = new Row();
		row3.appendChild(new Label("NIP"));
		row3.appendChild(nip);

		Row row4 = new Row();
		row4.appendChild(new Label("AGAMA"));
		row4.appendChild(agama);

		Row row5 = new Row();
		row5.appendChild(new Label("JENIS KELAMIN"));
		row5.appendChild(gender);

		Row row6 = new Row();
		row6.appendChild(new Label("BAGIAN"));
		row6.appendChild(bagian);

		Row row7 = new Row();
		row7.appendChild(new Label("TEMPAT LAHIR"));
		row7.appendChild(tempat);

		Row row8 = new Row();
		row8.appendChild(new Label("TANGGAL LAHIR"));
		row8.appendChild(tgl);

		Row row9 = new Row();
		row9.appendChild(new Label("ALAMAT"));
		row9.appendChild(alamat);

		Row row10 = new Row();
		row10.appendChild(new Label("TELP/HP/FAX"));
		row10.appendChild(kontak);

		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row3);
		grid.getRows().appendChild(row4);
		grid.getRows().appendChild(row5);
		grid.getRows().appendChild(row6);
		grid.getRows().appendChild(row7);
		grid.getRows().appendChild(row8);
		grid.getRows().appendChild(row9);
		grid.getRows().appendChild(row10);

		if(!Strings.isNullOrEmpty(teacher.getNama()))
			nama.setText(teacher.getNama());

		nip.setWidth("125px");
		nip.setText(teacher.getNip());

		bagian.setWidth("200px");
		bagian.setText(teacher.getBagian());

		tempat.setWidth("200px");
		tempat.setText(teacher.getTempatLahir());

		tgl.setWidth("125px");
		
		if(teacher.getTempatLahir() != null)
			tgl.setValue(teacher.getTglLahir());

		alamat.setWidth("300px");
		alamat.setText(teacher.getAlamat());

		kontak.setWidth("200px");
		kontak.setText(teacher.getKontak());
	}
}
