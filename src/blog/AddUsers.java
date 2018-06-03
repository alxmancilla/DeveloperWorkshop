package blog;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AddUsers {

	public static void main(String[] args) {

		/**
		 *    	"mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest");
 		 *			"mongodb://localhost:27017/test"
		 */
		MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://demo:<PASSWORD>@democlusteratlas-5nwqt.mongodb.net/test?retryWrites=true"
												);
		MongoClient client = new MongoClient(uri);
		
		MongoDatabase blog = client.getDatabase("blog");
		
		MongoCollection<Document> users = blog.getCollection("users");
		
		List<Document> list = new ArrayList<Document>();
		Date initDate = new Date();

		System.out.println("Inserting: ");
		try{
			for (int i = 0; i < 10000; i++) {
				int suffix = (int)Math.round(Math.random()*12345);
	
					Document user  = new Document()
						.append("name", "USER_"+suffix)
						.append("password", "pass"+ suffix)
						.append("lang", "ES")
						.append("creation_date", new Date())
						.append("karma", Integer.valueOf(suffix % 500));	
					
				list.add(user);
				if(i % 1000 == 999){
						System.out.print(".");
						users.insertMany(list);
						list.clear();
				}
			}		
			Date finalDate = new Date();
			long millis = finalDate.getTime()  - initDate.getTime();
			System.out.println("\n Seconds: " + millis/1000);  
		
		}catch(Exception e){
			System.out.println(e);
		}finally{
			client.close();
		}
	}
	
}
