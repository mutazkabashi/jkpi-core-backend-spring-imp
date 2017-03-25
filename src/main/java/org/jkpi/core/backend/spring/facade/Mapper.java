package org.jkpi.core.backend.spring.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.jkpi.core.backend.spring.domain.ReportColumn;
import org.jkpi.core.backend.spring.domain.ReportColumnDetails;
import org.jkpi.core.backend.spring.domain.ReportColumnDetailsImpl;
import org.jkpi.core.backend.spring.domain.ReportColumnImpl;
import org.jkpi.core.backend.spring.domain.ReportRecord;
import org.jkpi.core.backend.spring.domain.ReportRow;
import org.jkpi.core.backend.spring.service.ReportRowService;
import org.jkpi.core.backend.spring.utils.ReportOperations;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("mapper") 
@Scope("prototype") //TODO should be service (Singleton bean) instead of (Request Bean) 
public class Mapper {

	@Resource(name= "reportRowService")
	private ReportRowService reportRowService;
	//public Map<String, List<ReportColumn>> chartData = new TreeMap<String, List<ReportColumn>>();
	public List<String> groupsNames = new ArrayList<String>(); //ChartGroup Names
	public Map<String, List<Double>> columnData = new TreeMap<String, List<Double>>(); //Charts Data
	public Map<String, String> columnColor = new TreeMap<String,String>(); //Charts Data //TODO redesign ( column data and column color) 
	List<ReportRow> reportRows;
	List<ReportColumn> reportValues;
	
	public void clean(){
		groupsNames = new ArrayList<String>(); //ChartGroup Names
		columnData = new TreeMap<String, List<Double>>(); //Charts Data
	}

	public Map<String, List<Double>> createChartGroup(String reportName) throws java.lang.IndexOutOfBoundsException {

		Map<String, List<ReportColumnDetails>> chartData = new TreeMap<String, List<ReportColumnDetails>>();
		List<ReportRecord> reportRows = new LinkedList<ReportRecord>();// =
																	// reportRowService.getReportByName(reportName);
		List<ReportColumnDetails> reportValues = new ArrayList<ReportColumnDetails>();
		//reportRows = reportRowService.getLastUploadedReportsByName(reportName);
		reportRows = reportRowService.getLastUploadedReportsByName(reportName);
		String rowGroup = reportRows.get(0).getReportColumn().getReportRow().getRowName();
		int reportRowscounter = 0;
		for (ReportRecord reportRow : reportRows) {
			System.out.println(" Current Group(ROW) " + reportRow.getReportColumn().getReportRow().getRowName()
					+ " rowGroup variable value " + rowGroup + " Size "
					+ reportRows.size());
			if (!rowGroup.equalsIgnoreCase(reportRow.getReportColumn().getReportRow().getRowName())) {
				chartData.put(rowGroup, ReportOperations.cloneReportColumnDetailList(reportValues));
				reportValues.clear();
				rowGroup = reportRow.getReportColumn().getReportRow().getRowName();
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getReportColumn().getColumnName());
				tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				tempReportColumnDetail.setColumnValue(reportRow.getColumnValue());
				reportValues.add(tempReportColumnDetail);
				// reportValues = new ArrayList<ReportColumn>(); //TODO
			} else if (reportRowscounter == reportRows.size() - 1) {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getReportColumn().getColumnName());
				tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				tempReportColumnDetail.setColumnValue(reportRow.getColumnValue());
				reportValues.add(tempReportColumnDetail/*new ReportColumnImpl(reportRows.get(
						reportRows.size() - 1).getColumnName(), reportRows.get(
						reportRows.size() - 1).getColumnValue())*/);
				chartData.put(rowGroup, ReportOperations.cloneReportColumnDetailList(reportValues));

			} else {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getReportColumn().getColumnName());
				tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				tempReportColumnDetail.setColumnValue(reportRow.getColumnValue());
				reportValues.add(tempReportColumnDetail);
				/*reportValues.add(new ReportColumnImpl(
						reportRow.getColumnName(), reportRow.getColumnValue()));*/
			}

			++reportRowscounter;
		}
		
		return populateC3Charts(chartData);

	}
	
	//TODO for testing (should be changed in future)
	public Map<String, List<Double>> createChartGroup2(String reportName,Date startDate,Date endDate) throws java.lang.IndexOutOfBoundsException {
        clean();
		Map<String, List<ReportColumnDetails>> chartData = new TreeMap<String, List<ReportColumnDetails>>();
		List<ReportRecord> reportRows = new LinkedList<ReportRecord>();// =
																	// reportRowService.getReportByName(reportName);
		List<ReportColumnDetails> reportValues = new ArrayList<ReportColumnDetails>();
		//reportRows = reportRowService.getLastUploadedReportsByName(reportName);
		reportRows = reportRowService.getReportByPeriod(reportName, startDate, endDate);
		String rowGroup = reportRows.get(0).getReportColumn().getReportRow().getRowName();
		int reportRowscounter = 0;
		for (ReportRecord reportRow : reportRows) {
			System.out.println(" Current Group " + reportRow.getReportColumn().getReportRow().getRowName()
					+ " rowGroup variable value " + rowGroup + " Size "
					+ reportRows.size());
			if (!rowGroup.equalsIgnoreCase(reportRow.getReportColumn().getReportRow().getRowName())) {
				chartData.put(rowGroup, ReportOperations.cloneReportColumnDetailList(reportValues));
				reportValues.clear();
				rowGroup = reportRow.getReportColumn().getReportRow().getRowName();
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getReportColumn().getColumnName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				tempReportColumnDetail.setColumnValue(reportRow.getColumnValue());
				reportValues.add(tempReportColumnDetail);
				/*reportValues.add(new ReportColumnImpl(
						reportRow.getColumnName(), reportRow.getColumnValue()));*/
				
			} else if (reportRowscounter == reportRows.size() - 1) {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getReportColumn().getColumnName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				tempReportColumnDetail.setColumnValue(reportRow.getColumnValue());
				reportValues.add(tempReportColumnDetail/*new ReportColumnImpl(reportRows.get(
						reportRows.size() - 1).getColumnName(), reportRows.get(
						reportRows.size() - 1).getColumnValue())*/);
				chartData.put(rowGroup, ReportOperations.cloneReportColumnDetailList(reportValues));
				/*reportValues.add(new ReportColumnImpl(reportRows.get(
						reportRows.size() - 1).getColumnName(), reportRows.get(
						reportRows.size() - 1).getColumnValue()));
				chartData.put(rowGroup, ReportOperations.CloneReportColumnDetailList(reportValues));*/

			} else {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getReportColumn().getColumnName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				tempReportColumnDetail.setColumnValue(reportRow.getColumnValue());
				reportValues.add(tempReportColumnDetail);
				/*reportValues.add(new ReportColumnImpl(
						reportRow.getColumnName(), reportRow.getColumnValue()));*/
			}

			++reportRowscounter;
		}
		
		return populateC3Charts(chartData);

	}
	
	/**
	 * TODO we use <code>rowID <code> to calculate Row's Records total value , we should use different way   
	 * @param reportName
	 * @return
	 * @throws java.lang.IndexOutOfBoundsException
	 */
	public Map<String, List<Double>> createSummaryChart(String reportName) throws java.lang.IndexOutOfBoundsException {

		Map<String, List<ReportColumnDetails>> chartData = new TreeMap<String, List<ReportColumnDetails>>();
		List<ReportRecord> reportRecords = new LinkedList<ReportRecord>();// =
																	// reportRowService.getReportByName(reportName);
		List<ReportColumnDetails> reportValues = new ArrayList<ReportColumnDetails>();
		//reportRows = reportRowService.getLastUploadedReportsByName(reportName);
		reportRecords = reportRowService.getLastUploadedReportsByName(reportName);
		String rowGroup = reportRecords.get(0).getReportColumn().getReportRow().getReportGroup().getGroupName(); //get Group Name
		List<ReportRow> reportRows = new ArrayList<ReportRow>();
		reportRows = ReportOperations.getReportRecordsRows(reportRecords);
		int reportRowscounter = 0;
		for (ReportRow reportRow : reportRows) {
			System.out.println(" Current ReportGroup " + reportRow.getReportGroup().getGroupName()
					+ " rowGroup variable value " + rowGroup + " Size "
					+ reportRows.size());
			if (!rowGroup.equalsIgnoreCase(reportRow.getReportGroup().getGroupName())) {
				chartData.put(rowGroup, ReportOperations.cloneReportColumnDetailList(reportValues));
				reportValues.clear();
				rowGroup = reportRow.getReportGroup().getGroupName();
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getRowName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				//TODO we use rowID to calculate Row's Records total value
				tempReportColumnDetail.setColumnValue(new BigDecimal(reportRow.getReportRowID())); 
				reportValues.add(tempReportColumnDetail);
				// reportValues = new ArrayList<ReportColumn>(); //TODO
			} else if (reportRowscounter == reportRows.size() - 1) {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getRowName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				//TODO we use rowID to calculate Row's Records total value
				tempReportColumnDetail.setColumnValue(new BigDecimal(reportRow.getReportRowID())); 
				reportValues.add(tempReportColumnDetail/*new ReportColumnImpl(reportRows.get(
						reportRows.size() - 1).getColumnName(), reportRows.get(
						reportRows.size() - 1).getColumnValue())*/);
				chartData.put(rowGroup, ReportOperations.cloneReportColumnDetailList(reportValues));

			} else {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getRowName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				//TODO we use rowID to calculate Row's Records total value
				tempReportColumnDetail.setColumnValue(new BigDecimal(reportRow.getReportRowID())); 
				reportValues.add(tempReportColumnDetail);
				/*reportValues.add(new ReportColumnImpl(
						reportRow.getColumnName(), reportRow.getColumnValue()));*/
			}

			++reportRowscounter;
		}
		
		return populateC3Charts(chartData);

	}
	


	/**
	 * TODO we use <code>rowID <code> to calculate Row's Records total value , we should use different way   
	 * @param reportName
	 * @return
	 * @throws java.lang.IndexOutOfBoundsException
	 */
	public Map<String, List<Double>> createSummaryChartByGroupName(String reportName,String groupName) throws java.lang.IndexOutOfBoundsException {

		Map<String, List<ReportColumnDetails>> chartData = new TreeMap<String, List<ReportColumnDetails>>();
		List<ReportRecord> reportRecords = new LinkedList<ReportRecord>();// =
																	// reportRowService.getReportByName(reportName);
		List<ReportColumnDetails> reportValues = new ArrayList<ReportColumnDetails>();
		//reportRows = reportRowService.getLastUploadedReportsByName(reportName);
		reportRecords = reportRowService.getLastUploadedReportsByName(reportName);
		String rowGroup ="";//= reportRecords.get(0).getReportColumn().getReportRow().getReportGroup().getGroupName(); //get Group Name
		List<ReportRow> reportRows = new ArrayList<ReportRow>();
		reportRows = ReportOperations.getReportRecordsRows(reportRecords);
		int reportRowscounter = 0;
		for (ReportRow reportRow : reportRows) {
			/*System.out.println(" Current ReportGroup " + reportRow.getReportGroup().getGroupName()
					+ " rowGroup variable value " + rowGroup + " Size "
					+ reportRows.size());*/
			if (!groupName.equalsIgnoreCase(reportRow.getReportGroup().getGroupName())) {
				if(rowGroup.equalsIgnoreCase(groupName)){
				chartData.put(rowGroup, ReportOperations.cloneReportColumnDetailList(reportValues));
				/*reportValues.clear();
				rowGroup = reportRow.getReportGroup().getGroupName();
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getRowName());
				//TODO we use rowID to calculate Row's Records total value
				tempReportColumnDetail.setColumnValue(new BigDecimal(reportRow.getReportRowID())); 
				reportValues.add(tempReportColumnDetail);*/
				break;
				}
				else{
					continue;
				}
			} 
			
			else if (groupName.equalsIgnoreCase(reportRow.getReportGroup().getGroupName())) {
				rowGroup = groupName;
				/*chartData.put(rowGroup, ReportOperations.CloneReportColumnDetailList(reportValues));
				reportValues.clear();
				rowGroup = reportRow.getReportGroup().getGroupName();*/
				if(reportValues.contains(new ReportColumnDetailsImpl(reportRow.getRowName(),new BigDecimal(0) ,""))){ //TODO IOC & looking for better way
					ReportColumnDetails savedColumnDetail  = reportValues.get(reportValues.indexOf(new ReportColumnDetailsImpl(reportRow.getRowName(),new BigDecimal(0) ,""))); //TODO IOC & looking for better way 
					savedColumnDetail.setColumnValue(new BigDecimal(savedColumnDetail.getColumnValue().longValue()+reportRow.getReportRowID()));
				}
				else{
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getRowName());
				//TODO we use rowID to calculate Row's Records total value
				tempReportColumnDetail.setColumnValue(new BigDecimal(reportRow.getReportRowID())); 
				reportValues.add(tempReportColumnDetail);
				continue;
				}
			}
			
			/*else if (reportRowscounter == reportRows.size() - 1) {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getRowName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				//TODO we use rowID to calculate Row's Records total value
				tempReportColumnDetail.setColumnValue(new BigDecimal(reportRow.getReportRowID())); 
				reportValues.add(tempReportColumnDetailnew ReportColumnImpl(reportRows.get(
						reportRows.size() - 1).getColumnName(), reportRows.get(
						reportRows.size() - 1).getColumnValue()));
				chartData.put(rowGroup, ReportOperations.CloneReportColumnDetailList(reportValues));

			} else {
				ReportColumnDetails tempReportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				tempReportColumnDetail.setColumnName(reportRow.getRowName());
				//tempReportColumnDetail.setColumnColor(reportRow.getReportColumn().getColumnColor());
				//TODO we use rowID to calculate Row's Records total value
				tempReportColumnDetail.setColumnValue(new BigDecimal(reportRow.getReportRowID())); 
				reportValues.add(tempReportColumnDetail);
				reportValues.add(new ReportColumnImpl(
						reportRow.getColumnName(), reportRow.getColumnValue()));
			}*/

			++reportRowscounter;
		}
		
		return populateC3Charts(chartData);

	}
	

	//TODO should be moved to other packge
	public Map<String, List<Double>> populateC3Charts(
			Map<String, List<ReportColumnDetails>> chartData) {
		//List<String> groupsNames = new ArrayList<String>();
		//Map<String, List<Double>> columnData = new TreeMap<String, List<Double>>();
		List<Double> data = new ArrayList<Double>();
		for (Entry<String, List<ReportColumnDetails>> currentRow : chartData
				.entrySet()) {
			for (ReportColumnDetails reportColumn : currentRow.getValue()) {
				if (!groupsNames.contains(currentRow.getKey())) {
					groupsNames.add(currentRow.getKey());
				}
				// columnName = reportColumn.getColumnName();
				if (!columnData.containsKey(reportColumn.getColumnName())) {
					List<Double> temp = new ArrayList<Double>();
					temp.add(reportColumn.getColumnValue().doubleValue());
					columnData.put(reportColumn.getColumnName(), temp);
					if(reportColumn.getColumnColor() !=null) // Use Old Color value (initChart() Method)
					columnColor.put(reportColumn.getColumnName(),reportColumn.getColumnColor());
				} else {
					columnData.get(reportColumn.getColumnName()).add(
							reportColumn.getColumnValue().doubleValue());
				}

			}
		}

		return columnData;

	}
	

	

}
