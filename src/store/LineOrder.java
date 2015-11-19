/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

/**
 *
 * @author araceliteruel
 */
public class LineOrder {
    private Product product;
	private int quantity; 
	
	/**
	 * Constructor
	 * @param p Product
	 * @param quantity Quantity
	 */
	public LineOrder(Product p, int quantity) {
		this.product = p;
		this.quantity = quantity > 0?quantity:1;
	}

	/**
	 * Returns the product's quantity
	 * @return Product's quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Changes product's quantity
	 * @param quantity Quantity. Must be greater than 0.
	 */
	public void setQuantity(int quantity) {
		if(quantity > 0) this.quantity = quantity;
	}
	
	/**
	 * Adds a quantity to a product (or subtracts it if quantity is negative).
	 * Resulting quantity won't be lower than 1.
	 * @param quantity Quantity to add
	 */
	public void addQuantity(int quantity) {
		this.quantity += quantity;
		if(this.quantity < 1) this.quantity = 1;
	}
	
	/**
	 * Returns the product object
	 * @return product
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * Calculates and returns price of the line
	 * @return Price (unit price * quantity)
	 */
	public double getPrice() {
		return product.getPrice() * quantity;
	}
}
