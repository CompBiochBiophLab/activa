package com.o2hlink.activa.ui;
import java.util.ArrayList;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyProgressBar extends LinearLayout implements Runnable {

	private Context context;
	private ImageView imageHolder;
	private ArrayList<Integer> images;
	private Thread animationThread;
	private boolean stopped = true;
	int currentImage = 0;
	int nextImage = 0;

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		prepareLayout();
	}

	public MyProgressBar(Context context) {
		super(context);
		this.context = context;
		prepareLayout();
	}

	/**
	 * This is called when you want the dialog to be dismissed
	 */
	public void dismiss() {
		stopped = true;
		setVisibility(View.GONE);
	}

	/**
	 * Loads the layout and sets the initial set of images
	 */
	private void prepareLayout() {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.myprogressbar, null);
		addView(view);

		imageHolder = (ImageView) view.findViewById(R.id.progressimage);

		// Prepare an array list of images to be animated
		images = new ArrayList<Integer>();

		images.add(R.drawable.render_logo_movil0001);
		images.add(R.drawable.render_logo_movil0002);
		images.add(R.drawable.render_logo_movil0003);
		images.add(R.drawable.render_logo_movil0004);
		images.add(R.drawable.render_logo_movil0005);
		images.add(R.drawable.render_logo_movil0006);
		images.add(R.drawable.render_logo_movil0007);
		images.add(R.drawable.render_logo_movil0008);
	}

	/**
	 * Starts the animation thread
	 */
	public void startAnimation() {
		setVisibility(View.VISIBLE);
		animationThread = new Thread(this, "Progress");
		animationThread.start();
	}

	@Override
	public void run() {
		int count = 0;
		while (stopped) {
			try {
				// Sleep for 0.3 secs and after that change the images
				Thread.sleep(125);
				handler.sendEmptyMessage(0);
				count++;
				if (count > 400) 
					return;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// Logic to change the images
			currentImage = nextImage;
			if (currentImage < 7) {
				nextImage = currentImage + 1;
			} else {
				nextImage = 0;
			}
			imageHolder.setImageResource(images.get(currentImage));
			super.handleMessage(msg);
		}

	};

}

