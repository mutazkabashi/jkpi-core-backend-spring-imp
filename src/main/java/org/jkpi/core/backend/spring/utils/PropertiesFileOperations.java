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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * 
 * @author Mutaz Hussein Kabashi
 *
 */
public class PropertiesFileOperations {
	
	/*public static Properties loadFromFile(String file) throws IOException {
        Properties properties = new Properties();
        FileInputStream stream = new FileInputStream(file);
        try {
            properties.load(stream);
        } finally {
            stream.close();
        }
        return properties;
    }*/
	
	public static Properties loadPropertiesFilefromTomcat(String file) throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
        } finally {
            //stream.close();
        }
        return properties;
    }

}
