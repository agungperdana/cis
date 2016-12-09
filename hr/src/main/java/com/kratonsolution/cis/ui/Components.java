/**
 * 
 */
package com.kratonsolution.cis.ui;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import com.google.common.base.Strings;

/**
 * 
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 */
public class Components implements Serializable
{	
	private static final long serialVersionUID = 1L;

	public static final String string(Listbox listbox)
	{
		if(listbox != null && !listbox.getChildren().isEmpty() && listbox.getSelectedCount() > 0)
			return listbox.getSelectedItem().getValue().toString();
		
		return "";
	}
	
	public static final BigDecimal decimal(Component component)
	{
		if(component != null && (component instanceof Listbox) && !component.getChildren().isEmpty())
		{
			Listbox listbox = (Listbox)component;
			if(listbox.getSelectedItem() != null)
				return new BigDecimal(listbox.getSelectedItem().getValue().toString());
		}
		else if(component != null && (component instanceof Doublebox))
		{
			Doublebox doublebox = (Doublebox)component;
			return BigDecimal.valueOf(doublebox.doubleValue());
		}
		
		return BigDecimal.ZERO;
	}
	
	public static final void setDefault(Listbox listbox)
	{
		if(!listbox.getChildren().isEmpty())
			listbox.setSelectedIndex(0);
	}
	
	public static final Listbox newSelect()
	{
		Listbox listbox = new Listbox();
		listbox.setMold("select");
		listbox.setWidth("250px");
		
		return listbox;
	}
	
	public static final Listbox newSelect(String width)
	{
		Listbox listbox = new Listbox();
		listbox.setMold("select");
		
		if(!Strings.isNullOrEmpty(width))
			listbox.setWidth(width);
		else
			listbox.setWidth("250px");
		
		return listbox;
	}
	
	public static final Listbox newSelect(String label,String value)
	{
		Listbox listbox = new Listbox();
		listbox.setMold("select");
		listbox.setStyle("text-align:center;");
		listbox.appendChild(new Listitem(label,value));
		listbox.setSelectedIndex(0);
		
		return listbox;
	}
	
	public static final Listbox fullSpanSelect(String label,String value)
	{
		Listbox listbox = new Listbox();
		listbox.setMold("select");
		listbox.setStyle("text-align:center;");
		listbox.setWidth("100%");
	
		if(!Strings.isNullOrEmpty(label) && !Strings.isNullOrEmpty(value))
		{
			listbox.appendItem(label, value);
			listbox.setSelectedIndex(0);
		}
		
		return listbox;
	}
	
	public static final Listbox fullSpanSelect()
	{
		Listbox listbox = new Listbox();
		listbox.setMold("select");
		listbox.setWidth("100%");
		
		return listbox;
	}
	
	public static final Textbox readOnlyTextBox()
	{
		Textbox textbox = new Textbox();
		textbox.setWidth("100%");
		textbox.setReadonly(true);
		return textbox;
	}
	
	public static final Textbox readOnlyTextBox(String text)
	{
		Textbox textbox = new Textbox(text);
		textbox.setWidth("100%");
		textbox.setReadonly(true);
		return textbox;
	}
	
	public static final Textbox readOnlyTextBox(String text,boolean fullspan)
	{
		Textbox textbox = new Textbox(text);
		textbox.setReadonly(true);
		if(fullspan)
			textbox.setWidth("100%");
		else
			textbox.setWidth("300px");
		
		return textbox;
	}
	
	public static final Doublebox readOnlyDoubleBox()
	{
		Doublebox box = new Doublebox(0d);
		box.setWidth("100%");
		box.setReadonly(true);
		box.setStyle("text-align:right;");
		return box;
	}
	
	public static final Textbox readOnlyMoneyBox(BigDecimal decimal)
	{
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(true);
		
		Textbox box = new Textbox();
		box.setWidth("100%");
		box.setReadonly(true);
		box.setText(format.format(decimal));
		box.setStyle("text-align:right;");
		return box;
	}
	
	public static final Textbox readOnlyMoneyBox(BigDecimal decimal,boolean span)
	{
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(true);
		
		Textbox box = new Textbox();
		if(span)
			box.setWidth("100%");
		else
			box.setWidth("150px");
		
		box.setReadonly(true);
		box.setText(format.format(decimal));
		box.setStyle("text-align:right;");
		return box;
	}
	
	public static final Label numberLabel(BigDecimal amout)
	{
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(true);
		
		Label label = new Label();
		label.setWidth("100%");
		label.setValue(format.format(amout));
		label.setStyle("text-align:right;");
		
		return label;
	}
	
	public static final Doublebox doubleOne()
	{
		Doublebox box = new Doublebox(1);
		box.setWidth("100%");
		box.setStyle("text-align:right;");
		return box;
	}
	
	public static final Doublebox readOnlyDoubleOne()
	{
		Doublebox box = new Doublebox(1);
		box.setWidth("100%");
		box.setReadonly(true);
		box.setStyle("text-align:right;");
		return box;
	}
	
	public static final Doublebox readOnlyDoubleBox(double value)
	{
		Doublebox box = new Doublebox(0d);
		box.setWidth("100%");
		box.setReadonly(true);
		box.setStyle("text-align:right;");
		box.setValue(value);
		
		return box;
	}
	
	public static final Doublebox doubleBox()
	{
		Doublebox box = new Doublebox(0d);
		box.setWidth("100%");
		box.setStyle("text-align:right;");
		return box;
	}
	
	public static final Doublebox doubleBox(double value)
	{
		Doublebox box = new Doublebox(0d);
		box.setWidth("100%");
		box.setStyle("text-align:right;display:block;");
		box.setValue(value);
		
		return box;
	}
	
	public static final Doublebox doubleBox(BigDecimal value)
	{
		Doublebox box = new Doublebox(0d);
		box.setWidth("100%");
		box.setStyle("text-align:right;display:block;");

		if(value != null)
			box.setValue(value.doubleValue());
		
		return box;
	}
	
	public static final Decimalbox decimalbox(BigDecimal value)
	{
		Decimalbox box = new Decimalbox();
		box.setWidth("200px");
		box.setStyle("text-align:right;display:block;");

		if(value != null)
			box.setValue(value);
		
		return box;
	}
	
	public static final Decimalbox fullspanDecimalbox(BigDecimal value)
	{
		Decimalbox box = new Decimalbox();
		box.setWidth("100%");
		box.setStyle("text-align:right;display:block;");

		if(value != null)
			box.setValue(value);
		
		return box;
	}
	
	
	public static final Doublebox stdDoubleBox(double value)
	{
		Doublebox box = new Doublebox(0d);
		box.setWidth("200px");
		box.setStyle("text-align:right;display:block;");
		box.setValue(value);
		
		return box;
	}
	
	public static final boolean isEmpty(Listbox listbox)
	{
		if(listbox == null)
			return true;
		
		if(listbox.getChildren().isEmpty())
			return true;
		
		if(listbox.getSelectedCount() == 0)
			return true;
		
		return false;
	}
	
	public static final Checkbox readOnlyCheckbox()
	{
		Checkbox checkbox = new Checkbox();
		checkbox.setDisabled(true);
		
		return checkbox;
	}
	
	public static final Checkbox checkbox(boolean isChecked)
	{
		Checkbox checkbox = new Checkbox();
		checkbox.setChecked(isChecked);
		
		return checkbox;
	}
	
	public static final Checkbox status(boolean active)
	{
		Checkbox checkbox = new Checkbox("Active");
		checkbox.setChecked(active);
		
		return checkbox;
	}
	
	public static final Textbox textarea(String text,boolean readonly,boolean mandatory)
	{
		Textbox textbox = new Textbox();
		textbox.setRows(6);
		textbox.setCols(60);
		textbox.setReadonly(readonly);
		
		if(!Strings.isNullOrEmpty(text))
			textbox.setText(text);
		
		if(mandatory)
			textbox.setConstraint("no empty");
		
		return textbox;
	}
	
	public static final Checkbox checkbox(boolean readonly,boolean isChecked)
	{
		Checkbox checkbox = new Checkbox();
		checkbox.setChecked(isChecked);
		checkbox.setDisabled(readonly);
		
		return checkbox;
	}
	
	public static final Textbox mandatoryTextBox()
	{
		Textbox textbox = new Textbox();
		textbox.setWidth("100%");
		textbox.setConstraint("no empty");
		
		return textbox;
	}
	
	public static final Textbox mandatoryTextBox(boolean fullspan)
	{
		Textbox textbox = new Textbox();
		textbox.setConstraint("no empty");
		if(fullspan)
			textbox.setWidth("100%");
		else
			textbox.setWidth("250px");
		
		return textbox;
	}
	
	public static final Textbox moneyBox()
	{
		Textbox textbox = new Textbox();
		textbox.setWidth("100%");
		textbox.setReadonly(true);
		textbox.setStyle("text-align:right");
		
		return textbox;
	}
	
	public static final Textbox mandatoryTextBox(String text)
	{
		Textbox textbox = new Textbox(text);
		textbox.setWidth("100%");
		textbox.setConstraint("no empty");
		
		return textbox;
	}
	
	public static final Textbox stdTextBox(String text,boolean readonly)
	{
		Textbox textbox = new Textbox(text);
		textbox.setWidth("250px");
		textbox.setReadonly(readonly);
		
		return textbox;
	}
	
	public static final Textbox textBox(String text)
	{
		Textbox textbox = new Textbox(text);
		textbox.setWidth("100%");
		
		return textbox;
	}
	
	public static final Datebox mandatoryDatebox()
	{
		Datebox datebox = new Datebox(new Date());
		datebox.setWidth("100%");
		datebox.setConstraint("no empty");
		datebox.setFormat("dd/MM/yyyy");
		
		return datebox;
	}
	
	public static final Datebox mandatoryDatebox(String width)
	{
		Datebox datebox = new Datebox(new Date());
		datebox.setWidth(width);
		datebox.setConstraint("no empty");
		datebox.setFormat("dd/MM/yyyy");
		
		return datebox;
	}
	
	public static final Datebox mandatoryYearbox()
	{
		Datebox datebox = new Datebox(new Date());
		datebox.setWidth("100%");
		datebox.setConstraint("no empty");
		datebox.setFormat("yyyy");
		
		return datebox;
	}
	
	public static final Datebox mandatoryDatebox(Date date)
	{
		Datebox datebox = new Datebox();
		datebox.setWidth("100%");
		datebox.setConstraint("no empty");
		datebox.setFormat("dd/MM/yyyy");
		
		if(date != null)
			datebox.setValue(date);
			
		return datebox;
	}
	
	public static final Timebox fullspanTimebox(Time tm)
	{
		Timebox time = new Timebox(new Date());
		time.setWidth("100%");
		time.setConstraint("no empty");
		time.setFormat("HH:mm");
		
		if(tm != null)
			time.setValue(tm);
		
		return time;
	}
	
	public static final Timebox currentTimebox()
	{
		Timebox time = new Timebox(new Date());
		time.setWidth("150px");
		time.setConstraint("no empty");
		time.setFormat("HH:mm");
		
		return time;
	}
	
	public static final Timebox timebox()
	{
		Timebox time = new Timebox();
		time.setWidth("150px");
		time.setFormat("HH:mm");
		
		return time;
	}
	
	public static final Datebox currentDatebox()
	{
		Datebox datebox = new Datebox(new Date());
		datebox.setWidth("150px");
		datebox.setConstraint("no empty");
		datebox.setFormat("dd/MM/yyyy");
		
		return datebox;
	}
	
	public static final Datebox currentDatebox(boolean readonly)
	{
		Datebox datebox = new Datebox(new Date());
		datebox.setWidth("150px");
		datebox.setConstraint("no empty");
		datebox.setFormat("dd/MM/yyyy");
		datebox.setReadonly(readonly);
		
		return datebox;
	}
	
	public static final Datebox datebox()
	{
		Datebox datebox = new Datebox();
		datebox.setWidth("150px");
		datebox.setFormat("dd/MM/yyyy");
		
		return datebox;
	}
	
	public static final Datebox fullSpanDatebox(Date date)
	{
		Datebox datebox = new Datebox();
		datebox.setWidth("100%");
		datebox.setFormat("dd/MM/yyyy");
		if(date != null)
			datebox.setValue(date);
		
		return datebox;
	}
	
	public static final Datebox fullSpanReadOnlyDatebox(Date date)
	{
		Datebox datebox = new Datebox(date);
		datebox.setWidth("100%");
		datebox.setFormat("dd/MM/yyyy");
		datebox.setReadonly(true);
		datebox.setDisabled(true);

		if(date != null)
			datebox.setValue(date);
		
		return datebox;
	}
	
	public static Combobox autoComplete()
	{
		Combobox combobox = new Combobox();
		combobox.setAutodrop(true);
		combobox.setButtonVisible(false);
		combobox.setWidth("250px");
		
		return combobox;
	}

	public static final Row row(int numberOfCell,String lastCellContent)
	{
		Row row = new Row();
		for(int idx=1;idx<numberOfCell;idx++)
			row.appendChild(new Label(""));
		
		row.appendChild(new Label(lastCellContent));
		
		return row;
	}

}
