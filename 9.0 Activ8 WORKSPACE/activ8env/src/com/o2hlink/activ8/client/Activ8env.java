package com.o2hlink.activ8.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.o2hlink.activ8.client.injection.Factory;
import com.o2hlink.activ8.client.presenter.ApplicationPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Activ8env implements EntryPoint {
//FIELDS
	/**
	 * 
	 **/
	private final Factory factory = GWT.create(Factory.class);
//METHODS
	/**
	 * 
	 **/
	@Override
	public void onModuleLoad() {
		Image image = new Image();
		image.setSize("100%", "100%");
		RootPanel.get("Background").add(image);
		if (History.getToken().equals("")){
			image.setUrl("images/Avatar.jpg");
		}
		else if (History.getToken().equals("alveolus")){
			image.setUrl("images/Alveolo.jpg");
		}
		else if (History.getToken().equals("avatar")){
			image.setUrl("images/Avatar.jpg");
		}
		else if (History.getToken().equals("bronchus")){
			image.setUrl("images/bronquio.jpg");
		}
		else if (History.getToken().equals("cell_membrane")){
			image.setUrl("images/membrana_celular.jpg");
		}
		else if (History.getToken().equals("nuclei")){
			image.setUrl("images/cell.jpg");
		}
		else if (History.getToken().equals("cell")){
			image.setUrl("images/cell.jpg");
		}
		else if (History.getToken().equals("chromosome")){
			image.setUrl("images/chromosome.jpg");
		}
		else if (History.getToken().equals("chromosomes")){
			image.setUrl("images/chromosomes.jpg");
		}
		else if (History.getToken().equals("organs")){
			image.setUrl("images/organs.jpg");
		}		
		else if (History.getToken().equals("protein_viewer")){
			image.setUrl("images/cell.jpg");
		}		
		else if (History.getToken().equals("pathway_viewer")){
			image.setUrl("images/cell.jpg");
		}		
		
		ApplicationPresenter presenter = factory.getApplication();
		presenter.setPanel(RootPanel.get("Content"));
		presenter.bind();
	}
}
