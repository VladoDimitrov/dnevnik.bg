package Serializers;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import Exceptions.NoArticleException;
import dnevnik.Comment;

public class CommentDeserializer implements JsonDeserializer<Comment> {

	@Override
	public Comment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject(); 
		String username = jsonObject.get("user").getAsString();
		String commentText = jsonObject.get("commentText").getAsString();
		int article = jsonObject.get("article").getAsInt(); 
		int likes = jsonObject.get("likes").getAsInt();
		int dislikes = jsonObject.get("dis").getAsInt();
		int commentID = jsonObject.get("commentID").getAsInt();
		int idCounter = jsonObject.get("idCounter").getAsInt();
	
		String dateString = json.getAsJsonObject().get("dateAdded").getAsString();
		DateFormat format = new SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = null;
		try {
			 date = format.parse(dateString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		Comment comment = new Comment(username, commentText, likes, dislikes, commentID, article, date, idCounter);
		
		
		return comment;
	}
	
}
