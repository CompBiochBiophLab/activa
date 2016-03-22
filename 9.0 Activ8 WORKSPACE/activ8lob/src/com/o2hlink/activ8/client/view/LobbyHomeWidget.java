package com.o2hlink.activ8.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.o3d.client.Transform;
import com.google.gwt.o3d.client.event.MouseEvent;
import com.google.gwt.o3d.client.picking.PickInfo;
import com.google.gwt.user.client.Timer;
import com.o2hlink.activ8.client.display.LobbyHomeDisplay;
import com.o2hlink.activ8.client.event.LoadEvent;
import com.o2hlink.activ8.client.handler.LoadHandler;

/**
 * @author Miguel Angel Hernandez
 **/
public class LobbyHomeWidget extends HomeWidget implements LobbyHomeDisplay {
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
    private Widget3D feeds = new Widget3D();
    /**
	 * 
	 **/
	private Widget3D clinicianDoor = new Widget3D();
	/**
	 * 
	 **/
	private Widget3D patientDoor = new Widget3D();
	/**
	 * 
	 **/
	private Widget3D researcherDoor = new Widget3D();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public LobbyHomeWidget(){
		super();
		setTheme("models/lobby.o3dtgz");
		
		addLoadHandler(new LoadHandler(){
			public void onLoad(LoadEvent evt) {
				final Transform portatil = (Transform) getClient().getObjects("portatil2", "o3d.Transform").get(0);
				final Transform bandeja = (Transform) getClient().getObjects("bandeja", "o3d.Transform").get(0);
				final Transform globo = (Transform) getClient().getObjects("GLOBO_TERRAQUEO_PIVOT", "o3d.Transform").get(0);
				final Transform izquierda = (Transform) getClient().getObjects("puerta_izquierda", "o3d.Transform").get(0);
				final Transform central = (Transform) getClient().getObjects("puerta_central", "o3d.Transform").get(0);
				final Transform derecha = (Transform) getClient().getObjects("puerta_derecha", "o3d.Transform").get(0);
				
				focus(portatil);
				focus(bandeja);
				focus(globo);
				focus(izquierda);
				focus(central);
				focus(derecha);
				getClient().render();
				
				Timer timer = new Timer(){
					public void run() {
						blur(portatil);
						blur(bandeja);
						blur(globo);
						blur(izquierda);
						blur(central);
						blur(derecha);
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
	@Override
	public void mouseClick(PickInfo pickInfo, ClickEvent event){
		String name = pickInfo.getShapeInfo().getShape().getName();
		if (name.equals("puerta_izquierda"))
			getPatientDoor().fireEvent(event);
		else if (name.equals("puerta_central"))
			getClinicianDoor().fireEvent(event);
		else if (name.equals("puerta_derecha"))
			getResearcherDoor().fireEvent(event);
		else if (name.equals("telefono01"))
			getPhone().fireEvent(event);
		else if (name.equals("bandeja"))
			getFeeds().fireEvent(event);
		else if (name.equals("GLOBO_TERRAQUEO"))
			getGlobe().fireEvent(event);
	}
	/**
	 * 
	 **/
	@Override
	public void mouseOver(PickInfo pickInfo, MouseEvent event){
		System.out.println(pickInfo.getShapeInfo().getParent().getTransform().getName());
		String name = pickInfo.getShapeInfo().getShape().getName();
		if (name.equals("puerta_izquierda")||
			name.equals("puerta_central")||
			name.equals("puerta_derecha")||
			name.equals("telefono01")||
			name.equals("bandeja")||
			name.equals("GLOBO_TERRAQUEO"))
			focus(pickInfo);
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getClinicianDoor() {
		return clinicianDoor;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getPatientDoor() {
		return patientDoor;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getResearcherDoor() {
		return researcherDoor;
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
	public HasClickHandlers getPhone() {
		return phone;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getFeeds() {
		return feeds;
	}
}
