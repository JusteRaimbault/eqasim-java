package org.eqasim.wayne_county.mode_choice;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.population.PersonUtils;

import ch.ethz.matsim.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import ch.ethz.matsim.discrete_mode_choice.model.mode_availability.ModeAvailability;

public class WayneCountyModeAvailability implements ModeAvailability {
	@Override
	public Collection<String> getAvailableModes(Person person, List<DiscreteModeChoiceTrip> trips) {
		Collection<String> modes = new HashSet<>();

		// Modes that are always available
		modes.add(TransportMode.walk);
		//modes.add(TransportMode.pt);
		// modes.add(TransportMode.bike);

		// Check car availability
		boolean carAvailability = true;

		//if (PersonUtils.getLicense(person).equals("no")) {
		//	carAvailability = false;
		//}

		//if ("none".equals((String) person.getAttributes().getAttribute("carAvailability"))) {
		//	carAvailability = false;
		//}

		if (carAvailability) {
			modes.add(TransportMode.car);
		}

		return modes;
	}
}
