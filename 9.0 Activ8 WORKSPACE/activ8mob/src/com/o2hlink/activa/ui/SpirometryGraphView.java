package com.o2hlink.activa.ui;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.sensor.SensorManager;
import com.o2hlink.activa.data.sensor.Spirometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;

public class SpirometryGraphView extends View {
	
	Spirometer spirometer;
    
    public SpirometryGraphView(Context context, Spirometer spirometer) {
        super(context);
        this.spirometer = spirometer;
    }
    
    // the method that draws the balls
    @Override protected void onDraw(Canvas canvas) {
		View graph = (View) Activa.myApp.findViewById(R.id.graph);
		//Get the limits and data of the graph
		int maxFlow = 0;
		for (int i = 0; i < spirometer.flowLine.length; i++) {
			int number = spirometer.flowLine[i];
			if (maxFlow < number) maxFlow = number;
		}
		int maxYF = maxFlow + 200;
		float maxLegendYFlow = spirometer.results.get(SensorManager.DATAID_PEF)/maxFlow*maxYF;
		int maxTime = 0;
		for (int i = 0; i < spirometer.timeLine.length; i++) {
			int number = spirometer.timeLine[i];
			if (maxTime < number) maxTime = number;
		}
		int maxYT = maxTime + 600;
		float maxLegendYTime = spirometer.results.get(SensorManager.DATAID_FVC)/maxTime*maxYT;
		int integrate = 0;
//		int atfev1 = 0;
//		int limitIntegrate = Math.round(spirometer.results.get(SensorManager.DATAID_FEV1)*maxFlow/spirometer.results.get(SensorManager.DATAID_PEF));
//		for (int i = 0; i < spirometer.flowLine.length; i++) {
//			integrate += spirometer.flowLine[i];
//			if (limitIntegrate < integrate) {
//				atfev1 = i;
//				break;
//			}
//		}
//		float maxLegendXFlow = (float)spirometer.flowLine.length/((float)atfev1)*spirometer.results.get(SensorManager.DATAID_FEV1);
		float maxLegendXFlow = spirometer.results.get(SensorManager.DATAID_FVC);
		int atonesec = 0;
		int fev1Point = Math.round(spirometer.results.get(SensorManager.DATAID_FEV1)/spirometer.results.get(SensorManager.DATAID_FVC)*maxTime);
		for (int i = 0; i < spirometer.timeLine.length; i++) {
			int number = spirometer.timeLine[i];
			if (fev1Point < number) {
				atonesec = i;
				break;
			}
		}
		float maxLegendXTime = (float)spirometer.timeLine.length/(float)atonesec;
		// Create draw space
		int width = graph.getLayoutParams().width;
		int height = graph.getLayoutParams().height;
		Paint axis = new Paint();
		axis.setColor(Color.BLACK);
		axis.setStrokeWidth(3);
		Paint mark = new Paint();
		mark.setColor(Color.BLACK);
		mark.setStrokeWidth(2);
		Paint flow = new Paint();
		flow.setColor(Color.GREEN);
		flow.setStrokeWidth(1);
		flow.setTextSize(10);
		Paint time = new Paint();
		time.setColor(Color.MAGENTA);
		time.setStrokeWidth(1);
		time.setTextSize(10);
		// Draw the flow line
		for (int i = 0; i < spirometer.flowLine.length - 1; i++) {
			float startX = (((float)i/(float)spirometer.flowLine.length)*(((float)height)-(float)51.5));
			startX += 31.5;
			float startY = (((float)spirometer.flowLine[i]/(float)maxYF)*(((float)height)-(float)51.5));
			startY += 31.5;
			startY = (float)height - startY;
			float stopX = (((float)(i+1)/(float)spirometer.flowLine.length)*(((float)height)-(float)51.5));
			stopX += 31.5;
			float stopY = (((float)spirometer.flowLine[i+1]/(float)maxYF)*(((float)height)-(float)51.5));
			stopY += 31.5;
			stopY = (float)height - stopY;
			canvas.drawLine(startX, startY, stopX, stopY, flow);
		}
		// Draw the time line
		for (int i = 0; i < spirometer.timeLine.length - 1; i++) {
			float startX = (((float)i/(float)spirometer.timeLine.length)*(((float)height)-(float)51.5));
			startX += 31.5;
			float startY = (((float)spirometer.timeLine[i]/(float)maxYT)*(((float)height)-(float)51.5));
			startY += 31.5;
			startY = (float)height - startY;
			float stopX = (((float)(i+1)/(float)spirometer.timeLine.length)*(((float)height)-(float)51.5));
			stopX += 31.5;
			float stopY = (((float)spirometer.timeLine[i+1]/(float)maxYT)*(((float)height)-(float)51.5));
			stopY += 31.5;
			stopY = (float)height - stopY;
			canvas.drawLine(startX, startY, stopX, stopY, time);
		}
		// Draw horizontal axis and legend
		canvas.drawLine(20, height - 30, width - 20, height - 30, axis);
		canvas.drawText(Activa.myLanguageManager.SENSORS_SPIRO_TIMECHAT_LEGENDX, 37, 18, time);
		canvas.drawText(Activa.myLanguageManager.SENSORS_SPIRO_FLOWCHAT_LEGENDX, 37, 28, flow);
		// Draw vertical axis and legend
		canvas.drawLine(30, 20, 30, height - 20, axis);
		canvas.drawText(Activa.myLanguageManager.SENSORS_SPIRO_TIMECHAT_LEGENDY, (float)(width) - 55, height - 50, time);
		canvas.drawText(Activa.myLanguageManager.SENSORS_SPIRO_FLOWCHAT_LEGENDY, (float)(width) - 55, height - 40, flow);
		// Draw vertical marks
		canvas.drawLine(27, 20, 30, 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendYFlow) + "l/s", 0, 28, flow);
		canvas.drawText(String.format("%.1f", maxLegendYTime) + "l", 4, 18, time);
		canvas.drawLine(27, (float)((height-40)*(1.0/5.0)) + 20, 30, (float)((height-40)*(1.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendYFlow*0.8) + "l/s", 0, (float)((height-40)*(1.0/5.0)) + 20 + 8, flow);
		canvas.drawText(String.format("%.1f", maxLegendYTime*0.8) + "l", 4, (float)((height-40)*(1.0/5.0)) + 20 - 2, time);
		canvas.drawLine(27, (float)((height-40)*(2.0/5.0)) + 20, 30, (float)((height-40)*(2.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendYFlow*0.6) + "l/s", 0, (float)((height-40)*(2.0/5.0)) + 20 + 8, flow);
		canvas.drawText(String.format("%.1f", maxLegendYTime*0.6) + "l", 4, (float)((height-40)*(2.0/5.0)) + 20 - 2, time);
		canvas.drawLine(27, (float)((height-40)*(3.0/5.0)) + 20, 30, (float)((height-40)*(3.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendYFlow*0.4) + "l/s", 0, (float)((height-40)*(3.0/5.0)) + 20 + 8, flow);
		canvas.drawText(String.format("%.1f", maxLegendYTime*0.4) + "l", 4, (float)((height-40)*(3.0/5.0)) + 20 - 2, time);
		canvas.drawLine(27, (float)((height-40)*(4.0/5.0)) + 20, 30, (float)((height-40)*(4.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendYFlow*0.2) + "l/s", 0, (float)((height-40)*(4.0/5.0)) + 20 + 8, flow);
		canvas.drawText(String.format("%.1f", maxLegendYTime*0.2) + "l", 4, (float)((height-40)*(4.0/5.0)) + 20 - 2, time);
		// Draw horizontal marks
		canvas.drawLine(30 + (float)((width - 50)*(1.0/5.0)), height - 27, 30 + (float)((width - 50)*(1.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendXFlow*0.2) + "l", 20 + (float)((width - 50)*(1.0/5.0)), height - 9, flow);
		canvas.drawText(String.format("%.1f", maxLegendXTime*0.2) + "s", 20 + (float)((width - 50)*(1.0/5.0)), height - 19, time);
		canvas.drawLine(30 + (float)((width - 50)*(2.0/5.0)), height - 27, 30 + (float)((width - 50)*(2.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendXFlow*0.4) + "l", 20 + (float)((width - 50)*(2.0/5.0)), height - 9, flow);
		canvas.drawText(String.format("%.1f", maxLegendXTime*0.4) + "s", 20 + (float)((width - 50)*(2.0/5.0)), height - 19, time);
		canvas.drawLine(30 + (float)((width - 50)*(3.0/5.0)), height - 27, 30 + (float)((width - 50)*(3.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendXFlow*0.6) + "l", 20 + (float)((width - 50)*(3.0/5.0)), height - 9, flow);
		canvas.drawText(String.format("%.1f", maxLegendXTime*0.6) + "s", 20 + (float)((width - 50)*(3.0/5.0)), height - 19, time);
		canvas.drawLine(30 + (float)((width - 50)*(4.0/5.0)), height - 27, 30 + (float)((width - 50)*(4.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendXFlow*0.8) + "l", 20 + (float)((width - 50)*(4.0/5.0)), height - 9, flow);
		canvas.drawText(String.format("%.1f", maxLegendXTime*0.8) + "s", 20 + (float)((width - 50)*(4.0/5.0)), height - 19, time);
		canvas.drawLine(30 + (float)((width - 50)*(5.0/5.0)), height - 27, 30 + (float)((width - 50)*(5.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendXFlow) + "l", 20 + (float)((width - 50)*(5.0/5.0)), height - 9, flow);
		canvas.drawText(String.format("%.1f", maxLegendXTime) + "s", 20 + (float)((width - 50)*(5.0/5.0)), height - 19, time);
		
    }
    
}
