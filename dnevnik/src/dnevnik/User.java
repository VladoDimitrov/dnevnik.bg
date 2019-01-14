package dnevnik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import Exceptions.InvalidStringException;
import Exceptions.NoArticleException;
import Exceptions.NoLoggedUserException;

public class User {

	private final static User DEFAULT_USER = new User("Dnevnik", "Dnevnik");
	private final String username;
	private String password;
	private List<Integer> articlesID;
	private boolean isLogged;
	private Stack<Article> articles;
	private List<Integer> comments;

	public User(String username, String password) {

		this.username = username;
		this.password = password;
		this.comments = new ArrayList<Integer>();
		this.isLogged = false;
		this.articles = new Stack<Article>();
		this.articlesID = new ArrayList<Integer>();
	}

	public void setArticleByID(int id) {
		Article article = AllArticles.findByID(id);
		this.articles.add(article);
	}

	public List<Integer> getArticlesID() {
		return new ArrayList<Integer>(this.articlesID);
	}

	public List<Integer> getCommentsID() {
		return new ArrayList<Integer>(this.comments);
	}

	public void setArticlesID(int[] articlesID) {
		for (int x : articlesID) {
			this.articlesID.add(x);
		}
	}

	public void setCommentById(int id) {
		this.comments.add(id);
	}

	public void setComments(int[] comments) {
		for (int x : comments) {
			this.comments.add(x);
		}
	}

	public User(String username, String password, boolean isLogged) throws InvalidStringException {
		this(username, password);
		this.isLogged = isLogged;
	}

	public User(User user) throws InvalidStringException {
		this(user.getUsername(), user.getPassword());
	}

	// Add comment

	// <<<<<<< HEAD
	// public void addComment(Article article, String comment) throws
	// InvalidStringException, NoLoggedUserException {
	// Comment tempComm = new Comment(this, article, comment);
	// if (isLogged)
	// comments.push(tempComm);
	// else {
	// throw new NoLoggedUserException("Must be logged to add comment");
	// }
	// article.addComment(tempComm);
	// =======
	// public void addComment(Article article, String comment) throws
	// InvalidStringException {
	// Comment tempComm = new Comment(this, article, comment);
	// comments.push(tempComm);
	// article.addComment(tempComm);
	// }

	public void addComment(int articleID, String comment) throws InvalidStringException, NoLoggedUserException {

		try {
			Comment tempComm = new Comment(this, articleID, comment);
			if (isLogged) {
				comments.add(tempComm.getCommentID());
				AllArticles.findByID(articleID).addComment(tempComm);
			} else {
				throw new NoLoggedUserException("Must be logged to comment");
			}

		} catch (NoArticleException e) {
			System.out.println("Please try again.");
		}

	}

	public void addArticle(String title, String text, String tagsForArticle) throws NoLoggedUserException {
		if (text != null && text.trim().length() > 0 && title != null && title.trim().length() > 0) {
			Set<String> tags = null;

			if (tagsForArticle.equals("")) {
				tags = new HashSet<String>();
				
			}else {
				String[] tagsArray = tagsForArticle.split(" ");
				tags = new HashSet<String>(Arrays.asList(tagsArray));
			}
			
			// String answer = "";
			// while (!answer.equalsIgnoreCase("n")) {
			// System.out.println("Add new tag?");
			// Scanner sc = new Scanner(System.in);
			// answer = sc.next();
			// if (answer.equalsIgnoreCase("y")) {
			// String newTag = sc.nextLine();
			// tags.add(newTag);
			// }
			// }
			Article article = new Article(this, title, text , tags);
			if (isLogged) {
				this.articles.push(article);
				this.articlesID.add(article.getID());
			} else {
				throw new NoLoggedUserException("Must be logged to add article");
			}
			AllArticles.addArticle(article);
		}
	}

	public String getPassword() {
		return password;
	}

	public Stack<Article> getArticles() {
		return (Stack<Article>) this.articles.clone();
	}

	@Override
	public String toString() {
		return username;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	public String getUsername() {
		return username;
	}

	public Stack<Comment> getComments() {
		Stack<Comment> comments = new Stack<Comment>();
		for (int commentID : this.comments) {
			for (Comment comment : AllComments.getAllComments()) {
				if (commentID == comment.getCommentID()) {
					comments.push(comment);
				}
			}
		}
		return comments;
	}

	// public String getPass() {
	// return password;
	// }

	// equals + hashCode

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object user) {
		if (this == user)
			return true;
		if (user == null)
			return false;
		if (getClass() != user.getClass())
			return false;
		User other = (User) user;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	

	public static User getDefaultUser() {
		return DEFAULT_USER;
	}

	public void addCommentID(Comment comment) {
		this.comments.add(comment.getCommentID());
	}

	// All users subclass + getter

	// logIn method

	// @SuppressWarnings("resource");
	// public static User logIn() throws InvalidStringException {
	// System.out.print("Enter username: ");
	// String username = new Scanner(System.in).next();
	// System.out.println("Enter password: ");
	// String password = new Scanner(System.in).next();
	// User tempUser = null;
	// if (isValidUsername(username) && password != null) {
	// tempUser = new User(username, password);
	// }
	// if (User.AllUsers.allUsers.contains(tempUser)) {
	// return AllUsers.allUsers.get(AllUsers.allUsers.indexOf(tempUser));
	// }
	// System.out.println("Invalid username of password. Try again? (y/n)");
	// String choice = "";
	// while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
	// choice = new Scanner(System.in).next();
	// }
	// if (choice.equalsIgnoreCase("y"))
	// return logIn();
	// else
	// return new User.GuestUser();
	//
	// }

	// GuestUser class

	// public static final class GuestUser extends User {
	//
	// static final String GUEST_USERNAME = "GuestUser";
	// static final String GUEST_PASSWORD = "GuestPass";
	//
	// public GuestUser() throws InvalidStringException {
	// super(GUEST_USERNAME, GUEST_PASSWORD, false);
	// }
	// }

	// private String pickName() {

	// String name = "";
	// while (!isValidUsername(name)) {
	// System.out.println("Enter valid name");
	// name = new Scanner(System.in).next();
	// }
	// return name;
	// }

}
