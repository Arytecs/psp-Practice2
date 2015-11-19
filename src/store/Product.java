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
public class Product {
    private double price;
	private String name;
	private String category;
	private String reference;
	
	/**
	 * Constructor
	 * @param price Price
	 * @param ref Reference
	 * @param name Name
	 * @param category Category
	 */
	public Product(double price, String ref, String name, String category) {
		this.price = price;
		this.reference = ref;
		this.name  = name;
		this.category = category;
	}
	
	/**
	 * Returns the product's price
	 * @return product's price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Returns the product's reference
	 * @return product's reference
	 */
	public String getReference() {
		return reference;
	}
	
	/**
	 * Returns the product's name
	 * @return product's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the product's category
	 * @return product's category
	 */
	public String getCategory() {
		return category;
	}
}
