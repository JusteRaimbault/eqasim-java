/*
 * Cadyts - Calibration of dynamic traffic simulations
 *
 * Copyright 2009, 2010 Gunnar Fl�tter�d
 * 
 *
 * This file is part of Cadyts.
 *
 * Cadyts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cadyts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cadyts.  If not, see <http://www.gnu.org/licenses/>.
 *
 * contact: gunnar.floetteroed@epfl.ch
 *
 */ 
package cadyts.utilities.misc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Gunnar Fl�tter�d
 * 
 */
public class XMLHelpers {

	// -------------------- CONSTANTS --------------------

	public static final Extractor<Integer> DOUBLE2INT_EXTRACTOR = new Extractor<Integer>() {
		@Override
		public Integer extract(final String item) {
			return (int) Math.round(Double.parseDouble(item));
		}
	};

	// -------------------- HIDDEN CONSTRUCTOR --------------------

	private XMLHelpers() {
	}

	// -------------------- READING --------------------

	public static interface Extractor<T> {
		/**
		 * @param item
		 *            contains a String representation of a value of type T
		 * @return a T representation of item
		 */
		public T extract(final String item);
	}

	public static <T> List<T> extractItems(final String line,
			final Extractor<T> extractor) {
		final ArrayList<T> result = new ArrayList<T>();
		for (String item : line.split("\\s")) {
			final String trimmedItem = item.trim();
			if (trimmedItem.length() > 0) {
				result.add(extractor.extract(trimmedItem));
			}
		}
		result.trimToSize();
		return result;
	}

	public static List<String> extractItems(final String line) {
		return extractItems(line, new Extractor<String>() {
			public String extract(final String item) {
				return item;
			}
		});
	}

	// -------------------- WRITING --------------------

	public static void writeAttr(final String name, final Object value,
			final PrintWriter writer) throws IOException {
		writer.print(name);
		writer.print("=\"");
		writer.print(value.toString());
		writer.print("\" ");
	}

	public static void appendAttr(final String name, final Object value,
			final StringBuffer result) {
		result.append(name);
		result.append("=\"");
		result.append(value.toString());
		result.append("\" ");
	}
}
