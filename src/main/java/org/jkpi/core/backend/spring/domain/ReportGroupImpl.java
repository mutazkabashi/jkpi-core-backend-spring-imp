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
package org.jkpi.core.backend.spring.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.BatchSize;
import org.jkpi.core.backend.api.domain.Report;
import org.jkpi.core.backend.api.domain.ReportGroup;
import org.jkpi.core.backend.api.domain.ReportRow;

/**
 * Hibernate Implementation of {@link ReportGroup}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see Report
 * @see ReportRow
 *
 */
@Entity
@Table(name = "REPORT_GROUP")
public class ReportGroupImpl implements Serializable, ReportGroup {

	private static final long serialVersionUID = 1L;
	private Long reportGroupID;
	private Report report;
	private String groupName;
	private Set<ReportRow> reportRows;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "GROUP_ID")
	public Long getReportGroupID() {
		return this.reportGroupID;
	}

	public void setReportGroupID(Long reportGroupID) {
		this.reportGroupID = reportGroupID;
	}

	@ManyToOne(/* fetch = FetchType.EAGER, */ targetEntity = ReportImpl.class)
	@JoinColumn(name = "REPORT_ID", nullable = false)
	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	@Override
	public void setGroupName(String groupName) {
		this.groupName = groupName;

	}

	@Column(name = "GROUP_NAME", nullable = false, length = 100)
	public String getGroupName() {
		return this.groupName;
	}

	@OneToMany(/* fetch = FetchType.EAGER, */ mappedBy = "reportGroup", targetEntity = ReportRowImpl.class, cascade = CascadeType.REMOVE)
	@BatchSize(size = 20)
	public Set<ReportRow> getReportRows() {
		return this.reportRows;
	}

	public void setReportRows(Set<ReportRow> reportRows) {
		this.reportRows = reportRows;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportGroupImpl other = (ReportGroupImpl) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportGroupImpl [reportGroupID=" + reportGroupID + ", report=" + report + ", groupName=" + groupName
				+ ", reportRows=" + reportRows + "]";
	}

}