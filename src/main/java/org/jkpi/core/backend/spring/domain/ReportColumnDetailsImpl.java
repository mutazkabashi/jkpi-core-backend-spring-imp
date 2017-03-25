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

import java.math.BigDecimal;
import org.jkpi.core.backend.api.domain.ReportColumnDetails;

/**
 * @author Mutaz Hussein Kabashi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class ReportColumnDetailsImpl implements ReportColumnDetails {

	private String columnName;
	private BigDecimal columnValue;
	private String columnColor;

	/**
	 * Default Constructor
	 */
	public ReportColumnDetailsImpl() {

	}

	/**
	 * Constructor to create {@code ReportColumnDetails} by columnName,columnValue and columnbackgroundColor
	 * this contructor could be used by HQL (Hibernate Query language)
	 * @param columnName {@code Report}'s column name
	 * @param columnValue {@code Report}'s column value
	 * @param columnColor {@code Report}'s column background color
	 */
	public ReportColumnDetailsImpl(String columnName, BigDecimal columnValue, String columnColor) {
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.columnColor = columnColor;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;

	}

	public BigDecimal getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(BigDecimal columnValue) {
		this.columnValue = columnValue;

	}

	public String getColumnColor() {
		return columnColor;
	}

	public void setColumnColor(String columnColor) {
		this.columnColor = columnColor;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
		return result;
	}

	/**
	 * <code>columnName</code> and <code>columnColor</code> should be used to
	 * make sue that 2 columnDeatils are equls , here we use
	 * <code>columnName</code> only to solve
	 * <code> Mapper.createSummaryChartGroup() </code> method problem for more
	 * info see <code> Mapper Class<code>
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportColumnDetailsImpl other = (ReportColumnDetailsImpl) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}

}
