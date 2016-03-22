package com.o2hlink.pimtools;

//import com.o2hlink.activa.driver.Glucose;

/**
 * @author Adrian Rejas
 * 
 * This class defines a set of configuration options for the application.
*/

public class ActivaConfig
{
	/**
     * The version of the application.
     */
	public final static String VERSION  = "00";

	/**
     * The country of the application.
     */
    public static String COUNTRY = "es-ES";

    /**
     * The set of characters for the application.
     */
    public static String CHARSET  = "ISO8859_1";

    /**
     * The Connection protocol for connecting to server.
     */
    public final static String CONNECTION_PROTOCOL = "http";

    /**
     * The IP addess or DNS name associated with the main server.
     */
//    public final static String SERVERIP = "174.143.202.184";
    public final static String SERVERIP = "www.o2hlink.com";

    /**
     * <p>The port listened by the server.
     */
//    public final static int SERVERPORT = 8081;
    public final static int SERVERPORT = 80;

    /**
     * The path to the web portal inside the server
     */
    public final static String SERVERPATH = "/patient/mobile";

    /**
     * Serial key: "* # 0 6 *".
     */
    public final static int[] KEYCODE = {42, 35, 48, 54, 42};

    /**
     * The number of serial key.
     */
    public final static int KEYNUM = KEYCODE.length+1;

    /**
     * The valid time of input serial key.
     */
    public final static int TIMEOUT = (KEYCODE.length)*1500;

    /**
     * The interval time between this time and last time.
     */
    public final static int KEY_INTERVALTIME = 1500;

    /**
     * The max number of char in a line.
     */
    public final static int CHAR_MAXNUM_LINE = 21;

    /**
     * The max number of char in a line using a medium sized font.
     */
    public final static int CHAR_MAXNUM_LINE_MFONT = 27;

    /**
     * The max length of user ID (Patient, VN and Tech).
     */
    public final static int ID_MAX_LENGTH = 10;

    /**
     * The max length of password (VN and Tech).
     */
    public final static int PASSWORD_MAX_LENGTH = 18;

    //#if polish.Identifier == 208x320/A1000
    /**
     * Is the heartbeat signal enabled?
     * <p>Default is disabled, however it can be enabled through the jad file.
     */
    public static boolean HEARTBEAT_ENABLED = false;

    /**
     * The heartbeat signal interval in ms.
     */
    public final static int HEARTBEAT_INTERVAL = 3000;

    /**
     * The heartbeat signal port.
     */
    public final static int HEARTBEAT_PORT = 80;
    //#endif
    /**
     * Is the autorestart enabled?
     * <p>Default is disabled, however it can be enabled through the jad file.
     */
    public static boolean AUTORESTART_ENABLED = false;

    /**
     * The autorestart interval in ms.
     */
    public static final int AUTORESTART_INTERVAL = 60000;

    /**
     * The unit used by the glucometer to display the glucose measurements.
     */
    //public static String GLUCOMETER_UNIT = Glucose.US_UNIT;
    
    /**
     * The language used for showing messages to the User.
     */
    public static String LANGUAGE = "ES";

    /**
     * Number of ms. that is displayed a splash screen.
     */
    public static final int SPLASH_SCREEN_DELAY = 2000;

    /**
     * Code received by onActivityResult at enabling bluetooth.
     */
    public static final int REQUEST_ENABLE_BT = 122;
    
    /**
     * The URL pointing to the web services.
     */
    public static String SERVICES_URL = "http://www.activacentral.com/patient/activ8pat/";
//    public static String SERVICES_URL = "http://www.o2hlink.com/patient/activ8pat/";
    
    /**
     * Number of hours showed at schedule.
     */
    public static int SCHEDULE_HOURS = 18;
    
    /**
     * Time between updates at milliseconds. The half of this time serves also as 
     * the time you can execute a calendar event before the programmed time.
     */
    public static long UPDATES_TIMEOUT = 3600000;
    
    /**
     * Time to wait for considering an event as definitively cancelled.
     */
    public static long EVENT_CANCELLING_TIMEOUT = 86400000;
    
    /**
     * Time in number of days between user's updatings.
     */
    public static long USERS_UPDATING = 13;
    
    /**
     * Folder for storing the configuration files.
     */
    public static String FILES_FOLDER = "PimTools";
    
    /**
     * Folder for storing the environment files.
     */
    public static String ENVIRONMENT_FOLDER = "PimTools_Environments";
    
    /**
     * Folder for storing the environment files.
     */
    public static String PAYPAL_USA_ACCOUNT = "wlalinde@o2hlink.com";

    public static String PAYPAL_SPAIN_ACCOUNT = "o2h@o2hlink.com";
    
    /**
     * URL of the promotions configuration.
     */
    public static String PROMOTION_URL = "http://c224634.r34.cf1.rackcdn.com/promotioncentral.txt";
    
    /**
     * URL of the promotions configuration.
     */
//    public static String ACTIV8YOUTUBE_URL = "http://www.youtube.com/user/Activ8Mobile";

    public static String ACTIV8YOUTUBE_URL = "http://m.youtube.com/#/profile?desktop_uri=%2Fuser%2Factivacentral&sort=p&start=0&livestreaming_tutorial=False&ytsession={}&channel_id=0&user=activacentral";
    
    /**
     * Mail account for supporting.
     */
    public static String ACTIV8SUPPORT_URL = "activa-support@o2hlink.com";
    
    /**
     * URL of the promotions configuration.
     */
    public static String ACTIV8FACEBOOK_URL = "http://www.facebook.com/pages/ActivaCentral/129769057100786";
    
    /**
     * URL of the promotions configuration.
     */
    public static String ACTIV8TERMSANDCONDS_URL = "http://www.activacentral.com/prueba.html?content=Terms";
    public static String ACTIV8PRIVACYCONDS_URL = "http://www.activacentral.com/prueba.html?content=Privacy";
    
    public static String ACTIV8CALLBACKFORACCOUNTS = "http://www.activacentral.com/patient/oauth";
//    public static String ACTIV8CALLBACKFORACCOUNTS = "http://www.o2hlink.com/patient/oauth";
   
}
