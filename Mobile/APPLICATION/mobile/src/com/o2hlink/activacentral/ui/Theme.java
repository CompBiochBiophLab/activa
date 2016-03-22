package com.o2hlink.activacentral.ui;

public class Theme extends com.o2hlink.activ8.client.entity.Theme {

	private String description;
	
	private String features;
	
	private boolean purchased;
	
	public Theme() {
		super();
		this.description = "";
		this.features = "";
		this.purchased = false;
	}	

	public Theme(boolean purchased) {
		super();
		this.description = "";
		this.features = "";
		this.purchased = purchased;
	}	
	
	public Theme(com.o2hlink.activ8.client.entity.Theme theme, boolean purchased) {
		super();
		this.setId(theme.getId());
		this.setName(theme.getName());
		this.setCost(theme.getCost());
		this.setUrl2D(theme.getUrl2D());
		this.setUrl3D(theme.getUrl3D());
		this.setUrlMobile(theme.getUrlMobile());
		this.setUrlThumbnail(theme.getUrlThumbnail());
		this.description = "";
		this.features = "";
		this.purchased = false;
	}	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}
	
}
