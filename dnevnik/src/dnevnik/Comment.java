package dnevnik;

import java.util.Calendar;
import java.util.Date;

import Exceptions.InvalidStringException;
import Exceptions.NoArticleException;
import Exceptions.UserNotFoundException;

public class Comment {

//	private Article article;
	private final int articleID;
	private User user;
	private final String username;
	private final Date date;
	private String commentText;
	private int likes;
	private int dislikes;
	private int commentID;
	private static int idCounter = 0;

	public int getCommentID() {
		return commentID;
	}

	 public static int getIDcounter() {
	 return Comment.idCounter;
	 }

	public Comment(User user, int articleID, String commentText) throws NoArticleException {
		this.user = user;
		this.commentText = commentText;
//		article = AllArticles.findByID(articleID);
		this.articleID = articleID;
		if (AllArticles.findByID(articleID) == null)
			throw new NoArticleException("No such article");
		dislikes = 0;
		likes = 0;
		date = Calendar.getInstance().getTime();
		this.username = user.getUsername();
		commentID = ++idCounter;
	}

	public Comment(String username, String text, int likes, int dislikes, int id, int articleID, Date date,
			int idCounter) { /* public Comment(Comment comment) - ? */
		this.username = username;
		this.commentText = text;
		this.likes = likes;
		this.dislikes = dislikes;
		Comment.idCounter = idCounter;
		this.commentID = id;
		this.articleID = articleID;
		this.date = date;
//		article = AllArticles.findByID(articleID);
	}

//	private void setArticle(int articleByIndex) {
//		this.article = AllArticles.findByID(articleByIndex);
//	}
//
//	private void setUser(String username) {
//		try {
//			this.user = AllUsers.getUserByUsername(username);
//		} catch (UserNotFoundException | InvalidStringException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public int getArticleID() {
		return this.articleID;
	}

	public String getUsername() {
		return this.username;
	}

	public String getCommentText() {
		return commentText;
	}

	public void thumbsUp() {
		likes++;
	}

	public void thumbsDown() {
		dislikes++;
	}

	@Override
	public String toString() {
		String comment = "\t[" + commentID + "] " + user.toString() + "\n\t" + date + "\n\t" + commentText + "\n\tDislikes: "
				+ dislikes + " Likes: " + likes;
		return comment;
	}

//	public Article getArticle() {
//		return article;
//	}

	public User getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}

	public int getThumbsUp() {
		return likes;
	}

	public int getThumbsDown() {
		return dislikes;
	}

	public void edit(User user, String text) {
		if (text != null && user.equals(this.user)) {
			this.commentText = text;
		}
	}

	@Override
	public boolean equals(Object comment) {
		return commentID == ((Comment) comment).getCommentID();
	}

	@Override
	public int hashCode() {
		return commentID;
	}

	public void setUser(User user) {
		if (user!=null)
			this.user = user;
	}
}
