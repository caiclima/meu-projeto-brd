package br.com.callink.bradesco.seguro.web.faces.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;


public class SortOrderUtil {
	private Map<String, SortOrder> sortOrderMap = null;

	public SortOrderUtil(Map<String, SortOrder> sortOrderMap) {
		if (sortOrderMap == null) {
			throw new IllegalArgumentException("sortOrderMap can't be null");
		}

		this.sortOrderMap = sortOrderMap;
	}

	public SortOrderUtil(List<String> sortColumnNames) {
		if (sortColumnNames == null) {
			throw new IllegalArgumentException("sortColumnNames can't be null");
		}
		else {
			this.sortOrderMap = new HashMap<String, SortOrder>();

			for (String columName : sortColumnNames) {
				this.sortOrderMap.put(columName, SortOrder.unsorted);
			}
		}
	}

	public SortOrderUtil(String... sortColumnNames) {
		if (sortColumnNames != null) {
			this.sortOrderMap = new HashMap<String, SortOrder>();

			for (String columName : sortColumnNames) {
				this.sortOrderMap.put(columName, SortOrder.unsorted);
			}
		}
	}

	public SortOrder getSortOrder(String columnName) {
		if (!sortOrderMap.containsKey(columnName)) {
			throw new IllegalArgumentException(
					"invalid columnName, sortOrderMap don't contain a key named with that text!");
		}
		return sortOrderMap.get(columnName);
	}

	public void sortBy(String columnName) {
		if (StringUtils.isEmpty(columnName)) {
			throw new IllegalArgumentException(
					"columnName on SortUtil.sortBy(String columnName) can't be null");
		} else if (!sortOrderMap.containsKey(columnName)) {
			throw new IllegalArgumentException(
					"invalid columnName, sortOrderMap don't contain a key named with that text!");
		}

		for (String columnNameKey : sortOrderMap.keySet()) {
			SortOrder sortOrder = sortOrderMap.get(columnNameKey);

			if (!columnNameKey.equals(columnName)) {
				sortOrder = SortOrder.unsorted;
			} else {
				sortOrder = sortOrder.equals(SortOrder.ascending) ? SortOrder.descending
						: SortOrder.ascending;
			}

			sortOrderMap.put(columnNameKey, sortOrder);
		}
	}
}
