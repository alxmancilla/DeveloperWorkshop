package blog;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AddArticle {

	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"
	        , Locale.ENGLISH);

	/**
	 *    "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest"
	 * @param args
	 */
	public static void main(String[] args) {

		MongoClientURI uri = new MongoClientURI(
				"mongodb://127.0.0.1:27017"
				);
				
		MongoClient client = new MongoClient(uri);
        MongoDatabase blog = client.getDatabase("blog");
        MongoCollection<Document> articulos = blog.getCollection("articles");
        
        String myName = "alxmancilla";

        Document articulo = new Document("title","My article")
                .append("author", myName)
                .append("text", "Lorem ipsum dolor sit amet […]")
                .append("tags", Arrays.asList("demo", "Java", "MongoDB"))
                .append("date", new Date());
        
        articulos.insertOne(articulo);
	
  	client.close();
    }
}
