package Serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dnevnik.Comment;

public class CommentSerializer implements JsonSerializer<Comment> {

	@Override
	public JsonElement serialize(Comment src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		String commentText = src.getCommentText();
		object.addProperty("commentText", commentText);
		String username = src.getUsername();
		object.addProperty("user", username);
		int article = src.getArticleID();
		object.addProperty("article", article);
		int likes = src.getThumbsUp();
		object.addProperty("likes", likes);
		int dislikes = src.getThumbsDown();
		object.addProperty("dis", dislikes);
		int commentID = src.getCommentID();
		object.addProperty("commentID", commentID);
		int idCounter = Comment.getIDcounter();
		object.addProperty("idCounter", idCounter);
		
		JsonElement date = context.serialize(src.getDate());
		object.add("dateAdded", date);
		
		return object;
	}

}
