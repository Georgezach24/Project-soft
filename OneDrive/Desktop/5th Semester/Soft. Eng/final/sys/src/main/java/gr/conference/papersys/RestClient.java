package gr.conference.papersys;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestClient {

    private static final String BASE_URL = "http://localhost:8080/system/webapi/paper/create"; 

    private final Client client;

    public RestClient() {
        this.client = ClientBuilder.newClient();
    }

    public ResponseMessage createPaper(Paper paper) {
        try {
            Response response = client.target(BASE_URL + "/paper")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(paper, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return response.readEntity(ResponseMessage.class);
            } else {
                
                return response.readEntity(ResponseMessage.class);
            }
        } finally {
            client.close();
        }
    }

    public Paper getPaperById(Long paperId) {
        try {
            Response response = client.target(BASE_URL + "/paper/" + paperId)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(Paper.class);
            } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                
                return null;
            } else {
                
                return null;
            }
        } finally {
            client.close();
        }
    }

    

    public static void main(String[] args) {
        RestClient restClient = new RestClient();

        
        Paper newPaper = new Paper();
        newPaper.setTitle("Sample Paper");
        

        ResponseMessage createResponse = restClient.createPaper(newPaper);
        System.out.println("Create Paper Response: " + createResponse.getResponseMessage());

        
        Long paperIdToRetrieve = 1L; 
        Paper retrievedPaper = restClient.getPaperById(paperIdToRetrieve);
        if (retrievedPaper != null) {
            System.out.println("Retrieved Paper: " + retrievedPaper);
        } else {
            System.out.println("Paper not found");
        }

       
    }
}
