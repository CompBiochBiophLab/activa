package com.o2hlink.activ8.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.o2hlink.activ8.client.action.PatientUpdateAction;
import com.o2hlink.activ8.client.display.AccountPatientDisplay;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.event.UserChangeEvent;
import com.o2hlink.activ8.client.exception.UpdateException;
import com.o2hlink.activ8.client.i18n.Messages;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class AccountPatientPresenter extends AccountProfilePresenter {
//METHODS
	/**
	 * 
	 **/
	public void bind(){
		final AccountPatientDisplay display = (AccountPatientDisplay) getDisplay();
		
		final Patient patient = (Patient) getApplication().getUser();
		display.getBluetoothAddress().setValue(patient.getBluetoothAddress());
		display.getUpdate().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				patient.setBluetoothAddress(display.getBluetoothAddress().getValue());
				
				getDisplay().notifyLoading();
				getService().dispatch(
					new PatientUpdateAction(patient), 
					new AsyncCallback<VoidResponse>(){
						public void onFailure(Throwable caught) {
							try {
								throw caught;
							}
							catch (UpdateException exception){
								getDisplay().notifyFailure(Messages.instance.update_error());
							}
							catch (Throwable throwable) {
								getDisplay().notifyFailure(Messages.instance.unknown_error());
							}
						}
						public void onSuccess(VoidResponse result) {
							getDisplay().notifySuccess();
							getApplication().getBus().fireEvent(new UserChangeEvent(patient));
						}
					});
			}
		});
	}
}
