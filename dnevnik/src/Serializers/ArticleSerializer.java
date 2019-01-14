package Serializers;

import java.lang.reflect.Type;
import java.util.Stack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import dnevnik.Article;
import dnevnik.Comment;

public class ArticleSerializer implements JsonSerializer<Article> {
	
	
	///Zapisva komentarite kato samo ID-tata im, a User-ite samo kato username-ovete im i dvete sa unikalni

	@Override
	public JsonElement serialize(Article src, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject object = new JsonObject();
				String result = src.getAuthor().getUsername();
				object.addProperty("author", result);
				result = src.getTitle();
				object.addProperty("title", result);
				result = src.getMainText();
				object.addProperty("mainText", result);

			
				JsonArray jsonCommentsArray = new JsonArray();
				
				
				for (int i=0; i<src.getCommentsID().size(); i++) {
					JsonPrimitive jsonComment = new JsonPrimitive(src.getCommentsID().get(i).intValue());
					jsonCommentsArray.add(jsonComment);
				}
				
//				for (Integer x : src.getCommentsID()) {
//		             JsonPrimitive jsonComment = new JsonPrimitive(x.intValue());
//		            jsonCommentsArray.add(jsonComment);
//				}
				
				
				object.add("comments", jsonCommentsArray);

				
				
				JsonArray tags = new JsonArray();
				for (String x : src.getTags()) {
		             JsonPrimitive jsonTag = new JsonPrimitive(x);
		            tags.add(jsonTag);
				}
				
				object.add("tags", tags);
				
				JsonElement date = context.serialize(src.getDateAdded());
				object.add("dateAdded", date);
				
				JsonElement read = context.serialize(src.getReadCount());
				object.add("readCount", read);
				
				JsonElement id = context.serialize(src.getID());
				object.add("articleID", id);
				
				
				
				
				
				
				
				
				return object;
	}
	

}
