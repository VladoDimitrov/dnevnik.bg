package dnevnik;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class Article {

	private User author;
	private String authorName;
	private String title;
	private String mainText;
//	private Stack<Comment> comments;
	private List<Integer> comments;
	private Set<String> tags;
	private Date dateAdded;
	private int readCount;
	private int articleID;
	private static int idCounter = 0;
	private static Set<String> allTags = new HashSet<String>();



	public Article(User author, String title, String text, Set<String> tags) {
		this.author = author;
		this.title = title;
		this.mainText = text;
		if (tags != null ) {
		this.tags = tags;
		tags.forEach(tag -> allTags.add(tag));
		}
		readCount = 0;
		dateAdded = Calendar.getInstance().getTime();
		articleID = ++idCounter;
		comments = new ArrayList<Integer>();
		this.authorName = this.author.getUsername();
	}

	public Article(String author2, String title, String mainText, Set<String> tags) {
		this.setAuthorName(author2);
		this.title = title;
		this.mainText = mainText;
		this.tags = tags;
		tags.forEach(tag -> allTags.add(tag));

		readCount = 0;
		dateAdded = Calendar.getInstance().getTime();
		articleID = ++idCounter;
		comments = new ArrayList<Integer>();
	}

	public void addComment(Comment comment) {
		AllComments.addComment(comment);
		this.comments.add(comment.getCommentID());
	}
	

	public void showComments() {
		int commentNum = 0;
		for (Integer comment : comments) {
			System.out.print(++commentNum + ": ");
			System.out.println(comment.toString());
		}
	}

	public String getPreview() {
		
		 String articleToString = ("[" + articleID + "] " + title + " Added: " + dateAdded + " Read count: " + this.getReadCount() + " Comments: "+ this.getComments().size());
		 return articleToString+'\n';
	}
	
	
	
	public User getAuthor() {
		return author;
	}
	
	public int getID() {
		return articleID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(User user, String title) {
		if (user.equals((User) this.getAuthor()))
			this.title = title;
	}

	public String getMainText() {
		return mainText;
	}

	public void setMainText(User user, String mainText) {
		if (user.equals((User) this.getAuthor()))
			this.mainText = mainText;
	}

//	Get comments from AllComments
	
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
	
	
	
	public List<Integer> getCommentsID() {
		return new ArrayList<Integer>(this.comments);
		
	}
	
	public Comment getComment(int commentID) {
		Comment findComment = null;
		for (Comment comment : AllComments.getAllComments()) {
			if (comment.getCommentID() == commentID) findComment = comment;
		}
		return findComment;
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + articleID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (articleID != other.articleID)
			return false;
		return true;
	}

	public Set<String> getTags() {
		//return new HashSet<String>(tags);
		return this.tags;
	}
	
	@Override
	public String toString() {
		String articleToString = ("[" + articleID + "] " + title + "\nAuthor: " + author.getUsername() + "; Added: " + dateAdded + "\n" + mainText + "\nTags: " + tags);
		return '\n' + articleToString + '\n';
	}
	
	
	public Set<Comment> sortCommentsByLikes() {
		TreeSet<Comment> result = new TreeSet<Comment>(new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				if(o1.getThumbsUp()==o2.getThumbsDown()) {
					return 1;
				}
				return -(o1.getThumbsUp()-o2.getThumbsUp());
			}
		});
		result.addAll(getComments());
		return result;
	}

	public int getReadCount() {
		return readCount;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		if (authorName!=null)
			this.authorName = authorName;
	}

	public void setAuthor(User author) {
		if (author!=null)
			this.author = author;
	}

	public void setComments(int[] comments2 ) {
		for (int i : comments2) {
			this.comments.add(i);
		}
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}

	
	public void showAllComments(int articleID) {
		AllArticles.findByID(articleID).comments.forEach(comment -> System.out.println(comment.toString()));
	}
	
}
