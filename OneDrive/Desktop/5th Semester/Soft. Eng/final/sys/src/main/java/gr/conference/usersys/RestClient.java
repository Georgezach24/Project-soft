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

    public static String loginPost(String username, String password) {
        HttpClient client = HttpClients.createDefault();
        StringBuilder result = new StringBuilder();
        HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/login/" + username + "/" + password);
        request.addHeader("accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            result.append(handleResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String registerPost(String username, String password, String passwordConf, String email, String phone) {
        HttpClient client = HttpClients.createDefault();
        StringBuilder result = new StringBuilder();
        HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/register/" + username + "/" + password + "/" + passwordConf + "/" + email + "/" + phone);
        request.addHeader("accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            result.append(handleResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static void registerRequest() {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/register");
        request.addHeader("accept", "text/plain");

        try {
            HttpResponse response = client.execute(request);
            handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String updatePost(String currentUsername, String newUsername, String name, String surname, String email, String phone) {
        HttpClient client = HttpClients.createDefault();
        StringBuilder result = new StringBuilder();
        HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/update/" + currentUsername + "/" + newUsername + "/" + name + "/" + surname + "/" + email + "/" + phone);
        request.addHeader("accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            result.append(handleResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String passwordUpdatePost(String username, String oldPassword, String newPassword) {
        HttpClient client = HttpClients.createDefault();
        StringBuilder result = new StringBuilder();
        HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/passwordupdate/" + username + "/" + oldPassword + "/" + newPassword);
        request.addHeader("accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            result.append(handleResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String updateStatusPost(String username, String status) {
        HttpClient client = HttpClients.createDefault();
        StringBuilder result = new StringBuilder();
        HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/updatestatus/" + username + "/" + status);
        request.addHeader("accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            result.append(handleResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String deletePost(String username) {
        HttpClient client = HttpClients.createDefault();
        StringBuilder result = new StringBuilder();
        HttpPost request = new HttpPost("http://localhost:8080/system/webapi/usermng/delete/" + username);
        request.addHeader("accept", "application/json");

        try {
            HttpResponse response = client.execute(request);
            result.append(handleResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static void updateRequest() {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/update");
        request.addHeader("accept", "text/plain");

        try {
            HttpResponse response = client.execute(request);
            handleResponse(response);
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
            handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRequest() {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/delete");
        request.addHeader("accept", "text/plain");

        try {
            HttpResponse response = client.execute(request);
            handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatusRequest() {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/system/webapi/usermng/updatestatus");
        request.addHeader("accept", "text/plain");

        try {
            HttpResponse response = client.execute(request);
            handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String handleResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        reader.close(); // Close the reader to prevent memory leaks

        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Response: " + result.toString());
            return result.toString();
        } else {
            System.err.println("Request failed with status code: " + statusCode);
            return null;
        }
    }
}
