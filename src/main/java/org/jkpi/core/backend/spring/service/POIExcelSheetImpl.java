package org.jkpi.core.backend.spring.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jkpi.core.backend.api.service.ReportEngineService;
import org.jkpi.core.backend.api.domain.Report;
import org.jkpi.core.backend.api.domain.ReportColumn;
import org.jkpi.core.backend.api.domain.ReportColumnDetails;
import org.jkpi.core.backend.api.domain.ReportGroup;
import org.jkpi.core.backend.api.domain.ReportRecord;
import org.jkpi.core.backend.api.domain.ReportRow;

/**
 * Excelsheet Implementation of {@code ReportEngineService} using  Apache POI library
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see Report
 * @see ReportGroup
 * @see ReportRow
 * @see ReportColumnDetails
 * @see <a href="https://poi.apache.org"> Apache POI library </a>
 */
public class POIExcelSheetImpl implements ReportEngineService {
	
   Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>> reportValues = new HashMap<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>();
   Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>  reportGroups = new HashMap<ReportGroup, Map<ReportRow,List<ReportColumnDetails>>>();
   Map<ReportRow,List<ReportColumnDetails>>  reportRows = new HashMap<ReportRow, List<ReportColumnDetails>>();
   List<ReportColumnDetails> reportColumnsValues = new ArrayList<ReportColumnDetails>();
   List<ReportColumn> reportColumns = new ArrayList<ReportColumn>();
   ReportGroup reportGroup = new ReportGroupImpl(); //TODO IOC
   ReportRow creportRow = new ReportRowImpl(); //TODO IOC

	public Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>> extract(String filePath){
		reportValues = new HashMap<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>();
	    reportGroups = new HashMap<ReportGroup, Map<ReportRow,List<ReportColumnDetails>>>();
	    reportRows = new HashMap<ReportRow, List<ReportColumnDetails>>();
	   reportColumnsValues = new ArrayList<ReportColumnDetails>();
	   reportColumns = new ArrayList<ReportColumn>();
	   reportGroup = new ReportGroupImpl(); //TODO IOC
	   creportRow = new ReportRowImpl(); //TODO IOC
		try {

			//List<String> columnHeader = excelSheetHeader(filePath);
			FileInputStream file = new FileInputStream(new File(filePath));

			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			//Get the No Of sheets
			int sheetsNo = workbook.getNumberOfSheets();
			
			for (int i = 0; i < sheetsNo; i++) {
				
			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(i);
			System.out.println("Excel sheet Type "+getExcelSheetType(sheet));
			if(getExcelSheetType(sheet).equalsIgnoreCase("simple")){
				extractSimpleSheet(sheet);
			}
			else{
				extractComplexSheet(sheet);
			}
			}
			file.close();
		}catch (FileNotFoundException e) {
				e.printStackTrace();
		}catch (IOException e) {
				e.printStackTrace();
			}
		return reportValues;
	}

	public   Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>> extractSimpleSheet(XSSFSheet sheet) {
		     boolean lastRow = false;
				
			// Get first sheet from the workbook
			
			
			//List<String> columnHeader = excelSheetHeader(filePath,i);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			 if (reportColumns.size()==0){
	 				reportColumns = exceelSheetHeader(rowIterator);
	 				reportGroup.setGroupName("");
	 				creportRow.setRowName("");
	 				//++ rowNumber;
	                }
			while (rowIterator.hasNext() && !lastRow) {
				Row row = rowIterator.next();
				// int rowCounter = 0;
				if (row.getRowNum() == 0) {
					continue;
				}
				//TODO
				ReportColumnDetails reportColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
				//reportColumnDetail.setInsertionDate(new Date());
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				int columnCounter = 0;
				while (cellIterator.hasNext()
						&& columnCounter <= reportColumns.size()) {   //TODO could we use Code reportColumns.size() to create new row in complex report
					++columnCounter;
					Cell cell = cellIterator.next();
					

					// TODO so we want Check these if statmemet everytime
					// (boolean varaiable)

					// excelSheetRowHeader(filePath);

					if (cell.getColumnIndex() == 0
							&& cell.getStringCellValue().trim().length() > 1
							&& row.getRowNum() >= 1 || (reportRows.size()>0 && cell.getCellType()==Cell.CELL_TYPE_BLANK)) {
						if(reportColumnsValues.size()>0){
							ReportRow tempreportRow = new ReportRowImpl(); //TODO IOC
							tempreportRow.setRowName(creportRow.getRowName());
							reportRows.put(tempreportRow, cloneReportColumnDeatilsList(reportColumnsValues));
							reportColumnsValues= new ArrayList<ReportColumnDetails>(); //TODO IOC //NEW
						}
						creportRow.setRowName(cell.getStringCellValue());
						if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
							lastRow = true; // Pasrse won't parse empty Rows
							break;
						}else{
						
						continue;
						}
					}
					if (cell.getColumnIndex() > 0) {
						// System.out.println("Column index "+
						// columnHeader.indexOf(columnHeader.get(cell.getColumnIndex())));
						reportColumnDetail.setColumnName(reportColumns.get(cell
								.getColumnIndex() - 1).getColumnName());
						
						// reportRow.setColumnValue(cell.getStringCellValue());
						reportColumnDetail.setColumnValue(BigDecimal.valueOf(cell
								.getNumericCellValue()));
					}

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						// System.out.print(cell.getBooleanCellValue() +
						// "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						// System.out.print(cell.getNumericCellValue() +
						// "\t\t");
						break;
					case Cell.CELL_TYPE_STRING:
						/*
						 * System.out.print(cell.getStringCellValue().trim() +
						 * "\t\t");
						 */
						break;
					}
					
					ReportColumnDetails tempColumnDetails = new ReportColumnDetailsImpl(); //TODO Spring Inject (IOC)
					tempColumnDetails.setColumnName(reportColumns.get(cell.getColumnIndex()-1).getColumnName()); // TODO Method to Clone
					tempColumnDetails.setColumnValue(BigDecimal.valueOf(cell.getNumericCellValue()));
					tempColumnDetails.setColumnColor(reportColumns.get(cell.getColumnIndex()-1).getColumnColor());
					//temp.setDateCreated(reportColumnDetail.getInsertionDate());
					/*ReportRow tempreportRow = new ReportRowImpl(); //TODO IOC
					tempreportRow.setRowName(reportRow.getRowName());*/
					reportColumnsValues.add(tempColumnDetails);
					
					
				}
				//Last Row in Report
				ReportRow tempreportRow = new ReportRowImpl(); //TODO IOC
				tempreportRow.setRowName(creportRow.getRowName());
				reportRows.put(tempreportRow, cloneReportColumnDeatilsList(reportColumnsValues));
				reportColumnsValues= new ArrayList<ReportColumnDetails>(); //TODO IOC //NEW
			}
			  Report tempReport = new ReportImpl(); //TODO Spring Inject (IOC)
			  tempReport.setReportName(sheet.getSheetName());
			  ReportGroup tempreportGroup = new ReportGroupImpl(); //TODO IOC
			  tempreportGroup.setGroupName(tempReport.getReportName()+"_Group");
			  reportGroups.put(tempreportGroup,cloneReportRowsMap(reportRows));
			  reportValues.put(tempReport, cloneReporGroupsMap(reportGroups));
			  reportRows = new HashMap<ReportRow, List<ReportColumnDetails>>(); //TODO IOC
			  reportGroups = new HashMap<ReportGroup, Map<ReportRow,List<ReportColumnDetails>>>();
			
			
			/*
			 * FileOutputStream out = new FileOutputStream(new
			 * File("C:\\test.xls")); workbook.write(out); out.close();
			 */

		 
		return reportValues;
	}
    

	
	
	public   Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>> extractComplexSheet(XSSFSheet sheet){
		 
		

			
			System.out.println("Report Name "+ sheet.getSheetName());
			//List<String> columnHeader = excelSheetHeader(filePath,i);

			// Iterate through each rows from first sheet
			//Iterator rowIterator = sheet.rowIterator();
            //ReportGroup tempGroup  = new ReportGroupImpl();
			
            //ReportColumn creportColumn = new ReportColumnImpl(); //TODO IOC
            //ReportRecord creportRecord = new ReportRecordImpl(); //TODO IOC
           // int rowNumber =0;
            int noOfSheetRows = sheet.getPhysicalNumberOfRows();//LastRowNum();
            
            //Excell Sheet Header
             
            //XSSFRow headerRow = sheet.getRow(rowNumber);
            Iterator<Row> rowIterator = sheet.iterator();
               if (reportColumns.size()==0){
				reportColumns = exceelSheetHeader(rowIterator);
				reportGroup.setGroupName("");
				creportRow.setRowName("");
				//++ rowNumber;
               }
               boolean lastRow = false;
               boolean groupNamefilled=false;
  			 while(rowIterator.hasNext() /*&& rowNumber <noOfSheetRows*/ && !lastRow){
  				 Row row = /*sheet.getRow(rowNumber);*/rowIterator.next();
  				 Iterator<Cell> cellIterator = row.cellIterator();
  				while(cellIterator.hasNext()) {
  					Cell cell = cellIterator.next();
  					//System.out.println("Before case cell type "+cell.getCellType());
  					Map<ReportColumn, ReportRecord> reportRecords;
  					switch (cell.getCellType()) {
  					case Cell.CELL_TYPE_BOOLEAN:
  						break;
  					case Cell.CELL_TYPE_NUMERIC:
  						    ReportColumnDetails nColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
  	   					    nColumnDetail.setColumnName(reportColumns.get(cell.getColumnIndex()-1).getColumnName());
  	   					    nColumnDetail.setColumnColor(reportColumns.get(cell.getColumnIndex()-1).getColumnColor());
  	   					    //TODO ReportColumn Shoud be attaced to Report instead of reportGroup 
  							nColumnDetail.setColumnValue(BigDecimal.valueOf(cell.getNumericCellValue()));
  							reportColumnsValues.add(nColumnDetail);
  	   						//reportRows.put(creportRow,creportRecord); 
  							System.out.println("current Row NO "+row.getRowNum() +" total row now "+ sheet.getPhysicalNumberOfRows());
  							System.out.println("cell  Column Index "+ cell.getColumnIndex() + " report Columns Size " + (reportColumns.size()));
  							 if (row.getRowNum() == sheet.getPhysicalNumberOfRows()-1 && cell.getColumnIndex() == (reportColumns.size())) {
  							//Add Last Row
  		   						ReportRow nreportRow = new ReportRowImpl(); //TODO IOC
  								nreportRow.setRowName(creportRow.getRowName());
  								//reportRows.put(nreportRow, reportRecords);
  								reportRows.put(nreportRow, cloneReportColumnDeatilsList(reportColumnsValues));
  								reportColumnsValues= new ArrayList<ReportColumnDetails>(); //TODO IOC
  								//creportRow.setRowName(cell.getStringCellValue());
  								 //Add Last Row E
  		   						ReportGroup nreportGroup = new ReportGroupImpl(); //TODO IOC
  		 						nreportGroup.setGroupName(reportGroup.getGroupName());
  		 						//reportGroups.put(nreportGroup,reportRows );
  		 					   reportGroups.put(nreportGroup,cloneReportRowsMap(reportRows));
  	 						   reportRows= new HashMap<ReportRow, List<ReportColumnDetails>>(); //TODO IOC
  		 						System.out.println("ngrpup name "+ nreportGroup.getGroupName());
  		   						  lastRow = true;
  	     						break; //TODO break cellIterator while loop ????
  							}
  	   					
  						// }
  						/* System.out.println(cell.getNumericCellValue() +
  					     "\t\t");*/
  						// ++ rowNumber;  //TODO default Block 
  						break;
  					case Cell.CELL_TYPE_STRING:
  						System.out.println("Row Name Value "+ cell.getStringCellValue());
  						if(cell.getColumnIndex()==0 && row.getCell(1).getCellType() != Cell.CELL_TYPE_FORMULA){
  							if(!creportRow.getRowName().equalsIgnoreCase(row.getCell(0).getStringCellValue())){
  								if(creportRow.getRowName().equalsIgnoreCase("")){
  									creportRow.setRowName(row.getCell(0).getStringCellValue());
  	     							 break;
  	     						 }
  							
  							ReportRow nreportRow = new ReportRowImpl(); //TODO IOC
  							nreportRow.setRowName(creportRow.getRowName());
  							reportRows.put(nreportRow, cloneReportColumnDeatilsList(reportColumnsValues));
  							reportColumnsValues= new ArrayList<ReportColumnDetails>(); //TODO IOC
  							creportRow.setRowName(cell.getStringCellValue());
  							
  							}
  						 /*else if (creportRow.getRowName().trim().isEmpty()) { //For the first Group
  							creportRow.setRowName(cell.getStringCellValue());
  						}*/
  						}
  					//To solve the last row in each group
  						if(cell.getColumnIndex()==0 && row.getCell(1).getCellType() == Cell.CELL_TYPE_FORMULA && !creportRow.getRowName().equalsIgnoreCase("")){ 
  	   						ReportRow nreportRow = new ReportRowImpl(); //TODO IOC
  							nreportRow.setRowName(creportRow.getRowName());
  							reportRows.put(nreportRow, cloneReportColumnDeatilsList(reportColumnsValues));
  							reportColumnsValues= new ArrayList<ReportColumnDetails>(); //TODO IOC
  							creportRow.setRowName("");
  							//creportRow.setRowName(cell.getStringCellValue());
  							 
  						}
  						 
  						  //++ rowNumber;  //TODO default Block  
  						break;
  					//TODO 
  					/* Cell.CELL_TYPE_FORMULA Code Should be Moved to Cell.CELL_TYPE_STRING since we are already Check Cell.CELL_TYPE_FORMULA
  					 * see the last IF statement in case Cell.CELL_TYPE_STRING:
  					 * 
  					 */
  					case Cell.CELL_TYPE_FORMULA:
  						//System.out.println("cell value "+reportGroup.getGroupName());
  						
    					 if(!reportGroup.getGroupName().equalsIgnoreCase(row.getCell(0).getStringCellValue())){
    						 if(reportGroup.getGroupName().equalsIgnoreCase("")){
    							reportGroup.setGroupName(row.getCell(0).getStringCellValue());
    							 break;
    						 }

    						ReportGroup nreportGroup = new ReportGroupImpl(); //TODO IOC
    						nreportGroup.setGroupName(reportGroup.getGroupName());
    						//reportGroups.put(nreportGroup,reportRows );
    						reportGroups.put(nreportGroup,cloneReportRowsMap(reportRows));
    						reportRows= new HashMap<ReportRow, List<ReportColumnDetails>>(); //TODO IOC
    						reportGroup.setGroupName(row.getCell(0).getStringCellValue());
    						break; //TODO break cellIterator while loop ????
    						//System.out.println("ngrpup name "+ nreportGroup.getGroupName());
    					 }
    					
  						break;
  					case Cell.CELL_TYPE_BLANK: //TODO Last Row when there is not Blank cell in the sheet
  					 //Add Last Row
  						ReportRow nreportRow = new ReportRowImpl(); //TODO IOC
						nreportRow.setRowName(creportRow.getRowName());
						//reportRows.put(nreportRow, reportRecords);
						reportRows.put(nreportRow, cloneReportColumnDeatilsList(reportColumnsValues));
						reportColumnsValues= new ArrayList<ReportColumnDetails>(); //TODO IOC
						creportRow.setRowName(cell.getStringCellValue());
						 //Add Last Row E
  						ReportGroup nreportGroup = new ReportGroupImpl(); //TODO IOC
						nreportGroup.setGroupName(reportGroup.getGroupName());
						//reportGroups.put(nreportGroup,reportRows );
						reportGroups.put(nreportGroup,cloneReportRowsMap(reportRows));
						reportRows= new HashMap<ReportRow, List<ReportColumnDetails>>(); //TODO IOC
						//reportGroup.setGroupName(row.getCell(0).getStringCellValue());
						System.out.println("ngrpup name "+ nreportGroup.getGroupName());
  						  lastRow = true;
  						  /*System.out.println(cell.getStringCellValue().trim() +
  						  "\t\t");*/
  						 
  						break;
  					}
  				
  				}	
  			}

						 
			//}
			
			//printReportRows(reportRows);
			Report report = new ReportImpl();
			report.setReportName(sheet.getSheetName());
			reportValues.put(report, cloneReporGroupsMap(reportGroups));
			reportGroups = new HashMap<ReportGroup, Map<ReportRow,List<ReportColumnDetails>>>();
			int i =0;
		
         return reportValues;
		
		
	}

	public  List<ReportColumn> exceelSheetHeader(Iterator<Row> rowIterator){
		 XSSFRow headerRow = (XSSFRow) rowIterator.next();//sheet.getRow(0);
		 ReportColumn tempReportColumn ;
		 List<ReportColumn> columns = new ArrayList<ReportColumn>();
		// For each row, iterate through each columns
			Iterator headerCellIterator = headerRow.cellIterator();
			
			while (headerCellIterator.hasNext()) {
           tempReportColumn = new ReportColumnImpl(); //TODO IOC
           XSSFCell cell = (XSSFCell)headerCellIterator.next();
				System.out.println("cell value "+cell.getStringCellValue()+" cell index "+ cell.getCellType() + " = "+cell.CELL_TYPE_STRING);
				if (cell.getColumnIndex() > 0
						&& (cell.CELL_TYPE_NUMERIC == cell.getCellType()) /*cell.getStringCellValue().trim().length() > 1*/) { //TODO <--- fixing trim
					tempReportColumn.setColumnName(((Double)cell.getNumericCellValue()).toString());
					XSSFColor  cellColor = (cell.getCellStyle().getFillBackgroundColorColor());
					tempReportColumn.setColumnColor("#"+cellColor.getARGBHex().replaceAll("FF", ""));
					columns.add(tempReportColumn);
					
				}else if (cell.getColumnIndex() > 0
						&& (cell.CELL_TYPE_STRING == cell.getCellType()) && (cell.getStringCellValue().trim().length() > 1)) {
					tempReportColumn.setColumnName(cell.getStringCellValue().trim());
					XSSFColor  cellColor = (cell.getCellStyle().getFillBackgroundColorColor());
					System.out.println("Background Color "+cellColor.getARGBHex());
					tempReportColumn.setColumnColor("#"+cellColor.getARGBHex().replaceAll("FF", ""));
					columns.add(tempReportColumn);
				}
					
				

			}
			
			return columns;
	}

	
	//TODO rengineering
	public  List<String> excelSheetHeader(String filePath, int sheetNo) {
		ArrayList<String> result = new ArrayList<String>();
		try {

			FileInputStream file = new FileInputStream(new File(filePath));

			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(sheetNo);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					
					if (cell.getColumnIndex() > 0
							&& (cell.CELL_TYPE_NUMERIC == cell.getCellType()) /*cell.getStringCellValue().trim().length() > 1*/) { //TODO <--- fixing trim
						result.add(((Double)cell.getNumericCellValue()).toString());
					}else if (cell.getColumnIndex() > 0
							&& (cell.CELL_TYPE_STRING == cell.getCellType()) && cell.getStringCellValue().trim().length() > 1) {
						result.add(cell.getStringCellValue());
					}
						
					

				}
				break;
				// System.out.println("");
			}
			for (String row : result) {
				System.out.println("columns header " + row + " column index "
						+ result.indexOf(row));
			}
			file.close();
			/*
			 * FileOutputStream out = new FileOutputStream(new
			 * File("C:\\test.xls")); workbook.write(out); out.close();
			 */

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public  List<ReportColumnDetails> cloneReportColumnDeatilsList(List<ReportColumnDetails> list) {
		List<ReportColumnDetails> cloneList = new ArrayList<ReportColumnDetails>();
		for (ReportColumnDetails currentColumn : list) {
			ReportColumnDetails nColumnDetail = new ReportColumnDetailsImpl(); //TODO IOC
			nColumnDetail.setColumnColor(currentColumn.getColumnColor());
			nColumnDetail.setColumnName(currentColumn.getColumnName());
			nColumnDetail.setColumnValue(currentColumn.getColumnValue());
			cloneList
					.add(nColumnDetail);
		}
		return cloneList;
	}
	
	public  Map<ReportRow,List<ReportColumnDetails>> cloneReportRowsMap(Map<ReportRow,List<ReportColumnDetails>> rows) {
		Map<ReportRow,List<ReportColumnDetails>> reportRows = new HashMap<ReportRow,List<ReportColumnDetails>>();
		Set<Entry<ReportRow, List<ReportColumnDetails>>> rowsValues=rows.entrySet();
		for (Entry<ReportRow, List<ReportColumnDetails>> entry : rowsValues) {
			reportRows.put(entry.getKey(),cloneReportColumnDeatilsList(entry.getValue()));
		}
		return reportRows;
	}
	
	public  Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>> cloneReporGroupsMap(Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>> groups) {
		Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>> reportGroups = new HashMap<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>();
		Set<Entry<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>> groupValues=groups.entrySet();
		for (Entry<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> entry : groupValues) {
			reportGroups.put(entry.getKey(),cloneReportRowsMap(entry.getValue()));
		}
		return reportGroups;
	}
	
	public  void printReportRows(Map<ReportRow,List<ReportColumnDetails>> reportRowsValues){
		for (ReportRow cRow : reportRowsValues.keySet()) {
			System.out.println("Current Row "+ cRow.getRowName());
			for (ReportColumnDetails cColumnValues : reportRowsValues.get(cRow)) {
				System.out.println("current Column Name "+cColumnValues.getColumnName());
				System.out.println("current Column Value"+cColumnValues.getColumnValue());
				System.out.println("current Column Color"+cColumnValues.getColumnColor());
			}	
		}
	}
	
	public  void printReportValues(Map<Report, Map<ReportGroup,Map<ReportRow,List<ReportColumnDetails>>>>reportValues){
		 for ( Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportGroupe : reportValues.values()) {
			 for (ReportGroup currentReportGroup : reportGroupe.keySet()) {
				System.out.println("Group Name "+ currentReportGroup.getGroupName());
				 Map<ReportRow,List<ReportColumnDetails>>cReportRows =reportGroupe.get(currentReportGroup);
					for (ReportRow currentReportRow : cReportRows.keySet()) {
						System.out.println("ReportRow Name "+ currentReportRow.getRowName());
						List<ReportColumnDetails>cReportColumnDetails = cReportRows.get(currentReportRow);
						for ( ReportColumnDetails currentReportRecord : cReportColumnDetails) {
								System.out.println("ReportColumn Name "+ currentReportRecord.getColumnName());
								System.out.println("ReportColumn color "+ currentReportRecord.getColumnColor());
								System.out.println("Reportrecord value"+ currentReportRecord.getColumnValue());
							
						}
					}
					
			}
			  
		}
	}
	
	public String getExcelSheetType(XSSFSheet sheet){
		String result = "simple";
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				if(result.equalsIgnoreCase("complex")){
					break;
				}
				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext() && !result.equalsIgnoreCase("complex") ) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_FORMULA:
						result = "complex";
					    break;
					
					}
					

				}
				
				// System.out.println("");
			}
			
			
		return result;
	}
	

			/*
	 * f you have data int I rows and J columns you should try code like this:
	 * 
	 * for (int i = 0; i < sheetData.size(); i++) { List list = (List)
	 * sheetData.get(i); for (int j = 0; j < list.size(); j++) { HSSFCell
	 * employeeid = (HSSFCell) list.get(j);
	 * System.out.print(employeeid.getRichStringCellValue().getString()); if (j
	 * < list.size() - 1) { System.out.print(", "); } } System.out.println("");
	 * }
	 */

	/*
	 * You have to skip first two rows using rownum().Here is the sample code
	 * 
	 * HSSFWorkbook workBook = new HSSFWorkbook (fileSystem); HSSFSheet sheet =
	 * workBook.getSheetAt (0); Iterator<HSSFRow> rows = sheet.rowIterator ();
	 * 
	 * while (rows.hasNext ()) { HSSFRow row = rows.next (); // display row
	 * number in the console. System.out.println ("Row No.: " + row.getRowNum
	 * ()); if(row.getRowNum()==0 || row.getRowNum()==1){ continue; //just skip
	 * the rows if row number is 0 or 1 } }
	 * 
	 * url
	 * http://stackoverflow.com/questions/13639374/how-to-read-excel-file-omitting
	 * -first-two-rows
	 */

}
