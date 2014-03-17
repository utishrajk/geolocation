import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.flaptor.indextank.apiclient.IndexAlreadyExistsException;
import com.flaptor.indextank.apiclient.IndexDoesNotExistException;
import com.flaptor.indextank.apiclient.IndexTankClient;
import com.flaptor.indextank.apiclient.IndexTankClient.Index;
import com.flaptor.indextank.apiclient.IndexTankClient.Query;
import com.flaptor.indextank.apiclient.IndexTankClient.SearchResults;
import com.flaptor.indextank.apiclient.InvalidSyntaxException;
import com.flaptor.indextank.apiclient.MaximumIndexesExceededException;

public class CreateIndex {
	private final static String API_URL = "http://:0dliy2Z8qPwDAi@8ctgj.api.searchify.com";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IndexTankClient client = new IndexTankClient(API_URL);

		try {
			Index index = client.getIndex("test");

			if (index == null) {
				index = client.createIndex("test");
			}

			while (!index.hasStarted()) {
				Thread.sleep(300);
			}
			
			//Index the document with variables
			Float rating = 0.5f;
			Float reputation = 1.5f;
			Float visits = 10.0f;
			
			
			// now that we have created an index, and started (how do you make
			// sure it starts within added delay??), you can index
			// documents
			
			
			String documentId0 = "MyDoc0"; //3.5km
			Map<String, String> fields0 = new HashMap<String, String>();
			fields0.put("text", "chick fil a chickfila chick fila");
			fields0.put("title", "10300 Little Patuxent Pkwy, Columbia, MD 21044");
			fields0.put("author", "utish rajkarnikar");
			
			//create variables for fields
			Map<Integer, Float> variables0 = new HashMap<Integer, Float>();
			variables0.put(0, 39.215714f); //latitude
			variables0.put(1, -76.861556f); //longitude
			
			
			String documentId1 = "MyDoc1"; //1.7km
			Map<String, String> fields1 = new HashMap<String, String>();
			
			fields1.put("text", "chick fil a chickfila chick fila");
			fields1.put("title", "6375 Dobbin Rd, Columbia, MD 21045");
			fields1.put("author", "utish rajkarnikar");
			
			//create variables for fields
			Map<Integer, Float> variables1 = new HashMap<Integer, Float>();
			variables1.put(0, 39.197684f); //latitude
			variables1.put(1, -76.818470f); //longitude
			
			
			
			String documentId2 = "MyDoc2"; //2.5km
			Map<String, String> fields2 = new HashMap<String, String>();
			fields2.put("text", "chick fil a chickfila chick fila food fast food burger sandwich blah blah");
			fields2.put("title", "4905 Executive Park Dr, Columbia, MD 21045 ");
			fields2.put("author", "suniti rajkarnikar");
			
			//create variables2 for fields2
			Map<Integer, Float> variables2 = new HashMap<Integer, Float>();
			variables2.put(0, 39.235442f);
			variables2.put(1, -76.814521f);

			//link all variables with their documents
			index.addDocument(documentId0, fields0, variables0);
			index.addDocument(documentId1, fields1, variables1);
			index.addDocument(documentId2, fields2, variables2);
			
			index.addFunction(5, "-miles(d[0], d[1], q[0], q[1])");
			
			//user's geocoordinates
			Float mylongitude = -76.820655f;
			Float mylatitude = 39.213323f;

			SearchResults results = index.search(Query.forString("chick").withScoringFunction(5).withQueryVariable(0, mylatitude).withQueryVariable(1, mylongitude).withSnippetFields("text").withFetchFields("title", "timestamp"));

			System.out.println("Matches :" + results.matches);

			for (Map<String, Object> document : results.results) {
				System.out.println("doc id : " + document.get("docid") +
						"; title : " + document.get("title") +
						"; timestamp : " + document.get("timestamp") +
						"; snippet : " + document.get("snippet_text")); 
			}
			
			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexAlreadyExistsException e) {
			e.printStackTrace();
		} catch (MaximumIndexesExceededException e) {
			e.printStackTrace();
		} catch (IndexDoesNotExistException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
	}

}
