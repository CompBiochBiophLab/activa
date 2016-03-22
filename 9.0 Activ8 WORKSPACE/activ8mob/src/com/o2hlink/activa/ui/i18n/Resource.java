package com.o2hlink.activa.ui.i18n;

import com.o2hlink.activa.Activa;

/**
 * 
 */

public abstract class Resource
{
	/**
	 * LOGIN MESSAGES
	 */
	public String PSW_REQUEST;
	public String PSW_REQUEST_REPEAT;
	public String PSW_REG_TITLE;
	public String PSW_REG_TITLE_UPDATE;
	public String PSW_REG_REQUEST;
	public String PSW_REG_DATAREQUEST;
	public String PSW_REG_USERNAME;
	public String PSW_REG_PASSWORD;
	public String PSW_REG_PASSWORD_AGAIN;
	public String PSW_REG_FIRSTNAME;
	public String PSW_REG_LASTNAME;
	public String PSW_REG_DATE;
	public String PSW_REG_MALE;
	public String PSW_REG_HEIGHT;
	public String PSW_REG_WEIGHT;
	public String PSW_REG_FEMALE;
	public String PSW_INFO_USER;
	public String PSW_INFO_REGISTERED;
	public String PSW_REG_BADDATA;
	public String PSW_REG_NOTMATCH;
	public String PSW_REG_PATIENT;
	public String PSW_REG_CLINICIAN;
	public String PSW_REG_RESEARCHER;
	
	/**
	 * MAIN RESOURCES
	 */
	public int MAIN_QUEST;
	public int MAIN_PROGRAMS;
	public int MAIN_MAP;
	public int MAIN_ACTIVITIES;
	public int MAIN_MESSAGES;
	public int MAIN_SCHED;
	public int MAIN_NEWS;
	public int MAIN_NOTES;
	public String MAIN_FORBIDDEN;
	public String MAIN_REFRESHING;
	public String MAIN_NOTATDEMO;
	public String MAIN_NOTAVAILABLE;
	public String MAIN_SOCIAL;
	public String MAIN_MUSIC;
	public String MAIN_VIDEOS;
	public String MAIN_PICTURES;
	public String MAIN_PICTGAL;
	public String MAIN_VIDGAL;
	public String MAIN_PICCAM;
	public String MAIN_VIDCAM;
	public String MAIN_MUSICGAL;
	public String MAIN_SMSMESSAGES;
	public String MAIN_MAIL;
	public String MAIN_SELECT_ENVIRONMENT;
	public String MAIN_DOWNLOAD_ENVIRONMENT;
	public String MAIN_DEFAULT_ENVIRONMENT_LOAD;
	public String MAIN_AMBIENT_NOTLOADED;
	public String MAIN_LOAD_DEFAULT_AMBIENT;
	public String MAIN_SELECT_AMBIENT;
	
	/**
	 * MONTHS OF YEAR
	 */
	public String MONTH_JANUARY;
	public String MONTH_FEBRUARY;
	public String MONTH_MARCH;
	public String MONTH_APRIL;
	public String MONTH_MAY;
	public String MONTH_JUNE;
	public String MONTH_JULY;
	public String MONTH_AUGUST;
	public String MONTH_SEPTEMBER;
	public String MONTH_OCTOBER;
	public String MONTH_NOVEMBER;
	public String MONTH_DECEMBER;
	
	/**
	 * RESUMED DAYS OF THE WEEK
	 */
	public String WEEK_MONDAY;
	public String WEEK_TUESDAY;
	public String WEEK_WEDNESDAY;
	public String WEEK_THURSDAY;
	public String WEEK_FRYDAY;
	public String WEEK_SATURDAY;
	public String WEEK_SUNDAY;

	/**
	 * NEWS
	 */
	public String NEWS_TITLE;
	public String NEWS_LOADING;

	/**
	 * NOTES
	 */
	public String NOTES_TITLE;
	
	/**
	 * QUESTIONNAIRES
	 */
	public String QUEST_TITLE;
	public String QUEST_START;
	public String QUEST_WEIGHT_NOTDONE;
	public String QUEST_WEIGHT_DONE;
	public String QUEST_WEIGHT_0;
	public String QUEST_WEIGHT_1;
	public String QUEST_WEIGHT_2;
	public String BORG_0;
	public String BORG_05;
	public String BORG_1;
	public String BORG_2;
	public String BORG_3;
	public String BORG_4;
	public String BORG_5;
	public String BORG_7;
	public String BORG_9;
	public String BORG_10;
	public String QUEST_NOTVALID;
	
	/**
	 * SENSORS
	 */
	public String SENSORS_TITLE;
	public String SENSORS_HELP;
	public String SENSORS_CANCELLED;
	public String SENSORS_SEARCHING;
	public String SENSORS_READING;
	public String SENSORS_WAITING_DATA;
	public String SENSORS_OBTAINING_DATA;
	public String SENSORS_WAITING;
	public String SENSORS_NOBLUETOOTH;
	public String SENSORS_BLUETOOTH_NOTCONNECTED;
	public String SENSORS_BLUETOOTH_CONNECTING;
	public String SENSORS_BLUETOOTH_CONFIG;
	public String SENSORS_BLUETOOTH_NOTCONNECTION;
	public String SENSORS_BLUETOOTH_NOTPAIRED;
	public String SENSORS_BLUETOOTH_NOTMEASURED;
	public String SENSORS_BLUETOOTH_NOTCONFIG;
	public String SENSORS_BLUETOOTH_BADMEAS;
	public String SENSORS_EXERCISE_MESSAGE2;
	public String SENSORS_EXERCISE_MESSAGE1;
	public String SENSORS_EXERCISE_MESSAGE0;
	public String SENSORS_EXERCISE_MESSAGE2_OLD;
	public String SENSORS_EXERCISE_MESSAGE1_OLD;
	public String SENSORS_EXERCISE_MESSAGE0_OLD;
	public String SENSORS_EXERCISE_HEARTRATE;
	public String SENSORS_EXERCISE_O2SAT;
	public String SENSORS_EXERCISE_STEPSTOP;
	public String SENSORS_EXERCISE_BADMESSAGE;
	public String SENSORS_EXERCISE_CHECKTRACK;
	public String SENSORS_EXERCISE_TRACKLENGTH;
	public String SENSORS_EXERCISE_TRACKTIMES;
	public String SENSORS_PULSEOXI_TITLE;
	public String SENSORS_PULSEOXI_MESSAGE2;
	public String SENSORS_PULSEOXI_MESSAGE1;
	public String SENSORS_PULSEOXI_MESSAGE0;
	public String SENSORS_ZEPHYR_TITLE;
	public String SENSORS_ZEPHYR_MESSAGE2;
	public String SENSORS_ZEPHYR_MESSAGE1;
	public String SENSORS_ZEPHYR_MESSAGE0;
	public String SENSORS_SPIRO_TITLE;
	public String SENSORS_SPIRO_MESSAGE2;
	public String SENSORS_SPIRO_MESSAGE1;
	public String SENSORS_SPIRO_MESSAGE0;
	public String SENSORS_SPIRO_FVCMESSAGE2;
	public String SENSORS_SPIRO_FVCMESSAGE1;
	public String SENSORS_SPIRO_FVCMESSAGE0;
	public String SENSORS_SPIRO_FEV1MESSAGE2;
	public String SENSORS_SPIRO_FEV1MESSAGE1;
	public String SENSORS_SPIRO_FEV1MESSAGE0;
	public String SENSORS_SPIRO_FLOWCHAT_LEGENDY;
	public String SENSORS_SPIRO_FLOWCHAT_LEGENDX;
	public String SENSORS_SPIRO_TIMECHAT_LEGENDY;
	public String SENSORS_SPIRO_TIMECHAT_LEGENDX;
	public String SENSORS_SPIRO_DATAREQUEST;
	public String SENSORS_SPIRO_DATANEEDED;
	public String SENSORS_SPIRO_DATAREPEATED;
	public String SENSORS_BLOODPRESS_TITLE;
	public String SENSORS_BLOODPRESS_MESSAGE0;
	public String SENSORS_BLOODPRESS_MESSAGE1;
	public String SENSORS_BLOODPRESS_MESSAGE2;
	public String SENSORS_BLOODPRESS_MESSAGE3;
	public String SENSORS_BLOODPRESS_MESSAGE4;
	public String SENSORS_BLOODPRESS_MESSAGE5;
	public String SENSORS_BLOODPRESS_MESSAGE6;
	public String SENSORS_BLOODPRESS_MESSAGE7;
	public String SENSORS_WEIGHT_TITLE;
	public String SENSORS_DATA_LACKOF;
	public String SENSORS_DATA_FEV1;
	public String SENSORS_DATA_FVC;
	public String SENSORS_DATA_PEF;
	public String SENSORS_DATA_IC;
	public String SENSORS_DATA_HR;
	public String SENSORS_DATA_HR_AVRG;
	public String SENSORS_DATA_HR_PEAK;
	public String SENSORS_DATA_HR_LOW;
	public String SENSORS_DATA_BR;
	public String SENSORS_DATA_SO2;
	public String SENSORS_DATA_SO2_AVRG;
	public String SENSORS_DATA_SO2_PEAK;
	public String SENSORS_DATA_SO2_LOW;
	public String SENSORS_DATA_INITIAL_DYS;
	public String SENSORS_DATA_INITIAL_FAT;
	public String SENSORS_DATA_FINAL_DYS;
	public String SENSORS_DATA_FINAL_FAT;
	public String SENSORS_DATA_TEMP;
	public String SENSORS_DATA_TIME_EXE;
	public String SENSORS_DATA_TIME_DATA;
	public String SENSORS_DATA_SYS_PRESS;
	public String SENSORS_DATA_DIA_PRESS;
	public String SENSORS_DATA_WEIGHT;
	public String SENSORS_DATA_STEPS;
	public String SENSORS_DATA_STOPS;
	public String SENSORS_DATA_DISTANCE;
	public String SENSORS_EXERCISE_TITLE;
	public String SENSORS_EXERCISE_TIME_ASKING;
	public String SENSORS_EXERCISE_TIME_FAILURE;
	public String SENSORS_RESULTSWORD;
	public String SENSORS_CONFIG;
	public String SENSORS_CALIBRATION_SELECT;
	public String SENSORS_CALIBRATION_SELECT_ULTRALOW;
	public String SENSORS_CALIBRATION_SELECT_VERYLOW;
	public String SENSORS_CALIBRATION_SELECT_LOW;
	public String SENSORS_CALIBRATION_SELECT_NORMAL;
	public String SENSORS_CALIBRATION_SELECT_HIGH;
	public String SENSORS_CALIBRATION_SELECT_VERYHIGH;
	public String SENSORS_CALIBRATION_SELECT_ULTRAHIGH;
	public String SENSORS_CALIBRATION_INSTRUCTIONS;
	public String SENSORS_CALIBRATION_DONE;
	
	/**
	 * USER AND CONNECTION RESOURCES
	 */
	public String USER_NOID;
	public String USER_DEFAULT;
	
	/**
	 * CALENDAR
	 */
	public String CALENDAR_TITLE;
	public String CALENDAR_WEEK;
	public String CALENDAR_OF;
	public String CALENDAR_EARLY;
	
	/**
	 * PROGRAMS
	 */
	public String PROGRAMS_TITLE;
	public String PROGRAMS_FINISH;
	public String PROGRAMS_EXERCISE_TITLE;
	public String PROGRAMS_EXERCISE_CANCELLED;
	public String PROGRAMS_WALKING_TITLE;
	public String PROGRAMS_WALKING_DESC;
	public String PROGRAMS_WALKING_BORGAIR;
	public String PROGRAMS_WALKING_BORGFATIGUE;
	public String PROGRAMS_WALKING_MESSAGE0;
	public String PROGRAMS_WALKING_MESSAGE1;
	public String PROGRAMS_WALKING_MESSAGE2;
	public String PROGRAMS_WALKING_MESSAGE3;
	public String PROGRAMS_WALKING_MESSAGE4;
	public String PROGRAMS_WALKING_ADVERT0;
	public String PROGRAMS_WALKING_ADVERT1;
	public String PROGRAMS_WALKING_ADVERT01;
	public String PROGRAMS_WALKING_ADVERT2;
	public String PROGRAMS_TEST_TITLE;
	public String PROGRAMS_TEST_DESC;
	public String PROGRAMS_TEST_ADVERTSPIRO;
	public String PROGRAMS_TEST_ADVERTEXERCISE;
	public String PROGRAMS_WIIACTIVE_TITLE;
	public String PROGRAMS_WIIACTIVE_DESC;
	public String PROGRAMS_WIIACTIVE_SELECTCHALLENGE;
	public String PROGRAMS_WIIACTIVE_CHALLENGE_EASY1;
	public String PROGRAMS_WIIACTIVE_CHALLENGE_EASY2;
	public String PROGRAMS_WIIACTIVE_CHALLENGE_NORMAL1;
	public String PROGRAMS_WIIACTIVE_CHALLENGE_NORMAL2;
	public String PROGRAMS_WIIACTIVE_CHALLENGE_HARD1;
	public String PROGRAMS_WIIACTIVE_CHALLENGE_HARD2;
	public String PROGRAMS_WIIACTIVE_ADVERT_SENSORWAITING;
	public String PROGRAMS_WIIACTIVE_ADVERT;
	public String PROGRAMS_WIIACTIVE_WARMING;
	public String PROGRAMS_WIIACTIVE_VOLLEYBALL;
	public String PROGRAMS_WIIACTIVE_SIDESADDLES;
	public String PROGRAMS_WIIACTIVE_SIDETOSIDE;
	public String PROGRAMS_WIIACTIVE_TENNIS;
	public String PROGRAMS_WIIACTIVE_LUNGES;
	public String PROGRAMS_WIIACTIVE_COOLING;
	public String PROGRAMS_WIIACTIVE_RACEKNEES;
	public String PROGRAMS_WIIACTIVE_LUNGEJUMPS;
	public String PROGRAMS_WIIACTIVE_SKATE;
	public String PROGRAMS_WIIACTIVE_RACE;
	public String PROGRAMS_WIIACTIVE_CARDIOBOXING;
	public String PROGRAMS_WIIACTIVE_BICEPSCURLS;
	public String PROGRAMS_WIIACTIVE_SIDELUNGES;
	public String PROGRAMS_WIIACTIVE_PADDLE;
	public String PROGRAMS_WIIACTIVE_SHOULDER;
	public String PROGRAMS_WIIACTIVE_ABDOMINAL;
	public String PROGRAMS_WIIACTIVE_FISTKNEES;
	public String PROGRAMS_WIIACTIVE_BACKKICKS;
	public String PROGRAMS_WIIACTIVE_RACEKICKS;
	public String PROGRAMS_WIIACTIVE_PADDLEKICKS;
	public String PROGRAMS_WIIACTIVE_KICKTRICEPS;
	public String PROGRAMS_WIIACTIVE_SHORTKICKS;
	public String PROGRAMS_WIIACTIVE_INVERSELUNGES;
	public String PROGRAMS_WIIACTIVE_BASEBALL;
	public String PROGRAMS_WIIACTIVE_SIDESADDLESJUMPS;
	public String PROGRAMS_WIIACTIVE_LUNGESKNEES;
	
	/**
	 * MESSAGES
	 */
	public String MESSAGES_TITLE;
	public String MESSAGES_SENDER;
	public String MESSAGES_RECEIVER;
	public String MESSAGES_TOPIC;
	public String MESSAGES_DATE;
	public String MESSAGES_CONTACT_REQUEST;
	public String MESSAGES_ERROR_RECEIVER;
	public String MESSAGES_ERASE_RECEPTOR;
	
	/**
	 * CONNECTION
	 */
	public String CONNECTION_CHECKING;
	public String CONNECTION_CONNECTING;
	public String CONNECTION_REGISTERING;
	public String CONNECTION_QUESTIONNAIRES;
	public String CONNECTION_CALENDAR;
	public String CONNECTION_MAP;
	public String CONNECTION_NEWS;
	public String CONNECTION_HISTORY;
	public String CONNECTION_FEEDS;
	public String CONNECTION_NOTES;
	public String CONNECTION_CONTACTS;
	public String CONNECTION_MESSAGES;
	public String CONNECTION_PATIENTS;
	public String CONNECTION_UPDATING;
	public String CONNECTION_PENDING;
	public String CONNECTION_CONNECTED1;
	public String CONNECTION_CONNECTED2;
	public String CONNECTION_FAILED;
	public String CONNECTION_NOTE_SEND;
	public String CONNECTION_MESSAGE_NOTSENT;
	public String CONNECTION_MESSAGE_PENDING;
	public String CONNECTION_SENDING;
	public String CONNECTION_PROBLEM;
	/**
	 * NOTIFICATION
	 */
	public String NOTIFICATION_TITLE;
	public String NOTIFICATION_TIMETO;
	public String NOTIFICATION_FORGOTTEN;
	public String NOTIFICATION_DESC;
	public String NOTIFICATION_DESC_FORGOTTEN;
	
	/**
	 * MAP
	 */
	public String MAP_TITLE;
	
	/**
	 * PATIENTS
	 */
	public String PATIENTS_TITLE;
	public String PATIENTS_HISTORY;
	public String PATIENTS_MEAS;
	public String PATIENTS_QUEST;
	public String PATIENTS_HISTORY_PERSONALDATA;
	public String PATIENTS_PERSONALDATA_NAME;
	public String PATIENTS_PERSONALDATA_BIRTHDATE;
	public String PATIENTS_PERSONALDATA_NATION;
	public String PATIENTS_PERSONALDATA_SEX;
	public String PATIENTS_PERSONALDATA_EMAIL;
	public String PATIENTS_HISTORY_MEDICALRECORD;
	public String PATIENTS_MEDICALRECORD_DISEASE;
	public String PATIENTS_MEDICALRECORD_EXPLORATION;
	public String PATIENTS_HISTORY_LAST;
	public String PATIENTS_HISTORY_PULSEOXIMETRY;
	public String PATIENTS_HISTORY_SPIROMETRY;
	public String PATIENTS_HISTORY_EXERCISE;
	
	
	
	
	
	
	
	
	
	
	
	
	
    /**
     * COMUNICATION MESSAGES
     */
    public String ERR_GENERIC;
    public String ERR_REFRESH_ACT_LIST;
    public String ERR_SENSOR_DATA_READING;
    public String ERR_RETAKE_SENSOR_READING;
        
    public String ERROR_CODE;

    public static final String NEW_LINE = "\n";
	/**
	 * <p>The label to display in front of the Picture to display as Welcome</p>
	 */
	public String MHEALTH_WELCOME_LABEL;

	/**
     * <p>Strings to use for COMMAND text.</p>
     */
    public String CMD_OK;
    public String CMD_SELECT;
    public String CMD_CANCEL;
    public String CMD_BACK;
    public String CMD_EXIT;
    public String CMD_SEND;
    public String CMD_LOGIN;
    public String CMD_LOGOUT;
    public String CMD_RETRY;
    public String CMD_IGNORE;
    public String CMD_SNOOZE;
    public String CMD_RETURN;
    public String CMD_READ;
    public String CMD_NEXT;
    public String CMD_DEBUG;
    public String CMD_RETAKE;
    public String CMD_SKIP;
    public String CMD_OPTIONS;
    public String CMD_MARK;
    public String CMD_UNMARK;
    public String CMD_DELETE;
    public String CMD_CLEAR;
    public String CMD_PREVIOUS;
    
    /**
     * <p>Strings to use for MHProgress class.</p>
     */
	public String UI_PROGRESS_SERVER_LABEL;	
	public String UI_PROGRESS_SENSOR_LABEL;

	/**
     * <p>Strings to use for LoginScreen class.</p>
     */
	public String UI_LOGIN_TITLE;
	public String UI_ID_LABEL;
	public String UI_PASSWORD_LABEL;

	/**
     * <p>Strings to use for ChooseUserScreen class.</p>
     */
	public String UI_CHOOSEUSER_TITLE;
	public String UI_REFRESH_MENU;
	public String UI_ENTER_MENU;

	/**
     * <p>Strings to use for InputUserScreen class.</p>
     */
	public String UI_INPUTUSER_TITLE;
	public String UI_USERID_LABEL;
	public String UI_WRONGID_MSG = " 065";

	/**
     * <p>Strings to use for UserInfoScreen class.</p>
     */
	public String UI_USERINFO_TITLE;
	public String UI_INFO_LABEL;

	/**
     * <p>Strings to use for ActionListScreen class.</p>
     */
	public String UI_ACTLIST_TITLE;
	public String UI_ACTLIST_INACTIVE_ACTION = " 057";

	/**
     * <p>Strings to use for ActionInfoScreen class.</p>
     */
	public String UI_ACTINFO_TITLE;
	public String UI_TIME_LABEL;
	public String UI_TYPE_LABEL;
	public String UI_MSG_LABEL;

	/**
     * <p>Strings to use for SensorDataScreen class.</p>
     */
	public String UI_SENSORDATA_TITLE;
	public String UI_CANCEL_MENU;

	/**
     * <p>Strings to use for InstructionsScreen class.</p>
     */
	public String UI_INSTRU_TITLE;
	public String UI_INSTRU_DEFAULT = " 032";

	/**
     * <p>Strings to use for MessageScreen class.</p>
     */
	public String UI_ERROR_TITLE;
	public String UI_SUCCESS_TITLE;
	public String UI_DEFAULT_ERROR_MSG = " 031";
	/**
     * <p>Strings to use for TechnicianScreen class.</p>
     */
	public String UI_TECHNICIAN_TITLE;
	public String UI_HANDSET_ITEM;
	public String UI_SENSOR_ITEM;

	/**
     * <p>Strings to use for BindingInfoScreen class.</p>
     */
	public String UI_HANDSET_TITLE;
	public String UI_UID_LABEL;
	public String UI_IMEI_LABEL;
	public String UI_SERIAL_LABEL;
	public String UI_PHONE_LABEL;
	public String UI_BTADDR_LABEL;

	/**
     * <p>Strings to use for SensorListScreen class.</p>
     */
	public String UI_SENSORLIST_TITLE;
	
	/**
     * <p>Strings to use for SensorInfoScreen class.</p>
     */
	public String UI_SENSORINFO_TITLE;

	/**
     * <p>Strings to use for ConnectRetry class.</p>
     */
	public String UI_REG_TITLE;
	public String UI_PULL_TITLE;

	/**
	 * <p>Strings to use in Historical Data</p>
	 */
	public String UI_HISTDATA_NODATA_TITLE;
	public String UI_HISTDATA_MEASURE_TITLE;
	public String UI_TYPESLIST_TITLE;
	public String UI_TYPESLIST_MSG;
	public String UI_PINCODE_TITLE;
	public String UI_PINCODE_MSG;
	
	/**
	 * <p>Strings to use in Main Menu</p>
	 */
	public String UI_MAINMENU_TITLE;
	public String UI_MAINMENU_ACTIONS;
	public String UI_MAINMENU_JOURNALS;
	public String UI_MAINMENU_HISTDATA;
	public String UI_MAINMENU_SNOOZE;
	public String UI_MAINMENU_RETURN;
	
	public String UI_INVALID_FORMAT;
	
	/**
     * <p>Glucose sensor type</p>
     */
	public String DM_GLUCOSE_TYPE = "Glucose";

    /**
     * <p>Glucose sensor label</p>
     */
    public String DM_GLUCOSE_LABEL;

	/**
	 * <p>Glucose sensor id for Lifescan OneTouch Ultra</p>
	 */
	public String DM_GLUCOSE_Lifescan_OneTouch_Ultra_ID = "OneTouch Ultra";

	/**
	 * <p>Glucose sensor instruction for Lifescan OneTouch Ultra</p>
	 */
	public String DM_GLUCOSE_Lifescan_OneTouch_Ultra_INSTRUCTION;

	/**
	 * <p>Glucose sensor data error</p>
	 */
	public String DM_GLUCOSE_ERR_DATA = " 045";

	/**
	 * <p>Glucose sensor contains check strip data only.</p>
	 */
	public String DM_GLUCOSE_ERR_CHECK_STRIP = " 044";

	/**
	 * <p>Glucose sensor contains control solution data only.</p>
	 */
	public String DM_GLUCOSE_ERR_CONTROL_SOLUTION = " 051";

	/**
	 * <p>Blood pressure sensor type</p>
	 */
	public String DM_PRESSURE_TYPE = "BloodPressure";

    /**
     * <p>Blood pressure label</p>
     */
    public String DM_PRESSURE_LABEL;

	/**
	 * <p>Blood pressure sensor ID for A&D Medical UA767PC</p>
	 */
	public String DM_PRESSURE_AND_Medical_UA767PC_ID = "UA-767PC";

	/**
	 * <p>Blood pressure sensor instruction for A&D Medical UA767PC</p>
	 */
	public String DM_PRESSURE_AND_Medical_UA767PC_INSTRUCTION;
    
	/**
	 * <p>Blood pressure sensor ID for A&D Medical UA767BT</p>
	 */
	public String DM_PRESSURE_AND_Medical_UA767BT_ID = "UA-767BT";

	/**
	 * <p>Blood pressure sensor ID for A&D Medical UA767PBT</p>
	 */
	public String DM_PRESSURE_AND_Medical_UA767PBT_ID = "UA-767PBT";

	/**
	 * <p>Blood pressure sensor instruction for A&D Medical UA767BT</p>
	 */
	public String DM_PRESSURE_AND_Medical_UA767BT_INSTRUCTION;

	/**
	 * <p>Blood pressure sensor data error</p>
	 */
	public String DM_PRESSURE_ERR_DATA = " 046";
    
	/**
	 * <p>Pulse Oximetry sensor type</p>
	 */
	public String DM_PULSEOXIMETRY_TYPE = "PulseOximeter";

    /**
     * <p>Pulse Oximetry label</p>
     */
    public String DM_PULSEOXIMETRY_LABEL;

	/**
	 * <p>Pulse Oximetry sensor ID for Nonin 4100</p>
	 */
	public String DM_PULSEOXIMETRY_Nonin_4100_ID = "Nonin-4100";

	/**
	 * <p>Pulse Oximetry sensor instruction for Nonin 4100</p>
	 */
	public String DM_PULSEOXIMETRY_Nonin_4100_INSTRUCTION;
	
	/**
	 * <p>Pulse Oximetry sensor data error</p>
	 */
	public String DM_PULSEOXIMETRY_ERR_DATA = " 047";

	/**
	 * <p>Pulse Oximetry sensor type</p>
	 */
	public String DM_SPIROMETRY_TYPE = "Spirometer";

    /**
     * <p>Pulse Oximetry label</p>
     */
    public String DM_SPIROMETRY_LABEL;

	/**
	 * <p>Spirometry sensor ID for NDD EasyOne</p>
	 */
	public String DM_SPIROMETRY_NDD_EasyOne_ID = "NDD-EasyOne";

	/**
	 * <p>Spirometry sensor instructions for NDD EasyOne</p>
	 */
	public String DM_SPIROMETRY_NDD_EasyOne_INSTRUCTION;
	
	/**
	 * <p>Pulse Oximetry sensor data error</p>
	 */
	public String DM_SPIROMETRY_ERR_DATA = " 049";

	/**
	 * <p>Weight scale sensor type</p>
	 */
	public String DM_SCALE_TYPE = "Weight";

    /**
     * <p>Weight scale label</p>
     */
    public String DM_SCALE_LABEL;

	/**
	 * <p>Weight scale sensor ID for Tanita BF350</p>
	 */
	public String DM_SCALE_Tanita_BF350_ID = "BF-350";

	/**
	 * <p>Weight scale sensor instruction for Tanita BF350</p>
	 */
	public String DM_SCALE_Tanita_BF350_INSTRUCTION;
        
	/**
	 * <p>Weight scale sensor ID for A&D Medical UC321BT</p>
	 */
	public String DM_SCALE_AND_Medical_UC321BT_ID = "UC-321BT";

	/**
	 * <p>Weight scale sensor ID for A&D Medical UC321PBT</p>
	 */
	public String DM_SCALE_AND_Medical_UC321PBT_ID = "UC-321PBT";
	
	/**
	 * <p>Weight scale sensor instruction for A&D Medical UC321BT</p>
	 */
	public String DM_SCALE_AND_Medical_UC321BT_INSTRUCTION;

	/**
	 * <p>Weight scale sensor data error</p>
	 */
	public String DM_SCALE_ERR_DATA = " 048";

	/**
	 * <p>Measure data error</p>
	 */
	public String DM_ERR_DATA = " 042";

	/**
	 * <p>Sensor error, device type is not expected</p>
	 */
	public String DM_ERR_SENSOR = " 043";

	/**
	 * <p>Action error, null action or no data item in action</p>
	 */
	public String DM_ERR_ACTION = " 011";

	/**
	 * <p>Sensor error, null sensor</p>
	 */
	public String DM_ERR_SENSOR_NULL = " 013";

	/**
	 * <p>Sensor serial number mismatchment</p>
	 */
	public String DM_ERR_SENSOR_SERIAL = " 014";
		
	/**
	 * <p>Cannot find the sensor driver for one action</p>
	 */
	public String DM_ERR_SENSOR_DRIVER_NOT_FOUND= " 004";
		
	/**
	 * <p>Critical error due to the reference to BT driver is not initialized.</p>
	 */
	public String DM_ERR_BLUETOOTH_NOT_INITIALIZE = " 012";
	
	/**
	 * <p>Communication error in reading</p>
	 */
	public String DM_ERR_COMM_READ = " 039";

	/**
	 * <p>Communication error in reading sensor serial number</p>
	 */
	public String DM_ERR_COMM_READ_SERIAL = " 001";

	/**
	 * <p>Communication error in writing</p>
	 */
	public String DM_ERR_COMM_WRITE = " 041";

	/**
	 * <p>Communication error in writing sensor serial number</p>
	 */
	public String DM_ERR_COMM_WRITE_SERIAL = " 003";

	/**
	 * <p>Communication error in writing command to sensor</p>
	 */
	public String DM_ERR_COMM_WRITE_COMMAND = " 002";

	/**
	 * <p>Communication error in connecting to sensor</p>
	 */
	public String DM_ERR_COMM_CONNECT = " 038";

	/**
	 * <p>Timeout in reading</p>
	 */
	public String DM_ERR_COMM_TIMEOUT = " 040";

	/**
     * <p>Failed in accessing IMEI of the handset.</p>
     */
	public String DATA_ERR_FAIL_ACCESS_IMEI= " 008";

	/**
     * <p>Error occues in connecting to server.</p>
     */
	public String DATA_ERR_CONNECT_SERVER = " 034";

	/**
     * <p>Error in server response data.</p>
     */
	public String DATA_ERR_SERVER_RESPONSE= " 009";

	/**
     * <p>Error End-User ID.</p>
     */
	public String DATA_ERR_EUID = " 007";

	/**
     * <p>Unknown error occured.</p>
     */
	public String DATA_ERR_UNKNOWN = " 010";

	/**
     * <p>Server responses an internal error.</p>
     */
	public String PROT_ERR_SERVER_500 = " 020";

	/**
     * <p>Server responses an error.</p>
     */
	public String PROT_ERR_RESP_CODE= " 006";

	/**
     * <p>The error message displayed for IMEI of the handset is not registered in the system.</p>
     */
	public String PROT_ERR_INVALID_IMEI = " 018";

	/**
     * <p>The error message displayed when a patient uses a visiting nurse's handset.</p>
     */
	public String PROT_ERR_IMEI_NOT_FOR_END_USER = " 016";

	/**
     * <p>The error message displayed when the patiend is not active.</p>
     */
	public String PROT_ERR_INVALID_END_USER_STATUS = " 017";

	/**
     * <p>The error message displayed when Visiting Nurse or Technician use wrong ID or password.</p>
     */
	public String PROT_ERR_INVALID_USER_OR_PASSWORD = " 062";

	/**
     * <p>The error message displayed when the sensor binding information mismatches the action.</p>
     */
	public String PROT_ERR_INVALID_SENSOR_BINDING = " 019";

	/**
     * <p>The error message displayed when visiting nurse submits the data or get action list of an End-User not assigned to him/her.</p>
     */
	public String PROT_ERR_INVALID_VN_TO_EU = " 063";

	/**
     * <p>The error message displayed when the sensor data is invalid, e.g. the weight gives a very light value.</p>
     */
	public String PROT_ERR_INVALID_SENSOR_DATA = " 050";

	/**
     * <p>The error message displayed when the ID of VN or TC is inactive.</p>
     */
	public String PROT_ERR_INACTIVE_ID = " 059";

	/**
     * <p>The error message displayed when the ID of VN or TC is locked.</p>
     */
	public String PROT_ERR_LOCKED_ID = " 064";

	/**
     * <p>The success message displayed when the MIDlet submit sensor data to server successfully.</p>
     */
	public String PROT_SUCCESS_SUBMIT_DATA;

	/**
     * <p>The success message displayed when the MIDlet saves sensor data in handset successfully.</p>
     */
	public String PROT_OFFLINE_SUCCESS_SAVE_DATA;

	/**
     * <p>The message displayed when the MIDlet has already sent the sensor data to Application Server.</p>
     */
	public String PROT_DUPLICATED_SENSOR_DATA= " 005";
	
	/**
     * <p>The message indicates the action is expired (exceed tolarant time).</p>
     */
	public String PROT_ERR_EXPIRED_ACTION = " 037";

	/**
     * <p>The message indicates the history mode is expired (exceed historical time).</p>
     */
	public String PROT_ERR_EXPIRED_HISTORY = " 053";

	/**
     * <p>The message indicates the handset trying to use is not active in Application Server.</p>
     */
	public String PROT_ERR_HANDSET_STATUS = " 015";
	
	/**
     * <p>The message indicates the patient status has been inactivated.</p>
     */
	public String PROT_ERR_INVALID_END_USER_STATUS_FOR_VN = " 061";

	/**
     * <p>The message indicates the action downloaded has been changed in Application Server.</p>
     */
	public String PROT_ERR_CHECKPOINT_NOT_FOUND = " 035";

	/**
     * <p>The message indicates the data for this action is submitted too early.</p>
     */
	public String PROT_ERR_SUBMIT_TOO_EARLY = " 036";

	/**
     * <p>The message indicates the password of technician should be initialized from website first.</p>
     */
	public String PROT_ERR_INITIALIZE_PASSWORD = " 060";

	/**
     * <p>The message indicates the history option is locked for the End-User.</p>
     */
	public String PROT_ERR_LOCKED_HISTORY = " 056";

	/**
     * <p>The message indicates the pin code entered by the End-User is invalid.</p>
     */
	public String PROT_ERR_INVALID_PINCODE = " 058";

	/**
     * <p>The message indicates the End-User has no measurement types to view in the history option.</p>
     */
	public String PROT_ERR_HIST_NOTYPES = " 055";

	/**
     * <p>The message indicates the End-User has no data to view in the history option.</p>
     */
	public String PROT_ERR_HIST_NODATA = " 054";

	/**
     * <p>The message indicates the Splash Image is not stores in the App Server or it's bigger than 30 Kb.</p>
     */
	public String PROT_ERR_WRONG_SPLASH_IMAGE = " 021";
	
	/**
     * <p>The message indicates RecordStore couldn't be opened.</p>
     */
	public String RECORDSTORE_ERR_OPEN = " 025";

	/**
     * <p>The message indicates Record couldn't be opened.</p>
     */
	public String RECORDSTORE_ERR_OPEN_RECORD = " 026";

	/**
     * <p>The message indicates RecordStore unknown error.</p>
     */
	public String RECORDSTORE_ERR_GENERAL = " 024";

	/**
     * <p>The message indicates RecordStore storage limit reached.</p>
     */
	public String RECORDSTORE_ERR_STORAGE_LIMIT = " 029";
	
	/**
     * <p>The message indicates RecordStore update Record error.</p>
     */
	public String RECORDSTORE_ERR_UPDATE = " 030";
	
	/**
	 * <p>Error when trying to get action list or binding info from RMS</p>
	 */
	public String RECORDSTORE_ERR_READ_ACTIONS_BINDING;

	/**
	 * <p>Error when trying to forward old collected data</p>
	 */
	public String RECORDSTORE_ERR_FORWARD_DATA;

	/**
	 * <p>Error when saving collected data into RMS</p>
	 */
	public String RECORDSTORE_ERR_SAVE_COLLECTED_DATA;

	/**
	 * <p>Error when deleting old collected data</p>
	 */
	public String RECORDSTORE_ERR_DELETE_OLD_COLLECTED_DATA;

	/**
     * The internationalized labels for non-standard sensor data items
     * If a new value is added, it must also be handled in getLabelForSensorDataItem 
     */
    public String SDI_WEIGHT_WEIGHT_ID = "weight";
    public String SDI_WEIGHT_WEIGHT_LABEL;
    public String SDI_BP_PULSE_ID = "pulse";
    public String SDI_BP_PULSE_LABEL;
    public String SDI_GLUCOSE_CONCENTRATION_ID = "concentration";
    public String SDI_GLUCOSE_CONCENTRATION_LABEL;
        
    /**
     * <p>Journal related labels.</p>
     */
    public String UI_JNL_QUESTION_TITLE;
    public String UI_JNL_FEEDBACK_TITLE;
    public String UI_JNL_COMPLETION_TITLE;
    public String JNL_ANSWER_REQUIRED = " 052";
    public String JNL_COMPLETION_MESSAGE;
    public String JNL_COMPLETION_MESSAGE_MIX;
    
    /**
     * Returns the label to be shown for a specific sensor type
     * If no match is found, returns the same string received
     * @param type The sensor type
     * @return The internationalized label
     */
    public String getLabelForSensorType(String type)
    {
        if (type.equals(DM_GLUCOSE_TYPE))
            return DM_GLUCOSE_LABEL;
        else if (type.equals(DM_PRESSURE_TYPE))
            return DM_PRESSURE_LABEL;
        else if (type.equals(DM_SCALE_TYPE))
            return DM_SCALE_LABEL;
        else if (type.equals(DM_PULSEOXIMETRY_TYPE))
            return DM_PULSEOXIMETRY_LABEL;
        else if (type.equals(DM_SPIROMETRY_TYPE))
            return DM_SPIROMETRY_LABEL;
        else
            return type;
    }
    
    /**
     * Returns the label to be shown for a specific sensor data type
     * If no match is found, returns the same string received
     * @param sdi The sensor data type
     * @return The internationalized label
     */
    public String getLabelForSensorDataItem(String sdi)
    {
        if (sdi.equals(SDI_WEIGHT_WEIGHT_ID))
            return SDI_WEIGHT_WEIGHT_LABEL;
        else if (sdi.equals(SDI_BP_PULSE_ID))
            return SDI_BP_PULSE_LABEL;
        else if (sdi.equals(SDI_GLUCOSE_CONCENTRATION_ID))
            return SDI_GLUCOSE_CONCENTRATION_LABEL;
        else
            return sdi;
    }

}
