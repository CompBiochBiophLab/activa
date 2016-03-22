package com.o2hlink.pimtools.map;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ActivaItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> myOverlays = new ArrayList<OverlayItem>();
	Context myContext;
	
	public ActivaItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public ActivaItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		myContext = context;
	}
	
	public void addMyPositionOverlay(OverlayItem overlay) {
		if (myOverlays.size() > 0) 
			if (myOverlays.get(0) != null) myOverlays.remove(0);
	    myOverlays.add(0, overlay);
	    populate();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    myOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		  return myOverlays.get(i);
	}

	@Override
	public int size() {
		  return myOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = myOverlays.get(index);
	  AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
	  AlertDialog dialog = builder.create();
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  TimeTask time = new TimeTask(5000, 1000, dialog);
	  time.start();
	  return true;
	}

}

class TimeTask extends CountDownTimer {
	
	AlertDialog dialog;
	
	public TimeTask(long millisInFuture, long countDownInterval, AlertDialog dialog) {
		super(millisInFuture, countDownInterval);
		this.dialog = dialog;
	}

	long startTime;

	@Override
	public void onFinish() {
		dialog.dismiss();
	}

	@Override
	public void onTick(long millisUntilFinished) {
	}
}
