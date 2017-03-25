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
package org.jkpi.core.backend.spring.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jkpi.core.backend.api.dao.ReportRecordDao;
import org.jkpi.core.backend.api.domain.ReportDate;
import org.jkpi.core.backend.api.domain.ReportRecord;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Spring Implementation of {@link ReportRecordDao}
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportRecordDao
 * @see ReportRecord
 *
 */
@Repository("reportRecordDao")
public class ReportRecordDaoImpl implements ReportRecordDao {

	@PersistenceContext(unitName = "atd2")
	protected EntityManager em;


	@Override
	public ReportRecord readById(Long id) {
		return em.find(ReportRecord.class, id);
	}

	@Override
	public ReportRecord save(ReportRecord reportRecord) {
		return em.merge(reportRecord); 
	}

	@Override
	public int deleteReportRecordsByDate(String reportDate) {
		Query query = em
				.createQuery(" delete from com.almedtech.backend.domain.ReportRecordImpl rrecord where  rrecord.reportDate=:reportDate");
		 query.setParameter("reportDate",reportDate);
		return   query.executeUpdate();

	}

	
	@Override
	public List<ReportRecord> getLastUploadedReportByName(String reportName) {
				Query query = em
						.createQuery("from ReportRecordImpl record  where record.insertionDate = (select max(record.insertionDate) from record where record.reportColumn.reportRow.reportGroup.report.reportName=:reportName)  and record.reportColumn.reportRow.reportGroup.report.reportName=:reportName"); 
				query.setParameter("reportName", reportName);
				return (List<ReportRecord>) query.getResultList();
	}

	@Override
	public List<ReportRecord> getLastUploadedReportByReportNameAndGroupName(
			String reportName, String groupName) {
		Query query = em
				.createQuery("from ReportRecordImpl rrecord  join fetch rrecord.reportColumn rcolumn  join fetch rcolumn.reportRow rrow  join fetch rrow.reportGroup rgroup "+
                             "  join fetch  rgroup.report report where report.reportName =:reportName and rgroup.groupName=:groupName "+ 
                             " and rrecord.insertionDate = (select max(rrecord2.insertionDate) from ReportRecordImpl rrecord2 " +
                                                          " where rrecord2.reportColumn.reportRow.reportGroup.report.reportName=:reportName)");
		 query.setParameter("reportName", reportName);
		 query.setParameter("groupName", groupName);	
		return (List<ReportRecord>) query.getResultList();
	}

	@Override
	public List<ReportRecord> getReportByReportNameAndInsertionDate(
			String reportName, Date insertionDate) {
		Query query = em
				.createQuery("from ReportRecordImpl rrecord join fetch rrecord.reportColumn rcolumn join fetch rcolumn.reportRow rrow join fetch rrow.reportGroup rgroup "+
                             " join fetch  rgroup.report report where report.reportName =:reportName and rrecord.insertionDate=:insertionDate");
		 query.setParameter("reportName", reportName);
		 query.setParameter("insertionDate", insertionDate);	
		return (List<ReportRecord>) query.getResultList();
	}

	@Override
	public List<ReportRecord> getReportByReportNameGroupNameAndInsertionDate(
			String reportName, String groupName, Date insertionDate) {
		Query query = em
				.createQuery("from ReportRecordImpl record  join  record.reportColumn rcolumn join  rcolumn.reportRow rrow join  rrow.reportGroup rgroup" +
						     " join  rgroup.report rreport  where record.insertionDate =:insertionDate  and rgroup.groupName=:groupName and rreport.reportName=:reportName");
		 query.setParameter("reportName", reportName);
		 query.setParameter("groupName", groupName);
		 query.setParameter("insertionDate", insertionDate);	
		return (List<ReportRecord>) query.getResultList();
	}

	@Override
	public List<ReportRecord> getReportByReportNameGroupNameAndPeriod(
			String reportName, String groupName, Date startDate, Date endDate) {
		Query query = em
				.createQuery("from ReportRecordImpl rrecord  join fetch rrecord.reportColumn rcolumn  join fetch rcolumn.reportRow rrow   join fetch rrow.reportGroup rgroup "+
                             " join fetch  rgroup.report report where report.reportName =:reportName and rgroup.groupName=:groupName "+
                             " and rrecord.insertionDate between :startDate and :endDate order by rrow.rowName asc" );
		 query.setParameter("reportName", reportName);
		 query.setParameter("groupName", groupName);
		 query.setParameter("startDate", startDate);
		 query.setParameter("endDate", endDate);
		return (List<ReportRecord>) query.getResultList();
	}

	@Override
	public List<ReportRecord> getReportColumnValueByColumnNameGroupNameReportNameAndInsertionDate(
			String columnName, String groupName,
			String reportName, Date insetionDate) {
				Query query = em
						//Don't use join Fetch with select
						.createQuery("select NEW com.almedtech.backend.domain.ReportRecordImpl( column.columnName , rrecord.columnValue, row.rowName) from ReportRecordImpl rrecord join  rrecord.reportColumn column join  column.reportRow row " +
								     " join  row.reportGroup rgroup join  rgroup.report report  where rrecord.insertionDate=:insetionDate and column.columnName=:columnName " +
								     " and rgroup.groupName=:groupName and report.reportName=:reportName order by row.rowName asc"); 
				query.setParameter("reportName", reportName);
				query.setParameter("groupName", groupName);
				query.setParameter("columnName",columnName);
				query.setParameter("insetionDate",insetionDate);
				return (List<ReportRecord>) query.getResultList();
	}
	
	@Override
	public List<ReportRecord> getReportColumnValueByColumnNameGroupNameReportNameAndPeriod(String columnName,String groupName,
			String reportName,Date startDate,Date endDate){
				Query query = em
						//Don't use join Fetch with select
						.createQuery("select NEW com.almedtech.backend.domain.ReportRecordImpl( column.columnName , rrecord.columnValue, row.rowName) from ReportRecordImpl rrecord join  rrecord.reportColumn column join  column.reportRow row " +
								     " join  row.reportGroup rgroup join  rgroup.report report  where  column.columnName=:columnName " +
								     " and rgroup.groupName=:groupName and report.reportName=:reportName and rrecord.insertionDate between :startDate and :endDate   order by row.rowName asc"); 
				query.setParameter("reportName", reportName);
				query.setParameter("groupName", groupName);
				query.setParameter("columnName",columnName);
				query.setParameter("startDate",startDate);
				query.setParameter("endDate",endDate);
				return (List<ReportRecord>) query.getResultList();
	}
	
	@Override
	public List<ReportRecord> getReportAccomulatedColumnValueByColumnNameGroupNameReportNameAndPeriod(String columnName,String groupName,
			String reportName,Date startDate,Date endDate){
				Query query = em
						//Don't use join Fetch with select
						.createQuery("select NEW com.almedtech.backend.domain.ReportRecordImpl( column.columnName , sum (rrecord.columnValue), row.rowName) from ReportRecordImpl rrecord join  rrecord.reportColumn column join  column.reportRow row " +
								     " join  row.reportGroup rgroup join  rgroup.report report  where  column.columnName=:columnName " +
								     " and rgroup.groupName=:groupName and report.reportName=:reportName and rrecord.insertionDate between :startDate and :endDate group by column.columnName,row.rowName "
								     + " order by row.rowName asc"); 
				query.setParameter("reportName", reportName);
				query.setParameter("groupName", groupName);
				query.setParameter("columnName",columnName);
				query.setParameter("startDate",startDate);
				query.setParameter("endDate",endDate);
				return (List<ReportRecord>) query.getResultList();
	}
	
	@Override
	public List<ReportRecord> getPreviousUploadedReport(String reportName) {
			List<ReportDate> reportDatesList = getReportDates(reportName);
			Date previousUploadedDate = reportDatesList.get(reportDatesList.size()-2).getActualDate(); //TODO we should cache reportDates in memory instead of querying DB
					Query query = em
							.createQuery("from ReportRecordImpl record  where record.insertionDate = :previousUploadedDate  and record.reportColumn.reportRow.reportGroup.report.reportName=:reportName"); 
					query.setParameter("reportName", reportName);
					query.setParameter("previousUploadedDate", previousUploadedDate);
					return (List<ReportRecord>) query.getResultList();
		}
	
	@Override
	public List<ReportRecord>  getReportByPeriod(String reportName,Date startDate,Date endDate){
				String q="select NEW com.almedtech.backend.domain.ReportRecordImpl(record.reportColumn.columnName, sum(record.columnValue),record.reportColumn.reportRow.rowName) from ReportRecordImpl record"+ 
	                     " where record.reportColumn.columnName = record.reportColumn.columnName and record.reportColumn.reportRow.reportGroup.report.reportName=:reportName and record.insertionDate between :startDate and :endDate" +
						" group by record.reportColumn.columnName, record.reportColumn.reportRow.rowName order by record.reportColumn.reportRow.rowName";
				System.out.println("Query "+q);
				Query query =em.createQuery(q);
				   query.setParameter("reportName", reportName);
				   query.setParameter("startDate", startDate);
				   query.setParameter("endDate", endDate);
				   System.out.println("Query "+query.toString());
				   return (List<ReportRecord>) query.getResultList();
			}
	
	@Override
	public List<ReportRecord> getReportByPeriodForSummaryReport(String reportName,Date startDate,Date endDate) {
				Query query = em
						.createQuery("from ReportRecordImpl record  where record.insertionDate between  :startDate and :endDate and record.reportColumn.reportRow.reportGroup.report.reportName=:reportName"); 
				query.setParameter("reportName", reportName);
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				return (List<ReportRecord>) query.getResultList();
	}
	
	@Override
	public List<ReportDate>  getReportDates(String reportName){
		
		Query query =em.createQuery("select distinct NEW com.almedtech.backend.domain.ReportDateImpl (record.reportDate,record.insertionDate) from ReportRecordImpl record where record.reportColumn.reportRow.reportGroup.report.reportName=:reportName order by record.insertionDate asc");
		   query.setParameter("reportName", reportName);
		   return (List<ReportDate>) query.getResultList();
	}
	
}
