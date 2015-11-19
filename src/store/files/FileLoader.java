/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import store.Order;
import store.Product;
import store.User;

/**
 *
 * @author araceliteruel
 */
public class FileLoader {
    private String baseDir, userFile, productFile;
	
	/**
	 * Constructor
	 * @param baseDir Base directory where all files are located.
	 * @param userFile Name of the users file.
	 * @param productFile Name of the products file.
	 */
	public FileLoader(String baseDir, String userFile, String productFile) {
		this.baseDir     = baseDir;
		this.userFile    = userFile;
		this.productFile = productFile;
	}
	
	/**
	 * Reads the users file and returns a list of User objects contained in that file.
	 * @return List of User objects contained in users file.
	 */
	public List<User> loadUsers() {
		List<User> users;
		
		try (Stream<String> stream = Files.lines(
				Paths.get(baseDir, userFile));) {
			
			users = stream.map(line -> {
				String[] data = line.split(";");
				return new User(data[0], data[1]);
			}).collect(Collectors.toList());
			
		} catch (IOException e) {
			System.err.println("Error reading users in file " + userFile);
			users = new ArrayList<>(); // Empty list if it fails to read file
		}
		
		return users;
	}
	
	/**
	 * Writes a new user data in users file.
	 * @param user User object representing the new user.
	 * @return true if operation was successful or false otherwise.
	 */
	public boolean writeUser(User user) {
		try (PrintWriter writer = new PrintWriter(
				new FileWriter(Paths.get(baseDir, userFile).toFile(),true));) {
			writer.println(user.getUserName() + ";" + user.getPass());
		} catch (FileNotFoundException e) {
			System.err.println("Error: " + userFile + " not found.");
			return false;
		} catch (IOException e1) {
			System.err.println("Error adding user in file " + userFile);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Reads products from products file and returns them as a list.
	 * @return List of Product objects
	 */
	public List<Product> loadProducts() {
		List<Product> products;
		
		try (Stream<String> stream = Files.lines(
				Paths.get(baseDir, productFile));) {
			
			products = stream.map(line -> {
				String[] data = line.split(";");
				return new Product(Double.parseDouble(data[3]), data[0], data[1], data[2]);
			}).sorted(Comparator.comparing(Product::getName))
			.collect(Collectors.toList());
			
		} catch (IOException e) {
			System.err.println("Error reading products in file " + productFile);
			products = new ArrayList<>(); // Empty list if it fails to read file
		}
		
		return products;
	}
	
	/**
	 * Writes a new order to a file in the user's directory.
	 * @param user User object with user information.
	 * @param order Order object with order information.
	 * @return true if operation is successful. Otherwise false.
	 */
	public boolean writeOrder(User user, Order order) {
		File userDir = Paths.get(baseDir,user.getUserName()).toFile();
		if(!userDir.exists()) {
			userDir.mkdirs();
		}
		
		LocalDateTime date = LocalDateTime.now();
		String fileName = "order-" + date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".txt";
		try (PrintWriter writer = new PrintWriter(
				Paths.get(userDir.getAbsolutePath(), fileName).toFile());) {
			order.getLines().forEach(l -> writer.println(l.getProduct().getReference() + ";" + l.getProduct().getPrice() 
					+ ";" + l.getQuantity()));
		} catch (FileNotFoundException e) {
			System.err.println("Error: " + userFile + " not found.");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets a list of the files in a user directory (each one contains an order made).
	 * @param user User object with user information.
	 * @return List of files (String) in user directory.
	 */
	public List<String> listOrders(User user) {
		Path path = Paths.get(baseDir, user.getUserName());
		if(!path.toFile().exists()) return new ArrayList<>();
		
		try(Stream<Path> stream = Files.list(Paths.get(baseDir, user.getUserName()))) {
			return stream.filter(p -> p.toFile().isFile())
		    .map(p -> p.toFile().getName())
			.collect(Collectors.toList());
		} catch (IOException e) {
			System.err.println("Error reading orders.");
			return new ArrayList<>();
		}
	}
	
	/**
	 * Gets the order in a file
	 * @param prods The product's list for helping regenerate the order (only references and prices are stored).
	 * @param user User object with user information.
	 * @param file Name of the file selected to generate an Order object from.
	 * @return The Order object generated from the selected file.
	 */
	public Order getOrder(List<Product> prods, User user, String file) {
		Order order = new Order();
		
		try (Stream<String> stream = Files.lines(
				Paths.get(baseDir, user.getUserName(), file));) {
			
			stream.forEach(line -> {
				String[] data = line.split(";");
				Optional<Product> prod = prods.stream().filter(p -> p.getReference().equals(data[0])).findFirst();
				if(prod.isPresent()) {
					Product p = prod.get();
					order.addProduct(
							new Product(Double.parseDouble(data[1]), data[0], p.getName(), p.getCategory()), 
							Integer.parseInt(data[2]));
				}
			});
			
		} catch (IOException e) {
			System.err.println("Error reading " + file);
		}
		
		return order;
	}
}
