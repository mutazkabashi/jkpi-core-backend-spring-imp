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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * @author Mutaz Hussein Kabashi
 *
 */
public class DateOperations {
	
	//TODO refactrong
	public static Date convertStringToDate(String passedDate,String dateFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat/*"dd-MMM-yyyy"*/);
		String dateInString = passedDate/*"7-Jun-2013"*/;
	    Date date = null;
		try {
	 
			 date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));
			//return date;
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
	finally{
		return date;
	}
	}
	
	public static String getCurrentDateTime(String dateFormat){
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			return formatter.format(new Date());
		
	}
	
	//TODO should be added to Date Utils
		public static String getPreviousMonthInString(String month){
			switch (month) {
			case "Janauary":
				return "December";
				//break;
			case "February":
				return "Janauary";
			case "March":
				return "February";
			case "April":
				return "March";
				//break;
			case "May":
				return "April";
			case "June":
				return "May";
			case "July":
				return "June";
				//break;
			case "August":
				return "July";
			case "September":
				return "August";
			case "October":
				return "September";
				//break;
			case "November":
				return "October";
			case "December":
				return "November";
			
				//break;

			default:
				return month;
				//break;
			}
		}

		//TODO should be added to Date Utils
			public static String getMonthInString(String month){
				switch (month) {
				case "Janauary":
					return "Janauary";
					//break;
				case "February":
					return "February";
				case "March":
					return "March";
				case "April":
					return "April";
					//break;
				case "May":
					return "May";
				case "June":
					return "June";
				case "July":
					return "July";
					//break;
				case "August":
					return "August";
				case "September":
					return "September";
				case "October":
					return "October";
					//break;
				case "November":
					return "November";
				case "December":
					return "December";
				
					//break;

				default:
					return month;
					//break;
				}
			}

		
		//TODO should be added to Date Utils
			public static String getPreviousMonthInNo(String month){
				switch (month) {
				case "Janauary":
					return "12";
					//break;
				case "February":
					return "01";
				case "March":
					return "02";
				case "April":
					return "03";
					//break;
				case "May":
					return "04";
				case "June":
					return "05";
				case "July":
					return "06";
					//break;
				case "August":
					return "07";
				case "September":
					return "08";
				case "October":
					return "09";
					//break;
				case "November":
					return "10";
				case "December":
					return "11";
				
					//break;

				default:
					return month;
					//break;
				}
			}
			
			//TODO should be added to Date Utils
					public static String getPreviousMonthInNoForIntegerMonth(String month){
						switch (month) {
						case "01":
							return "12";
							//break;
						case "02":
							return "01";
						case "03":
							return "02";
						case "04":
							return "03";
							//break;
						case "05":
							return "04";
						case "06":
							return "05";
						case "07":
							return "06";
							//break;
						case "08":
							return "07";
						case "09":
							return "08";
						case "10":
							return "09";
							//break;
						case "11":
							return "10";
						case "12":
							return "11";
						
							//break;

						default:
							return month;
							//break;
						}
					}
			
			public static String getMonthInNo(String month){
				switch (month) {
				case "Janauary":
					return "01";
					//break;
				case "February":
					return "02";
				case "March":
					return "03";
				case "April":
					return "04";
					//break;
				case "May":
					return "05";
				case "June":
					return "06";
				case "July":
					return "07";
					//break;
				case "August":
					return "08";
				case "September":
					return "09";
				case "October":
					return "10";
					//break;
				case "November":
					return "11";
				case "December":
					return "12";
				
					//break;

				default:
					return month;
					//break;
				}
			}
		
		public static String getPreviousYear(String year){
			int previousYear = Integer.parseInt(year)-1; 
			return String.valueOf(previousYear);
		}
		
		public static String getDateMinusOneMonthForYYYYMMDDFormat(String date){
			String day =date.substring(date.lastIndexOf("-")+1);
			String previousMonth = getPreviousMonthInNoForIntegerMonth(date.substring(date.indexOf("-")+1,date.lastIndexOf("-")));
			String year = date.substring(0,date.indexOf("-"));
			if(previousMonth.equalsIgnoreCase("12")){
				year = getPreviousYear(year);
			}
			return day+"-"+previousMonth+"-"+year;
		}
		
		public static String getDateMinusOneYear(String previousYearDate){
			String previousYear = "01"+"-"+previousYearDate.substring(0, previousYearDate.indexOf("-"))+"-"+getPreviousYear(previousYearDate.substring(previousYearDate.indexOf("-")+1));
		    return previousYear;
		}
		
		public static String getDateMinusOneYearForDDMMYYYYFormat(String previousYearDate){
			String previousYear = "01"+"-"+previousYearDate.substring(previousYearDate.indexOf("-")+1,previousYearDate.lastIndexOf("-"))+"-"+getPreviousYear(previousYearDate.substring(0,previousYearDate.indexOf("-")));
		    return previousYear;
		}


}
