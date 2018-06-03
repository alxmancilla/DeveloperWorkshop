package gridfs;

import java.io.*;
import java.util.TreeMap;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class FilesUploader {

	   public static void main(String[] args) throws IOException, ParseException {

	        CommandLineParser parser = new DefaultParser();
	        Options cmdOpts;
	        cmdOpts = new Options();
	        cmdOpts.addOption("host", true, "server to connect to");
	        cmdOpts.addOption("port", true, "port to connect to");
	        cmdOpts.addOption("db", true, "database to connect to");
	        cmdOpts.addOption("coll", true, "collection name used for GridFS bucket");
	        cmdOpts.addOption("blobList", true, "REQUIRED comma delimited file with list of ExternalID's & blob file name");

	        CommandLine cmd = parser.parse(cmdOpts, args);

	        String host, db, coll, blobList;
	        Integer port;

	        if (cmd.hasOption("host"))
	            host = cmd.getOptionValue("host");
	        else
	            host = "localhost";

	        if (cmd.hasOption("port"))
	            port = Integer.parseInt(cmd.getOptionValue("port"));
	        else
	            port = 27017;

	        if (cmd.hasOption("db"))
	            db = cmd.getOptionValue("db");
	        else
	            db = "gridfsDB";

	        if (cmd.hasOption("coll"))
	            coll = cmd.getOptionValue("coll");
	        else
	            coll = "fsColl";

	        if (cmd.hasOption("blobList"))
	            blobList = cmd.getOptionValue("blobList");
	        else {
	            blobList = "./src/blobs-list-spanish.txt";
	            //System.out.println("The blob list is REQUIRED. Aborting");
	            //return;
	        }


	        MongoClient client = new MongoClient(host, port);
	        MongoDatabase mdb = client.getDatabase(db);
	        GridFSBucket gfsBucket = GridFSBuckets.create(mdb, coll);
	        File f = new File(blobList);
	        System.out.println(f.getAbsolutePath());

	        for(int i=0; i < 50; i++)   
	        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
	            for (String line; (line = br.readLine()) != null; ) {

	                // Line format: NumericExternalID,FileName,Keywords
	                String[] tokens = line.split("[,]");
	                Integer externalID = Integer.parseInt(tokens[0]);
	                String fileName = tokens[1];
	                String keywords = tokens[2];

	                File file= new File(fileName);
	                InputStream is = new FileInputStream(file);

	                TreeMap<String,Object> metadata= new TreeMap<>();
	                metadata.put("ExternalID", externalID);
	                metadata.put("Keywords", keywords);

	                GridFSUploadOptions options = new GridFSUploadOptions()
	                        .chunkSizeBytes(1024)
	                        .metadata(new Document(metadata));

	               // ObjectId objId = gfsBucket.uploadFromStream(file.getName(), is, options);

	                //System.out.println("For ExternalID " + externalID + ", the ObjectID is" + objId.toString());
	            }
	        } catch (Exception e)
	        {
	            System.err.println(e.getMessage());
	        } finally {
	            System.out.println("Done for " + i);
	        }

	        client.close();
	    }

}
