package com.shara.hr.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.A;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vbox;

import com.google.common.base.Strings;
import com.shara.hr.dm.DocumentImage;
import com.shara.hr.dm.Documents;
import com.shara.hr.svc.DocumentsService;
import com.shara.hr.ui.dashboard.DashboardContent;

public class ImageGaleri extends DashboardContent
{
	private static final long serialVersionUID = 1L;

	private DocumentsService service = Springs.get(DocumentsService.class);

	private Hbox layout = new Hbox();

	private Listbox listbox = new Listbox();

	private Image image = new Image();

	private Vbox content = new Vbox();

	private Hbox control = new Hbox();

	private int current = 0;

	private Hbox upbox = new Hbox();

	private Fileupload fileupload = new Fileupload("Pilih gambar");
	
	private List<DocumentImage> images = new ArrayList<>();
	
	public ImageGaleri(Documents document)
	{
		super();
		setTitle("GALERI FOTO");
		disableClose();
		
		setHflex("1");
		setVflex("1");

		Label label = new Label("UPLOAD FOTO");
		
		fileupload.addEventListener(Events.ON_UPLOAD, new EventListener<UploadEvent>()
		{
			@Override
			public void onEvent(UploadEvent upload) throws Exception
			{
				if(upload.getMedia() != null && upload.getMedia().getByteData() != null)
				{
					Documents on = service.find(document.getId());

					DocumentImage image = new DocumentImage();
					image.setDocument(on);
					image.setScan(upload.getMedia().getByteData());
					image.setName(upload.getMedia().getName());

					on.getImages().add(image);

					service.edit(on);

					images.add(image);

					canvas.getChildren().clear();

					ImageGaleri galeri = new ImageGaleri(on);
					galeri.setCanvas(canvas);

					canvas.appendChild(galeri);
				}
			}
		});

		upbox.setHflex("1");
		upbox.setHeight("40px");
		upbox.appendChild(label);
		upbox.appendChild(fileupload);

		images.addAll(document.getImages());

		setImage();

		A pref = new A();
		pref.setIconSclass("z-icon-arrow-circle-left z-icon-2x");
		pref.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				if(current > 0)
					current--;

				setImage();
			}
		});

		A next = new A();
		next.setIconSclass("z-icon-arrow-circle-right z-icon-2x");
		next.addEventListener(Events.ON_CLICK, new EventListener<Event>()
		{
			@Override
			public void onEvent(Event arg0) throws Exception
			{
				if(current < images.size()-1)
					current++;

				setImage();
			}
		});

		image.setWidth("100%");
		image.setVflex("1");
		image.setStyle("border:solid silver 1px;");

		layout.setHflex("1");
		layout.setVflex("1");

		control.setHflex("1");
		control.setHeight("30px");
		control.setPack("center");
		control.setAlign("center");
		control.appendChild(pref);
		control.appendChild(next);

		content.setHflex("1");
		content.setVflex("1");
		content.appendChild(image);
		content.appendChild(control);

		Listheader one = new Listheader("Daftar foto");
		one.setWidth("80%");
		
		Listheader two = new Listheader();
		two.setWidth("20%");
		
		Listhead head = new Listhead();
		head.appendChild(one);
		head.appendChild(two);

		listbox.setWidth("150px");
		listbox.setVflex("1");
		listbox.appendChild(head);

		layout.appendChild(listbox);
		layout.appendChild(content);

		appendChild(upbox);
		appendChild(layout);

		for(int idx=0;idx<images.size();idx++)
		{
			final DocumentImage target = images.get(idx);
			
			Listcell rem = new Listcell();
			rem.setIconSclass("z-icon-remove");
			rem.addEventListener(Events.ON_CLICK, new EventListener<Event>()
			{
				@Override
				public void onEvent(Event arg0) throws Exception
				{
					Documents on = service.find(document.getId());
					if(on != null && !fileupload.isDisabled())
					{
						Iterator<DocumentImage> iterator = on.getImages().iterator();
						while (iterator.hasNext())
						{
							DocumentImage img = (DocumentImage) iterator.next();
							if(img.getId().equals(target.getId()))
								iterator.remove();
						}

						service.edit(on);

						canvas.getChildren().clear();

						ImageGaleri galeri = new ImageGaleri(on);
						galeri.setCanvas(canvas);

						canvas.appendChild(galeri);
					}
				}
			});
			
			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell(Strings.isNullOrEmpty(target.getName())?"Gambar "+(idx+1):target.getName()));
			listitem.appendChild(rem);

			listbox.appendChild(listitem);
		}
	}

	private void setImage()
	{
		try
		{
			image.setContent(new AImage("Gambar "+current+1, images.get(current).getScan()));
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public void setReadonly(boolean readonly)
	{
		fileupload.setDisabled(true);
	}
}
