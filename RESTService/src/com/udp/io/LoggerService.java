package com.udp.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.udp.helper.Constants;
import com.udp.helper.FileHelper;

/**
* Logs all the information that is being generated.
* This class is used for registering all the events in
* a text log file.
* @author sscerbatiuc
*/
public class LoggerService implements Recordable {

   private File   logFile;
   private String logFilePath;

   private static LoggerService instance;

   /**
    * CONSTRUCTOR
    */
   private LoggerService(String filePath) throws IOException{

       this.setLogFilePath(FileHelper.parseFilePath(filePath));
       this.setLogFile(new File(this.getLogFilePath()));

   }

   /**
    * Singleton method: makes sure there is only one instance
    * of the current class and creates it if necessary.
    * @return LoggerService
    */
   public static LoggerService getInstance() throws  IOException{
       if(instance == null){
           instance = new LoggerService(Constants.TXT_LOG_FILE_PATH);
       }
       return  instance;
   }


   //-------------- GETTERS & SETTERS ----------------------

   public File getLogFile() { return logFile; }

   public void setLogFile(File logFile) { this.logFile = logFile; }

   public String getLogFilePath() { return logFilePath;  }

   public void setLogFilePath(String logFilePath) { this.logFilePath = logFilePath; }


   //-------------------------------------------------------

   /**
    * Reads all the content of the file and returns it as a <code>String</code>
    * @param filePathString String representing the path to the actual file
    * @return String
    */
   public String readFromFile(String filePathString) {
       try {
           Path filePath = Paths.get(filePathString);
           String fileContent =  new String(Files.readAllBytes(filePath));
           return fileContent;
       } catch (Exception ex) {

       }
       return null;
   }

   /**
    * Writes the specified message to the log <code>File</code>
    * @param message String
    * @return boolean - true - success; false - failure
    */
   public boolean writeToFile(String message) {

       try{
           FileWriter logFileWriter = new FileWriter(this.getLogFile(), Constants.APPEND_TEXT);
           logFileWriter.write(message.toString());
           logFileWriter.flush();
           logFileWriter.close();
           return true;

       } catch(IOException ex){
           System.out.println("Couldn't write to text log file");
       }
       return false;

   }
}
