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
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.BatchSize;
import org.jkpi.core.backend.api.domain.Report;
import org.jkpi.core.backend.api.domain.ReportColumn;
import org.jkpi.core.backend.api.domain.ReportRecord;

/**
 * Hibernate Implementation of {@link ReportRecord}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportColumn
 *
 */
@Entity
@Table(name = "REPORT_RECORD")
@BatchSize(size=10)
public class ReportRecordImpl implements Serializable, ReportRecord {

	private static final long serialVersionUID = 1L;

	private Long reportRecordID;
	private ReportColumn reportColumn;
	private BigDecimal columnValue;  //TODO change it to BigDecimal (check this http://stackoverflow.com/questions/15916345/convert-postgresql-column-from-character-varying-to-integer)
	private String reportDate;
	private Date   insertionDate;
	
	
	/**
	 * Default Constructor
	 */
	public ReportRecordImpl() {
		super();
	}


	/**
     * Constructor to create {@code ReportRecord} by columnName,columnValue and rowname
	 * this contructor could be used by HQL (Hibernate Query language)
	 * @param rowName
	 * @param columnName
	 * @param columnValue
	 */
	public ReportRecordImpl(String columnName, BigDecimal columnValue,String rowName) {
		super();
		this.reportColumn = new ReportColumnImpl(); //TODO IOC
		this.reportColumn.setReportRow(new ReportRowImpl()); //TODO IOC
		this.reportColumn.getReportRow().setRowName(rowName);
		this.reportColumn.setColumnName(columnName);
		this.columnValue = columnValue;
	}
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "RECORD_ID")
	public Long getReportRecordID() {
		return this.reportRecordID;
	}

	public void setReportRecordID(Long reportRecordID) {
		this.reportRecordID = reportRecordID;
	}

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ReportColumnImpl.class)
	// TODO(VI)
	@JoinColumn(name = "COLUMN_ID", nullable = false)
	public ReportColumn getReportColumn() {
		return this.reportColumn;
	}

	public void setReportColumn(ReportColumn reportColumn) {
		this.reportColumn = reportColumn;
	}


	@Column(name = "COLUMN_VALUE")
	public BigDecimal getColumnValue() {
		return this.columnValue;
	}

	
	public void setColumnValue(BigDecimal columnValue) {
		this.columnValue = columnValue;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name = "INSERTION_DATE", updatable = false)
	@Temporal(TemporalType.DATE)
	public Date getInsertionDate() {
		return insertionDate;
	}

	
	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnValue == null) ? 0 : columnValue.hashCode());
		result = prime * result + ((insertionDate == null) ? 0 : insertionDate.hashCode());
		result = prime * result + ((reportColumn == null) ? 0 : reportColumn.hashCode());
		result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
		result = prime * result + ((reportRecordID == null) ? 0 : reportRecordID.hashCode());
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
		ReportRecordImpl other = (ReportRecordImpl) obj;
		if (columnValue == null) {
			if (other.columnValue != null)
				return false;
		} else if (!columnValue.equals(other.columnValue))
			return false;
		if (insertionDate == null) {
			if (other.insertionDate != null)
				return false;
		} else if (!insertionDate.equals(other.insertionDate))
			return false;
		if (reportColumn == null) {
			if (other.reportColumn != null)
				return false;
		} else if (!reportColumn.equals(other.reportColumn))
			return false;
		if (reportDate == null) {
			if (other.reportDate != null)
				return false;
		} else if (!reportDate.equals(other.reportDate))
			return false;
		if (reportRecordID == null) {
			if (other.reportRecordID != null)
				return false;
		} else if (!reportRecordID.equals(other.reportRecordID))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ReportRecordImpl [reportRecordID=" + reportRecordID + ", reportColumn=" + reportColumn
				+ ", columnValue=" + columnValue + ", reportDate=" + reportDate + ", insertionDate=" + insertionDate
				+ "]";
	}

	

	

}