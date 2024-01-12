package gr.conference.usersys;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
   
   public static void passwordUpdateRequest() {
       HttpClient client = HttpClients.createDefault();
       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/passwordupdate");
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
   
   public static void updateRequest()
   {
	   HttpClient client = HttpClients.createDefault();
       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/update");
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
   
   public static void AdminloginRequest()
   {
	   HttpClient client = HttpClients.createDefault();
       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/adminlogin");
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
   
   public static void deleteRequest()
   {
	   HttpClient client = HttpClients.createDefault();
       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/delete");
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
   
   public static void updateStatusRequest()
   {
	   HttpClient client = HttpClients.createDefault();
       HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/updatestatus");
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
   
   public static String loginPost(String username , String password)
   {
	   UserDBHandler.loginUser(username, password);
		  
	   HttpClient client = HttpClients.createDefault();
	   StringBuilder result = new StringBuilder();
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/login/" + username + "/" + password);
       request.addHeader("accept", "application/json");

       try {
    	   
           HttpResponse response = client.execute(request);
           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

           String line;
           
           while ((line = reader.readLine()) != null) {
               result.append(line);
           }
           
           
           System.out.println("POST Response:\n" + result.toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       
       return result.toString();
   }
   
   public static String updatePost(String oldusername,String newusername , String nameString , 
		   String surname , String email , String phone)
   {
	   UserDBHandler.updateUserInfo(oldusername, newusername ,nameString, surname, email, phone);
		  
	   HttpClient client = HttpClients.createDefault();
	   StringBuilder result = new StringBuilder();
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/update/" + newusername + "/" + nameString + "/" + surname + "/" + email + "/" + phone);
       request.addHeader("accept", "application/json");

       try {
    	   
           HttpResponse response = client.execute(request);
           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

           String line;
           
           while ((line = reader.readLine()) != null) {
               result.append(line);
           }
           
           
           System.out.println("POST Response:\n" + result.toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       
       return result.toString();
   }
   
   public static String deletePost(String username)
   {
	   UserDBHandler.deleteUser(username);
		  
	   HttpClient client = HttpClients.createDefault();
	   StringBuilder result = new StringBuilder();
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/delete/" + username);
       request.addHeader("accept", "application/json");

       try {
    	   
           HttpResponse response = client.execute(request);
           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

           String line;
           
           while ((line = reader.readLine()) != null) {
               result.append(line);
           }
           
           
           System.out.println("POST Response:\n" + result.toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       
       return result.toString();
   }
   
   public static String AdminLoginPost(String username , String password)
   {
	   UserDBHandler.loginAdmin(username, password);
		  
	   HttpClient client = HttpClients.createDefault();
	   StringBuilder result = new StringBuilder();
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/adminlogin/" + username + "/" + password);
       request.addHeader("accept", "application/json");

       try {
    	   
           HttpResponse response = client.execute(request);
           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

           String line;
           
           while ((line = reader.readLine()) != null) {
               result.append(line);
           }
           
           
           System.out.println("POST Response:\n" + result.toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       
       return result.toString();
   }
   
   public static String passwordUpdatePost(String username, String oldPassword , String newPassword)
   {
	   UserDBHandler.updatePassword(username, oldPassword, newPassword);
		  
	   HttpClient client = HttpClients.createDefault();
	   StringBuilder result = new StringBuilder();
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/passwordupdate/" + username + "/" + oldPassword + "/" + newPassword);
       request.addHeader("accept", "application/json");

       try {
    	   
           HttpResponse response = client.execute(request);
           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

           String line;
           
           while ((line = reader.readLine()) != null) {
               result.append(line);
           }
           
           
           System.out.println("POST Response:\n" + result.toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       
       return result.toString();
   }
   
   public static String updateStatusPost(String username , String status)
   {
	   UserDBHandler.updateStatus(username, status);
		  
	   HttpClient client = HttpClients.createDefault();
	   StringBuilder result = new StringBuilder();
       HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/updatestatus/" + username + "/" + status);
       request.addHeader("accept", "application/json");

       try {
    	   
           HttpResponse response = client.execute(request);
           BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

           String line;
           
           while ((line = reader.readLine()) != null) {
               result.append(line);
           }
           
           
           System.out.println("POST Response:\n" + result.toString());
       } catch (IOException e) {
           e.printStackTrace();
       }
       
       return result.toString();
   }

public static boolean isResponse() {
	return response;
}
  
}
