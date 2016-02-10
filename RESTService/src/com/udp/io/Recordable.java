package com.udp.io;

/**
 * Defines the methods for recording and reading information
 * to/from files
 * @author sscerbatiuc
 */
public interface Recordable {

    public String readFromFile(String filePath);
    public boolean writeToFile(String msg);

}