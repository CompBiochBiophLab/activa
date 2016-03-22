package com.o2hlink.activa.ui.widget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.o2hlink.activa.Activa;
import com.o2hlink.activa.ActivaConfig;
import com.o2hlink.activa.R;
import com.o2hlink.activa.data.calendar.Event;
import com.o2hlink.activa.data.message.Contact;
import com.o2hlink.activa.ui.i18n.ResourceEnglish;
import com.o2hlink.activa.ui.i18n.ResourceSpanish;

public class Ambient {
	
	public static final String FOLDER_PATIENT = "Patient";
	public static final String FOLDER_CLINICIAN = "Clinician";
	public static final String FOLDER_RESEARCHER = "Researcher";
	public static final String FILE_CONFIG_SPA = "environmentspa.xml";
	public static final String FILE_CONFIG_ENG = "environmenteng.xml";
	public static final String FILE_DEMO_IMAGE = "mini.jpg";
	public static final String ONLINE_ROOMS_PATIENT = "http://c0034480.cdn1.cloudfiles.rackspacecloud.com/mobileroomspatient.xml";
	public static final String ONLINE_ROOMS_CLINICIAN = "http://c0034482.cdn1.cloudfiles.rackspacecloud.com/mobileroomsclinician.xml";
	public static final String ONLINE_ROOMS_RESEARCHER = "http://c0034484.cdn1.cloudfiles.rackspacecloud.com/mobileroomsresearcher.xml";
	
	private static Ambient instance;
	
	public String name;
	
	public String image;
	
	public int height;
	public int width;
	
	public boolean loaded;
	
	public Hashtable<Integer, Subenvironment> subenvironments;
	
	private Ambient () {
		this.loaded = false;
		this.subenvironments = new Hashtable<Integer, Subenvironment>();
	}
	
	public static Ambient getInstance() {
		if (instance != null) return instance;
		else return new Ambient();
	}
	
	public static void freeInstance() {
		instance = null;
	}
	
	public Drawable getBackground() {
		if (!this.image.substring(this.image.length()-4).equals(".jpg")) {
			int id = Activa.myApp.getResources().getIdentifier("com.o2hlink.activa:drawable/" + this.image, null, null);
			return Activa.myApp.getResources().getDrawable(id);
		}
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = null;
		switch (Activa.myMobileManager.user.getType()) {
			case 0:
				userfolder = new File(originalfolder, FOLDER_PATIENT);
				break;
			case 1:
				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
				break;
			case 2:
				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
				break;
			default:
				break;
		}
		if (userfolder != null) {
			File ambient = new File(userfolder, this.name);
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
	
	public static File[] getAmbientList () {
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = null;
		switch (Activa.myMobileManager.user.getType()) {
			case 0:
				userfolder = new File(originalfolder, FOLDER_PATIENT);
				break;
			case 1:
				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
				break;
			case 2:
				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
				break;
			default:
				break;
		}
		if (userfolder != null) {
			return userfolder.listFiles();
		}
		else return null;
	}
	
	public boolean getFirstEnvironment() {
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = null;
		switch (Activa.myMobileManager.user.getType()) {
			case 0:
				userfolder = new File(originalfolder, FOLDER_PATIENT);
				break;
			case 1:
				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
				break;
			case 2:
				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
				break;
			default:
				break;
		}
		if (userfolder != null) {
			File[] ambients = userfolder.listFiles();
			int numsubamb = 0;
			File ambient = ambients[0];
			this.name = ambient.getName();
			File configuration;
			String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
			if (selectedLang == null) {
				//default is English
				configuration = new File(ambient, FILE_CONFIG_ENG);
			}
			else {
				if(selectedLang.equals("es")) {
					configuration = new File(ambient, FILE_CONFIG_SPA);
				}
				else if(selectedLang.equals("en")) {
					configuration = new File(ambient, FILE_CONFIG_ENG);
				}
				else {
					//default is English
					configuration = new File(ambient, FILE_CONFIG_ENG);
				}
			}
			String xml = "";
			InputStream fIn;
			try {
				fIn = new FileInputStream(configuration);
		        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
		        char[] inputBuffer = new char[255];   
		        int bytesRead = isr.read(inputBuffer);
		        while (bytesRead != -1) {
		        	String toadd = new String(inputBuffer);
		        	if (bytesRead < 255) toadd = toadd.substring(0, bytesRead);
		        	xml += toadd;
		        	bytesRead = isr.read(inputBuffer);
		        }
				XmlPullParserFactory factory;
				factory = XmlPullParserFactory.newInstance();
			    factory.setNamespaceAware(true);
			    XmlPullParser info = factory.newPullParser();
			    info.setInput(new StringReader(xml));
			    int event = info.getEventType();
			    while (event != XmlPullParser.END_DOCUMENT) {
			    	if(event == XmlPullParser.START_DOCUMENT) {
			    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
			        } else if(event == XmlPullParser.START_TAG) {
			        	if (info.getName().equalsIgnoreCase("AMBIENT")) {
			        		numsubamb = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"SUBENVIRONMENTS"));
			        		this.image = info.getAttributeValue(info.getNamespace(),"FILE");
			        		this.width = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
			        		this.height = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
			            } else if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
			        		int id = this.subenvironments.size();
			        		this.subenvironments.put(id, Subenvironment.getSubenvironment(info, id, this.name));
			            }
			        } 
			        else if(event == XmlPullParser.END_TAG) {
		            	if (info.getName().equalsIgnoreCase("AMBIENT")) {
		            		if (this.subenvironments.size() != numsubamb) {
		            			this.loaded = false;
		            			return false;
		            		}
		            	}
		            }
		            event = info.next();
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				this.loaded = false;
        		return false;
			} catch (IOException e) {
				e.printStackTrace();
				this.loaded = false;
        		return false;
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				this.loaded = false;
        		return false;
			}
			this.loaded = true;
		    return true;
		}
		else {
			this.loaded = false;
			return false;
		}
	}
	
	public boolean getEnvironment(String name) {
		if (name == null) return false;
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = null;
		switch (Activa.myMobileManager.user.getType()) {
			case 0:
				userfolder = new File(originalfolder, FOLDER_PATIENT);
				break;
			case 1:
				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
				break;
			case 2:
				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
				break;
			default:
				break;
		}
		if (userfolder != null) {
			File[] ambients = userfolder.listFiles();
			File ambient = null;
			if (ambients == null) ambients = new File[0];
			for (int i = 0; i < ambients.length; i++) {
				if (ambients[i].getName().equals(name)) {
					ambient = ambients[i];
					break;
				}
			}
			if (ambient == null) return false;
			int numsubamb = 0;
			this.name = ambient.getName();
			File configuration;
			String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
			if (selectedLang == null) {
				//default is English
				configuration = new File(ambient, FILE_CONFIG_ENG);
			}
			else {
				if(selectedLang.equals("es")) {
					configuration = new File(ambient, FILE_CONFIG_SPA);
				}
				else if(selectedLang.equals("en")) {
					configuration = new File(ambient, FILE_CONFIG_ENG);
				}
				else {
					//default is English
					configuration = new File(ambient, FILE_CONFIG_ENG);
				}
			}
			String xml = "";
			InputStream fIn;
			try {
				fIn = new FileInputStream(configuration);
		        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
		        char[] inputBuffer = new char[255];   
		        int bytesRead = isr.read(inputBuffer);
		        while (bytesRead != -1) {
		        	String toadd = new String(inputBuffer);
		        	if (bytesRead < 255) toadd = toadd.substring(0, bytesRead);
		        	xml += toadd;
		        	bytesRead = isr.read(inputBuffer);
		        }
				XmlPullParserFactory factory;
				factory = XmlPullParserFactory.newInstance();
			    factory.setNamespaceAware(true);
			    XmlPullParser info = factory.newPullParser();
			    info.setInput(new StringReader(xml));
			    int event = info.getEventType();
			    while (event != XmlPullParser.END_DOCUMENT) {
			    	if(event == XmlPullParser.START_DOCUMENT) {
			    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
			        } else if(event == XmlPullParser.START_TAG) {
			        	if (info.getName().equalsIgnoreCase("AMBIENT")) {
			        		numsubamb = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"SUBENVIRONMENTS"));
			        		this.image = info.getAttributeValue(info.getNamespace(),"FILE");
			        		if (!(Drawable.createFromStream(new FileInputStream(new File(ambient, this.image)), "src") instanceof Drawable)) return false;
			        		this.width = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
			        		this.height = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
			            } else if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
			        		int id = this.subenvironments.size();
			        		this.subenvironments.put(id, Subenvironment.getSubenvironment(info, id, this.name));
			            }
			        } 
			        else if(event == XmlPullParser.END_TAG) {
		            	if (info.getName().equalsIgnoreCase("AMBIENT")) {
		            		if (this.subenvironments.size() != numsubamb) {
		            			this.loaded = false;
		            			return false;
		            		}
		            	}
		            }
		            event = info.next();
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				this.loaded = false;
        		return false;
			} catch (IOException e) {
				e.printStackTrace();
				this.loaded = false;
        		return false;
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				this.loaded = false;
        		return false;
			}
			this.loaded = true;
		    return true;
		}
		else {
			this.loaded = false;
			return false;
		}
	}
	
	public boolean getDefaultEnvironment() {
		InputStream fIn = null;       
		int numsubamb = 0;
        File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
		switch (Activa.myMobileManager.user.getType()) {
			case 1:
				if (selectedLang == null) {
					//default is English
					fIn = Activa.myApp.getResources().openRawResource(R.raw.clinenvironmenteng);
				}
				else {
					if(selectedLang.equals("es")) {
						fIn = Activa.myApp.getResources().openRawResource(R.raw.clinenvironmentspa);
					}
					else if(selectedLang.equals("en")) {
						fIn = Activa.myApp.getResources().openRawResource(R.raw.clinenvironmenteng);
					}
					else {
						//default is English
						fIn = Activa.myApp.getResources().openRawResource(R.raw.clinenvironmenteng);
					}
				}
				break;
			case 2:
				if (selectedLang == null) {
					//default is English
					fIn = Activa.myApp.getResources().openRawResource(R.raw.resenvironmenteng);
				}
				else {
					if(selectedLang.equals("es")) {
						fIn = Activa.myApp.getResources().openRawResource(R.raw.resenvironmentspa);
					}
					else if(selectedLang.equals("en")) {
						fIn = Activa.myApp.getResources().openRawResource(R.raw.resenvironmenteng);
					}
					else {
						//default is English
						fIn = Activa.myApp.getResources().openRawResource(R.raw.resenvironmenteng);
					}
				}
				break;
			case 0:
			default:
				if (selectedLang == null) {
					//default is English
					fIn = Activa.myApp.getResources().openRawResource(R.raw.patenvironmenteng);
				}
				else {
					if(selectedLang.equals("es")) {
						fIn = Activa.myApp.getResources().openRawResource(R.raw.patenvironmentspa);
					}
					else if(selectedLang.equals("en")) {
						fIn = Activa.myApp.getResources().openRawResource(R.raw.patenvironmenteng);
					}
					else {
						//default is English
						fIn = Activa.myApp.getResources().openRawResource(R.raw.patenvironmenteng);
					}
				}
				break;
		}
        try {         
        	InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");   
    		String xml = "";
    		char[] inputBuffer = new char[255];   
		    int bytesRead = isr.read(inputBuffer);
		    while (bytesRead != -1) {
		   	String toadd = new String(inputBuffer);
		   	if (bytesRead < 255) toadd = toadd.substring(0, bytesRead);
		   	xml += toadd;
		     	bytesRead = isr.read(inputBuffer);
		    }
		    XmlPullParserFactory factory;
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser info = factory.newPullParser();
			info.setInput(new StringReader(xml));
			int event = info.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
			   	if(event == XmlPullParser.START_DOCUMENT) {
			   	} else if(event == XmlPullParser.END_DOCUMENT) {    	
			    } else if(event == XmlPullParser.START_TAG) {
			      	if (info.getName().equalsIgnoreCase("AMBIENT")) {
			       		numsubamb = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"SUBENVIRONMENTS"));
			       		this.image = info.getAttributeValue(info.getNamespace(),"FILE");
			       		this.width = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
			       		this.height = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
			        } else if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
			       		int id = this.subenvironments.size();
			       		Subenvironment sub = Subenvironment.getSubenvironment(info, id, this.name);
			       		this.subenvironments.put(id, sub);
			        }
			   } 
			   else if(event == XmlPullParser.END_TAG) {
		           	if (info.getName().equalsIgnoreCase("AMBIENT")) {
		           		if (this.subenvironments.size() != numsubamb) {
		           			this.loaded = false;
		           			return false;
		           		}
		           	}
		       }
		       event = info.next();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.loaded = false;
	    	return false;
		} catch (IOException e) {
			e.printStackTrace();
			this.loaded = false;
	    	return false;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			this.loaded = false;
	    	return false;
		}
		this.loaded = true;
		return true;
	}
	
	public static boolean checkEnvironment(String name) {
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = null;
		switch (Activa.myMobileManager.user.getType()) {
			case 0:
				userfolder = new File(originalfolder, FOLDER_PATIENT);
				break;
			case 1:
				userfolder = new File(originalfolder, FOLDER_CLINICIAN);
				break;
			case 2:
				userfolder = new File(originalfolder, FOLDER_RESEARCHER);
				break;
			default:
				break;
		}
		if (userfolder != null) {
			File[] ambients = userfolder.listFiles();
			File ambient = null;
			for (int i = 0; i < ambients.length; i++) {
				if (ambients[i].getName().equals(name)) {
					ambient = ambients[i];
					break;
				}
			}
			int numsubamb = 0, subambcount = 0;
			File configuration;
			String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
			if (selectedLang == null) {
				//default is English
				configuration = new File(ambient, FILE_CONFIG_ENG);
			}
			else {
				if(selectedLang.equals("es")) {
					configuration = new File(ambient, FILE_CONFIG_SPA);
				}
				else if(selectedLang.equals("en")) {
					configuration = new File(ambient, FILE_CONFIG_ENG);
				}
				else {
					//default is English
					configuration = new File(ambient, FILE_CONFIG_ENG);
				}
			}
			String xml = "";
			InputStream fIn;
			try {
				fIn = new FileInputStream(configuration);
		        InputStreamReader isr = new InputStreamReader(fIn,"UTF-8");          
		        char[] inputBuffer = new char[255];   
		        int bytesRead = isr.read(inputBuffer);
		        while (bytesRead != -1) {
		        	String toadd = new String(inputBuffer);
		        	if (bytesRead < 255) toadd = toadd.substring(0, bytesRead);
		        	xml += toadd;
		        	bytesRead = isr.read(inputBuffer);
		        }
				XmlPullParserFactory factory;
				factory = XmlPullParserFactory.newInstance();
			    factory.setNamespaceAware(true);
			    XmlPullParser info = factory.newPullParser();
			    info.setInput(new StringReader(xml));
			    int event = info.getEventType();
			    while (event != XmlPullParser.END_DOCUMENT) {
			    	if(event == XmlPullParser.START_DOCUMENT) {
			    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
			        } else if(event == XmlPullParser.START_TAG) {
			        	if (info.getName().equalsIgnoreCase("AMBIENT")) {
			        		numsubamb = Integer.parseInt(info.getAttributeValue(info.getNamespace(),"SUBENVIRONMENTS"));
			        		String file = info.getAttributeValue(info.getNamespace(),"FILE");
			        		if (file == null) return false;
			        		if (!(Drawable.createFromStream(new FileInputStream(new File(ambient, file)), "src") instanceof Drawable)) return false;
			        		if (!(Drawable.createFromStream(new FileInputStream(new File(ambient, "mini.jpg")), "src") instanceof Drawable)) return false;
			        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"WIDTH"));
			        		Integer.parseInt(info.getAttributeValue(info.getNamespace(),"HEIGHT"));
			            } else if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
			        		if (Subenvironment.checkSubenvironment(info, ambient)) subambcount++;
			        		else return false;
			            }
			        } 
			        else if(event == XmlPullParser.END_TAG) {
		            	if (info.getName().equalsIgnoreCase("AMBIENT")) {
		            		if (subambcount != numsubamb) {
		            			return false;
		            		}
		            	}
		            }
		            event = info.next();
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
        		return false;
			} catch (IOException e) {
				e.printStackTrace();
        		return false;
			} catch (XmlPullParserException e) {
				e.printStackTrace();
        		return false;
			}
		    return true;
		}
		else {
			return false;
		}
	}
	
	public static HashSet<String> getFilesForDownloading(String url) {
		HashSet<String> returned = new HashSet<String>();
		String[] xml = {Activa.myProtocolManager.readOnlineFile(url + "/" + FILE_CONFIG_SPA),
		                Activa.myProtocolManager.readOnlineFile(url + "/" + FILE_CONFIG_ENG)};
		for (int i = 0; i < xml.length; i++) {
			try {
				XmlPullParserFactory factory;
				factory = XmlPullParserFactory.newInstance();
			    factory.setNamespaceAware(true);
			    XmlPullParser info = factory.newPullParser();
			    info.setInput(new StringReader(xml[i]));
			    int event = info.getEventType();
			    while (event != XmlPullParser.END_DOCUMENT) {
			    	if(event == XmlPullParser.START_DOCUMENT) {
			    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
			        } else if(event == XmlPullParser.START_TAG) {
			        	if (info.getName().equalsIgnoreCase("AMBIENT")) {
			        		String file = info.getAttributeValue(info.getNamespace(),"FILE");
			        		returned.add(file);
			        		returned.add("mini.jpg");
			        		returned.add(FILE_CONFIG_ENG);
			        		returned.add(FILE_CONFIG_SPA);
			        	} else if (info.getName().equalsIgnoreCase("SUBENVIRONMENT")) {
			        		HashSet<String> subfiles = Subenvironment.getFilesForDownloading(info);
			        		if (subfiles == null) return null;
			        		returned.addAll(subfiles);
			            }
			        } 
			        else if(event == XmlPullParser.END_TAG) {
		            	if (info.getName().equalsIgnoreCase("AMBIENT")) {
		            		break;
		            	}
		            }
		            event = info.next();
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
        		return null;
			} catch (IOException e) {
				e.printStackTrace();
        		return null;
			} catch (XmlPullParserException e) {
				e.printStackTrace();
        		return null;
			}
		}
		return returned;
	}
	
	public static ArrayList<String[]> getAmbientsForDownloading() {
		ArrayList<String[]> returned = new ArrayList<String[]>();
		String xml = null;
		switch (Activa.myMobileManager.user.getType()) {
			case 0:
				xml = Activa.myProtocolManager.readOnlineFile(ONLINE_ROOMS_PATIENT);
				break;
			case 1:
				xml = Activa.myProtocolManager.readOnlineFile(ONLINE_ROOMS_CLINICIAN);
				break;
			case 2:
				xml = Activa.myProtocolManager.readOnlineFile(ONLINE_ROOMS_RESEARCHER);
				break;
			default:
				break;
		}
		try {
			if (xml == null) return returned;
			XmlPullParserFactory factory;
			factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(true);
		    XmlPullParser info = factory.newPullParser();
		    info.setInput(new StringReader(xml));
		    int event = info.getEventType();
		    while (event != XmlPullParser.END_DOCUMENT) {
		    	if(event == XmlPullParser.START_DOCUMENT) {
		    	} else if(event == XmlPullParser.END_DOCUMENT) {    	
		        } else if(event == XmlPullParser.START_TAG) {
		        	if (info.getName().equalsIgnoreCase("AMBIENT")) {
		        		String[] element = new String[3];
		        		element[0] = info.getAttributeValue(info.getNamespace(),"NAME");
		        		element[1] = info.getAttributeValue(info.getNamespace(),"URL"); 
		        		element[2] = info.getAttributeValue(info.getNamespace(),"PRICE"); 
		        		returned.add(element);
		        	} 
		        } 
		        else if(event == XmlPullParser.END_TAG) {
	            	if (info.getName().equalsIgnoreCase("AMBIENTS")) {
	            		break;
	            	}
	            }
	            event = info.next();
		    }
		    return returned;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
    		return null;
		} catch (IOException e) {
			e.printStackTrace();
    		return null;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
    		return null;
		}
	}
	
}
