package blog;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class AddArticles {
	public static void main(String[] args) {
		MongoClientURI uri = new MongoClientURI(
				   "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest");
				
		MongoClient client = new MongoClient(uri);
		
		MongoDatabase blog = client.getDatabase("blog");
		
		MongoCollection<Document> articles = blog.getCollection("articles");
		
		List<Document> list = new ArrayList<Document>();
		
		for (int i = 0; i < 1000000; i++) {
			Document article = new Document()
					.append("_id", Integer.valueOf(i))
					.append("title", "a b c" + i)
					.append("author", "USER_"+ Math.round(Math.random()*1000))
					.append("text", "Lorem ipsum dolor sit amet, ..." + Math.round( Math.random()*12345 ))
					.append("tags", Arrays.asList("demo","español","MongoDB"));
			list.add(article);
			if(i % 100 == 99){
				articles.insertMany(list);
				list.clear();
			}
		}
		
		client.close();
		
		
	}
}
