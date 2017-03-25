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
import org.jkpi.core.backend.api.domain.ReportRecord;
import org.jkpi.core.backend.api.domain.ReportRow;

/**
 * Hibernate Implementation of {@link ReportColumn}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportRow
 * @see ReportRecord
 *
 */
@Entity
@Table(name = "REPORT_COLUMN")
public class ReportColumnImpl implements Serializable, ReportColumn {

	private static final long serialVersionUID = 1L;

	private Long reportColumnID;
	private ReportRow reportRow;
	private String columnName;
	private String columnColor;
	private Set<ReportRecord> reportRecords;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "COLUMN_ID")
	public Long getReportColumnID() {
		return this.reportColumnID;
	}

	public void setReportColumnID(Long reportColumnID) {
		this.reportColumnID = reportColumnID;
	}

	@ManyToOne(/* fetch = FetchType.EAGER, */ targetEntity = ReportRowImpl.class)
	@JoinColumn(name = "ROW_ID", nullable = false)
	public ReportRow getReportRow() {
		return this.reportRow;
	}

	public void setReportRow(ReportRow reportRow) {
		this.reportRow = reportRow;
	}

	@Override
	public void setColumnName(String columnName) {
		this.columnName = columnName;

	}

	@Column(name = "COLUMN_NAME")
	public String getColumnName() {
		return this.columnName;
	}

	@Override
	public void setColumnColor(String columnColor) {
		this.columnColor = columnColor;

	}

	@Column(name = "COLUMN_COLOR")
	public String getColumnColor() {
		return this.columnColor;
	}

	@OneToMany(/* fetch = FetchType.EAGER, */ mappedBy = "reportColumn", targetEntity = ReportRecordImpl.class, cascade = CascadeType.REMOVE)
	@BatchSize(size = 50)
	public Set<ReportRecord> getReportRecords() {
		return this.reportRecords;
	}

	public void setReportRecords(Set<ReportRecord> reportRows) {
		this.reportRecords = reportRows;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
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
		ReportColumnImpl other = (ReportColumnImpl) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportColumnImpl [reportColumnID=" + reportColumnID + ", reportRow=" + reportRow + ", columnName="
				+ columnName + ", columnColor=" + columnColor + ", reportRecords=" + reportRecords + "]";
	}

}