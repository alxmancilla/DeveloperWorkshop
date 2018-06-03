package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;

public class BulkInsertSample {

	public static void main(String[] args) {

		/***  Java driver 3.6:  
		 * 		   "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest&retryWrites=true"
		 *				  "mongodb://demo:<PASSWORD>@node-100.am-cloudgroup.0675.mongodbdns.com:27017/test?replicaSet=Demo_PSS&authSource=admin&retryWrites=true"
		 **/
		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://localhost:27017/test"
					);
		
			
		MongoClient client = new MongoClient(uri);
						
		MongoDatabase test = client.getDatabase("blog2");
					
		MongoCollection<Document> users = test.getCollection("users");
		
		System.out.println("Beginning with " + users.count()+" users" );  
		Date initDate = new Date();
		System.out.println("Inserting: ");
		List<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
		for (int i = 0; i < 1000000; i++) {
			int suffix = (int)Math.round(Math.random()*12345);

				Document user  = new Document()
					.append("name", "USER_"+suffix)
					.append("password", "pass"+ suffix)
					.append("lang", "ES")
					.append("karma", Integer.valueOf(suffix % 500));	
				
			list.add(new InsertOneModel<Document>(user));
			if(i % 1000 == 999){
				try{
					users.bulkWrite(list,
							  new BulkWriteOptions().ordered(false));
					System.out.print(".");
					list.clear();
				}catch(Exception e){
					System.out.println(e);
				}
			}	
		}		
		Date finalDate = new Date();
		long millis = finalDate.getTime()  - initDate.getTime();
		System.out.println("\nEnding with a total of users: " + users.count());  
		System.out.println("\n Seconds: " + millis/1000);  

		client.close();		
		
		
		
	}
}
