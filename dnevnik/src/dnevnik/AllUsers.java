package dnevnik;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import Exceptions.InvalidStringException;
import Exceptions.NoLoggedUserException;
import Exceptions.UserNotFoundException;

public class AllUsers {
	private static final int MIN_PASS_LEN = 4;
	private static Set<User> allUsers = new HashSet<User>();
	

	public static void register(String username, String pass) {
		
		User newUser = null;
		
		if (isValidUsername(username) && isValidPassword(pass)) {
			newUser = new User(username, pass);
		}
		if (!allUsers.contains(newUser)) {
			allUsers.add(newUser);
		}
	}
	public static void register(User user) {
		register(user.getUsername(), user.getPassword());
	}
	
	private static boolean isValidPassword(String password) {
		return (password != null && password.trim().length() > 0);
	}
	
	private static boolean isValidUsername(String username) {
		return (username != null && username.trim().length() > 0);
	}
	
	public static User getUserByUsername(String username) throws UserNotFoundException, InvalidStringException {
		if (isValidUsername(username)) {
			for (User x : AllUsers.getAllUsers()) {
				if (x.getUsername().equals(username))
					return x;
			}
			throw new UserNotFoundException("No User with this Username!");
		}
		throw new InvalidStringException("Invalid username passed!");
	}
	
	public static Set<User> getAllUsers() {
		return new HashSet<User>(AllUsers.allUsers);
	}
	
//	public static void logOut() throws NoLoggedUserException, UserNotFoundException {
//		LoggedUser.getInstance().setLogged(false);
//		LoggedUser.setLoggedUser(null);
//		System.out.println("You logged out");
//	}
	
	
	public static User logIn(String username, String password) throws UserNotFoundException {
		if (username!=null && username.trim().length()>2) {
			for (User user : AllUsers.allUsers) {
				if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
					if (!user.isLogged()) {
						user.setLogged(true);
//						try {
//							LoggedUser.setLoggedUser(user);
//						} catch (UserNotFoundException e) {
//							e.printStackTrace();
//						}
						System.out.println("You are now logged in as: "+user.getUsername());
						return user;
					} else {
						System.out.println("You are already logged in as this user!");
						return user;
					}
				}
			}
			throw new UserNotFoundException("Invalid username or password!");
		}
		return null;
	}
	
	public Stack<Article> getAllArticlesFromUser(String username) throws InvalidStringException, UserNotFoundException {
		if (username!=null && username.trim().length()>0) {
			for (User x : AllUsers.allUsers) {
				if (x.getUsername().equals(username)) {
					return x.getArticles();
				}
			}
		} else {
			throw new InvalidStringException("Invalid string!");
		}
		throw new UserNotFoundException("User not found");	
	}
	
	public Stack<Comment> getAllCommentsFromUser(String username) throws InvalidStringException, UserNotFoundException {
		if (username!=null && username.trim().length()>0) {
			for (User x : AllUsers.allUsers) {
				if (x.getUsername().equals(username)) {
					return x.getComments();
				}
			}
		} else {
			throw new InvalidStringException("Invalid string!");
		}
		throw new UserNotFoundException("No such user");	
	}
	public static User logOut() {
		return User.getDefaultUser();
	}
	
	
}