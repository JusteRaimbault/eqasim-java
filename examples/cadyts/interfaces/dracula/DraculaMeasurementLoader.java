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
package cadyts.interfaces.dracula;

import cadyts.interfaces.defaults.BasicMeasurementLoader;

/**
 * 
 * @author Gunnar Fl�tter�d
 * 
 */
class DraculaMeasurementLoader extends BasicMeasurementLoader<DraculaLink> {

	// -------------------- CONSTRUCTION --------------------

	DraculaMeasurementLoader(final DraculaCalibrator calibrator) {
		super(calibrator);
	}

	// --------------- IMPLEMENTATION OF BasicMeasurementLoader ---------------

	@Override
	protected DraculaLink label2link(final String label) {
		return new DraculaLink(Integer.parseInt(label));
	}
}
