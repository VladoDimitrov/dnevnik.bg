package Serializers;


import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import Exceptions.InvalidStringException;
import Exceptions.UserNotFoundException;
import dnevnik.Article;
import dnevnik.Comment;
import dnevnik.User;
import dnevnik.AllUsers;

public class ArticleDeserializer implements JsonDeserializer<Article> {

	@Override
	public Article deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject(); 
		String author = json.getAsJsonObject().get("author").getAsString();
		
		String title = json.getAsJsonObject().get("title").getAsString();
		
		String mainText = json.getAsJsonObject().get("mainText").getAsString();
		
		String dateString = json.getAsJsonObject().get("dateAdded").getAsString();
		DateFormat format = new SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = null;
		try {
			 date = format.parse(dateString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JsonArray jsonCommentsArray = jsonObject.get("comments").getAsJsonArray();
		int [] commentsID = new int [jsonCommentsArray.size()];
		for (int i = 0; i<commentsID.length; i++) {
			JsonElement jsonComment = jsonCommentsArray.get(i);
			commentsID[i] = jsonComment.getAsInt();
		}
		int[] comments = new int [commentsID.length];
		comments = Arrays.copyOf(commentsID, commentsID.length);
		
		
		JsonArray jsonTagsArray = jsonObject.get("tags").getAsJsonArray();
		String [] tags = new String [jsonTagsArray.size()];
		for (int i = 0; i<tags.length; i++) {
			JsonElement jsonTag = jsonTagsArray.get(i);
			tags[i] = jsonTag.getAsString();
		}
		Set<String> tags1 = new HashSet<String>(Arrays.asList(tags));
		
		int readCount = json.getAsJsonObject().get("readCount").getAsInt();
		
		int articleID = json.getAsJsonObject().get("articleID").getAsInt();
		
		
//		 if (jsonObject.has("comments")) {
//	            JsonElement elem = jsonObject.get("comments");
//	            if (elem != null && !elem.isJsonNull()) {
//
//	                Gson gsonDeserializer = new GsonBuilder()
//	                        .registerTypeAdapter(Comment.class, new CommentTypeAdapter())
//	                        .create();
//	                gsonDeserializer.fromJson(jsonObject.get("comment"), Article.class);
//	            }
//	        }
//		 }
//		Stack<Comment> = json.getAsJsonArray().
		
	//user = AllUsers.getUserByUsername(author);
		Article article = new Article(author, title, mainText, tags1);
		article.setArticleID(articleID);
		article.setReadCount(readCount);
		article.setComments(comments);
		article.setDateAdded(date);
		return article;
		
	}

}
