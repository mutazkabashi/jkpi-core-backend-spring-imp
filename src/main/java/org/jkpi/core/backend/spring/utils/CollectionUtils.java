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
package org.jkpi.core.backend.spring.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jkpi.core.backend.api.domain.ReportColumn;
import org.jkpi.core.backend.api.domain.ReportRecord;

/**
 * A Utility Class which provides methods to handle Collections
 * @author Mutaz Hussein Kabashi
 *
 */
public class CollectionUtils {

	/**
	 * Converts {@code Set} to {@code List}
	 * @param set
	 * @return
	 */
	public static <E> List<E> convertSetToList(Set<E> set) {
		return new ArrayList<E>(set);
	}

	/**
	 * removes/delete all Objects from a list  except the passed/specified one
	 * @param specifiedObject the object to be kept on the list
	 * @param list the list after modification/deletion of the unspcified objects
	 * @return
	 */
	public static <E> List<E> removeUnspecifiedObjectFromList(
			E specifiedObject, List<E> list) {
		Iterator<E> it = list.iterator();
		while (it.hasNext()) {
			E element = it.next();
			if (!element.equals(specifiedObject)) {
				it.remove();
			}
		}
		return list;
	}

	/**
	 * computes the total value of {@code Report}'s Column by calculate the column's cells values
	 * @param list a list of {@code Report}'s {@code ReportColumn}'s Cells
	 * @return
	 */
	public static List<Double> getColumnTotalValue(List<ReportRecord> list) {
		double counter = 0;
		for (ReportRecord reportRecord : list) {
			counter = counter + reportRecord.getColumnValue().doubleValue();
		}
		List<Double> columnTotalList = new ArrayList<Double>();
		columnTotalList.add(counter);
		return columnTotalList;
	}

	/**
	 * TODO
	 * @param list
	 * @return
	 */
	public static List<Double> getListDeatilVales(List<ReportRecord> list) {
		Map<String, Double> recordsValuesList = new LinkedHashMap<String, Double>();
		for (ReportRecord reportRecord : list) {
			if (!recordsValuesList.isEmpty()
					&& recordsValuesList.containsKey(reportRecord
							.getReportColumn().getReportRow().getRowName())) {
				Double savedColumnValue = recordsValuesList.get(reportRecord
						.getReportColumn().getReportRow().getRowName());
				savedColumnValue = savedColumnValue
						+ reportRecord.getColumnValue().doubleValue();
				recordsValuesList.put(reportRecord.getReportColumn()
						.getReportRow().getRowName(), savedColumnValue);
			} else {
				recordsValuesList.put(reportRecord.getReportColumn()
						.getReportRow().getRowName(), reportRecord
						.getColumnValue().doubleValue());
			}
		}
		List<Double> resultList = new ArrayList<Double>(
				recordsValuesList.values());
		return resultList;
	}

	/**
	 * TODO
	 * @param list
	 * @return
	 */
	public static List<Double> getListVales(List<ReportRecord> list) {
		List<Double> recordsValuesList = new ArrayList<Double>();
		for (ReportRecord reportRecord : list) {
			recordsValuesList.add(reportRecord.getColumnValue().doubleValue());
		}
		return recordsValuesList;
	}

}
