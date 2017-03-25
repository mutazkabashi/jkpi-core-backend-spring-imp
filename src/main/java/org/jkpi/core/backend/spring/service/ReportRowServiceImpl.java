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
package org.jkpi.core.backend.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.jkpi.core.backend.api.dao.ReportRowDao;
import org.jkpi.core.backend.api.domain.Report;
import org.jkpi.core.backend.api.domain.ReportColumn;
import org.jkpi.core.backend.api.domain.ReportColumnDetails;
import org.jkpi.core.backend.api.domain.ReportGroup;
import org.jkpi.core.backend.api.domain.ReportRecord;
import org.jkpi.core.backend.api.domain.ReportRow;
import org.jkpi.core.backend.api.service.ReportColumnService;
import org.jkpi.core.backend.api.service.ReportGroupService;
import org.jkpi.core.backend.api.service.ReportRecordService;
import org.jkpi.core.backend.api.service.ReportRowService;
import org.jkpi.core.backend.api.service.ReportService;
import org.jkpi.core.backend.spring.dao.ReportRowDaoImpl;
import org.jkpi.core.backend.spring.domain.ReportColumnImpl;
import org.jkpi.core.backend.spring.domain.ReportRecordImpl;
import org.jkpi.core.backend.spring.utils.DateOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//TODO We should use Facade Class/Service
/**
 * Spring Implementation of {@link ReportRowService}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportService
 * @see ReportGroupService
 * @see ReportColumnService
 * @see ReportRecordService
 * @see ReportRowDao
 *
 */
@Service("reportRowService")
public class ReportRowServiceImpl implements ReportRowService {

	@Resource(name = "reportService")
	private ReportService reportService;
	@Resource(name = "reportGroupService")
	private ReportGroupService reportGroupService;
	@Resource(name = "reportColumnService")
	private ReportColumnService reportColumnService;
	@Resource(name = "reportRecordService")
	private ReportRecordService reportRecordService;

	@Resource(name = "reportRowDao")
	private ReportRowDao reportRowDao = new ReportRowDaoImpl();

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ReportRow saveReportRow(ReportRow reportRow) {
		return reportRowDao.save(reportRow);
	}

	// TODO we should use facade class/Service
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveNewReportRows(Map<ReportGroup, Map<ReportRow, List<ReportColumnDetails>>> reportRows, Report report,
			String insertionDate) {
		Report temp = reportService.saveReport(report);
		for (ReportGroup currentReportGroup : reportRows.keySet()) {
			// currentReportGroup.setGroupName(currentReportGroup.getGroupName().trim());
			// //TODO Trim => Exception
			System.out.println("Group Name " + currentReportGroup.getGroupName());
			currentReportGroup.setReport(temp);
			ReportGroup currentReportGroup2 = reportGroupService.saveReportGroup(currentReportGroup);
			Map<ReportRow, List<ReportColumnDetails>> cReportRows = reportRows.get(currentReportGroup);
			for (ReportRow currentReportRow : cReportRows.keySet()) {
				System.out.println("ReportRow Name " + currentReportRow.getRowName());
				currentReportRow.setReportGroup(currentReportGroup2);
				ReportRow currentReportRow2 = saveReportRow(currentReportRow);
				List<ReportColumnDetails> cReportColumnDetails = cReportRows.get(currentReportRow);
				for (ReportColumnDetails currentReportRecord : cReportColumnDetails) {
					System.out.println("ReportColumn Name " + currentReportRecord.getColumnName());
					System.out.println("ReportColumn color " + currentReportRecord.getColumnColor());
					System.out.println("Reportrecord value" + currentReportRecord.getColumnValue());
					ReportColumn currentColumn = new ReportColumnImpl(); // TODO
																			// IOC
					ReportRecord currentRecord = new ReportRecordImpl(); // TODO
																			// IOC
					currentColumn.setColumnName(
							currentReportRecord.getColumnName()/* .trim() */);
					currentColumn.setColumnColor(currentReportRecord.getColumnColor());
					currentColumn.setReportRow(currentReportRow2);
					ReportColumn currentColumn2 = reportColumnService.saveReportColumn(currentColumn);
					currentRecord.setColumnValue(currentReportRecord.getColumnValue());
					currentRecord.setReportColumn(currentColumn2);
					currentRecord.setReportDate(insertionDate);
					currentRecord.setInsertionDate(
							DateOperations.convertStringToDate("01-" + insertionDate, "dd-MMMM-yyyy")); // TODO
					currentRecord = reportRecordService.saveReportRecord(currentRecord);

				}
			}

		}
		/*
		 * for (ReportRow reportRow : reportRows) { reportRow.setReport(temp);
		 * reportRow.setDateCreated(creationDate); reportRowDao.save(reportRow);
		 * }
		 */
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveExistingReportRows(List<ReportRecord> reportRecords, String reportDate, Date insertionDate) {

		for (ReportRecord reportRecord : reportRecords) {
			// reportRecord.setReportColumn(reportColumn);
			reportRecord.setReportDate(reportDate);
			reportRecord.setInsertionDate(DateOperations.convertStringToDate("01-" + reportDate, "dd-MMMM-yyyy"));
			reportRecordService.saveReportRecord(reportRecord);
		}
	}

}
