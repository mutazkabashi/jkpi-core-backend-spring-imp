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

import java.util.Comparator;

import org.jkpi.core.backend.api.domain.ReportRow;

/**
 * 
 * @author Mutaz Hussein Kabashi
 *
 */
public class ReportRowSorter implements Comparator<ReportRow>{
	 
    @Override
    public int compare(ReportRow r1, ReportRow r2) {
    	System.out.println("r1 "+r1.getReportGroup().getGroupName());
    	System.out.println("r2 "+r2.getReportGroup().getGroupName());
    	//int compareResult = r1.getRowName().substring(0, r1.getRowName().indexOf("$")).compareTo(r2.getRowName().substring(0, r2.getRowName().indexOf("$")));
    	int compareResult = r1.getReportGroup().getGroupName().compareTo(r2.getReportGroup().getGroupName());
    	if(compareResult >0){
            return 1;
        } if(compareResult<0) {
            return -1;
        }
        if(compareResult==0){
        return 0;
        }
        else{
        return 0;
        }
    }
}
