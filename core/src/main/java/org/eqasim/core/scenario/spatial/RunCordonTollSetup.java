package org.eqasim.core.scenario.spatial;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;
import java.util.LinkedList;


import org.eqasim.core.scenario.SpatialUtils;
import org.eqasim.core.misc.ParallelProgress;

import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.config.CommandLine;
import org.matsim.core.config.CommandLine.ConfigurationException;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
//import org.matsim.core.utils.io.MatsimXmlWriter;

public class RunCordonTollSetup {

	private static final GeometryFactory factory = new GeometryFactory();

	static public void main(String[] args) throws ConfigurationException, InterruptedException, IOException {
		CommandLine cmd = new CommandLine.Builder(args) //
				.requireOptions("input-path", "output-path", "shape-path", "shape-attribute", "shape-value", "price") //
				.build();

		String shapeAttribute = cmd.getOptionStrict("shape-attribute");
		String shapeValue = cmd.getOptionStrict("shape-value");
		URL shapeUrl = new File(cmd.getOptionStrict("shape-path")).toURI().toURL();
		//double price = Double.parseDouble(cmd.getOptionStrict("price"));
		String price = cmd.getOptionStrict("price");

		Polygon polygon = SpatialUtils.loadPolygon(shapeUrl, shapeAttribute, shapeValue);

		Network network = NetworkUtils.createNetwork();
		new MatsimNetworkReader(network).readFile(cmd.getOptionStrict("input-path"));

		List<Link> tolledLinks = getTolledLinks(polygon, network);

		writeTollFile(tolledLinks, cmd.getOptionStrict("output-path"), price);
	}

	static private List<Link> getTolledLinks(Polygon polygon, Network network) throws InterruptedException {
		ParallelProgress progress = new ParallelProgress("Extracting tolled links...", network.getLinks().size());

		List<Link> res = new LinkedList<>();

		for (Link link : network.getLinks().values()) {
			Point point = factory.createPoint(new Coordinate(link.getCoord().getX(), link.getCoord().getY()));

			if (polygon.contains(point)) {
				res.add(link);
			}
		}

		progress.close();
		return(res);
	}

	static private void writeTollFile(List<Link> links, String path, String price) throws IOException {

		 String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE roadpricing SYSTEM \"http://www.matsim.org/files/dtd/roadpricing_v1.dtd\">\n"+
		 									"<roadpricing type=\"link\" name=\"cordon toll\">\n";
     String footer = "</roadpricing>";

		 //MatsimXmlWriter w = new MatsimXmlWriter(); // abstract class - Matsim has no generic xml writer?
		 BufferedWriter xml = new BufferedWriter(new FileWriter(path));
		 xml.write(header);
		 xml.write("<links>\n");

		 for (Link link : links) {
			 	xml.write("<link id=\""+link.getId()+"\" />\n");
		 }

		 xml.write("</links>\n");

		 xml.write("<cost start_time=\"00:00\" end_time=\"23:59\" amount=\""+price+"\"/>\n");

     xml.write(footer);
     xml.close();
	}


}
