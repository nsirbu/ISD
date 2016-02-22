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

    //----------- FileWriter CONSTANTS ---------------
    public static final boolean APPEND_TEXT = true;
    public static final boolean OVERWRITE_TEXT = false;

    // ---------- SERVER CONSTANTS --------------------
    public static final int SERVER_PORT;

    // ---------  SENSOR GENERAL STATES ---------------
    public static final int SENSOR_ON;
    public static final int SENSOR_OFF;

    public static final String PIR_SENSOR_PREFIX    = "P";
    public static final String LIGHT_SENSOR_PREFIX  = "L";
    public static final String HEARTBEAT_MSG_PREFIX = "H";

    // ---------- HEARTBEAT CONSTANTS ----------------
    public static final boolean HEARTBEAT_IS_LATE = false;
    public static final boolean HEARTBEAT_OK	  = true;

    // ---------- INITIALISATION BLOCK ----------------
    static {
        SENSOR_ON    = 1;
        SENSOR_OFF   = 0;
        SERVER_PORT  = 9876;
    }

}
