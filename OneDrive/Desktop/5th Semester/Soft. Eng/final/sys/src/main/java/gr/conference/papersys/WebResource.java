package gr.conference.papersys;


import com.google.gson.Gson;

import gr.conference.confsys.ConferenceDBHandler;
import gr.conference.usersys.ResponseMessage;
import jakarta.ws.rs.*;


@Path("/paper")
public class WebResource {

	@GET
	@Path("/create")
	@Produces("text/plain")
	public String requestCreate()
	{
		return "Create new Paper!";
	}
	
	@POST
	@Path("/create/{p1}/{p2}/{p3}")
	@Produces("application/json")
	public String paperCreate(@PathParam("p1") String conf , 
		   @PathParam("p2") String username , @PathParam("p3") String title)
	{
		ResponseMessage msg = new ResponseMessage();
		if(!PaperDBHandler.createPaper(conf, username, title))
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Paper created successfully");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Error creating Paper");
		}
		
		return new Gson().toJson(msg);
	}

}
