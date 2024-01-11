package gr.conference.usersys;

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
	@Path("/adminlogin")
	@Produces("text/plain")
	public String requestAdminLogin()
	{	
		return "Admin Login is required!";
	}
	
	@GET
	@Path("/register")
	@Produces("text/plain")
	public String requestRegister()
	{	
		return "Register new User";
	}
	
	@GET
	@Path("/update")
	@Produces("text/plain")
	public String requestUserInfoUpdate()
	{	
		return "Fill the information correctlly in order to update";
	}
	
	@POST
	@Path("/update/{p1}/{p2}/{p3}/{p4}/{p5}")
	@Produces("application/json")
	public String userInformationUpdate(@PathParam("p1") String username ,
			@PathParam("p2") String name , @PathParam("p3") String surname ,
			@PathParam("p4") String email,@PathParam("p5") String phone)
	{
		ResponseMessage msg = new ResponseMessage();
		if(UserDBHandler.updateUserInfo(username, username, name, surname, email, phone))
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Your info has been updated succesfully");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Error changing info");
		}
		
		return new Gson().toJson(msg);
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
	@Path("/adminlogin/{p1}/{p2}")
	@Produces("application/json")
	public String adminLogin(@PathParam("p1") String username ,@PathParam("p2") String password )
	{
		ResponseMessage msg = new ResponseMessage();
		if(UserDBHandler.loginAdmin(username, password))
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
