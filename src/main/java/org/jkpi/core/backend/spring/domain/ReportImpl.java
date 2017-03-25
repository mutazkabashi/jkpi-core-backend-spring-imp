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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.BatchSize;
import org.jkpi.core.backend.api.domain.Report;
import org.jkpi.core.backend.api.domain.ReportGroup;
import org.springframework.stereotype.Component;

/**
 * Hibernate Implementation of {@link Report}
 * 
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 * @see ReportGroup
 *
 */
@Component("report")
@Entity
@Table(name = "REPORT")
public class ReportImpl implements java.io.Serializable,Report {
	
	private static final long serialVersionUID = 1L;
 
	private Long reportId;
	private String reportName;
	private Set<ReportGroup> reportGroups = new HashSet<ReportGroup>();
 
 
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REPORT_ID")
	public Long getReportId() {
		return this.reportId;
	}
 
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
 
	@Column(name = "REPORT_NAME", unique = true, nullable = false, length = 100)
	public String getReportName() {
		return this.reportName;
	}
 
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
 
	
 
	@OneToMany(/*fetch = FetchType.EAGER,*/ mappedBy = "report",targetEntity=ReportGroupImpl.class,cascade=CascadeType.REMOVE)
	@BatchSize(size=20)
	public Set<ReportGroup> getReportGroups() {
		return this.reportGroups;
	}
 
	public void setReportGroups(Set<ReportGroup> reportGroups) {
		this.reportGroups = reportGroups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((reportName == null) ? 0 : reportName.hashCode());
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
		ReportImpl other = (ReportImpl) obj;
		if (reportName == null) {
			if (other.reportName != null)
				return false;
		} else if (!reportName.equals(other.reportName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportImpl [reportId=" + reportId + ", reportName=" + reportName + ", reportGroups=" + reportGroups
				+ "]";
	}

	
 
}