package gr.conference.papersys;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.google.gson.Gson;

@Path("/paper")
public class WebResource {

	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response paperCreate(PaperRequest request) {
	    ResponseMessage msg = new ResponseMessage();
	    boolean success = PaperDBHandler.createPaper(request.getConferenceName(), request.getUsername(), request.getTitle());

	    if (success) {
	        msg.setResponseCode("200");
	        msg.setResponseMessage("Paper created successfully");
	        return Response.status(Response.Status.CREATED).entity(new Gson().toJson(msg)).build();
	    } else {
	        msg.setResponseCode("400");
	        msg.setResponseMessage("Error creating Paper");
	        return Response.status(Response.Status.BAD_REQUEST).entity(new Gson().toJson(msg)).build();
	    }
	}


}
