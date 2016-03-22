package com.o2hlink.activacentral.ui;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import com.o2hlink.activ8.client.entity.Spirometry;
import com.o2hlink.activacentral.Activa;
import com.o2hlink.activacentral.data.sensor.SixMinutes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SixMinutesGraphViewWithCustomData extends View {
	
	List<Double> hr;
	
	List<Double> so2;
	
	List<Double> time;

	int width;
	int height;
	
    public SixMinutesGraphViewWithCustomData(Context context, List<Double> hr, List<Double> so2, List<Double> time, int width, int height) {
        super(context);
        this.hr = hr;
        this.so2 = so2;
        this.time = time;
        this.width = width;
        this.height = height;
    }
    
    @Override protected void onDraw(Canvas canvas) {
		//Get the limits and data of the graph
    	double maxLegendHr = 220f;
    	double maxLegendSo2 = 100f;
    	double maxLegendTime;
    	if ((time == null)||(time.size() == 0)) maxLegendTime = 360f;
    	else maxLegendTime = time.get(time.size()-1);
		// Create draw space
		Paint axis = new Paint();
		axis.setColor(Color.BLACK);
		axis.setStrokeWidth(3);
		Paint mark = new Paint();
		mark.setColor(Color.BLACK);
		mark.setStrokeWidth(2);
		Paint hrline = new Paint();
		hrline.setColor(Color.GREEN);
		hrline.setStrokeWidth(1);
		hrline.setTextSize(10);
		Paint so2line = new Paint();
		so2line.setColor(Color.MAGENTA);
		so2line.setStrokeWidth(1);
		so2line.setTextSize(10);
		// Draw the lines
		if ((hr.size() == time.size())&&(so2.size() == time.size())) {
			Hashtable<Double, Double> hrtrack = new Hashtable<Double, Double>();
			Hashtable<Double, Double> so2track = new Hashtable<Double, Double>();
			for (int i = 0; i <= time.size() - 1; i++) {
				hrtrack.put(time.get(i), hr.get(i));
				so2track.put(time.get(i), so2.get(i));
			}
			Collections.sort(time);
			for (int i = 0; i < time.size() - 1; i++) {
				float startX = (((float)time.get(i).floatValue()/(float)maxLegendTime)*(((float)width)-(float)51.5));
				startX += 31.5;
				float stopX = (((float)time.get(i + 1).floatValue()/(float)maxLegendTime)*(((float)width)-(float)51.5));
				stopX += 31.5;
				float startYHR = (((float)hrtrack.get(time.get(i)).floatValue()/(float)maxLegendHr)*(((float)height)-(float)51.5));
				startYHR += 31.5;
				startYHR = (float)height - startYHR;
				float stopYHR = (((float)hrtrack.get(time.get(i + 1)).floatValue()/(float)maxLegendHr)*(((float)height)-(float)51.5));
				stopYHR += 31.5;
				stopYHR = (float)height - stopYHR;
				float startYO2 = (((float)so2track.get(time.get(i)).floatValue()/(float)maxLegendSo2)*(((float)height)-(float)51.5));
				startYO2 += 31.5;
				startYO2 = (float)height - startYO2;
				float stopYO2 = (((float)so2track.get(time.get(i + 1)).floatValue()/(float)maxLegendSo2)*(((float)height)-(float)51.5));
				stopYO2 += 31.5;
				stopYO2 = (float)height - stopYO2;
				canvas.drawLine(startX, startYHR, stopX, stopYHR, hrline);
				canvas.drawLine(startX, startYO2, stopX, stopYO2, so2line);
			}
		}
		// Draw horizontal axis and legend
		canvas.drawLine(20, height - 30, width - 20, height - 30, axis);
//		canvas.drawText(Activa.myLanguageManager.SENSORS_SIXMIN_HRCHAT_LEGENDY, 37, 18, hrline);
//		canvas.drawText(Activa.myLanguageManager.SENSORS_SIXMIN_SO2CHAT_LEGENDY, 37, 28, so2line);
		// Draw vertical axis and legend
		canvas.drawLine(30, 20, 30, height - 20, axis);
//		canvas.drawText(Activa.myLanguageManager.SENSORS_SIXMIN_TIMECHAT_LEGENDX, (float)(width) - 55, height - 50, axis);
		// Draw vertical marks
		canvas.drawLine(27, 20, 30, 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendHr) + "bmp", 0, 28, hrline);
		canvas.drawText(String.format("%.1f", maxLegendSo2) + "%", 4, 18, so2line);
		canvas.drawLine(27, (float)((height-40)*(1.0/5.0)) + 20, 30, (float)((height-40)*(1.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendHr*0.8) + "bmp", 0, (float)((height-40)*(1.0/5.0)) + 20 + 8, hrline);
		canvas.drawText(String.format("%.1f", maxLegendSo2*0.8) + "%", 4, (float)((height-40)*(1.0/5.0)) + 20 - 2, so2line);
		canvas.drawLine(27, (float)((height-40)*(2.0/5.0)) + 20, 30, (float)((height-40)*(2.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendHr*0.6) + "bmp", 0, (float)((height-40)*(2.0/5.0)) + 20 + 8, hrline);
		canvas.drawText(String.format("%.1f", maxLegendSo2*0.6) + "%", 4, (float)((height-40)*(2.0/5.0)) + 20 - 2, so2line);
		canvas.drawLine(27, (float)((height-40)*(3.0/5.0)) + 20, 30, (float)((height-40)*(3.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendHr*0.4) + "bmp", 0, (float)((height-40)*(3.0/5.0)) + 20 + 8, hrline);
		canvas.drawText(String.format("%.1f", maxLegendSo2*0.4) + "%", 4, (float)((height-40)*(3.0/5.0)) + 20 - 2, so2line);
		canvas.drawLine(27, (float)((height-40)*(4.0/5.0)) + 20, 30, (float)((height-40)*(4.0/5.0)) + 20, mark);
		canvas.drawText(String.format("%.1f", maxLegendHr*0.2) + "bmp", 0, (float)((height-40)*(4.0/5.0)) + 20 + 8, hrline);
		canvas.drawText(String.format("%.1f", maxLegendSo2*0.2) + "%", 4, (float)((height-40)*(4.0/5.0)) + 20 - 2, so2line);
		// Draw horizontal marks
		canvas.drawLine(30 + (float)((width - 50)*(1.0/5.0)), height - 27, 30 + (float)((width - 50)*(1.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendTime*0.2) + "s", 20 + (float)((width - 50)*(1.0/5.0)), height - 9, axis);
		canvas.drawLine(30 + (float)((width - 50)*(2.0/5.0)), height - 27, 30 + (float)((width - 50)*(2.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendTime*0.4) + "s", 20 + (float)((width - 50)*(2.0/5.0)), height - 9, axis);
		canvas.drawLine(30 + (float)((width - 50)*(3.0/5.0)), height - 27, 30 + (float)((width - 50)*(3.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendTime*0.6) + "s", 20 + (float)((width - 50)*(3.0/5.0)), height - 9, axis);
		canvas.drawLine(30 + (float)((width - 50)*(4.0/5.0)), height - 27, 30 + (float)((width - 50)*(4.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendTime*0.8) + "s", 20 + (float)((width - 50)*(4.0/5.0)), height - 9, axis);
		canvas.drawLine(30 + (float)((width - 50)*(5.0/5.0)), height - 27, 30 + (float)((width - 50)*(5.0/5.0)), height - 30, mark);
		canvas.drawText(String.format("%.1f", maxLegendTime) + "s", 20 + (float)((width - 50)*(5.0/5.0)), height - 9, axis);
    }
    
}
