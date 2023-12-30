package gr.conference.sys;

/**
*
* @author Giorgos Zachos
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;



public class RestClient {

	public static  boolean response = true;

   public static void registerRequest() {
       HttpClient client = HttpClients.createDefault();
       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/register");
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

   public static void registerPost(String username , String password ,String password2, String email, String phone) {
	   UserDBHandler.registerUser(username, password, password2 , email, phone);
	  
	   HttpClient client = HttpClients.createDefault();
       
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/register/" + username + "/" + password + "/" + email + "/" + phone);
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
   }
   
   public static void loginRequest()
   {
	   HttpClient client = HttpClients.createDefault();
       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/login");
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
   
   public static void loginPost(String username , String password)
   {
	   UserDBHandler.loginUser(username, password);
		  
	   HttpClient client = HttpClients.createDefault();
       
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/login/" + username + "/" + password);
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
   }

public static boolean isResponse() {
	return response;
}
  
}
