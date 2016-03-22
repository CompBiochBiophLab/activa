package com.o2hlink.pimtools.ui.widget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Xml;

import com.o2hlink.pimtools.R;
import com.o2hlink.pimtools.Activa;
import com.o2hlink.pimtools.ActivaConfig;

public class Ambient {
	
	public static final String FOLDER_PATIENT = "Patient";
	public static final String FOLDER_CLINICIAN = "Clinician";
	public static final String FOLDER_RESEARCHER = "Researcher";
	public static final String FOLDER_MINIMAGES = "Images";
	public static final String FILE_CONFIG_SPA = "environmentspa.xml";
	public static final String FILE_CONFIG_ENG = "environmenteng.xml";
	public static final String FILE_DEMO_IMAGE = "mini.jpg";
//	public static final String ONLINE_ROOMS_PATIENT = "http://c0034480.cdn1.cloudfiles.rackspacecloud.com/mobileroomspatient.xml";
	public static final String ONLINE_ROOMS_PATIENT = "http://c0034480.cdn1.cloudfiles.rackspacecloud.com/mobileroomspatientdev.xml";
//	public static final String ONLINE_ROOMS_CLINICIAN = "http://c0034482.cdn1.cloudfiles.rackspacecloud.com/mobileroomsclinician.xml";
	public static final String ONLINE_ROOMS_CLINICIAN = "http://c0034482.cdn1.cloudfiles.rackspacecloud.com/mobileroomscliniciandev.xml";
//	public static final String ONLINE_ROOMS_RESEARCHER = "http://c0034484.cdn1.cloudfiles.rackspacecloud.com/mobileroomsresearcher.xml";
	public static final String ONLINE_ROOMS_RESEARCHER = "http://c0034484.cdn1.cloudfiles.rackspacecloud.com/mobileroomsresearcherdev.xml";

	private static Ambient instance;
	
	public String name;
	
	public String image;
	
	public int height;
	public int width;
	
	public boolean loaded;
	
	public boolean sensorservice = false;
	
	public Hashtable<Integer, Subenvironment> subenvironments;

	public Hashtable<Integer, Widget> widgets;
	
	public Ambient () {
		this.loaded = false;
		this.subenvironments = new Hashtable<Integer, Subenvironment>();
		this.widgets = new Hashtable<Integer, Widget>();
		this.sensorservice = false;
	}
	
	public static Ambient getInstance() {
		if (instance != null) return instance;
		else return new Ambient();
	}
	
	public static void freeInstance() {
		instance = null;
	}
	
	public Drawable getBackground() {
		if (!((this.image.substring(this.image.length()-4).equals(".jpg"))||(this.image.substring(this.image.length()-4).equals(".png")))) {
			int id = Activa.myApp.getResources().getIdentifier("com.o2hlink.pimtools:drawable/" + this.image, null, null);
			return Activa.myApp.getResources().getDrawable(id);
		}
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
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
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
		if (userfolder != null) {
			return userfolder.listFiles();
		}
		else return null;
	}
	
	public static boolean ambientDownloaded(String ambientname) {
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
		File[] ambientlist = userfolder.listFiles();
		if (ambientlist == null) return false;
		if (ambientlist.length == 0) return false;
		for (int i = 0; i < ambientlist.length; i++) {
			if (ambientname.equals(ambientlist[i].getName())) return true;
		}
		return false;
		
	}
	
	public boolean getFirstEnvironment() {
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
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
			            } else if (info.getName().equalsIgnoreCase("WIDGET")) {
			        		int id = this.widgets.size();
			        		this.widgets.put(id, Widget.getWidget(info, id));
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
		String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
		if (name == null) return false;
		if (name.equals("default")) return getSavedDefaultEnvironment(selectedLang);
		else return getEnvironment(name, selectedLang);
	}
	
	public boolean getEnvironment(String name, String selectedLang) {
		try {
			if (name == null) return false;
			File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
			File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
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
				            } else if (info.getName().equalsIgnoreCase("WIDGET")) {
				        		int id = this.widgets.size();
				        		this.widgets.put(id, Widget.getWidget(info, id));
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
				this.checkSensorMonitoring();
			    return true;
			}
			else {
				this.loaded = false;
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean getDefaultEnvironment() {
        String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
        return getDefaultEnvironment(selectedLang);
	}
	
	public boolean getSavedDefaultEnvironment(String selectedLang) {
		try {
			File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
			File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
			if (userfolder != null) {
				File ambient = new File(userfolder, "default");
				if (ambient == null) return false;
				if (!ambient.exists()) return false;
				int numsubamb = 0;
				this.name = ambient.getName();
				File configuration;
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
					       		Subenvironment sub = Subenvironment.getSubenvironment(info, id, this.name);
					       		this.subenvironments.put(id, sub);
					        } else if (info.getName().equalsIgnoreCase("WIDGET")) {
				        		int id = this.widgets.size();
				        		this.widgets.put(id, Widget.getWidget(info, id));
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
				this.checkSensorMonitoring();
				return true;
			} else return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean getDefaultEnvironment(String selectedLang) {
		InputStream fIn = null;       
		int numsubamb = 0;
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
			        } else if (info.getName().equalsIgnoreCase("WIDGET")) {
		        		int id = this.widgets.size();
		        		this.widgets.put(id, Widget.getWidget(info, id));
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
		this.checkSensorMonitoring();
		return true;
	}
	
	public void checkSensorMonitoring() {
		Enumeration<Subenvironment> subs = this.subenvironments.elements();
		while (subs.hasMoreElements()) {
			Enumeration<Widget> widgets = subs.nextElement().widgets.elements();
			while (widgets.hasMoreElements()) {
				int function = widgets.nextElement().function;
				if (function == Widget.FUNCTION_SENSORS){
					this.sensorservice = true;
					return;
				}
			}
		}
		this.sensorservice = false;
	}
	
	public static boolean checkEnvironment(String name) {
		File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
		File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
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
		        		String[] element = new String[5];
		        		String selectedLang = Activa.myApp.getResources().getConfiguration().locale.getLanguage();
		    			if (selectedLang == null) {
		    				//default is English
			    			element[0] = info.getAttributeValue(info.getNamespace(),"NAMEENG");
			        		element[1] = info.getAttributeValue(info.getNamespace(),"URL"); 
			        		element[2] = info.getAttributeValue(info.getNamespace(),"PRICE"); 
			        		element[3] = info.getAttributeValue(info.getNamespace(),"EXTRAENG");
			        		element[4] = info.getAttributeValue(info.getNamespace(),"DESCENG");
		    			}
		    			else {
		    				if(selectedLang.equals("es")) {
				    			element[0] = info.getAttributeValue(info.getNamespace(),"NAMESPA");
				        		element[1] = info.getAttributeValue(info.getNamespace(),"URL"); 
				        		element[2] = info.getAttributeValue(info.getNamespace(),"PRICE"); 
				        		element[3] = info.getAttributeValue(info.getNamespace(),"EXTRASPA");
				        		element[4] = info.getAttributeValue(info.getNamespace(),"DESCSPA");
		    				}
		    				else if(selectedLang.equals("en")) {
				    			element[0] = info.getAttributeValue(info.getNamespace(),"NAMEENG");
				        		element[1] = info.getAttributeValue(info.getNamespace(),"URL"); 
				        		element[2] = info.getAttributeValue(info.getNamespace(),"PRICE"); 
				        		element[3] = info.getAttributeValue(info.getNamespace(),"EXTRAENG");
				        		element[4] = info.getAttributeValue(info.getNamespace(),"DESCENG");
		    				}
		    				else {
		    					//default is English
				    			element[0] = info.getAttributeValue(info.getNamespace(),"NAMEENG");
				        		element[1] = info.getAttributeValue(info.getNamespace(),"URL"); 
				        		element[2] = info.getAttributeValue(info.getNamespace(),"PRICE"); 
				        		element[3] = info.getAttributeValue(info.getNamespace(),"EXTRAENG");
				        		element[4] = info.getAttributeValue(info.getNamespace(),"DESCENG");
		    				}
		    			}
		    			if (element[0] == null) element[0] = info.getAttributeValue(info.getNamespace(),"NAME");
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
	
	public static int getNumberOfFilesForDownloading(String url) {
		return getFilesForDownloading(url).size();
	}
	
	public static int getNumberOfFilesDownloaded (String ambientname) {
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
		if (!userfolder.exists()) return 0;
		File ambientfolder = new File(userfolder, ambientname);
		if (!ambientfolder.exists()) return 0;
		return ambientfolder.list().length;
	}
	
	public String passEnvironmentToXML() {
		XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	       serializer.setOutput(writer);
	       serializer.startDocument("UTF-8", true);
	       serializer.startTag("", "AMBIENT");
	       serializer.attribute("", "FILE", this.image);
	       serializer.attribute("", "SUBENVIRONMENTS", Integer.toString(this.subenvironments.size()));
	       serializer.attribute("", "WIDTH", Integer.toString(this.width));
	       serializer.attribute("", "HEIGHT", Integer.toString(this.height));
	       Enumeration<Subenvironment> subs = this.subenvironments.elements();
	       while (subs.hasMoreElements()){
	    	   Subenvironment sub = subs.nextElement();
	           if (!sub.passSubenvironemntToXML(serializer)) return null;
	       }
	       Enumeration<Widget> wids = this.widgets.elements();
	       while (wids.hasMoreElements()){
	    	   Widget wid = wids.nextElement();
	           if (!wid.passSubenvironemntToXML(serializer)) return null;
	       }
	       serializer.endTag("", "AMBIENT");
	       serializer.endDocument();
	       String test = writer.toString();
	       return writer.toString();
	    } catch (Exception e) {
			e.printStackTrace();
	       return null;
	    } 
	}
	
	public boolean saveEnvironmentFiles(Ambient spanish, Ambient english) {
		try {
			File originalfolder = new File(Environment.getExternalStorageDirectory(), ActivaConfig.ENVIRONMENT_FOLDER);
			File userfolder = new File(originalfolder, Activa.myMobileManager.user.getName());
			File ambient = new File(userfolder, spanish.name);
			if (!ambient.exists()) ambient.mkdirs();
			File spanishfile = new File(ambient, FILE_CONFIG_SPA);
			if (!spanishfile.exists()) spanishfile.createNewFile();
			File englishfile = new File(ambient, FILE_CONFIG_ENG);
			if (!englishfile.exists()) englishfile.createNewFile();
    		FileOutputStream fout = new FileOutputStream(spanishfile);
    		OutputStreamWriter osw = new OutputStreamWriter(fout);
    		osw.write(spanish.passEnvironmentToXML());
    		osw.close();
    		fout.close();
			fout = new FileOutputStream(englishfile);
    		osw = new OutputStreamWriter(fout);
    		osw.write(english.passEnvironmentToXML());
    		osw.close();
    		fout.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean changeWidgetFunctionality(Integer subenvironmentID, Integer widgetID, int widgetNewFunction) {
		try {
			if ((Activa.myMobileManager.user.getAmbient() == null)||(Activa.myMobileManager.user.getAmbient().equals("null"))) {
				Activa.myMobileManager.user.setAmbient("default");
				Activa.myMobileManager.saveUsers();
			}
			if (subenvironmentID == -1) {
				this.widgets.get(widgetID).function = widgetNewFunction;
				this.widgets.get(widgetID).name = Activa.myLanguageManager.getFunctionName(widgetNewFunction);
			} else {
				this.subenvironments.get(subenvironmentID).widgets.get(widgetID).function = widgetNewFunction;
				this.subenvironments.get(subenvironmentID).widgets.get(widgetID).name = Activa.myLanguageManager.getFunctionName(widgetNewFunction);
			}
			Ambient spanish = new Ambient();
			if (Activa.myMobileManager.user.getAmbient().equals("default")) {
				spanish.getDefaultEnvironment("es");
				spanish.name = "default";
			}
			else spanish.getEnvironment(Activa.myMobileManager.user.getAmbient(), "es");
			if (subenvironmentID == -1) {
				spanish.widgets.get(widgetID).function = widgetNewFunction;
				spanish.widgets.get(widgetID).name = Activa.myLanguageManager.getFunctionName(widgetNewFunction);
			} else {
				spanish.subenvironments.get(subenvironmentID).widgets.get(widgetID).function = widgetNewFunction;
				spanish.subenvironments.get(subenvironmentID).widgets.get(widgetID).name = Activa.myLanguageManager.getFunctionName(widgetNewFunction);
			}
			Ambient english = new Ambient();
			if (Activa.myMobileManager.user.getAmbient().equals("default")) {
				english.getDefaultEnvironment("en");
				english.name = "default";
			}
			else english.getEnvironment(Activa.myMobileManager.user.getAmbient(), "en");
			if (subenvironmentID == -1) {
				english.widgets.get(widgetID).function = widgetNewFunction;
				english.widgets.get(widgetID).name = Activa.myLanguageManager.getFunctionName(widgetNewFunction);
			} else {
				english.subenvironments.get(subenvironmentID).widgets.get(widgetID).function = widgetNewFunction;
				english.subenvironments.get(subenvironmentID).widgets.get(widgetID).name = Activa.myLanguageManager.getFunctionName(widgetNewFunction);
			}
			saveEnvironmentFiles(spanish, english);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Integer getSubenvironmentID (Subenvironment sub) {
		if (sub == null) return -1;
		Enumeration<Integer> keys = this.subenvironments.keys();
		while (keys.hasMoreElements()) {
			Integer key = keys.nextElement();
			if (this.subenvironments.get(key).equals(sub)) return key;
		}
		return -1;
	}
	
	public Integer getWidgetID (Widget wid) {
		Enumeration<Integer> keys = this.widgets.keys();
		while (keys.hasMoreElements()) {
			Integer key = keys.nextElement();
			if (this.widgets.get(key).equals(wid)) return key;
		}
		return -1;
	}
	
}