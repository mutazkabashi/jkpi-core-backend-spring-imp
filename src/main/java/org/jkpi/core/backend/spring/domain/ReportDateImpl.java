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
package org.jkpi.core.backend.spring.domain;

import java.util.Date;

import org.jkpi.core.backend.api.domain.ReportDate;

/**
 * Implementation of {@code ReportDate}
 * @author Mutaz Hussein Kabashi
 *
 */
public class ReportDateImpl implements ReportDate {
	
	private String stringDate;
	private Date actualDate;
	
	/**
	 * Default constructor
	 */
	public ReportDateImpl(){
		
	}
	
	/**
	 * Constructor to create {@code ReportDate} by a date
	 * this contructor could be used by HQL (Hibernate Query language)
	 * @param stringDate A {@code Date} in a string format
	 * @param actualDate A Date in {@code Date} format
	 */
	public ReportDateImpl(String stringDate,Date actualDate){
		super();
		this.stringDate = stringDate;
		this.actualDate = actualDate;
	}
	

	
	public String getStringDate() {
		return stringDate;
	}
	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}
	public Date getActualDate() {
		return actualDate;
	}
	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actualDate == null) ? 0 : actualDate.hashCode());
		result = prime * result
				+ ((stringDate == null) ? 0 : stringDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportDateImpl other = (ReportDateImpl) obj;
	/*	if (actualDate == null) {
			if (other.actualDate != null)
				return false;
		} else if (!actualDate.equals(other.actualDate))
			return false;*/
		if (stringDate == null) {
			if (other.stringDate != null)
				return false;
		} else if (!stringDate.equals(other.stringDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportDateImpl [stringDate=" + stringDate + ", actualDate=" + actualDate + "]";
	}
	
	

}
