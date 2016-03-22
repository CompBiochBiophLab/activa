package com.o2hlink.activ8.client.view;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.o2hlink.activ8.client.display.Display;
import com.o2hlink.activ8.client.handler.LoadHandler;
import com.o2hlink.activ8.client.handler.UnloadHandler;

/**
 * @author Miguel Angel Hernandez
 **/
public class VideoWidget extends BaseWidget implements Display {
	public VideoWidget(String code){
		initWidget(new HTML(code));
	}
	@Override
	public HandlerRegistration addLoadHandler(LoadHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandlerRegistration addUnloadHandler(UnloadHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

}
