/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author araceliteruel
 */
public class Order {
    private List<LineOrder> lines;
	
	/**
	 * Constructor. Initializes the LineProduct List.
	 */
	public Order() {
		lines = new ArrayList<>();
	}
	
	/**
	 * Adds a product to the order
	 * @param p Product object
	 * @param quantity quantity of that product to add (must be greater than 0)
	 */
	public void addProduct(Product p, int quantity) {
		if(hasProduct(p.getReference())) {
			LineOrder line = getLineByRef(p.getReference());
			line.addQuantity(quantity);
		} else {
			lines.add(new LineOrder(p, quantity));
			Collections.sort(lines, (l1, l2) -> l1.getProduct().getName().compareTo(l2.getProduct().getName()));
		}
	}
	
	/**
	 * Checks if the order has a product or not.
	 * @param ref The reference of the product to search.
	 * @return True if the product is in the order. False otherwise.
	 */
	public boolean hasProduct(String ref) {
		return lines.stream().filter(l -> l.getProduct().getReference().equals(ref)).count() > 0?true:false;
	}
	
	/**
	 * Gets a line of the order (LineOrder) that contains a product with a reference.
	 * @param ref Reference of the product to search.
	 * @return LineOrder object if the product is present. Otherwise null.
	 */
	public LineOrder getLineByRef(String ref) {
		Optional<LineOrder> line = lines.stream().filter(l -> l.getProduct().getReference().equals(ref)).findFirst();
		return line.isPresent()?line.get():null;
	}
	
	/**
	 * Gets the lines of the order. LineOrder list.
	 * @return LineOrder list
	 */
	public List<LineOrder> getLines() {
		return lines;
	}
	
	/**
	 * Removes a line from the order.
	 * @param line Number of line to remove.
	 * @return true if a line was removed. Otherwise false.
	 */
	public boolean removeLine(int line) {
		if(lines.size() <= line || line < 0) return false;
		
		lines.remove(line);
		return true;
	}
	
	/**
	 * Calculates the total price of the order and returns it.
	 * @return Total price of the order.
	 */
	public double getTotalPrice() {
		return lines.stream().map(LineOrder::getPrice).reduce(0.0, (p1, p2) -> p1 + p2);
	}
}
