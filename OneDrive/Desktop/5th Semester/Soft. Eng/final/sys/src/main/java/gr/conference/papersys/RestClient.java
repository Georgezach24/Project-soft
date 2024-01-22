package gr.conference.papersys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import gr.conference.confsys.ConferenceDBHandler;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestClient {
	
	public static void createPaperRequest()
	{
		HttpClient client = HttpClients.createDefault();
	       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/paper/create");
	       request.addHeader("accept", "text/plain");

	       try {
	           HttpResponse response = client.execute(request);
	           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	           String line;
	           StringBuilder result = new StringBuilder();
	           while ((line = reader.readLine()) != null) {
	               result.append(line);
	           }

	           System.out.println("GET Response:\n" + result.toString());
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	}
	
	public static String paperCreatePost(String name , String user , String conf) {
		   PaperDBHandler.createPaper(conf , user , name);
		  
		   HttpClient client = HttpClients.createDefault();
	       
	       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/paper/create/" + conf + "/" + user + "/" + name);
	       request.addHeader("accept", "application/json");

	       try {
	    	   
	           HttpResponse response = client.execute(request);
	           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	           String line;
	           StringBuilder result = new StringBuilder();
	           while ((line = reader.readLine()) != null) {
	               result.append(line);
	           }
	           
	           
	           System.out.println("POST Response:\n" + result.toString());
	           return result.toString();
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	       
	       return null;
	 }
}
