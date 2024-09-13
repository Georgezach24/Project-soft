package gr.conference.papersys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

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
	
	public static String paperCreatePost(String title, String username, String conferenceName) {
	    HttpClient client = HttpClients.createDefault();
	    HttpPost request = new HttpPost("http://localhost:8080/system/webapi/paper/create");

	    request.addHeader("accept", "application/json");
	    request.addHeader("Content-Type", "application/json");

	    PaperRequest paperRequest = new PaperRequest();
	    paperRequest.setTitle(title);
	    paperRequest.setUsername(username);
	    paperRequest.setConferenceName(conferenceName);

	    String jsonRequest = new Gson().toJson(paperRequest);
	    
	    try {
	        request.setEntity(new StringEntity(jsonRequest));

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
