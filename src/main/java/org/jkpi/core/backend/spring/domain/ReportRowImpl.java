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
import org.jkpi.core.backend.api.domain.ReportColumn;
import org.jkpi.core.backend.api.domain.ReportGroup;
import org.jkpi.core.backend.api.domain.ReportRow;

/**
 * Hibernate Implementation of {@link ReportRow}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportGroup
 *
 */
@Entity
@Table(name = "REPORT_ROW")
public class ReportRowImpl implements Serializable, ReportRow {

	private static final long serialVersionUID = 1L;

	private Long reportRowID;
	private String rowName;
	private ReportGroup reportGroup;
	private Set<ReportColumn> reportColumns;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ROW_ID")
	public Long getReportRowID() {
		return this.reportRowID;
	}

	public void setReportRowID(Long reportRowID) {
		this.reportRowID = reportRowID;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	@ManyToOne(/* fetch = FetchType.EAGER, */ targetEntity = ReportGroupImpl.class)
	@JoinColumn(name = "GROUP_ID", nullable = false)
	public ReportGroup getReportGroup() {
		return this.reportGroup;
	}

	public void setReportGroup(ReportGroup reportGroup) {
		this.reportGroup = reportGroup;
	}

	@OneToMany(/* fetch = FetchType.EAGER, */ mappedBy = "reportRow", targetEntity = ReportColumnImpl.class, cascade = CascadeType.REMOVE)
	@BatchSize(size = 20)
	public Set<ReportColumn> getReportColumns() {
		return this.reportColumns;
	}

	public void setReportColumns(Set<ReportColumn> reportColumns) {
		this.reportColumns = reportColumns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rowName == null) ? 0 : rowName.hashCode());
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
		ReportRowImpl other = (ReportRowImpl) obj;
		if (rowName == null) {
			if (other.rowName != null)
				return false;
		} else if (!rowName.equals(other.rowName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportRowImpl [reportRowID=" + reportRowID + ", rowName=" + rowName + ", reportGroup=" + reportGroup
				+ ", reportColumns=" + reportColumns + "]";
	}

}