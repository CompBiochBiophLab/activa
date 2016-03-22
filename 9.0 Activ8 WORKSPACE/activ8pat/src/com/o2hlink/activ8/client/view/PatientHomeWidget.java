package com.o2hlink.activ8.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.o3d.client.CursorType;
import com.google.gwt.o3d.client.Transform;
import com.google.gwt.o3d.client.event.MouseEvent;
import com.google.gwt.o3d.client.picking.PickInfo;
import com.google.gwt.user.client.Timer;
import com.o2hlink.activ8.client.display.PatientHomeDisplay;
import com.o2hlink.activ8.client.event.LoadEvent;
import com.o2hlink.activ8.client.handler.LoadHandler;

/**
 * Widget of patient home
 * @author Miguel Angel Hernandez
 **/
public class PatientHomeWidget extends HomeWidget implements PatientHomeDisplay {
//FIELDS
    /**
     * 
     **/
    private Widget3D phone = new Widget3D();
    /**
     * 
     **/
    private Widget3D globe = new Widget3D();
    /**
     * 
     **/
    private Widget3D calendar = new Widget3D();
    /**
     * 
     **/
    private Widget3D feeds = new Widget3D();
    /**
     * 
     **/
    private Widget3D email = new Widget3D();
    /**
     * 
     **/
    private Widget3D notes = new Widget3D();
    /**
	 * 
	 **/
	private Widget3D mobilePhone = new Widget3D();
	/**
	 * 
	 **/
	private Widget3D history = new Widget3D();
	/**
	 * 
	 **/
	private Widget3D assignedMeasurements = new Widget3D();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public PatientHomeWidget(){
		super();
		
		setTheme("models/paciente.o3dtgz");
		
		addLoadHandler(new LoadHandler(){
			public void onLoad(LoadEvent evt) {
				final Transform revistas = (Transform) getClient().getObjects("revistas", "o3d.Transform").get(0);
				final Transform telefono = (Transform) getClient().getObjects("telefono", "o3d.Transform").get(0);
				final Transform bandeja = (Transform) getClient().getObjects("portatil2", "o3d.Transform").get(0);
				final Transform sobre = (Transform) getClient().getObjects("sobre", "o3d.Transform").get(0);
				final Transform calendar = (Transform) getClient().getObjects("calendario", "o3d.Transform").get(0);
				final Transform bicicleta = (Transform) getClient().getObjects("bicicleta", "o3d.Transform").get(0);
				final Transform pizarra = (Transform) getClient().getObjects("pizarra_electronica_s", "o3d.Transform").get(0);
				
				focus(revistas);
				focus(telefono);
				focus(bandeja);
				focus(sobre);
				focus(calendar);
				focus(bicicleta);
				focus(pizarra);
				getClient().render();
				
				Timer timer = new Timer(){
					public void run() {
						blur(revistas);
						blur(telefono);
						blur(bandeja);
						blur(sobre);
						blur(calendar);
						blur(bicicleta);
						blur(pizarra);
						getClient().render();
					}
				};
				timer.schedule(7000);
			}
		});
	}
//METHODS
	/**
	 * 
	 **/
	/*
	@Override
	public String getModelURI(){
		return "models/paciente.o3dtgz";
	}
	*/
	/**
	 * 
	 **/
	@Override
	public void mouseClick(PickInfo pickInfo, ClickEvent event){
		String name = pickInfo.getShapeInfo().getShape().getName();
		System.out.println(name);
		if (name.equals("revistas"))
			getFeeds().fireEvent(event);
		else if (name.equals("telefono"))
			getPhone().fireEvent(event);
		else if (name.equals("portatil2"))
			getEmail().fireEvent(event);
		else if (name.equals("calendario"))
			getCalendar().fireEvent(event);
		else if (name.equals("GLOBO_TERRAQUEO"))
			getGlobe().fireEvent(event);
		else if (name.equals("pizarra01"))
			getNotes().fireEvent(event);
		else if (name.equals("bicicleta"))
			getAssignedMeasurements().fireEvent(event);
		else if (name.equals("iphone"))
			getMobilePhone().fireEvent(event);
		else if (name.equals("sobre"))
			getHistory().fireEvent(event);
		else if (name.equals("pizarra_electronica_s"))
			getNotes().fireEvent(event);
	}
	/**
	 * 
	 **/
	public void mouseOver(PickInfo pickInfo, MouseEvent event){
		String name = pickInfo.getShapeInfo().getShape().getName();
		if (name.equals("revistas") ||
			name.equals("telefono") ||
			name.equals("portatil2")  ||
			name.equals("calendario")||
			name.equals("GLOBO_TERRAQUEO")||
			name.equals("pizarra01")||
			name.equals("bicicleta")||
			name.equals("iphone")||
			name.equals("sobre")||
			name.equals("pizarra_electronica_s")){
			getClient().setCursor(CursorType.POINTER);
			focus(pickInfo);
		}
		else getClient().setCursor(CursorType.DEFAULT);
	}
	/**
	 * 
	 **/
	public HasClickHandlers getAssignedMeasurements() {
		return assignedMeasurements;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getHistory() {
		return history;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getCalendar() {
		return calendar;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getEmail() {
		return email;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getFeeds() {
		return feeds;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getGlobe() {
		return globe;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getNotes() {
		return notes;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getPhone() {
		return phone;
	}
}
