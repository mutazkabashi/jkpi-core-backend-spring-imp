/*
 * JKPI Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkpi.core.backend.spring.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 
 * @author Mutaz Hussein Kabashi
 *
 */
public class FileOperations {
	
	public static void copyFileUsingJava7Files(File source, File dest)
			throws IOException {
		Files.copy(source.toPath(), dest.toPath());
	}
	
	public static void moveFileUsingJava7Files(File source, File dest)
			throws IOException {
		Files.move(source.toPath(), dest.toPath());
	}
	
	public static String  createNewFolder(String folderName) throws SecurityException {
		File theDir = new File(folderName);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + folderName);
		    boolean result = false;

		    //try{
		        theDir.mkdir();
		        //result = true;
		    //} 
		    /*catch(SecurityException se){
		        //handle it
		    } */       
		   /* if(result) {    
		        System.out.println("DIR created");  
		    }*/
		}
		return theDir.getName();
		
	}

}
