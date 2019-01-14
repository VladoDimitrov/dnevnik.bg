package dnevnik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Exceptions.InvalidStringException;
import Exceptions.NoArticleException;
import Exceptions.NoLoggedUserException;
import Exceptions.UserNotFoundException;
import Serializers.ArticleDeserializer;
import Serializers.ArticleSerializer;
import Serializers.CommentDeserializer;
import Serializers.CommentSerializer;
import Serializers.UserDeserializer;
import Serializers.UserSerializer;

public class Main {

	private static boolean isRunning = true;
	private static User currentUser = User.getDefaultUser();

	public static void main(String[] args) {
		load();
		System.out.println("Hello!");

		showAvailableCommands();

		while (isRunning) {
			System.out.println("What would you like to do next?");
			String command = null;
			Scanner in = new Scanner(System.in);
			command = in.nextLine();
			String[] params = command.split("\\||__");

			switch (params[0]) {
			case "10":
				registerNewUser(params[1], params[2], params[3]);
				pressToContinue();
				break;
			case "11":
				currentUser = logIn(params[1], params[2]);
				pressToContinue();
				break;
			case "12":
				logOut();
				pressToContinue();
				break;
			case "20":
				preview();
				pressToContinue();
				break;
			case "21":
				writeArticle(params[1], params[2], params.length < 4 ? "" : params[3]);
				pressToContinue();
				break;
			case "22":
				readArticle(Integer.parseInt(params[1]));
				pressToContinue();
				break;
			case "23":
				editArticle(Integer.parseInt(params[1]), params[2], params[3]);
				pressToContinue();
				break;
			case "32":
				readCommentsOfArticle(Integer.parseInt(params[1]));
				pressToContinue();
				break;
			case "33":
				commentArticle(Integer.parseInt(params[1]), params[2]);
				pressToContinue();
				break;
			case "31":
				readCommentsOfUser(params[1]);
				pressToContinue();
				break;
			case "34":
				likeComment(Integer.parseInt(params[1]));
				pressToContinue();
				break;
			case "35":
				dislikeComment(Integer.parseInt(params[1]));
				pressToContinue();
				break;
			case "40":
			case "41":
			case "42":
			case "43":
				searchForWord(Integer.parseInt(params[0]), params[1]);
				pressToContinue();
				break;
			case "90":
				showAvailableCommands();
				break;
			case "00":

				System.out.println("Thank you for visiting! Until next time!");
				isRunning = false;

			}
			save();
		}

	}

	private static void registerNewUser(String name, String pass, String repeatPass) {

		if (pass.equals(repeatPass)) {
			AllUsers.register(name, pass);
			System.out.println("New user registered");
		} else {
			System.out.println("Passwords don't match");
		}

	}

	private static User logIn(String name, String pass) {

		if (currentUser == null) {
			try {
				if (AllUsers.getAllUsers().contains(new User(name, pass))) {
					return AllUsers.logIn(name, pass);
				}
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
		}

		currentUser.setLogged(false);
		currentUser = null;
		User newUser = null;
		try {
			newUser = AllUsers.logIn(name, pass);
		} catch (UserNotFoundException e) {
			System.out.println("Username not found");
		}
		return newUser;

	}

	private static void logOut() {
		System.out.println("You logged out this user: " + currentUser.getUsername());
		currentUser.setLogged(false);
		currentUser = User.getDefaultUser();

	}

	private static void preview() {
		AllArticles.previewTop5ByCategory();
	}

	private static void readArticle(int articleID) {
		Article article = AllArticles.findByID(articleID);
		article.setReadCount(article.getReadCount() + 1);
		System.out.println(article.toString());

	}

	private static void writeArticle(String title, String text, String tags) {

		try {
			currentUser.addArticle(title, text, tags);
			return;
		} catch (NoLoggedUserException e) {
			System.out.println("Must be logged in to write or comment");
			return;
		}
	}

	private static void editArticle(int articleID, String title, String text) {
		Article article = AllArticles.findByID(articleID);
		if (!article.equals(null)) {
			if (title.length() != 0) {
				article.setTitle(currentUser, title);
			}
			if (text.length() != 0) {
				article.setMainText(currentUser, text);
			}

		}

	}

	private static void commentArticle(int articleID, String commentText) {
		if (currentUser != User.getDefaultUser()) {
			try {
				Article article = AllArticles.findByID(articleID);
				Comment comment = new Comment(currentUser, articleID, commentText);
				article.addComment(comment);
				currentUser.addCommentID(comment);
			} catch (NoArticleException e) {
				System.out.println("Invalid Article ID");
			}
		} else {
			System.out.println("You must be logged to comment");
			return;
		}
	}

	private static void readCommentsOfArticle(int articleID) {

		AllArticles.findByID(articleID).getComments().forEach(comment -> System.out.println(comment));

	}

	private static void readCommentsOfUser(String username) {
		try {
			AllUsers.getUserByUsername(username).getComments().forEach(comment -> System.out.println(comment));
			return;
		} catch (UserNotFoundException e) {
			System.out.println("Unable to find user");
			return;
		} catch (InvalidStringException e) {
			System.out.println("User hasn't commented");
			return;
		}
	}

	private static void likeComment(int commentID) {
		AllComments.findComment(commentID).thumbsUp();
	}

	private static void dislikeComment(int commentID) {
		AllComments.findComment(commentID).thumbsDown();
	}

	private static void searchForWord(int command, String word) {
		try {
			switch (command) {
			case 40:
				AllArticles.searchForWord(word).forEach(article -> System.out.println(article.getPreview()));
				break;
			case 41:
				AllArticles.searchForWordInTitle(word).forEach(article -> System.out.println(article.getPreview()));
				break;
			case 42:
				AllArticles.searchForWordInText(word).forEach(article -> System.out.println(article.getPreview()));
				break;
			case 43:
				AllArticles.searchForWordInTags(word).forEach(article -> System.out.println(article.getPreview()));
				break;
			default:
				break;
			}
		} catch (NoArticleException e) {
			System.out.println("Article not found.");
			return;
		}
	}

	private static void showAvailableCommands() {

		try {
			BufferedReader commands = new BufferedReader(new FileReader("src\\files\\Available commands.txt"));
			String line = null;
			while ((line = commands.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("E kak bez komandi...");
		}

	}

	private static void pressToContinue() {
		System.out.println("Press any key to continue...");
		try {
			System.in.read();
		} catch (IOException e) {
			return;
		}
	}

	static void saveCommentsIntoFile(String filePath) {
		Set<Comment> comments = AllComments.getAllComments();
		Gson gson = new GsonBuilder().registerTypeAdapter(Comment.class, new CommentSerializer()).setPrettyPrinting()
				.create();
		try {
			FileWriter writer = new FileWriter(filePath);
			String result = gson.toJson(comments);
			writer.write(result);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	static void loadComments(String filePath) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Comment.class, new CommentDeserializer()).create();
		Set<Comment> comments = new HashSet<Comment>();
		try {
			Reader reader = new FileReader(filePath);
			Type listType = new TypeToken<HashSet<Comment>>() {
			}.getType();
			comments = (Set<Comment>) gson.fromJson(reader, listType);
			AllComments.setAllComments(comments);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	static void loadArticles(String filePath) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Article.class, new ArticleDeserializer()).create();
		Set<Article> articles = new HashSet<Article>();
		try {
			Reader reader = new FileReader(filePath);
			Type listType = new TypeToken<HashSet<Article>>() {
			}.getType();
			articles = (Set<Article>) gson.fromJson(reader, listType);
			AllArticles.setAllArticles(articles);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void saveArticleIntoFile(String filePath) {
		Set<Article> articles = AllArticles.getAllArticles();
		Gson gson = new GsonBuilder().registerTypeAdapter(Article.class, new ArticleSerializer()).setPrettyPrinting()
				.create();
		try {
			FileWriter writer = new FileWriter(filePath);
			String result = gson.toJson(articles);
			writer.write(result);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void saveUsersIntoFile(String filePath) {
		Set<User> users = AllUsers.getAllUsers();
		Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserSerializer()).setPrettyPrinting()
				.create();

		try {
			FileWriter writer = new FileWriter(filePath);
			String result = gson.toJson(users);
			writer.write(result);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	static void loadUsers(String filePath) {
		Set<User> users = new HashSet<User>();
		Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserDeserializer()).setPrettyPrinting()
				.create();

		try {
			FileReader reader = new FileReader(filePath);
			Type listType = new TypeToken<HashSet<User>>() {
			}.getType();
			users = (Set<User>) gson.fromJson(reader, listType);
			if (users != null)
				for (User x : users) {
					AllUsers.register(x);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// if (users!=null) {
		// for (User x : users) {
		// if (x.isLogged()) {
		// try {
		// LoggedUser.setLoggedUser(x);
		// } catch (UserNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// }
	}

	public static void load() {
		loadArticles("src\\files\\articles.json");
		loadUsers("src\\files\\users1.json");
		loadComments("src\\files\\comments2.json");

		for (Article x : AllArticles.getAllArticles()) {
			try {
				x.setAuthor(AllUsers.getUserByUsername(x.getAuthorName()));
			} catch (UserNotFoundException | InvalidStringException e) {
				System.out.println("ima greshka nqkva!");
				e.printStackTrace();
			}
		}

		for (User u : AllUsers.getAllUsers()) {
			for (Integer i : u.getArticlesID()) {
				u.setArticleByID(i);
			}
		}

		for (Comment c : AllComments.getAllComments()) {
			String username = c.getUsername();
			try {
				User user = AllUsers.getUserByUsername(username);
				c.setUser(user);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidStringException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		for (User u : AllUsers.getAllUsers()) {
			List<Integer> articles = new ArrayList<Integer>();
			for (Article a : AllArticles.getAllArticles()) {
				if (a.getAuthor().equals(u))
					articles.add(a.getID());
			}
			int[] arrayArticles = new int[articles.size()];
			for (int i = 0; i < articles.size(); i++)
				arrayArticles[i] = articles.get(i);
			u.setArticlesID(arrayArticles);

			List<Integer> comments = new ArrayList<Integer>();
			for (Comment c : AllComments.getAllComments()) {
				if (c.getUser().equals(u))
					comments.add(c.getCommentID());
			}
			int[] arrayComments = new int[comments.size()];
			for (int i = 0; i < comments.size(); i++)
				arrayComments[i] = comments.get(i);
			u.setComments(arrayComments);
		}
	}
	private static void save() {
		saveUsersIntoFile("src\\files\\users1.json");
		saveCommentsIntoFile("src\\files\\comments2.json");
		saveArticleIntoFile("src\\files\\articles.json");
	}
}
