package com.o2hlink.activa.exterior;

import com.o2hlink.activa.Activa;

public class ImageGallery extends ExternalApp {

	public void startApplication() {
		Activa.myApp.startActivityForResult(intent, ExteriorManager.INTENT_RESULT_IMAGESELECTED);
    }
	
}
