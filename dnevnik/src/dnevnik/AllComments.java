package dnevnik;

import java.util.HashSet;
import java.util.Set;

public class AllComments {

	private static Set<Comment> allComments = new HashSet<Comment>();

	public static Set<Comment> getAllComments() {
		return new HashSet<Comment>(allComments);
	}

	public static void addComment(Comment comment) {
		AllComments.allComments.add(comment);
	}

	public static void setAllComments(Set<Comment> comments) {
		if (comments != null) {
			AllComments.allComments = new HashSet<>(comments);
			return;
		}
		AllComments.allComments = new HashSet<>();
	}

	public static Comment findComment(int commentID) {

		for (Comment comment : allComments) {
			if (comment.getCommentID() == commentID) {
				return comment;
			}
		}

		System.out.println("Couldn't find comment");
		return null;

	}
}
