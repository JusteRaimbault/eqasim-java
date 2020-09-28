/*
 *  *********************************************************************** *
 *  * project: org.matsim.*
 *  * Builder.java
 *  *                                                                         *
 *  * *********************************************************************** *
 *  *                                                                         *
 *  * copyright       : (C) 2015 by the members listed in the COPYING, *
 *  *                   LICENSE and WARRANTY file.                            *
 *  * email           : info at matsim dot org                                *
 *  *                                                                         *
 *  * *********************************************************************** *
 *  *                                                                         *
 *  *   This program is free software; you can redistribute it and/or modify  *
 *  *   it under the terms of the GNU General Public License as published by  *
 *  *   the Free Software Foundation; either version 2 of the License, or     *
 *  *   (at your option) any later version.                                   *
 *  *   See also COPYING, LICENSE and WARRANTY file                           *
 *  *                                                                         *
 *  * ***********************************************************************
 */

package org.eqasim.jakarta.roadpricing;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.controler.ControlerDefaults;
import org.matsim.core.router.costcalculators.RandomizingTimeDistanceTravelDisutilityFactory;
import org.matsim.core.router.costcalculators.TravelDisutilityFactory;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;

import javax.inject.Inject;

public final class JakartaMcRoadPricingTravelDisutilityFactory implements TravelDisutilityFactory {
	private final JakartaMcRoadPricingScheme scheme;
	private final double marginalUtilityOfMoney ;
	private TravelDisutilityFactory previousTravelDisutilityFactory;
	private double sigma = 3. ;

	@Inject
	JakartaMcRoadPricingTravelDisutilityFactory(Scenario scenario, JakartaMcRoadPricingScheme roadPricingScheme) {
		this(ControlerDefaults.createDefaultTravelDisutilityFactory(scenario), roadPricingScheme, scenario.getConfig());
	}

	public JakartaMcRoadPricingTravelDisutilityFactory(TravelDisutilityFactory previousTravelDisutilityFactory, JakartaMcRoadPricingScheme scheme, double marginalUtilityOfMoney) {
		this.scheme = scheme ;
		this.marginalUtilityOfMoney = marginalUtilityOfMoney ;
		this.previousTravelDisutilityFactory = previousTravelDisutilityFactory ;
	}

	public JakartaMcRoadPricingTravelDisutilityFactory(TravelDisutilityFactory previousTravelDisutilityFactory, JakartaMcRoadPricingScheme scheme, Config config) {
		this( previousTravelDisutilityFactory, scheme, config.planCalcScore().getMarginalUtilityOfMoney() ) ;
	}

	@Override
	public TravelDisutility createTravelDisutility(TravelTime timeCalculator) {
		if ( this.sigma != 0. ) {
			if ( previousTravelDisutilityFactory instanceof RandomizingTimeDistanceTravelDisutilityFactory) {
				((RandomizingTimeDistanceTravelDisutilityFactory) previousTravelDisutilityFactory).setSigma( this.sigma );
			} else {
				throw new RuntimeException("cannot use sigma!=null together with provided travel disutility factory");
			}
		}
		return new JakartaMcTravelDisutilityIncludingToll(
				previousTravelDisutilityFactory.createTravelDisutility(timeCalculator),
				this.scheme,
				this.marginalUtilityOfMoney,
				this.sigma
		);
	}

	public void setSigma( double val ) {
		this.sigma = val ;
	}

}

