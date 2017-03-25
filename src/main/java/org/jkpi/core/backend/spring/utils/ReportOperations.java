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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jkpi.core.backend.api.domain.Report;
import org.jkpi.core.backend.api.domain.ReportColumn;
import org.jkpi.core.backend.api.domain.ReportColumnDetails;
import org.jkpi.core.backend.api.domain.ReportGroup;
import org.jkpi.core.backend.api.domain.ReportRecord;
import org.jkpi.core.backend.api.domain.ReportRow;
import org.jkpi.core.backend.spring.domain.ReportColumnDetailsImpl;
import org.jkpi.core.backend.spring.domain.ReportColumnImpl;
import org.jkpi.core.backend.spring.domain.ReportRecordImpl;
import org.jkpi.core.backend.spring.domain.ReportRowImpl;
/**
 * 
 * @author Mutaz Hussein Kabashi
 *
 */
public class ReportOperations {
	
	
	public static List<ReportGroup> getReportGroups(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report ){
		List<ReportGroup>ReportGroups = new ArrayList<ReportGroup>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					ReportGroups.add(currentReportGroup);
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						
						
				}
				  
			}
			 return ReportGroups;
		
	}
	
	public static List<ReportRow> getReportRows(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report ){
		List<ReportRow>ReportRows = new ArrayList<ReportRow>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							ReportRows.add(currentReportRow);
							
						}
						
				}
				  
			}
			 return ReportRows;
		
	}
	
	public static List<ReportColumnDetails> getReportColumnDetails(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report ){
		List<ReportColumnDetails>ReportColumnDetails = new ArrayList<ReportColumnDetails>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							ReportColumnDetails.addAll(cReportRows.get(currentReportRow));
							
						}
						
				}
				  
			}
			 return ReportColumnDetails;
		
	}
	
	public static List<ReportColumn> getReportColumns(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report ){
		List<ReportColumn>ReportColumn = new ArrayList<ReportColumn>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							for (ReportColumnDetails currentreportColumnDeatil : cReportRows.get(currentReportRow)) {
								ReportColumn tempReportColumn = new ReportColumnImpl();
								tempReportColumn.setColumnColor(currentreportColumnDeatil.getColumnColor());
								tempReportColumn.setColumnName(currentreportColumnDeatil.getColumnName());
								ReportColumn.add(tempReportColumn);
							}
							
							
						}
						
				}
				  
			}
			 return ReportColumn;
		
	}
	
	
	
	public static List<ReportRecord> getReportRecords(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report ){
		List<ReportRecord>ReportRecords = new ArrayList<ReportRecord>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
                            for (ReportColumnDetails currentReportColumnDetail : cReportRows.get(currentReportRow)) {
                            	ReportRecord tempReportRecord = new ReportRecordImpl(); //TODO IOC
                            	tempReportRecord.setColumnValue(currentReportColumnDetail.getColumnValue());
                                //tempReportRecord.setInsertionDate(currentReportColumnDetail.);
                            	ReportRecords.add(tempReportRecord);
							}
							
							
						}
						
				}
				  
			}
			 return ReportRecords;
		
	}
	
	public static List<ReportRow> getReportRowsByGroup(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report,ReportGroup reportGroup ){
		List<ReportRow>ReportRows = new ArrayList<ReportRow>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					if(currentReportGroup.getGroupName().equalsIgnoreCase(reportGroup.getGroupName())){
						for (ReportRow currentReportRow : reportGroup.getReportRows()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							ReportRow tempReportRow = new ReportRowImpl();
							tempReportRow.setRowName(currentReportRow.getRowName());
							tempReportRow.setReportColumns(currentReportRow.getReportColumns());
							ReportRows.add(tempReportRow);
						}
				 }
						
				}
				  
			}
			 return ReportRows;
		
	}
	
	
	public static List<ReportColumn> getReportColumnsByRows(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report,ReportRow reportRow ){
		List<ReportColumn>ReportColumns = new ArrayList<ReportColumn>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							if(reportRow.getRowName().equalsIgnoreCase(currentReportRow.getRowName())){
								for (ReportColumn currentReportColumn : reportRow.getReportColumns()) {
									ReportColumn tempReportColumn = new ReportColumnImpl();
									tempReportColumn.setColumnName(currentReportColumn.getColumnName());
									tempReportColumn.setColumnColor(currentReportColumn.getColumnColor());
									tempReportColumn.setReportColumnID(currentReportColumn.getReportColumnID());
									ReportColumns.add(tempReportColumn);
								}
							}
							
							
						}
						
				}
				  
			}
			 return ReportColumns;
		
	}
	
	public static List<ReportRecord> getReportRecordsByColumn(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report,ReportColumn reportColumn ){
		List<ReportRecord>ReportRecord = new ArrayList<ReportRecord>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							for (ReportColumnDetails currentreportColumnDeatil : cReportRows.get(currentReportRow)) {
								ReportRecord tempReportRecord = new ReportRecordImpl();
								if(reportColumn.getColumnName().equalsIgnoreCase(currentreportColumnDeatil.getColumnName())){
								tempReportRecord.setColumnValue(currentreportColumnDeatil.getColumnValue());
								//tempReportRecord.setColumnName(currentreportColumnDeatil.getColumnName());
								ReportRecord.add(tempReportRecord);
								}
							}
							
							
						}
						
				}
				  
			}
			 return ReportRecord;
		
	}
	
	public static List<ReportRecord> getReportRecordsByRowAndColumnOld(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report,ReportRow reportRow,ReportColumn reportColumn ){
		List<ReportRecord>ReportRecord = new ArrayList<ReportRecord>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							if(currentReportRow.getRowName().equalsIgnoreCase(reportRow.getRowName())){
							for (ReportColumnDetails currentreportColumnDeatil : cReportRows.get(currentReportRow)) {
								ReportRecord tempReportRecord = new ReportRecordImpl();
								if(reportColumn.getColumnName().equalsIgnoreCase(currentreportColumnDeatil.getColumnName())){
								tempReportRecord.setColumnValue(currentreportColumnDeatil.getColumnValue());
								//tempReportRecord.setColumnName(currentreportColumnDeatil.getColumnName());
								ReportRecord.add(tempReportRecord);
								}
							}
						}
							
						}
						
				}
				  
			}
			 return ReportRecord;
		
	}
	
	public static List<ReportRecord> getReportRecordsByRowAndColumn(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>report,ReportRow reportRow,ReportColumn reportColumn ){
		List<ReportRecord>ReportRecord = new ArrayList<ReportRecord>();
			 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : report.values()) {
				 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
					//System.out.println("Group Name "+ currentReportGroup.getGroupName());
					 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
						for (ReportRow currentReportRow : cReportRows.keySet()) {
							//System.out.println("ReportRow Name "+ currentReportRow.getRowName());
							if(currentReportRow.getRowName().equalsIgnoreCase(reportRow.getRowName())){
							for (ReportColumnDetails currentreportColumnDeatil : cReportRows.get(currentReportRow)) {
								ReportRecord tempReportRecord = new ReportRecordImpl();
								if(reportColumn.getColumnName().equalsIgnoreCase(currentreportColumnDeatil.getColumnName())){
								tempReportRecord.setColumnValue(currentreportColumnDeatil.getColumnValue());
								//tempReportRecord.setColumnName(currentreportColumnDeatil.getColumnName());
								ReportRecord.add(tempReportRecord);
								}
							}
						}
							
						}
						
				}
				  
			}
			 return ReportRecord;
		
	}
	
	public static ReportColumn getReportRecordColumnByColumn(ReportRow reportRow,ReportColumn reportColumn ){
		ReportColumn rColumn = new ReportColumnImpl(); //TODO IOC
			 for ( ReportColumn currentReportColumn : reportRow.getReportColumns()) {
				 if(currentReportColumn.getColumnName().equalsIgnoreCase(rColumn.getColumnName())){
					 rColumn.setColumnColor(currentReportColumn.getColumnColor());
					 rColumn.setColumnName(currentReportColumn.getColumnName());
					 rColumn.setReportColumnID(currentReportColumn.getReportColumnID());
				 }
				  
			}
			 return rColumn;
		
	}
	
	/** WE use collection here so we colud pass a Set or a  List as a paramter to <Code>getReportRows<Code> method*/
	public static List<ReportRow> getReportRows(Collection<ReportGroup> reportGroups){
		List<ReportRow>ReportRows = new ArrayList<ReportRow>();
			 for ( ReportGroup reportGroup : reportGroups) {
				ReportRows.addAll(reportGroup.getReportRows());
				  
			}
			 return ReportRows;
		
	}
	
	public static List<ReportColumn> getReportColumns(List<ReportRow> reportRows){
		List<ReportColumn>ReportColumns = new ArrayList<ReportColumn>();
			 for ( ReportRow reportRow : reportRows) {
				ReportColumns.addAll(reportRow.getReportColumns());
				  
			}
			 return ReportColumns;
		
	}
	
	/**
	//TODO we use <CODE>reportRowID<CODE> to calculate the total value of it's records values , we should use different way to do this calculation
	//TODO <code>ReportRow.RowName</code> Property consists of <code>rowName</code> & <code>columnName</code> separated by $
	 * @param reportRecords
	 * @return List<ReportRow>
	 */
	public static List<ReportRow> getReportRecordsRows(List<ReportRecord> reportRecords){
		Set<ReportColumn> reportColumns = getReportRecordsColumns(reportRecords);
		List<ReportRow> reportRows = new ArrayList<ReportRow>();
		for (ReportRecord currentReportRecord : reportRecords) {
			if(reportRows.size()==0 || !reportRows.contains(currentReportRecord.getReportColumn().getReportRow().getRowName())){
				ReportRow reportRow = new ReportRowImpl(); //TODO IOC
				reportRow.setRowName(currentReportRecord.getReportColumn().getReportRow().getRowName()+"$"+currentReportRecord.getReportColumn().getColumnName());
				reportRow.setReportGroup(currentReportRecord.getReportColumn().getReportRow().getReportGroup());
				//TODO we use reportRowID to caculate the total value of it's records values, we should use different way to do that
				reportRow.setReportRowID(currentReportRecord.getColumnValue().longValue());
				reportRow.setReportColumns(reportColumns);
				reportRows.add(reportRow);
				continue;
			}
			if(reportRows.contains(currentReportRecord.getReportColumn().getReportRow().getRowName())){
				ReportRow reportRow =reportRows.get(reportRows.indexOf(currentReportRecord.getReportColumn().getReportRow()));
				reportRow.setReportRowID(reportRow.getReportRowID()+currentReportRecord.getColumnValue().longValue());
			}
		}
		return reportRows;
	}
	
	/**
	 * TODO should be merged with  <code>ReportOperation.getReportRecordsRows()</code> method
	 * This method assumes that the  excel sheet's rows has the same columns(i.e all report's groups and rows has the same structure)
	 * @param reportRecords
	 * @return
	 */
	public static Set<ReportColumn> getReportRecordsColumns(List<ReportRecord> reportRecords){
		Set<ReportColumn> reportColumns = new HashSet<ReportColumn>();
		for (ReportRecord currentReportRecord : reportRecords) {
			if(reportColumns.size()==0 || !reportColumns.contains(currentReportRecord.getReportColumn())){
				ReportColumn reportColumn = new ReportColumnImpl(); //TODO IOC
				reportColumn.setColumnName(currentReportRecord.getReportColumn().getColumnName());
				reportColumn.setColumnColor(currentReportRecord.getReportColumn().getColumnColor());
				reportColumns.add(reportColumn);
				continue;
			}
			/*if(reportColumns.contains(currentReportRecord.getReportColumn().getReportRow().getRowName())){
				ReportRow reportRow =reportRows.get(reportRows.indexOf(currentReportRecord.getReportColumn().getReportRow()));
				reportRow.setReportRowID(reportRow.getReportRowID()+currentReportRecord.getColumnValue().longValue());
			}*/
		}
		return reportColumns;
	}
	
	
	public static List<ReportColumnDetails> cloneReportColumnDetailList(List<ReportColumnDetails> list) {
		List<ReportColumnDetails> cloneList = new ArrayList<ReportColumnDetails>();
		for (ReportColumnDetails currentRow : list) {
			ReportColumnDetails temp = new ReportColumnDetailsImpl(); //TODO IOC
			temp.setColumnColor(currentRow.getColumnColor());
			temp.setColumnName(currentRow.getColumnName());
			temp.setColumnValue(currentRow.getColumnValue());
			cloneList
					.add(temp);
		}
		return cloneList;
	}
	
	/**
	 //TODO this method returns Map<String,List<Double>> instead of Map<String,Double> , becuase <code>fpREvenue.entries</code>> field
	  * of type Map<String,List<Double>> ,  in th futrue we should change the return type to  Map<String,Double>
	 * @param reportRows
	 * @return
	 */
	public static Map<String,List<Double>> calcuateRowTotalValue(Map<String,List<Double>> reportRows){
		Map<String,List<Double>> resultMap = new LinkedHashMap<String,List<Double>>();
		//double rowTotalValue= 0;
		for (String currentRow : reportRows.keySet()) {
			double rowTotalValue= 0;
			for (Double curretValue : reportRows.get(currentRow)) {
				rowTotalValue = rowTotalValue + curretValue;
			}
			List<Double> rowValues = new ArrayList<Double>();
			rowValues.add(rowTotalValue);
			resultMap.put(currentRow, rowValues);
		}
		return resultMap;
		
	}
	
	/**
	//TODO this method is a temporary solution, we should use other technique  to poupulate detailsColumnData
	 * @param reportRows
	 * @return
	 */
	public static Map<String,List<Double>> calcuateDetailsColumnData(Map<String,List<Double>> detailsColumnData){
		Map<String,List<Double>> resultMap = new LinkedHashMap<String,List<Double>>();
		//double rowTotalValue= 0;
		for (String currentRow : detailsColumnData.keySet()) {
			List<Double> currentList = detailsColumnData.get(currentRow);
			List<Double> newList = new ArrayList<Double>();
			int counter =0;
            while(counter <currentList.size()/2){
            	double d1= currentList.get(counter);
            	double d2 = currentList.get((currentList.size())/2+counter);
            	Double rowtotalValue = d1+d2;
            	++counter;
            	newList.add(rowtotalValue);
            }
			resultMap.put(currentRow, newList);
		}
		return resultMap;
		
	}

}
