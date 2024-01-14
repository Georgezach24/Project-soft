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
	
	@GET
	@Path("/search")
	@Produces("text/plain")
	public String requestSearch()
	{
		return "Search a conference!";
	}
	
	@GET
	@Path("/update")
	@Produces("text/plain")
	public String requestUpdate()
	{
		return "Update a conference!";
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
	
	@POST
	@Path("/search/{p1}")
	@Produces("application/json")
	public String search(@PathParam("p1") String name , String desc)
	{
		ResponseMessage msg = new ResponseMessage();
		if(!ConferenceDBHandler.searchConferences(name, desc).isEmpty())
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Conference search was successfull");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Error in searching conference");
		}
		
		return new Gson().toJson(msg);
	}
	
	@POST
	@Path("/update/{p1}/{p2}/{p3}")
	@Produces("application/json")
	public String update(@PathParam("p1") String oldName , @PathParam("p2")String newnName , @PathParam("p3")String desc)
	{
		ResponseMessage msg = new ResponseMessage();
		if(!ConferenceDBHandler.updateConference(newnName, newnName, desc))
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Conference update was successfull");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Error in updating conference");
		}
		
		return new Gson().toJson(msg);
	}
}
