package blog;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestInsertUser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		MongoClient client = new MongoClient("localhost", 27017);
		
		MongoDatabase blog = client.getDatabase("blog");
		
		MongoCollection<Document> users = blog.getCollection("users");
		// { "name" : "amancilla", "password" : "top secret", "lang" : "ES" }
		Document user = new Document()
				.append("name",  "user3")
				.append("password", "12345")
				.append("lang", "ES")
				.append("email", "correo@miserver.com");
		
		users.insertOne(user);
		
		client.close();
		
	}

}
