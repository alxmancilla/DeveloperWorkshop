package blog;



import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FindUser {

	public static void main(String[] args) {
		MongoClientURI uri = new MongoClientURI(
				   "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest");
				
		MongoClient client = new MongoClient(uri);
		
		MongoDatabase blog = client.getDatabase("blog");
		
		MongoCollection<Document> users = blog.getCollection("users");
		/**
		Document user  = new Document()
					.append("name", "zxcv")
					.append("password", "top secret")
					.append("lang", "ES");
			users.insertOne(user);
			**/
			
		Document filter = new Document("name", "alxmancilla");
			List<Document> documents = users.find(filter).into(new ArrayList<Document>());
			

	       for (Document doc : documents) {
	           System.out.println(doc.toJson());
	       }
		
		client.close();
		
	}
	
}
