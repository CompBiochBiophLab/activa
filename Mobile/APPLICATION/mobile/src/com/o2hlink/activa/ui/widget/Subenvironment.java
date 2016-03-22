package com.o2hlink.activa.ui.widget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;

public class Subenvironment {
	
	public int id;
	
	public int height;
	public int width;
	public int top;
	public int left;

	public int fileheight;
	public int filewidth;
	
	public String name;
	public String ambientname;
	
	public String image;
	public String animationfilesprefix;
	public int animationframes;
	public int shownfrom;
	
	public boolean expanded;

	public Hashtable<Integer, Widget> widgets;
	
	public Subenvironment(int id, String ambient) {
		this.id = id;
		this.expanded = false;
		this.animationframes = 0;
		this.shownfrom = 0;
		this.ambientname = ambient;
		this.widgets = new Hashtable<Integer, Widget>();
	}
	
	public Drawable getBackground() {
		if (!((this.image.substring(this.image.length()-4).equals(".jpg"))||(this.image.substring(this.image.length()-4).equals(".png")))) {
			int id = Activa.myApp.getResources().getIdentifier("com.o2hlink.activa:drawable/" + this.image, null, null);
			return Activa.myApp.getResources().getDrawable(id);
		}
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
//		switch (Activa.myMobileManager.user.getType()) {
//			case 0:
//				userfolder = new File(originalfolder, FOLDER_PATIENT);
//				break;
//			case 1:
//				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
//				break;
//			case 2:
//				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
//				break;
//			default:
//				break;
//		}
		if (userfolder != null) {
			File ambient = new File(userfolder, this.ambientname);
			File image = new File(ambient, this.image);
			try {
				return Drawable.createFromStream(new FileInputStream(image), "src");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	public AnimationDrawable getAnimationMainToSub() {
		if (!this.image.substring(this.image.length()-4).equals(".jpg")) {
			return null;
		}
		if (Activa.myMobileManager.user.animations == 0) return null;
		AnimationDrawable animation = null;
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
//		switch (Activa.myMobileManager.user.getType()) {
//			case 0:
//				userfolder = new File(originalfolder, FOLDER_PATIENT);
//				break;
//			case 1:
//				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
//				break;
//			case 2:
//				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
//				break;
//			default:
//				break;
//		}
		if (userfolder != null) {
			File ambient = new File(userfolder, this.ambientname);
			animation = new AnimationDrawable();
			animation.setOneShot(true);
			if (this.animationframes <= 0) return null;
			for (int i = 0; i < this.animationframes; i = i + Activa.myMobileManager.user.animations) {
				String name = this.animationfilesprefix + String.format("%04d", i) + ".jpg";
				File file = new File(ambient, name);
				Drawable shot;
				try {
					shot = Drawable.createFromStream(new FileInputStream(file), "src");
					animation.addFrame(shot, 25);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return animation;
	}
	
	public AnimationDrawable getAnimationSubToMain() {
		if (!this.image.substring(this.image.length()-4).equals(".jpg")) {
			return null;
		}
		if (Activa.myMobileManager.user.animations == 0) return null;
		AnimationDrawable animation = null;
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
//		switch (Activa.myMobileManager.user.getType()) {
//			case 0:
//				userfolder = new File(originalfolder, FOLDER_PATIENT);
//				break;
//			case 1:
//				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
//				break;
//			case 2:
//				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
//				break;
//			default:
//				break;
//		}
		if (userfolder != null) {
			File ambient = new File(userfolder, this.ambientname);
			animation = new AnimationDrawable();
			animation.setOneShot(true);
			if (this.animationframes <= 0) return null;
			for (int i = this.animationframes - 1; i >= 0; i = i - Activa.myMobileManager.user.animations) {
				String name = this.animationfilesprefix + String.format("%04d", i) + ".jpg";
				File file = new File(ambient, name);
				Drawable shot;
				try {
					shot = Drawable.createFromStream(new FileInputStream(file), "src");
					animation.addFrame(shot, 25);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return animation;
	}
	
	public static Subenvironment getSubenvironment(XmlPullParser info, int id, String ambient) {
		Subenvironment returned = new Subenvironment(id, ambient);
		int numwidget = 0;
		try {
			int event = info.getEventType();
		    while (event != XmlPullParser.END_DOCUMENT) {
		    	if(event == XmlPullParser.START_DOCUMENT) {
		    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
		        } else if(event == XmlPullParser.START_TAG) {
		        	if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
		        		numwidget = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDGETS"));
		        		returned.name = info.getAttributeValue(info.getNamespace(),"NAME");
		        		if (returned.name == null) returned.name = "Undefined";
		        		returned.image = info.getAttributeValue(info.getNamespace(),"FILE");
		        		Drawable image = returned.getBackground();
		        		if (!(image instanceof Drawable)) return null;
		        		returned.animationfilesprefix = info.getAttributeValue(info.getNamespace(),"ANIMATION");
		        		returned.animationframes = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FRAMES"));
		        		returned.top = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"TOP"));
		        		returned.left = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"LEFT"));
		        		returned.width = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
		        		returned.height = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
		        		returned.filewidth = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FILEWIDTH"));
		        		returned.fileheight = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FILEHEIGHT"));
		        		if (Integer.parseInt(info.getAttributeValue(info.getNamespace(),"EXPANDED")) > 0) returned.expanded = true;
		        		returned.shownfrom = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"SHOWNFROM"));
		            } else if (info.getName().equalsIgnoreCase("WIDGET")) {
		            	int idwid = returned.widgets.size();
		            	returned.widgets.put(idwid, Widget.getWidget(info, idwid));
		            }
		        } 
		        else if(event == XmlPullParser.END_TAG) {
	            	if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
	            		if (returned.widgets.size() != numwidget)  return null;
		            	break;
	            	}
	            }
	            event = info.next();
		    }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
    		return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returned;
	}

	public static boolean checkSubenvironment(XmlPullParser info, File file) {
		int numwidget = 0;
		int widgetcount = 0;
		try {
			int event = info.getEventType();
		    while (event != XmlPullParser.END_DOCUMENT) {
		    	if(event == XmlPullParser.START_DOCUMENT) {
		    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
		        } else if(event == XmlPullParser.START_TAG) {
		        	if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
		        		numwidget = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDGETS"));
		        		if (info.getAttributeValue(info.getNamespace(),"NAME") == null) return false;
		        		String filename = info.getAttributeValue(info.getNamespace(),"FILE");
		        		File imagefile = new File(file, filename);
		        		if (!imagefile.exists()) return false;
		        		if (info.getAttributeValue(info.getNamespace(),"ANIMATION") == null) return false;
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FRAMES"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"TOP"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"LEFT"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FILEWIDTH"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FILEHEIGHT"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"EXPANDED"));
		        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"SHOWNFROM"));
		            } else if (info.getName().equalsIgnoreCase("WIDGET")) {
		            	if (Widget.checkWidget(info)) widgetcount++;
		            	else return false;
		            }
		        } 
		        else if(event == XmlPullParser.END_TAG) {
	            	if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
	            		if (widgetcount != numwidget)  return false;
		            	break;
	            	}
	            }
	            event = info.next();
		    }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
    		return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static HashSet<String> getFilesForDownloading(XmlPullParser info) {
		HashSet<String> files = new HashSet<String>();
		try {
			int event = info.getEventType();
		    while (event != XmlPullParser.END_DOCUMENT) {
		    	if(event == XmlPullParser.START_DOCUMENT) {
		    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
		        } else if(event == XmlPullParser.START_TAG) {
		        	if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
		        		String filename = info.getAttributeValue(info.getNamespace(),"FILE");
		        		files.add(filename);
		        		String animationfilesprefix = info.getAttributeValue(info.getNamespace(),"ANIMATION");
		        		int animationframes = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"FRAMES"));
		        		for (int i = 0; i < animationframes; i++) {
		    				String animationfile = animationfilesprefix + String.format("%04d", i) + ".jpg";
		    				files.add(animationfile);
		    			}
		            }
		        } 
		        else if(event == XmlPullParser.END_TAG) {
	            	if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
		            	break;
	            	}
	            }
	            event = info.next();
		    }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
    		return null;
		} catch (IOException e) {
			e.printStackTrace();
    		return null;
		}
		return files;
	}
	
}
