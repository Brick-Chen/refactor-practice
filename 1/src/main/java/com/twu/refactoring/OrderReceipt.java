package com.twu.refactoring;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 * 
 */
public class OrderReceipt {
	public static final String PRINTING_ORDERS = "======Printing Orders======\n";
	public static final double TAX_RATE = .10;
	private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
	}

	public String printReceipt() {
		StringBuilder output = new StringBuilder();

		// print headers
		output.append(PRINTING_ORDERS);

        output.append(order.getCustomerName());
        output.append(order.getCustomerAddress());

		// prints lineItems
		printItemInfo(output);
		return output.toString();
	}

	private void printItemInfo(StringBuilder output) {
		double totSalesTx = 0d;
		double tot = 0d;
		for (LineItem lineItem : order.getLineItems()) {
			getItemInfo(output, lineItem);

			// calculate sales tax @ rate of 10%
            double salesTax = lineItem.totalAmount() * TAX_RATE;
            totSalesTx += salesTax;

            // calculate total amount of lineItem = price * quantity + 10 % sales tax
            tot += lineItem.totalAmount() + salesTax;

		}

		// prints the state tax
		output.append("Sales Tax").append('\t').append(totSalesTx);

		// print total amount
		output.append("Total Amount").append('\t').append(tot);
	}

	private void getItemInfo(StringBuilder output, LineItem lineItem) {
		output.append(lineItem.getDescription());
		output.append('\t');
		output.append(lineItem.getPrice());
		output.append('\t');
		output.append(lineItem.getQuantity());
		output.append('\t');
		output.append(lineItem.totalAmount());
		output.append('\n');
	}
}