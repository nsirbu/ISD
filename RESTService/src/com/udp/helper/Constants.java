package com.udp.helper;

/**
 * This class contains the constants for specific operations
 * in the application
 */
public class Constants {

    //----------- LOG FILE CONSTANTS ------------------
    // These paths are appended to the current user home directory
    public final static String JSON_FILE_PATH    = "/Desktop/Arduino/messages.json";
    public final static String TXT_LOG_FILE_PATH = "/Desktop/Arduino/log.txt";
    
  //----------- CONFIGURATION FILE PATH ------------------
    public final static String CONFIGURATION_FILE_PATH    = "P:/config.xml";

    //----------- FileWriter CONSTANTS ---------------
    public static final boolean APPEND_TEXT = true;
    public static final boolean OVERWRITE_TEXT = false;

    // ---------- SERVER CONSTANTS --------------------
    public static final int SERVER_PORT;
    
    // ---------- ARDUINO BOARD CONSTANTS -------------
    public static final String ARDUINO_IP;
    public static final int ARDUINO_PORT;

    // ---------  SENSOR GENERAL STATES ---------------
    public static final int SENSOR_ON;
    public static final int SENSOR_OFF;

    public static final String PIR_SENSOR_PREFIX    = "P";
    public static final String LIGHT_SENSOR_PREFIX  = "L";
    public static final String HEARTBEAT_MSG_PREFIX = "H";

    // ---------- HEARTBEAT CONSTANTS ----------------
    public static final boolean HEARTBEAT_IS_LATE = false;
    public static final boolean HEARTBEAT_OK	  = true;
    
    // ---------- MINIMUM TIME DELAY TO CHECK PRESENCE IN THE ROOM ---------
    // If no motion detection after this period of milliseconds, we consider that there is no one in the room.
    public static final int MINIMUM_DELAY_TO_CHECK_PRESENCE_IN_ROOM = 5000;

    // ---------- INITIALISATION BLOCK ----------------
    static {
        SENSOR_ON    = 1;
        SENSOR_OFF   = 0;
        SERVER_PORT  = 9876;
        ARDUINO_IP = "172.17.41.54";
        ARDUINO_PORT = 9876;
    }

}