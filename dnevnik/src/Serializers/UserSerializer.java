package Serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dnevnik.User;

public class UserSerializer implements JsonSerializer<User> {

	@Override
	public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		String username = src.getUsername();
		object.addProperty("username", username);
		String password = src.getPassword();
		object.addProperty("password", password);
		
		JsonArray jsonUserArticlesArray = new JsonArray();
		for (int i=0; i<src.getArticlesID().size(); i++) {
			JsonPrimitive jsonUserArticleID = new JsonPrimitive(src.getArticlesID().get(i));
			jsonUserArticlesArray.add(jsonUserArticleID);
		}
		
		object.add("articles", jsonUserArticlesArray);
		
		boolean isLogged = src.isLogged();
		
		object.addProperty("isLogged", isLogged);
		
		JsonArray jsonUserCommentsArray = new JsonArray();
		for (int i=0; i<src.getCommentsID().size(); i++) {
			JsonPrimitive jsonUserCommentID = new JsonPrimitive(src.getCommentsID().get(i));
			jsonUserCommentsArray.add(jsonUserCommentID);
		}
		
		object.add("comments", jsonUserCommentsArray);
		return object;
	}

}
