package com.o2hlink.activa.exterior;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewer extends Activity {
	
	public static Uri image;
	
	public ImageViewer(Uri image) {
		ImageViewer.image = image;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageURI(ImageViewer.image);
	}
	
	@Override
    public void onDestroy() {
		Activa.myExteriorManager.externalApplications.get(ExteriorManager.APP_PICTURES).startApplication();
	}
	
}
