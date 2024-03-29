package swen221.monopoly;

import java.util.*;

/**
 */

public class ColourGroup {
	private ArrayList<Street> streets = new ArrayList<Street>();
	private String colour;

	/**
	 * Create colour group made up of Streets supplied as arguments
	 */
	public ColourGroup(String colour, Street... streets) {
		for (Street street : streets) {
			this.streets.add(street);
			street.setColourGroup(this);
		}
		this.colour = colour;
	}

	public String getColour() {
		return colour;
	}
}
