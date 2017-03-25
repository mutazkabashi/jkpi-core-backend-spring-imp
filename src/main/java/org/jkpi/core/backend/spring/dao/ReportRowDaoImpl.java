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

import org.jkpi.core.backend.api.dao.ReportRowDao;
import org.jkpi.core.backend.api.domain.ReportRow;
import org.springframework.stereotype.Repository;

/**
 * Spring Implementation of {@link ReportRowDao}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportRowDao
 * @see ReportRow
 *
 */
@Repository("reportRowDao")
public class ReportRowDaoImpl implements ReportRowDao {

	@PersistenceContext(unitName = "atd2")
	protected EntityManager em;

	@Override
	public ReportRow readById(Long id) {
		return em.find(ReportRow.class, id);
	}

	@Override
	public ReportRow save(ReportRow reportRow) {

		return em.merge(reportRow);
	}

}
