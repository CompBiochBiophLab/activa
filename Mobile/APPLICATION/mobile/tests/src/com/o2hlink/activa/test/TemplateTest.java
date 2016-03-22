package com.o2hlink.activa.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

public class TemplateTest extends AndroidTestCase {
		
	protected double fValue1;
	protected double fValue2;

	public TemplateTest() {
		super();
	}
		
	@Override
	public void setUp() {
	    fValue1= 2.0;
	    fValue2= 3.0;
	}
		
	@Override
	public void tearDown() {
			
	}
		
	@SmallTest
	public void testTestProccess() {
	    double result= fValue1 + fValue2;
	    assertTrue(result == 5.0);
	}

}
