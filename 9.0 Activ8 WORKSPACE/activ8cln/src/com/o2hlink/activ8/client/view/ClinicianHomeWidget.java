package com.o2hlink.activ8.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.o3d.client.Shape;
import com.google.gwt.o3d.client.Transform;
import com.google.gwt.o3d.client.event.MouseEvent;
import com.google.gwt.o3d.client.picking.PickInfo;
import com.google.gwt.user.client.Timer;
import com.o2hlink.activ8.client.display.ClinicianHomeDisplay;
import com.o2hlink.activ8.client.event.LoadEvent;
import com.o2hlink.activ8.client.handler.LoadHandler;

/**
 * @author Miguel Angel Hernandez
 **/
public class ClinicianHomeWidget extends HomeWidget implements ClinicianHomeDisplay {
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
    private Widget3D studygroups = new Widget3D();
	/**
	 * 
	 **/
	private HasClickHandlers papers = new Widget3D();
	/**
	 * 
	 **/
	private HasClickHandlers patients = new Widget3D();
	/**
	 * 
	 **/
	private HasClickHandlers projects = new Widget3D();
	/**
	 * 
	 **/
	private HasClickHandlers funding = new Widget3D();
	/**
	 * 
	 **/
	private HasClickHandlers shopcart = new Widget3D();
	/**
	 * 
	 **/
	private List<HasClickHandlers> datasets = new ArrayList<HasClickHandlers>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ClinicianHomeWidget(){
		super();
		setTheme("models/clinico.o3dtgz");
		
		addLoadHandler(new LoadHandler(){
			public void onLoad(LoadEvent evt) {
				final Transform revistas = (Transform) getClient().getObjects("revistas", "o3d.Transform").get(0);
				final Transform telefono = (Transform) getClient().getObjects("telefono", "o3d.Transform").get(0);
				final Transform bandeja = (Transform) getClient().getObjects("portatil2", "o3d.Transform").get(0);
				final Transform calendario = (Transform) getClient().getObjects("calendario", "o3d.Transform").get(0);
				final Transform pizarra = (Transform) getClient().getObjects("pizarra_electronica_s", "o3d.Transform").get(0);
				final Transform papers = (Transform) getClient().getObjects("papers", "o3d.Transform").get(0);
				final Transform diagrama = (Transform) getClient().getObjects("DIAGRAMA", "o3d.Transform").get(0);
				final Transform globo = (Transform) getClient().getObjects("GLOBO_TERRAQUEO_PIVOT", "o3d.Transform").get(0);
				final Transform datasets = (Transform) getClient().getObjects("libro_grupos_estudio_PIVOT", "o3d.Transform").get(0);
				
				focus(revistas);
				focus(telefono);
				focus(bandeja);
				focus(calendario);
				focus(pizarra);
				focus(papers);
				focus(diagrama);
				focus(globo);
				focus(datasets);
				getClient().render();
				
				Timer timer = new Timer(){
					public void run() {
						blur(revistas);
						blur(telefono);
						blur(bandeja);
						blur(calendario);
						blur(pizarra);
						blur(papers);
						blur(diagrama);
						blur(globo);
						blur(datasets);
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
		else if (name.equals("pizarra_electronica_s"))
			getNotes().fireEvent(event);
		else if (name.equals("laptop"))
			getShopcart().fireEvent(event);
		else if (name.startsWith("archivador"))
			getPatients().fireEvent(event);
		else if (name.equals("papers"))
			getPapers().fireEvent(event);
		else if (name.equals("DIAGRAMA"))
			getProjects().fireEvent(event);
		else if (name.equals("libro_grupos_estudio")) {
			String id = pickInfo.getShapeInfo().getParent().getTransform().getName();
			if (id.startsWith("dataset")){
				for (int i=0; i<datasets.size(); ++i)
					if (id.equals("dataset"+i))
						datasets.get(i).fireEvent(event);				
			}
			else getDatasets().fireEvent(event);
		}
	}
	/**
	 * 
	 **/
	@Override
	protected void mouseOver(PickInfo pickInfo, MouseEvent event){
		String name = pickInfo.getShapeInfo().getShape().getName();
		System.out.println(pickInfo.getShapeInfo().getParent().getTransform().getName());
		if (name.equals("revistas") ||
			name.equals("telefono") ||
			name.equals("portatil2")  ||
			name.equals("calendario")||
			name.equals("GLOBO_TERRAQUEO")||
			name.equals("pizarra_electronica_s")||
			name.equals("laptop")||
			name.startsWith("archivador")||
			name.equals("papers")||
			name.equals("DIAGRAMA")||
			name.equals("libro_grupos_estudio"))
			focus(pickInfo);
		else {
			/*for (int i=0; i<datasets.size(); ++i)
				if (pickInfo.getShapeInfo().getParent().getTransform().getName().equals("dataset"+i))
					datasets.get(i).fireEvent(event);*/
		}		
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getPatients() {
		return patients;
	}
	/**
	 * Called when the model is displayed
	 **/
	@Override
	public HasClickHandlers createStudyGroupAccess() {
		Shape clone = (Shape) getClient().getObjects("libro_grupos_estudio", "o3d.ObjectBase").get(0);
		Widget3D dataset = new Widget3D();
		if (clone!=null){
			Transform newTransform = getModelPack().createTransform();
			newTransform.setName("dataset"+datasets.size());
			newTransform.setParent(getModelTransform());
			newTransform.addShape(clone);
			newTransform.translate(157.884+6.5*(datasets.size()+1), 103.509, 88.509);
			System.out.println("Creating dataset");
			//update transforms and render
			getTransformInfo().update();
			getClient().render();
			
			datasets.add(dataset);
			return dataset;
		}
		else System.out.println("Libro not found!:(");
		return null;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getPapers() {
		return papers;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getProjects() {
		return projects;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getFunding() {
		return funding;
	}
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getShopcart() {
		return shopcart;
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
	/**
	 * 
	 **/
	@Override
	public HasClickHandlers getDatasets() {
		return studygroups;
	}
}
