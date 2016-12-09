package com.kratonsolution.cis.ui.form;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Caption;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.kratonsolution.cis.dm.Teacher;
import com.kratonsolution.cis.ui.ImageGaleri;

public class TeacherZoom extends Window
{
	private static final long serialVersionUID = 1L;

	private Vlayout layout = new Vlayout();
	
	private Tabbox box = new Tabbox();
	
	public TeacherZoom(Teacher teacher)
	{
		Caption caption = new Caption(teacher.getNama().toUpperCase());
		caption.setIconSclass("z-icon-user z-icon-2x");
		
		setClosable(true);
		setSizable(false);
		setMaximizable(false);
		setMinimizable(false);
		setWidth("650px");
		setHeight("600px");
		setPosition("center");
		
		layout.setHflex("1");
		layout.setVflex("1");
		layout.setStyle("overflow:auto;");
		layout.appendChild(box);
		
		box.setHflex("1");
		box.setVflex("1");
		box.appendChild(new Tabs());
		box.appendChild(new Tabpanels());
		box.getTabs().appendChild(new Tab("RINCIAN"));
		box.getTabs().appendChild(new Tab("GALERI FOTO GOLONGAN"));
		box.getTabs().appendChild(new Tab("GALERI FOTO JABATAN"));
		box.getTabs().appendChild(new Tab("GALERI FOTO PENDIDIKAN"));
		box.getTabpanels().appendChild(new Tabpanel());
		box.getTabpanels().appendChild(new Tabpanel());
		box.getTabpanels().appendChild(new Tabpanel());
		box.getTabpanels().appendChild(new Tabpanel());
		
		appendChild(caption);
		appendChild(layout);
	
		initDetail(teacher);
		initGolongan(teacher);
		initJabatan(teacher);
		initEducation(teacher);
	}
	
	private void initDetail(Teacher teacher)
	{
		Vlayout out = new Vlayout();
		out.setHflex("1");
		out.setVflex("1");
		out.setStyle("overflow:auto;");
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		Grid grid = new Grid();
		grid.setWidth("100%");
		grid.appendChild(new Columns());
		grid.appendChild(new Rows());
		grid.getColumns().appendChild(new Column(null,null,"200px"));
		grid.getColumns().appendChild(new Column(null,null,"125px"));
		grid.setSpan("1");

		Row row0 = new Row();
		row0.appendChild(new Label("TIPE"));
		row0.appendChild(new Label(teacher.getType().getDisplay()));

		Row row1 = new Row();
		row1.appendChild(new Label("NAMA"));
		row1.appendChild(new Label(teacher.getNama()));

		Row row2 = new Row();
		row2.appendChild(new Label("NIDN"));
		row2.appendChild(new Label(teacher.getNidn()));

		Row row3 = new Row();
		row3.appendChild(new Label("NIP"));
		row3.appendChild(new Label(teacher.getNip()));
		
		Row row35 = new Row();
		row35.appendChild(new Label("MASA KERJA"));
		row35.appendChild(new Label((teacher.getYearExp()>0?teacher.getYearExp()+" Thn ":"")+(teacher.getMonthExp()>0?teacher.getMonthExp()+" Bln":"")));

		Row row4 = new Row();
		row4.appendChild(new Label("AGAMA"));
		row4.appendChild(new Label(teacher.getAgama().dispalay()));

		Row row5 = new Row();
		row5.appendChild(new Label("JENIS KELAMIN"));
		row5.appendChild(new Label(teacher.getGender().display()));

		Row row6 = new Row();
		row6.appendChild(new Label("BAGIAN"));
		row6.appendChild(new Label(teacher.getBagian()));

		Row row7 = new Row();
		row7.appendChild(new Label("TEMPAT LAHIR"));
		row7.appendChild(new Label(teacher.getTempatLahir()));

		Row row8 = new Row();
		row8.appendChild(new Label("TANGGAL LAHIR"));
		row8.appendChild(new Label(format.format(teacher.getTglLahir())));

		Row row9 = new Row();
		row9.appendChild(new Label("ALAMAT"));
		row9.appendChild(new Label(teacher.getAlamat()));

		Row row10 = new Row();
		row10.appendChild(new Label("TELP/HP/FAX"));
		row10.appendChild(new Label(teacher.getKontak()));
		
		Cell cgol = new Cell();
		cgol.setColspan(2);
		cgol.appendChild(new Label("INFORMASI GOLONGAN"));
		cgol.setStyle("font-weight:bold");
		
		Row row11 = new Row();
		row11.appendChild(cgol);
		
		Row row12 = new Row();
		row12.appendChild(new Label("GOLONGAN"));
		row12.appendChild(new Label(teacher.getStep()!=null?teacher.getStep().getNote():"-"));
		
		Row row13 = new Row();
		row13.appendChild(new Label("TMT"));
		row13.appendChild(new Label(teacher.getStep()!=null?format.format(teacher.getStep().getStart()):"-"));
		
		Cell cjab = new Cell();
		cjab.setColspan(2);
		cjab.appendChild(new Label("INFORMASI JABATAN"));
		cjab.setStyle("font-weight:bold");
		
		Row row14 = new Row();
		row14.appendChild(cjab);
		
		Row row15 = new Row();
		row15.appendChild(new Label("JABATAN"));
		row15.appendChild(new Label(teacher.getPath()!=null?teacher.getPath().getNote():"-"));
		
		Row row16 = new Row();
		row16.appendChild(new Label("TMT"));
		row16.appendChild(new Label(teacher.getPath()!=null?format.format(teacher.getPath().getStart()):"-"));
		
		Cell cpen = new Cell();
		cpen.setColspan(2);
		cpen.appendChild(new Label("INFORMASI PENDIDIKAN"));
		cpen.setStyle("font-weight:bold");
		
		Row row17 = new Row();
		row17.appendChild(cpen);
		
		Row row18 = new Row();
		row18.appendChild(new Label("NAMA PERGURUAN TINGGI"));
		row18.appendChild(new Label(teacher.getEducation()!=null?teacher.getEducation().getName():"-"));
		
		Row row19 = new Row();
		row19.appendChild(new Label("TAHUN MULAI"));
		row19.appendChild(new Label(teacher.getEducation()!=null?teacher.getEducation().getYearStart()+"":""));
		
		Row row20 = new Row();
		row20.appendChild(new Label("TAHUN SELESAI"));
		row20.appendChild(new Label(teacher.getEducation()!=null?teacher.getEducation().getYearEnd()+"":"-"));
		
		Row row21 = new Row();
		row21.appendChild(new Label("TINGKAT IJAZAH"));
		row21.appendChild(new Label(teacher.getEducation()!=null?teacher.getEducation().getLevel().name()+"":"-"));

		grid.getRows().appendChild(row0);
		grid.getRows().appendChild(row1);
		grid.getRows().appendChild(row2);
		grid.getRows().appendChild(row3);
		grid.getRows().appendChild(row35);
		grid.getRows().appendChild(row4);
		grid.getRows().appendChild(row5);
		grid.getRows().appendChild(row6);
		grid.getRows().appendChild(row7);
		grid.getRows().appendChild(row8);
		grid.getRows().appendChild(row9);
		grid.getRows().appendChild(row10);
		grid.getRows().appendChild(row11);
		grid.getRows().appendChild(row12);
		grid.getRows().appendChild(row13);
		grid.getRows().appendChild(row14);
		grid.getRows().appendChild(row15);
		grid.getRows().appendChild(row16);
		grid.getRows().appendChild(row17);
		grid.getRows().appendChild(row18);
		grid.getRows().appendChild(row19);
		grid.getRows().appendChild(row20);
		grid.getRows().appendChild(row21);
		
		out.appendChild(grid);
		
		box.getTabpanels().getChildren().get(0).appendChild(out);
	}
	
	private void initGolongan(Teacher teacher)
	{
		if(teacher.getStep() != null)
		{
			ImageGaleri galeri = new ImageGaleri(teacher.getStep());
			galeri.setReadonly(true);
			box.getTabpanels().getChildren().get(1).appendChild(galeri);
		}
	}
	
	private void initJabatan(Teacher teacher)
	{
		if(teacher.getPath() != null)
		{
			ImageGaleri galeri = new ImageGaleri(teacher.getPath());
			galeri.setReadonly(true);
			
			box.getTabpanels().getChildren().get(2).appendChild(galeri);
		}

	}
	
	private void initEducation(Teacher teacher)
	{
		if(teacher.getEducation() != null)
		{
			ImageGaleri galeri = new ImageGaleri(teacher.getEducation());
			galeri.setReadonly(true);
			
			box.getTabpanels().getChildren().get(3).appendChild(galeri);
		}
	}
}
