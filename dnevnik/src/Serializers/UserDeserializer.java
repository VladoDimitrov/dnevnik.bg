package Serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import Exceptions.InvalidStringException;
import dnevnik.User;

public class UserDeserializer implements JsonDeserializer<User> {

	@Override
	public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String username = json.getAsJsonObject().get("username").getAsString();
		
		String password = json.getAsJsonObject().get("password").getAsString();
		
		JsonArray jsonUserArticlesArray = jsonObject.get("articles").getAsJsonArray();
		int [] articlesID = new int[jsonUserArticlesArray.size()];
		for (int i=0; i<articlesID.length; i++) {
			JsonElement jsonUserArticle = jsonUserArticlesArray.get(i);
			articlesID[i] = jsonUserArticle.getAsInt();
		}
		
		
		JsonArray jsonUserCommentsArray = jsonObject.get("comments").getAsJsonArray();
		int [] commentsID = new int [jsonUserCommentsArray.size()];
		for (int i=0; i<jsonUserCommentsArray.size(); i++) {
			JsonElement jsonUserComment = jsonUserCommentsArray.get(i);
			commentsID[i] = jsonUserComment.getAsInt();
			
		}
		
		boolean isLogged = json.getAsJsonObject().get("isLogged").getAsBoolean();
		
		try {
			User user = new User(username, password, isLogged);
			user.setArticlesID(articlesID);
			user.setComments(commentsID);
			return user;
		} catch (InvalidStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
