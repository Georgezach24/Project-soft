package gr.conference.confsys;

import com.google.gson.Gson;

import gr.conference.usersys.ResponseMessage;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("conference")
public class WebResource {
	
	@GET
	@Path("/create")
	@Produces("text/plain")
	public String requestCreate()
	{
		return "Create new conference!";
	}
	
	
	@POST
	@Path("/create/{p1}/{p2}/{p3}")
	@Produces("application/json")
	public String passwordUpdate(@PathParam("p1") String name , 
		   @PathParam("p2") String username , @PathParam("p3") String desc)
	{
		ResponseMessage msg = new ResponseMessage();
		if(!ConferenceDBHandler.createConference(name,username,desc))
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Conference created successfully");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Error creating conference");
		}
		
		return new Gson().toJson(msg);
	}
}
