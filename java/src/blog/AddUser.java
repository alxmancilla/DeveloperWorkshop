package blog;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AddUser {

	public static void main(String[] args) {
		/*
		 * 	  "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest");
		 */
			
		MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://demo:<PASSWORD>@democlusteratlas-5nwqt.mongodb.net/test?retryWrites=true"
				);
				
			MongoClient client = new MongoClient(uri);
			
			try{
				MongoDatabase blog = client.getDatabase("blog");
				
				MongoCollection<Document> users = blog.getCollection("users");
				
				Document user  = new Document()
							.append("name", "alxmancilla")
							.append("password", "top secret")
							.append("lang", "ES")
							.append("creation_date", new Date());
			
				users.insertOne(user);
				
				Document filter = new Document("name", "alxmancilla");
				List<Document> documents = users.find().into(new ArrayList<Document>());
				for (Document doc : documents) {
					System.out.println(doc.toJson());
				}
				
				
				users.deleteOne(filter);

			}catch(Exception e){
				System.out.println(e);
			}finally{
				client.close();
			}
		
	}
	
}
