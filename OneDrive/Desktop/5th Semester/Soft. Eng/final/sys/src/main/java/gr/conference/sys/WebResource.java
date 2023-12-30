package gr.conference.sys;

/**
*
* @author Giorgos Zachos
*/
import com.google.gson.Gson;

import jakarta.ws.rs.*;



@Path("usermng")
public class WebResource 
{

	@GET
	@Path("/login")
	@Produces("text/plain")
	public String requestLogin()
	{	
		return "Login is required!";
	}
	
	@GET
	@Path("/register")
	@Produces("text/plain")
	public String requestRegister()
	{	
		return "Register new User";
	}
	
	@POST
	@Path("/login/{p1}/{p2}")
	@Produces("application/json")
	public String login(@PathParam("p1") String username ,@PathParam("p2") String password )
	{
		ResponseMessage msg = new ResponseMessage();
		if(UserDBHandler.loginUser(username, password))
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Succes!");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Wrong username or password!");
		}
		
		return new Gson().toJson(msg);
	}
	
	@POST
	@Path("/register/{p1}/{p2}/{p3}/{p4}")
	@Produces("application/json")
	public String register(@PathParam("p1") String username ,
			@PathParam("p2") String password , @PathParam("p3") String email ,@PathParam("p4") String phone )
	{
		ResponseMessage msg = new ResponseMessage();
		System.out.print("debug");
		if(UserDBHandler.loginUser(username, password))
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Succes registering user");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Error registering user");
		}
		
		
		return new Gson().toJson(msg);
	}


	
}
