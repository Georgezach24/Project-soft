package gr.conference.confsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import gr.conference.usersys.UserDBHandler;

public class RestClient {
	public static void confCreateRequest()
	{
		HttpClient client = HttpClients.createDefault();
	       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/conference/create");
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
	
	public static void confSearchRequest()
	{
		HttpClient client = HttpClients.createDefault();
	       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/conference/search");
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
	
	public static void confUpdateRequest()
	{
		HttpClient client = HttpClients.createDefault();
	       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/conference/update");
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
	
	public static String confCreatePost(String name , String user , String desc) {
		   ConferenceDBHandler.createConference(name, user, desc);
		  
		   HttpClient client = HttpClients.createDefault();
	       
	       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/conference/create/" + name + "/" + user + "/" + desc);
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
	
	public static String confUpdatePost(String oldName , String newName , String desc) {
		   ConferenceDBHandler.updateConference(oldName, newName, desc);
		  
		   HttpClient client = HttpClients.createDefault();
	       
	       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/conference/update/" + oldName + "/" + newName + "/" + desc);
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
	
	public static List<Conference> confSearchPost(String name , String desc) {
		  List<Conference>  conferences = ConferenceDBHandler.searchConferences(name, desc);
		  
		   HttpClient client = HttpClients.createDefault();
	       
	       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/conference/search/" + name);
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
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	       
	       return conferences;
	   }
}
