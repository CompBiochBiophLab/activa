package com.o2hlink.activ8.client.presenter;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.o2hlink.activ8.client.view.PathwayViewerWidget;
import com.o2hlink.activ8.client.view.ProteinViewerWidget;

/**
 * @author Miguel Angel Hernandez
 **/
public class EnvHomePresenter extends HomePresenter {
//FIELDS
	/**
	 * 
	 **/
	@Inject
	private Provider<BiologicalPresenter> main;
	/**
	 * 
	 **/
	@Inject
	private Provider<AlveolusPanelPresenter> alveolus;
	/**
	 * 
	 **/
	@Inject
	public Provider<AvatarPanelPresenter> avatar;
	/**
	 * 
	 **/
	@Inject
	public Provider<BronchusPanelPresenter> bronchus;
	/**
	 * 
	 **/
	@Inject
	public Provider<CellMembranePanelPresenter> membrane;
	/**
	 * 
	 **/
	@Inject
	public Provider<CellNucleusModelPresenter> nuclei;
	/**
	 * 
	 **/
	@Inject
	public Provider<CellPanelPresenter> cell;
	/**
	 * 
	 **/
	@Inject
	public Provider<ChromosomePanelPresenter> chromosome;
	/**
	 * 
	 **/
	@Inject
	public Provider<ChromosomeSetPanelPresenter> chromosomes;
	/**
	 * 
	 **/
	@Inject
	public Provider<GenePanelPresenter> gene;
	/**
	 * 
	 **/
	@Inject
	public Provider<LungPanelPresenter> lung;
	/**
	 * 
	 **/
	@Inject
	public Provider<OrgansPanelPresenter> organs;
	/**
	 * 
	 **/
	@Inject
	public Provider<ProteinPresenter> protein;
//METHODS
	/**
	 * 
	 **/
	@Override
	public void bind(){
		BiologicalPresenter container = main.get();
		getDisplay().add(container.getDisplay());
		
		if (History.getToken().equals("")){
			AvatarPanelPresenter presenter = avatar.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());			
		}
		else if (History.getToken().equals("alveolus")){
			AlveolusPanelPresenter presenter = alveolus.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("avatar")){
			AvatarPanelPresenter presenter = avatar.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("lung")){
			LungPanelPresenter presenter = lung.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("bronchus")){
			BronchusPanelPresenter presenter = bronchus.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("cell_membrane")){
			CellMembranePanelPresenter presenter = membrane.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("nuclei")){
			CellNucleusModelPresenter presenter = nuclei.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("cell")){
			CellPanelPresenter presenter = cell.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("chromosome")){
			ChromosomePanelPresenter presenter = chromosome.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("chromosomes")){
			ChromosomeSetPanelPresenter presenter = chromosomes.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}
		else if (History.getToken().equals("organs")){
			OrgansPanelPresenter presenter = organs.get();
			presenter.bind();
			container.getDisplay().setSection(presenter.getDisplay());
		}		
		else if (History.getToken().equals("protein_viewer")){
			container.getDisplay().setSection(new ProteinViewerWidget());
		}		
		else if (History.getToken().equals("pathway_viewer")){
			container.getDisplay().setSection(new PathwayViewerWidget());
		}		
	}
}
