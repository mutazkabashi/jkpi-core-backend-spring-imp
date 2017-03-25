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

import org.jkpi.core.backend.spring.dao.ReportDao;
import org.jkpi.core.backend.spring.dao.ReportGroupDao;
import org.jkpi.core.backend.spring.dao.ReportGroupDaoImpl;
import org.jkpi.core.backend.spring.domain.Report;
import org.jkpi.core.backend.spring.domain.ReportGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("reportGroupService")
public class ReportGroupServiceImpl implements ReportGroupService {
	
	@Resource(name= "reportGroupDao")
	private ReportGroupDao reportGroupDao ; // = new ReportGroupDaoImpl();

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ReportGroup saveReportGroup(ReportGroup reportGroup) {
		System.out.println("ReportGroup Dao "+reportGroupDao.getClass().getName());
		return reportGroupDao.save(reportGroup);
	}

	/*@Override
	public List<ReportGroup> getAllReportGroups() {
		return reportGroupDao.getAllReports();
	}*/

}
