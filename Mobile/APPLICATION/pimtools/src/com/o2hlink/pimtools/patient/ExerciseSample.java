package com.o2hlink.pimtools.patient;

import java.util.ArrayList;
import java.util.List;

import android.text.Html;
import android.text.Spanned;

import com.o2hlink.activ8.client.entity.Exercise;
import com.o2hlink.activ8.client.entity.SixMinutesWalk;
import com.o2hlink.activ8.client.entity.SixMinutesWalkFlow;
import com.o2hlink.activ8.client.entity.SpirometryFlow;
import com.o2hlink.activ8.common.entity.ExerciseType;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaUtil;
import com.o2hlink.pimtools.data.sensor.SensorManager;

public class ExerciseSample extends Exercise {
	
	String exercisetypestring;
	
	String exercisefatiguestring;
	
	String exerciseeffortstring;
	
	/**
	 * The public constructor.
	 * @param eventId
	 * @param date
	 */
	public ExerciseSample() {
		super();
	}	

	public ExerciseSample(Exercise exercise) {
		super();
		this.setDate(exercise.getDate());
		this.setEvent(exercise.getEvent());
//		this.setHeartRateInitial(exercise.getHeartRateInitial());
//		this.setHeartRateFinal(exercise.getHeartRateFinal());
		this.setHeartRateAverage(exercise.getHeartRateAverage());
		this.setHeartRatePeak(exercise.getHeartRatePeak());
		this.setHeartRateLow(exercise.getHeartRateLow());
//		this.setOxygenInitial(exercise.getOxygenInitial());
//		this.setOxygenFinal(exercise.getOxygenFinal());
		this.setOxygenAverage(exercise.getOxygenAverage());
		this.setOxygenPeak(exercise.getOxygenPeak());
		this.setOxygenLow(exercise.getOxygenLow());
		this.setInitialDyspnea(exercise.getInitialDyspnea());
		this.setInitialFatigue(exercise.getInitialFatigue());
		this.setFinalDyspnea(exercise.getFinalDyspnea());
		this.setFinalFatigue(exercise.getFinalFatigue());
		this.setDistance(exercise.getDistance());
		this.setTime(exercise.getTime());
		this.setSteps(exercise.getSteps());
		this.setStops(exercise.getStops());
		this.setType(exercise.getType());
		switch (this.getType()) {
			case WALKING_FLAT:
				exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS1.substring(2);
				break;
			case WALKING_SLOPE:
				exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS2.substring(2);
				break;
			case PHYSICAL_VIDEOGAME:
				exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS3.substring(2);
				break;
			case RUNNING:
				exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS4.substring(2);
				break;
			case BYKE:
				exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS5.substring(2);
				break;
			case SWIMMING:
				exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS6.substring(2);
				break;
			case GYM:
				exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS7.substring(2);
				break;
		default:
			exercisetypestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_ANS1.substring(2);
			break;
		}
		this.setPerceivedEffort(exercise.getPerceivedEffort());
		switch (this.getPerceivedEffort()) {
			case 1:
				exerciseeffortstring = Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS1.substring(2);
				break;
			case 2:
				exerciseeffortstring = Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS2.substring(2);
				break;
			case 3:
				exerciseeffortstring = Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS3.substring(2);
				break;
			case 4:
				exerciseeffortstring = Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS4.substring(2);
				break;
			default:
				exerciseeffortstring = Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_ANS4.substring(2);
				break;
		}
		this.setPerceivedFatigue(exercise.getPerceivedFatigue());
		switch (this.getPerceivedFatigue()) {
			case 1:
				exercisefatiguestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS1.substring(2);
				break;
			case 2:
				exercisefatiguestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS2.substring(2);
				break;
			case 3:
				exercisefatiguestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS3.substring(2);
				break;
			case 4:
				exercisefatiguestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS4.substring(2);
				break;
			default:
				exercisefatiguestring = Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_ANS4.substring(2);
				break;
		}
	}	
	
	public Spanned getExerciseSpanData() {
		return Html.fromHtml(
			"<big><b>" + Activa.myLanguageManager.SENSORS_EXERCISE_TITLE + " " + ActivaUtil.dateToReadableString(this.getDate()) + " " + ActivaUtil.timeToReadableString(this.getDate()) + "</b></big><br/><br/>" +
//			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_INIT + ": </b>" + String.format("%.1f", this.getHeartRateInitial()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_INIT)) + "<br/>" + 
//			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_FINAL + ": </b>" + Math.round(this.getHeartRateFinal()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_FINAL)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_AVRG + ": </b>" + String.format("%.1f", this.getHeartRateAverage()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_AVRG)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_PEAK + ": </b>" + Math.round(this.getHeartRatePeak()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_PEAK)) + "<br/>" + 
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_HR_LOW + ": </b>" + Math.round(this.getHeartRateLow()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_HR_LOW)) + "<br/><br/>" + 
//			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_INIT + ": </b>" + String.format("%.1f", this.getOxygenInitial()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_INIT)) + "<br/>" +
//			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_FINAL + ": </b>" + Math.round(this.getOxygenFinal()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_FINAL)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_AVRG + ": </b>" + String.format("%.1f", this.getOxygenAverage()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_AVRG)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_PEAK + ": </b>" + Math.round(this.getOxygenPeak()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_PEAK)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_SO2_LOW + ": </b>" + Math.round(this.getOxygenLow()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_SO2_LOW)) + "<br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_INITIAL_DYS + ": </b>" + String.format("%.1f", this.getInitialDyspnea()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_AIR_PRE)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_INITIAL_FAT + ": </b>" + String.format("%.1f", this.getInitialFatigue()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_FATIGUE_PRE)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FINAL_DYS + ": </b>" + String.format("%.1f", this.getFinalDyspnea()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_AIR_POST)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_FINAL_FAT + ": </b>" + String.format("%.1f", this.getFinalFatigue()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_BORG_FATIGUE_POST)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_STEPS + ": </b>" + this.getSteps() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_STEPS)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_STOPS + ": </b>" + this.getStops() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_STOPS)) + "<br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_DISTANCE + ": </b>" + Math.round(this.getDistance()) + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_DISTANCE)) + "<br/><br/>" +
			"<b>" + Activa.myLanguageManager.SENSORS_DATA_TIME_EXE + ": </b>" + (int) this.getTime() + " " + SensorManager.getUnitForMeasurement(SensorManager.getUnitIDForMeasurementID(SensorManager.DATAID_TIME_EXE)) + "<br/><br/>" +
			"<b>" + Activa.myLanguageManager.PROGRAMS_EXERCISE_FIRSTQUEST_TITLE + ": </b>" + this.exercisetypestring + "<br/>" +
			"<b>" + Activa.myLanguageManager.PROGRAMS_EXERCISE_SECQUEST_TITLE + ": </b>" + this.exerciseeffortstring + "<br/>" +
			"<b>" + Activa.myLanguageManager.PROGRAMS_EXERCISE_THIRDQUEST_TITLE + ": </b>" + this.exercisefatiguestring + "<br/><br/>"
		);
	}

}
