package blog;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AddUsersWithRetryableWrites {

	public static void main(String[] args) {
		MongoClientURI uri = new MongoClientURI(
				   "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest&retryWrites=true");
				
		MongoClient client = new MongoClient(uri);
		
		MongoDatabase blog = client.getDatabase("blog");
		
		MongoCollection<Document> users = blog.getCollection("users");
		
		for (int i = 0; i < 100000; i++) {
			int suffix = (int)Math.round(Math.random()*12345);
			
			try{
				Document user  = new Document()
					.append("name", "USER_"+suffix)
					.append("password", "pass"+ suffix)
					.append("lang", "ES")
					.append("karma", Integer.valueOf(suffix % 500));			
				users.insertOne(user);
			}catch(Exception e){
				
				System.out.println(e);
			}
		}		
		
		client.close();
		
	}
	
}
