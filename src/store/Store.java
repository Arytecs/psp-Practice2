/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

import store.files.FileLoader;

/**
 *
 * @author araceliteruel
 */
public class Store {
    private List<Product> products, currentProducts;
	private List<User> users;
	private Set<String> categories;
	private FileLoader fileLoader;
	private User loggedUser;
	private Order currentOrder;

	/**
	 * Constructor
	 * @param baseDir Directory containing users and products files.
	 * @param userFile Users' file name
	 * @param productFile Products' file name
	 */
	public Store(String baseDir, String userFile, String productFile) {
		fileLoader = new FileLoader(baseDir, userFile, productFile);

		users = fileLoader.loadUsers();
		loggedUser = null;

		products = fileLoader.loadProducts();
		currentProducts = products;
		
		currentOrder = new Order();

		categories = products.stream().map(Product::getCategory).collect(Collectors.toCollection(TreeSet::new));
	}

	/**
	 * Returns if a username corresponds to a password. If successful, will assign that user to the
	 * logged user's reference.
	 * @param username Name of the user to check
	 * @param pass Password
	 * @return true if login is correct. Otherwise false.
	 */
	public boolean login(String username, String pass) {
		User user = getUserByName(username);
		if(user == null) return false;
		
		String storedPass = user.getPass();
		if(storedPass.equals(DigestUtils.sha256Hex(pass))) {
			loggedUser = user;
			return true;
		}
				
		return false;
	}
	
	/**
	 * Registers a new user only if it doesn't exist. Writing it to the file and inserting in the list.
	 * @param username Name of user
	 * @param pass Password
	 * @return true if operation successful. Otherwise false.
	 */
	public boolean regUser(String username, String pass) {
		if(userExists(username)) return false;
		
		User newUser = new User(username, DigestUtils.sha256Hex(pass));
		if(!fileLoader.writeUser(newUser)) return false;
		
		users.add(newUser);
		return true;
	}
	
	/**
	 * Check if a user exists
	 * @param username Name of the user
	 * @return true if the user exists. Otherwise false.
	 */
	public boolean userExists(String username) {
		return users.stream().filter(u -> u.getUserName().equals(username)).count() > 0?true:false;
	}
	
	/**
	 * Returns a {@link User user} searching by its name. 
	 * @param username Name of the user
	 * @return User object if it exists, otherwise <em>null</em>.
	 */
	public User getUserByName(String username) {
		Optional<User> user = users.stream().filter(u -> u.getUserName().equals(username)).findFirst();
		return user.isPresent()?user.get():null;
	}
	
	/**
	 * Returns logged user's object
	 * @return Logged user's object or null if no user is logged.
	 */
	public User getLoggedUser() {
		return loggedUser;
	}
	
	/**
	 * Returns products categories.
	 * @return A set of products categories.
	 */
	public Set<String> getCategories() {
		return categories;
	}
	
	/**
	 * Returns a list of products corresponding to a category.
	 * @param category String containing the name of the category
	 * @return List of products corresponding to the category
	 */
	public List<Product> getProductsByCategory(String category) {
		currentProducts = products.stream().filter(p-> p.getCategory().equals(category)).collect(Collectors.toList());
		return currentProducts;
	}
	
	/**
	 * Returns the last list of products. For example, if {@link Store#getProductsByCategory(String) getProductsByCategory}
	 * is called before, it will return that list of products.
	 * @return Last returned list of products. By default all products.
	 */
	public List<Product> getCurrentProducts() {
		return currentProducts;
	}
	
	/**
	 * Returns current order (shopping cart)
	 * @return current Order
	 */
	public Order getCurrentOrder() {
		return currentOrder;
	}
	
	/**
	 * Generates a file with the current Order. If successful, creates a new Order (shopping cart).
	 * @return true if order was successfully made. Otherwise false.
	 */
	public boolean makeOrder() {
		if(fileLoader.writeOrder(loggedUser, currentOrder)) {
			currentOrder = new Order();
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns a List of files containing logged user's orders.
	 * @return List of file names containing orders.
	 */
	public List<String> getOrderList() {
		return fileLoader.listOrders(loggedUser);
	}
	
	/**
	 * 
	 * @param numFile Number of file from the list {@link Store#getOrderList() getOrderList} method returns.
	 * @return Order object representing the selected file's order. Null if file doesn't exist.
	 */
	public Order getOrder(int numFile) {
		List<String> files = fileLoader.listOrders(loggedUser);
		if(numFile < 0 || numFile > files.size()) return null;
		
		Order order = fileLoader.getOrder(products, loggedUser, files.get(numFile));
		
		return order;
	}
}
