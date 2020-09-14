package com.twu.refactoring;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.ArrayList;
import java.util.Iterator;

public class Customer {
	private static final int REGULAR_OFFSET = 2;
	private static final double REGULAR_PLUS = 2;
	private static final double CHILDREN_PLUS = 1.5;
	private static final int CHILDREN_OFFSET = 3;
	private static final double NEW_RELEASE_FACTOR = 3;
	private static final double REGULAR_CHILDREN_FACTOR = 1.5;

	private static final String STATEMENT_HEADER = "Rental Record for %s\n";
	private static final String STATEMENT_FIGURE = "\t%s\t%.1f\n";
	private static final String STATEMENT_FOOTER = "Amount owed is %.1f\nYou earned %d frequent renter points";

	private String name;
	private ArrayList<Rental> rentalList = new ArrayList<Rental>();

	public Customer(String name) {
		this.name = name;
	}

	public void addRental(Rental arg) {
		rentalList.add(arg);
	}

	public String getName() {
		return name;
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Iterator<Rental> rentals = rentalList.iterator();
		String result = String.format(STATEMENT_HEADER, getName());
		while (rentals.hasNext()) {
			double thisAmount = 0;
			Rental each = rentals.next();

			// determine amounts for each line
			thisAmount = calculateAmounts(each);

			// add frequent renter points
			frequentRenterPoints++;
			// add bonus for a two day new release rental
			if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
					&& each.getDaysRented() > 1)
				frequentRenterPoints++;

			// show figures for this rental
			result += String.format(STATEMENT_FIGURE, each.getMovie().getTitle(), thisAmount);
			totalAmount += thisAmount;

		}
		// add footer lines
		result += String.format(STATEMENT_FOOTER, totalAmount, frequentRenterPoints);
		return result;
	}

	private double calculateAmounts(Rental rental) {
		double thisAmount = 0;
		switch (rental.getMovie().getPriceCode()) {
			case Movie.REGULAR:
				thisAmount += REGULAR_PLUS;
				if (rental.getDaysRented() > REGULAR_OFFSET)
					thisAmount += (rental.getDaysRented() - REGULAR_OFFSET) * REGULAR_CHILDREN_FACTOR;
				break;
			case Movie.NEW_RELEASE:
				thisAmount += rental.getDaysRented() * NEW_RELEASE_FACTOR;
				break;
			case Movie.CHILDRENS:
				thisAmount += CHILDREN_PLUS;
				if (rental.getDaysRented() > CHILDREN_OFFSET)
					thisAmount += (rental.getDaysRented() - CHILDREN_OFFSET) * REGULAR_CHILDREN_FACTOR;
				break;

		}
		return thisAmount;
	}

}
