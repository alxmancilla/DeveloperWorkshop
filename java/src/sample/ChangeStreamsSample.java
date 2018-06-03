package sample;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.client.model.changestream.OperationType;

public class ChangeStreamsSample {
	/*
	 * 		   "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=replicaTest&retryWrites=true"
	 **/
	static boolean continueLooping = true;
	
	public static void main(String[] args) {
		MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://demo:<PASSWORD>@democlusteratlas-5nwqt.mongodb.net/test?retryWrites=true"
								);
		
		MongoClient client = new MongoClient(uri);
		
		MongoDatabase blog = client.getDatabase("blog");
		
		MongoCollection<Document> collection = blog.getCollection("users");
		
		
		@SuppressWarnings("rawtypes")
		Block<ChangeStreamDocument> printBlock = new Block<ChangeStreamDocument>() {
		       public void apply(final ChangeStreamDocument document) {
		    	   OperationType opType = document.getOperationType();
		    	   System.out.println("¡Change Stream recibido!");
		    	   System.out.println("Tipo de operacion: " + document.getOperationType().getValue());
		    	   System.out.println("Document key:" + document.getDocumentKey().toJson());
		    	   
		    	   if(document.getFullDocument() != null) {
		    		   System.out.println(((Document)document.getFullDocument()).toJson());
		    	   }
		    	   
		    	   if(opType.getValue().equals("delete")){
		    		   ChangeStreamsSample.continueLooping = false;
		    	   }
		       }
		};	

		
		collection.watch(Arrays.asList(
						Aggregates.match(
								Filters.in("operationType", 
											Arrays.asList("insert", "update", "replace", "delete"))
						)
					)).fullDocument(FullDocument.UPDATE_LOOKUP).forEach((Block)printBlock);
		
		
		while(ChangeStreamsSample.continueLooping){
			// keep listening for change streams
		}
		
		
		client.close();
		
	}
}
