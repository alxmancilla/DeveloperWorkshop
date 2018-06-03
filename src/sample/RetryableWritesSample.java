package sample;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.session.ClientSession;

public class RetryableWritesSample {

	public static void main(String[] args) {

		/***  Java driver 3.6:  mongodb+srv://amatlas:<PASSWORD>@demoatlas-5nwqt.mongodb.net/test 
		 * 		   "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest&retryWrites=true"
		 **/
		
		MongoClientURI uri = new MongoClientURI(
				  "mongodb://demo:<PASSWORD>@node-100.am-cloudgroup.0675.mongodbdns.com:27017/test?replicaSet=Demo_PSS&authSource=admin&retryWrites=true"
				);
		
			
		MongoClient client = new MongoClient(uri);
						
		MongoDatabase blog = client.getDatabase("blog");
					
		MongoCollection<Document> collection = blog.getCollection("users");
		
		System.out.println("Beginning with " + collection.count()+" users" );  
		System.out.println("Inserting: ");
		List<Document> list = new ArrayList<Document>();
		for (int i = 0; i < 1000000; i++) {
			int suffix = (int)Math.round(Math.random()*12345);

				Document user  = new Document()
					.append("name", "USER_"+suffix)
					.append("password", "pass"+ suffix)
					.append("lang", "ES")
					.append("karma", Integer.valueOf(suffix % 500));	
				
			list.add(user);
			if(i % 1000 == 999){
				try{
					collection.insertMany(list);
					System.out.print(".");
					list.clear();
				}catch(Exception e){
					System.out.println(e);
				}
			}	
		}		

		System.out.println("\nEnding with a total of users: " + collection.count());  
		
		client.close();		
		
		
		
	}
}
