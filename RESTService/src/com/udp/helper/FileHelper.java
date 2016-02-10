package com.udp.helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;

/**
* Contains static methods used for manipulating files and files' paths
* @author sscerbatiuc
*/
public class FileHelper {

   /**
    * Concatenates the current user home directory path with the
    * indicated directory path, in which the file will be stored.
    * If the obtained path does not exist, this method creates
    * the whole path to the file
    * @param fileDirectory String
    * @return String
    */
   public static String parseFilePath(String fileDirectory) throws IOException {

       String currentHomeDir = System.getProperty("user.home");
       Path filePath = Paths.get(currentHomeDir.concat(fileDirectory));
       if(Files.notExists(filePath)){                      // Check if the path exists
           Files.createDirectories(filePath.getParent());  //Create folder
           Files.createFile(filePath);                     //Create file
       }
       return filePath.toString();
   }
}
