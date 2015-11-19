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
public class User {
    private String username;
	private String pass;
	
	/**
	 * Constructor
	 * @param username Name of the user
	 * @param pass Password of the user (usually encrypted)
	 */
	public User(String username, String pass) {
		this.username = username;
		this.pass = pass;
	}
	
	/**
	 * Gets user's name
	 * @return user's name
	 */
	public String getUserName() {
		return username;
	}
	
	/**
	 * Gets user's password
	 * @return user's password
	 */
	public String getPass() {
		return pass;
	}
}
