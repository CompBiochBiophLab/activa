package com.o2hlink.activa.exterior;

import com.o2hlink.activa.Activa;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

public class ExternalApp {
    
	public String name;
	
	public String packageName;
	
	public String activityName;

    public Intent intent;

    public int icon;
    
    boolean externalApplication;
    
    public ExternalApp () {
    	this.externalApplication = true;
    }
    
    public final void setActivity(ComponentName className, int launchFlags) {
        this.intent = new Intent(Intent.ACTION_MAIN);
        this.intent.addCategory(Intent.CATEGORY_LAUNCHER);
        this.intent.setComponent(className);
        this.intent.setFlags(launchFlags);
    }
    
    final void setActivity(Intent intent) {
        this.intent = intent;
    }
    
    public void startApplication() {
    	Activa.myApp.startActivity(intent);
    }
    
    public void redirectToMarket() {
    	Activa.myApp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExternalApp)) {
            return false;
        }

        ExternalApp that = (ExternalApp) o;
        return name.equals(that.name) &&
                intent.getComponent().getClassName().equals(
                        that.intent.getComponent().getClassName());
    }

    @Override
    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        final String name = intent.getComponent().getClassName();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
