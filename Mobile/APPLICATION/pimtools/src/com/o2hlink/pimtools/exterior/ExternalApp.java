package com.o2hlink.pimtools.exterior;

import com.o2hlink.pimtools.Activa;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
    
    public ExternalApp(ResolveInfo infoProgram, PackageManager manager) {
    	this.name = infoProgram.loadLabel(manager).toString();
    	this.packageName = infoProgram.activityInfo.applicationInfo.packageName;
    	this.activityName = infoProgram.activityInfo.name;
    	this.icon = 0;
    	this.setActivity(new ComponentName(this.packageName, this.activityName), 
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
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
    	try {
    		Activa.myApp.startActivity(intent);
    	} catch (Exception e) {
			e.printStackTrace();
		}
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
    
    public String toString() {
    	if (this.name != null) return this.name;
    	else return "";
    }
}
