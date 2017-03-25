package org.jkpi.core.backend.spring.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jkpi.core.backend.spring.domain.Report;
import org.jkpi.core.backend.spring.domain.ReportColumn;
import org.jkpi.core.backend.spring.domain.ReportColumnDetails;
import org.jkpi.core.backend.spring.domain.ReportColumnImpl;
import org.jkpi.core.backend.spring.domain.ReportDate;
import org.jkpi.core.backend.spring.domain.ReportGroup;
import org.jkpi.core.backend.spring.domain.ReportImpl;
import org.jkpi.core.backend.spring.domain.ReportRecord;
import org.jkpi.core.backend.spring.domain.ReportRow;
import org.jkpi.core.backend.spring.service.ReportRecordService;
import org.jkpi.core.backend.spring.service.ReportRecordServiceImpl;
import org.jkpi.core.backend.spring.service.ReportRowService;
import org.jkpi.core.backend.spring.service.ReportRowServiceImpl;
import org.jkpi.core.backend.spring.service.ReportService;
import org.jkpi.core.backend.spring.utils.ReportOperations;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

//TODO refactoring (Name of the Class & Design [Package name,using interface and Impl ,..etc])
@Service("reportFacade") 
@Scope("prototype") //TODO should be service (Singleton bean) instead of (Request Bean) 
public class ReportFacade {
	
	@Resource(name= "reportService")
	private ReportService reportService ; //= new ReportRowServiceImpl();
	
	@Resource(name= "reportRowService")
	private ReportRowService reportRowService ; //= new ReportRowServiceImpl();
	
	@Resource(name= "reportRecordService")
	private ReportRecordService reportRecordService; // = new ReportRecordServiceImpl();
	
	public List<ReportDate> getReportDates(String reportName){
		return reportRowService.getReportDates(reportName);
		
	}
	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
	public List<ReportRecord> getLastUploadedReportsByName(String reportName){
	    //return reportRowService.getLastUploadedReportsByName(reportName); //Quick Solution
		return reportRecordService.getLastUploadedReportByName(reportName);
	}
	
	public List<ReportRecord> getReportByPeriodForSummaryReport(String reportName,Date startDate,Date endDate){
	    return reportRowService.getReportByPeriodForSummaryReport(reportName, startDate, endDate);
	}
	
	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
	public List<ReportRecord> getPreviousUploadedReport(String reportName){
		 return reportRowService.getPreviousUploadedReport(reportName);
	}
	
	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
	public List<ReportRecord> getLastUploadedReportByReportNameAndGroupName(String reportName,String  groupName){
		return reportRecordService.getLastUploadedReportByReportNameAndGroupName(reportName, groupName);
	}
	
	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
	public List<ReportRecord> getReportByReportNameAndInsertionDate(String reportName,Date insertionDate){
    	return reportRecordService.getReportByReportNameAndInsertionDate(reportName, insertionDate);
	}

	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
	public List<ReportRecord> getReportByReportNameGroupNameAndInsertionDate(String reportName,String groupName,Date insertionDate){
    	return reportRecordService.getReportByReportNameGroupNameAndInsertionDate(reportName, groupName, insertionDate);

	}

	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
	public List<ReportRecord> getReportByReportNameGroupNameAndPeriod(String reportName,String groupName,Date startDate,Date endDate){
    	return reportRecordService.getReportByReportNameGroupNameAndPeriod(reportName, groupName, startDate, endDate);


	}
	
	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
		public List<ReportRecord> getReportColumnValueByColumnNameGroupNameReportNameAndInsertionDate(String columnName, String groupName,
				String reportName, Date insetionDate){
	    	return reportRecordService.getReportColumnValueByColumnNameGroupNameReportNameAndInsertionDate(columnName, groupName, reportName, insetionDate);


		}
	
   
	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
		
		public List<ReportRecord> getReportColumnValueByColumnNameGroupNameReportNameAndPeriod(String columnName, String groupName,
			    String reportName, Date startDate,Date endDate){
			   	return reportRecordService.getReportColumnValueByColumnNameGroupNameReportNameAndPeriod(columnName, groupName, reportName, startDate, endDate);


				}
		
	//TODO Quick Solution (to meet the dead line), new technique/Implementation should be used
		
		public List<ReportRecord> getReportAccomulatedColumnValueByColumnNameGroupNameReportNameAndPeriod(String columnName, String groupName,
					    String reportName, Date startDate,Date endDate){
					   	return reportRecordService.getReportAccomulatedColumnValueByColumnNameGroupNameReportNameAndPeriod(columnName, groupName, reportName, startDate, endDate);


						}
		/**
		 * TODO method name should be modified to (update/modify)ReportByDate(), and it should be delete report records first and then save,the uploaded
		 * report's records to data base (some of FileUpload class code should be moved here)
		 * 
		 */
		public int deleteReportRecordsByDate(String reportDate){
			  return reportRecordService.deleteReportRecordsByDate(reportDate);
		}
		
		public void deleteReport(Report report){
			reportService.deleteReport(report);
		}
		
		public void deleteAllReports(){
			reportService.deleteAllReports();
		}
		
		public List<Report> getAllReports(){
			return reportService.getAllReports();
		}
		
		//TODO 
		/*public String modifyReportecordsData(){
			// TODO workaround to solve multireport problem
			 Map<Report, Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>>> currentReportRows = new HashMap<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>();
			 Report currentReport = new ReportImpl(); //TODO IOC
			 currentReport.setReportName(report.getReportName());
			 currentReportRows.put(currentReport, reportRows.get(report));
			// TODO workaround to solve multireport problem E
			List<ReportRecord> uploadedReportRecord = new ArrayList<ReportRecord>();
			List<ReportGroup> creportGroups =new ArrayList<ReportGroup>(existingReports.get(existingReports.indexOf(report)).getReportGroups());//report.getReportGroups();
			List<ReportRow>   creportRows = ReportOperations.getReportRows(creportGroups);
			List<ReportColumn> creportColumn = ReportOperations.getReportColumns(creportRows);
			for (ReportGroup currentReportGroup :ReportOperations.getReportGroups(reportRowscurrentReportRows)) {
				if(creportGroups.contains(currentReportGroup)){
					System.out.println("Report group name "+ currentReportGroup.getGroupName());
					for (ReportRow currentreportRow : ReportOperations.getReportRowsByGroup(reportRowscurrentReportRows,creportGroups.get(creportGroups.indexOf(currentReportGroup)))) {//creportGroups.get(creportGroups.indexOf(currentReportGroup)).getReportRows()) {
						System.out.println("currentreportRow Name "+ currentreportRow.getRowName());
						System.out.println("currentreportRow Size "+ currentreportRow.getReportColumns().size());
						if(creportRows.contains(currentreportRow)){
							for (ReportColumn currentreportColumn : ReportOperations.getReportColumnsByRows(reportRowscurrentReportRows,currentreportRow)creportRows.get(creportRows.indexOf(currentreportRow)).getReportColumns()) {
								if(creportColumn.contains(currentreportColumn)creportColumn.contains(currentreportColumn)){
									ReportColumn tempReportColumn = new ReportColumnImpl(); //TODO IOC
									tempReportColumn.setReportColumnID(currentreportColumn.getReportColumnID());
									System.out.println("currentreportColumn name "+ currentreportColumn.getColumnName() +" tempReportColumn name "+tempReportColumn.getColumnName());
									for (ReportRecord currentreportRecord : ReportOperations.getReportRecordsByRowAndColumn(reportRowscurrentReportRows, currentreportRow, currentreportColumn)getReportRecordsByColumn(reportRows, currentreportColumn)) {
										System.out.println("Record Row name "+currentreportRow +" Record Column Name "+ currentreportColumn.getColumnName() +" record Column_id "+currentreportColumn.getReportColumnID());
										currentreportRecord.setReportColumn(tempReportColumn);
										uploadedReportRecord.add(currentreportRecord);
										System.out.println("record Column ID "+currentreportRecord.getReportColumn().getReportColumnID());
									}
								}
							}
							
						}
					}
				}
			}
			  //System.out.println("uploadedReportRecord size "+ uploadedReportRecord.size());
			reportRowService.saveExistingReportRows(uploadedReportRecord, reportDate, new Date());
		}

	*/

}
