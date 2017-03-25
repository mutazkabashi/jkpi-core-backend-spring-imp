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

import javax.annotation.Resource;

import org.jkpi.core.backend.api.dao.ReportRecordDao;
import org.jkpi.core.backend.api.domain.ReportRecord;
import org.jkpi.core.backend.api.service.ReportColumnService;
import org.jkpi.core.backend.api.service.ReportRecordService;
import org.jkpi.core.backend.spring.dao.ReportRecordDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Implementation of {@link ReportRecordService}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportRecordService
 * @see ReportRecord
 *
 */
@Service("reportRecordService")
public class ReportRecordServiceImpl implements ReportRecordService {
	
	@Resource(name= "reportRecordDao")
	private ReportRecordDao reportRecordDao ;//= new ReportRecordDaoImpl();

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ReportRecord saveReportRecord(ReportRecord reportRecord) {
		System.out.println("ReportColumn Dao "+reportRecordDao.getClass().getName());
		return reportRecordDao.save(reportRecord);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteReportRecordsByDate(String reportDate){
		return reportRecordDao.deleteReportRecordsByDate(reportDate);
	}
	
	
	@Override
	public List<ReportRecord> getLastUploadedReportByName(String reportName){
		return reportRecordDao.getLastUploadedReportByName(reportName);
	}
	
	@Override
	public List<ReportRecord> getLastUploadedReportByReportNameAndGroupName(String reportName,String  groupName){
		return reportRecordDao.getLastUploadedReportByReportNameAndGroupName(reportName, groupName);
	}
	
	@Override
	public List<ReportRecord> getReportByReportNameAndInsertionDate(String reportName,Date insertionDate){
		return reportRecordDao.getReportByReportNameAndInsertionDate(reportName, insertionDate);
	}
	
	@Override
	public List<ReportRecord> getReportByReportNameGroupNameAndInsertionDate(String reportName,String groupName,Date insertionDate){
		return reportRecordDao.getReportByReportNameGroupNameAndInsertionDate(reportName, groupName, insertionDate);
	}
	
	@Override
	public List<ReportRecord> getReportByReportNameGroupNameAndPeriod(String reportName,String groupName,Date startDate,Date endDate){
		return reportRecordDao.getReportByReportNameGroupNameAndPeriod(reportName, groupName, startDate, endDate);
	}
	
	@Override
	public List<ReportRecord> getReportColumnValueByColumnNameGroupNameReportNameAndInsertionDate(String columnName, String groupName,
			String reportName, Date insetionDate){
		return reportRecordDao.getReportColumnValueByColumnNameGroupNameReportNameAndInsertionDate(columnName, groupName,reportName, insetionDate);
	}
	
	@Override
	public List<ReportRecord> getReportColumnValueByColumnNameGroupNameReportNameAndPeriod(String columnName, String groupName,
			String reportName,Date startDate ,Date endDate){
		return reportRecordDao.getReportColumnValueByColumnNameGroupNameReportNameAndPeriod(columnName, groupName,reportName, startDate,endDate);
	}
	
	@Override
	public List<ReportRecord> getReportAccomulatedColumnValueByColumnNameGroupNameReportNameAndPeriod(String columnName, String groupName,
			String reportName,Date startDate ,Date endDate){
		return reportRecordDao.getReportAccomulatedColumnValueByColumnNameGroupNameReportNameAndPeriod(columnName, groupName,reportName, startDate,endDate);
	}


}
