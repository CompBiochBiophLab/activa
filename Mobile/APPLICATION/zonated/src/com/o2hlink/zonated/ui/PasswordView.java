package com.o2hlink.zonated.ui;

import com.o2hlink.zonated.R;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.ZonatedUtil;
import com.o2hlink.zonated.mobile.User;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

	int radius = 0;
	
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
//			p.setColor(0x00fd41);
//			p.setStrokeWidth(6);
//	    	canvas.drawLine(lines[i][0], lines[i][1], lines[i][2], lines[i][3], p);
//    	}
    }
    
    // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {
    	draw = false;
        int eventaction = event.getAction(); 
        int extratop, extraleft;
        double radCircle;
        
        extratop = ((RelativeLayout)Zonated.myApp.findViewById(R.id.pointsLayout)).getPaddingTop();
        extraleft = ((RelativeLayout)Zonated.myApp.findViewById(R.id.pointsLayout)).getPaddingLeft();
        
        X = (int)event.getX(); 
        Y = (int)event.getY(); 
		
		ImageView[] buttons = new ImageView[9];

		buttons[0] = (ImageView) Zonated.myApp.findViewById(R.id.topleft);
		buttons[1] = (ImageView) Zonated.myApp.findViewById(R.id.top);
		buttons[2] = (ImageView) Zonated.myApp.findViewById(R.id.topright);
		buttons[3] = (ImageView) Zonated.myApp.findViewById(R.id.centerleft);
		buttons[4] = (ImageView) Zonated.myApp.findViewById(R.id.center);
		buttons[5] = (ImageView) Zonated.myApp.findViewById(R.id.centerright);
		buttons[6] = (ImageView) Zonated.myApp.findViewById(R.id.bottomleft);
		buttons[7] = (ImageView) Zonated.myApp.findViewById(R.id.bottom);
		buttons[8] = (ImageView) Zonated.myApp.findViewById(R.id.bottomright);

		for (ImageView button : buttons) {
    		// check if inside the bounds of the ball (circle)
    		// get the center for the ball
    		centerX = button.getLeft() + button.getWidth()/2 + extraleft;
    		centerY = button.getTop() + button.getHeight()/2 + extratop;
    		radius = Math.max(button.getWidth()/2, button.getHeight()/2);
    		
    		// calculate the radius from the touch to the center of the ball
    		radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
    		
    		if (radCircle < radius){
    			if (prevButtonID > 0) {
    				prevCenterX = ((ImageView) Zonated.myApp.findViewById(prevButtonID)).getLeft() + button.getWidth()/2 + extraleft;
    				prevCenterY = ((ImageView) Zonated.myApp.findViewById(prevButtonID)).getTop() + button.getHeight()/2 + extratop;
        		}
    			prevButtonID = buttonID;
    			buttonID = button.getId();
                break;
    		}
		}
		
        switch (eventaction) { 
	        case MotionEvent.ACTION_DOWN: 	             
	        	if (buttonID > 0) Zonated.myMobileManager.password += (String)(Zonated.myApp.findViewById(buttonID).getTag());
	            break; 	
	        case MotionEvent.ACTION_MOVE:   
	            if (buttonID > 0) {
	            	((ImageView) Zonated.myApp.findViewById(buttonID)).setImageResource(R.drawable.patternok);
	            	if ((prevButtonID != buttonID)||(prevButtonID < 0)) {
	            		Zonated.myMobileManager.password += (String)(Zonated.myApp.findViewById(buttonID).getTag());
	        			draw = true;
	            	}
	            }	        	
	            break;	
	        case MotionEvent.ACTION_UP: 
	        	Zonated.myMobileManager.password = ZonatedUtil.reduceGraphicalPassword(Zonated.myMobileManager.password);
	        	if (mode == 0) {
		        	boolean logged = Zonated.myMobileManager.getUserFromPassword();
		        	if (logged) {
		        		Zonated.myApp.setTitle(Zonated.myApp.getString(R.string.app_name) + " - "  + Zonated.myMobileManager.user.firstname + " " + Zonated.myMobileManager.user.lastname);
		        		Zonated.myMobileManager.currentpassword = Zonated.myMobileManager.password;
		        		Zonated.myUIManager.UIquest.loadSharedQuestionnaires(true);
		        	}
		        	else {
		        		Zonated.myUIManager.UIlogin.loadLoginScreen();
		        		Zonated.myUIManager.UImisc.loadInfoPopup(Zonated.myLanguageManager.PSW_REG_BADPASSWRD);
		        	}
		            break;	        		
	        	} 
	        	else if (mode == 1){
	        		Zonated.myUIManager.UIlogin.loadRepeatPassword(user, Zonated.myMobileManager.password);
	        	}
	        	else if (mode == 2) {
	        		if (Zonated.myMobileManager.password.equalsIgnoreCase(prevPassword)) {
		        		Zonated.myMobileManager.addUserWithPassword(user);
		        		Zonated.myApp.setTitle(Zonated.myApp.getString(R.string.app_name) + " - " + Zonated.myMobileManager.user.firstname + " " + Zonated.myMobileManager.user.lastname);
		        		Zonated.myMobileManager.currentpassword = Zonated.myMobileManager.password;
		        		Zonated.myUIManager.UIlogin.loadUserInfoScreen(user, !Zonated.myMobileManager.user.isCreated());
	        		}
	        		else {
		        		Zonated.myUIManager.UIlogin.loadMatrixPasswordForRegistering();
		        		Toast toast = Toast.makeText(Zonated.myApp, Zonated.myLanguageManager.PSW_REG_PASSWORD_NOTMATCH, Toast.LENGTH_LONG);
		        		toast.show();
					}
	        	} 
	        	else if (mode == 3){
//	        		Long id = Zonated.myMobileManager.getUserIdFromPassword();
	        		if (Zonated.myMobileManager.currentpassword.equals(Zonated.myMobileManager.password))
	        			Zonated.myUIManager.UIoptions.loadNewPasswordForChanging(user, Zonated.myMobileManager.password);
	        		else {
		        		Zonated.myUIManager.UIoptions.loadMatrixPasswordForChanging();
		        		Toast toast = Toast.makeText(Zonated.myApp, Zonated.myLanguageManager.PSW_REG_PASSWORD_NOTMATCH, Toast.LENGTH_LONG);
		        		toast.show();
	        		}
	        	}
	        	else if (mode == 4){
	        		Zonated.myUIManager.UIoptions.loadRepeatPasswordForChanging(user, Zonated.myMobileManager.password);
	        	}
	        	else if (mode == 5) {
	        		if (Zonated.myMobileManager.password.equalsIgnoreCase(prevPassword)) {
	        			Zonated.myMobileManager.users.remove(prevPassword);
		        		Zonated.myMobileManager.addUserWithPassword(user);
		        		Zonated.myMobileManager.saveUsers();
		        		Zonated.myMobileManager.currentpassword = Zonated.myMobileManager.password;
		        		Zonated.myUIManager.UIoptions.showOptions();
	        		}
	        		else {
		        		Zonated.myUIManager.UIoptions.loadMatrixPasswordForChanging();
		        		Toast toast = Toast.makeText(Zonated.myApp, Zonated.myLanguageManager.PSW_REG_PASSWORD_NOTMATCH, Toast.LENGTH_LONG);
		        		toast.show();
	        		}
	        	}
        } 
        // redraw the canvas
        invalidate(); 
        return true; 
    }
    
}
