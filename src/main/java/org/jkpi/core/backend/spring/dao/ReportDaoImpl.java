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
import org.jkpi.core.backend.api.dao.ReportDao;
import org.jkpi.core.backend.api.domain.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Implementation of {@link ReportDao}
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportDao
 * @see Report
 *
 */

@Repository("reportDao")
public class ReportDaoImpl implements ReportDao {

	@PersistenceContext(unitName = "atd2")
	protected EntityManager em;

	@Override
	public Report readById(Long id) {
		
		return em.find(Report.class, id);
	}

	@Override
	public Report save(Report report) {
		 return em.merge(report);
	}

	@Override
	public void delete(Report report) {
		em.remove(report);

	}

	@Override
	public List<Report> getAllReports() {
		Query query = em
				.createQuery("select distinct report from ReportImpl report join fetch report.reportGroups rgroup join fetch rgroup.reportRows row " +
		" join fetch row.reportColumns column join fetch column.reportRecords");
		return (List<Report>) query.getResultList();
	}

	@Override
	public List<Report> getReportsByName(String ReportName) {
				Query query = em
						.createQuery("from ReportImpl report where report.reportName=:name");
				 query.setParameter("name", ReportName);
				return (List<Report>) query.getResultList();
	}

}
