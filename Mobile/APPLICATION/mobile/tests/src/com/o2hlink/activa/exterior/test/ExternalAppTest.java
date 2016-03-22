package com.o2hlink.activa.exterior.test;

import com.o2hlink.activa.exterior.ExternalApp;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

public class ExternalAppTest extends AndroidTestCase {
	
    protected ExternalApp externalapp;

	public ExternalAppTest() {
		super();
	}
	
	@Override
	public void setUp() {
        externalapp = new ExternalApp();
	}
	
	@Override
	public void tearDown() {
		
	}
	
	@SmallTest
	public void AvoidToStartNullApp() {
		externalapp.intent = null;
        externalapp.startApplication();
	}
}
