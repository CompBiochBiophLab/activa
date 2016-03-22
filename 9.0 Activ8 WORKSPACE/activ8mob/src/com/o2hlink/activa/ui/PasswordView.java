package com.o2hlink.activa.ui;

import java.util.Date;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.R;
import com.o2hlink.activa.mobile.User;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;

public class PasswordView extends View {
    private int buttonID = -1; 
    private int prevButtonID = -1;
    private int mode;
    private User user;
    private String prevPassword;
	
	int prevCenterX = 0;
	int prevCenterY = 0;

	int centerX = 0;
	int centerY = 0;
	
	int X = -1;
	int Y = -1;
	
	boolean draw = false;
	
	int[][] lines;
	int numLines;
    
    public PasswordView(Context context, int mode) {
        super(context);
        setFocusable(true); //necessary for getting the touch events   
        this.mode = mode;
        lines = new int[20][4];
        numLines = 0;
    }
    
    public PasswordView(Context context, int mode, User user) {
        super(context);
        setFocusable(true); //necessary for getting the touch events   
        this.mode = mode;
        lines = new int[20][4];
        numLines = 0;
        this.user = user;
    }
    
    public PasswordView(Context context, int mode, User user, String prevPassword) {
        super(context);
        setFocusable(true); //necessary for getting the touch events   
        this.mode = mode;
        lines = new int[20][4];
        numLines = 0;
        this.user = user;
        this.prevPassword = prevPassword;
    }
    
    // the method that draws the balls
    @Override protected void onDraw(Canvas canvas) {
//		if (draw) {
//	    	lines[numLines][0] = prevCenterX;
//	    	lines[numLines][1] = prevCenterY;
//	    	lines[numLines][2] = centerX;
//	    	lines[numLines][3] = centerY;
//	    	numLines++;
//		}
//    	for (int i = 0; i < numLines; i++) {
//			Paint p = new Paint();
//			p.setColor(Color.GREEN);
//			p.setStrokeWidth(6);
//	    	canvas.drawLine(lines[i][0], lines[i][1], lines[i][2], lines[i][3], p);
//    	}
    }
    
    // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {
    	draw = false;
        int eventaction = event.getAction(); 
        int tag;
        double radCircle;
        
        X = (int)event.getX(); 
        Y = (int)event.getY(); 
		
		RadioButton[] buttons = new RadioButton[9];
		
		View board = (View) Activa.myApp.findViewById(R.id.board);
		
		tag = Integer.parseInt(((String)board.getTag()));

		buttons[0] = (RadioButton) Activa.myApp.findViewById(R.id.topleft);
		buttons[1] = (RadioButton) Activa.myApp.findViewById(R.id.top);
		buttons[2] = (RadioButton) Activa.myApp.findViewById(R.id.topright);
		buttons[3] = (RadioButton) Activa.myApp.findViewById(R.id.centerleft);
		buttons[4] = (RadioButton) Activa.myApp.findViewById(R.id.center);
		buttons[5] = (RadioButton) Activa.myApp.findViewById(R.id.centerright);
		buttons[6] = (RadioButton) Activa.myApp.findViewById(R.id.bottomleft);
		buttons[7] = (RadioButton) Activa.myApp.findViewById(R.id.bottom);
		buttons[8] = (RadioButton) Activa.myApp.findViewById(R.id.bottomright);

		for (RadioButton button : buttons) {
    		// check if inside the bounds of the ball (circle)
    		// get the center for the ball
    		centerX = button.getLeft() + 21 + tag;
    		centerY = button.getTop() + 21 + tag;
    		
    		// calculate the radius from the touch to the center of the ball
    		radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
    		
    		if (radCircle < 60){
    			if (prevButtonID > 0) {
    				prevCenterX = ((RadioButton) Activa.myApp.findViewById(prevButtonID)).getLeft() + 21 + tag;
    				prevCenterY = ((RadioButton) Activa.myApp.findViewById(prevButtonID)).getTop() + 21 + tag;
        		}
    			prevButtonID = buttonID;
    			buttonID = button.getId();
                break;
    		}
		}
		
        switch (eventaction) { 
	        case MotionEvent.ACTION_DOWN: 	             
	        	if (buttonID > 0) Activa.myMobileManager.password += (String)(Activa.myApp.findViewById(buttonID).getTag());
	            break; 	
	        case MotionEvent.ACTION_MOVE:   
	            if (buttonID > 0) {
	            	((RadioButton) Activa.myApp.findViewById(buttonID)).setChecked(true);
	            	if ((prevButtonID != buttonID)||(prevButtonID < 0)) {
	            		Activa.myMobileManager.password += (String)(Activa.myApp.findViewById(buttonID).getTag());
	        			draw = true;
	            	}
	            }	        	
	            break;	
	        case MotionEvent.ACTION_UP: 
	        	if (mode == 0) {
		        	boolean logged = Activa.myMobileManager.getUserFromPassword();
		        	if (logged) {
		        		Activa.myApp.setTitle("Activa - " + Activa.myMobileManager.user.firstname + " " + Activa.myMobileManager.user.lastname);
		        		if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.showAmbients(false);
		        		else Activa.myUIManager.loadGeneratedMainScreen(true, false);
		        	}
		        	else {
		        		if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.showAmbients(false);
		        		else Activa.myUIManager.loadGeneratedMainScreen(true, false);
		        	}
		            break;	        		
	        	} 
	        	else if (mode == 1){
	        		Activa.myUIManager.loadRepeatPassword(user, Activa.myMobileManager.password);
	        	}
	        	else {
	        		if (Activa.myMobileManager.password.equalsIgnoreCase(prevPassword)) {
		        		Activa.myMobileManager.addUserWithPassword(user);
		        		Activa.myApp.setTitle("Activa - " + Activa.myMobileManager.user.firstname + " " + Activa.myMobileManager.user.lastname);
		        		Activa.myMobileManager.saveUsers();
		        		Activa.myUIManager.loadUserInfoScreen(user);
	        		}
	        		else {
		        		if (Activa.myMobileManager.user.getAmbient() == null) Activa.myUIManager.showAmbients(false);
		        		else Activa.myUIManager.loadGeneratedMainScreen(false, false);
	        			Activa.myUIManager.loadInfoPopup(Activa.myLanguageManager.PSW_REG_NOTMATCH);
	        		}
	        	}
        } 
        // redraw the canvas
        invalidate(); 
        return true; 
	
    }
}
