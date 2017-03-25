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

import java.util.List;

import javax.annotation.Resource;

import org.jkpi.core.backend.api.dao.ReportDao;
import org.jkpi.core.backend.api.domain.Report;
import org.jkpi.core.backend.api.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Implementation of {@link ReportService}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportService
 * @see ReportDao
 *
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Resource(name= "reportDao")
	private ReportDao reportDao ;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Report saveReport(Report report) {
		return reportDao.save(report);
	}

	@Override
	public List<Report> getAllReports() {
		return reportDao.getAllReports();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteReport(Report report) {
		 reportDao.delete(report);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAllReports(){
		for (Report report : getAllReports()) {
			reportDao.delete(report);
		}
	}

}
