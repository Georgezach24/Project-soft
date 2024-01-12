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
	@Path("/passwordupdate")
	@Produces("text/plain")
	public String requestPasswordUpdate()
	{	
		return "Provide your changes!";
	}
	
	@GET
	@Path("/adminlogin")
	@Produces("text/plain")
	public String requestAdminLogin()
	{	
		return "Admin Login is required!";
	}
	
	@GET
	@Path("/delete")
	@Produces("text/plain")
	public String requestDelete()
	{	
		return "Your account is goin to be deleted!";
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
	
	@GET
	@Path("/updatestatus")
	@Produces("text/plain")
	public String requestStatusUpdate()
	{	
		return "Update the status!";
	}
	
	@POST
	@Path("/update/{p1}/{p2}/{p3}/{p4}/{p5}")
	@Produces("application/json")
	public String userInformationUpdate(@PathParam("p1") String username ,
			@PathParam("p2") String name , @PathParam("p3") String surname ,
			@PathParam("p4") String email,@PathParam("p5") String phone)
	{
		ResponseMessage msg = new ResponseMessage();
		if(!UserDBHandler.updateUserInfo(username, username, name, surname, email, phone))
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
	@Path("/passwordupdate/{p1}/{p2}/{p3}")
	@Produces("application/json")
	public String passwordUpdate(@PathParam("p1") String username , 
		   @PathParam("p2") String oldPassword , @PathParam("p3") String newPassword)
	{
		ResponseMessage msg = new ResponseMessage();
		if(!UserDBHandler.updatePassword(username, oldPassword, newPassword))
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
	
	@POST
	@Path("/updatestatus/{p1}/{p2}")
	@Produces("application/json")
	public String statusUpdate(@PathParam("p1") String username, @PathParam("p2") String status)
	{
		ResponseMessage msg = new ResponseMessage();
		if(!UserDBHandler.updateStatus(username, status))
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
	
	@DELETE
	@Path("/delete/{p1}")
	@Produces("application/json")
	public String deleteUser(@PathParam("p1") String username)
	{
		ResponseMessage msg = new ResponseMessage();
		if(UserDBHandler.deleteUser(username))
		{
			msg.setResponseCode("200");
			msg.setResponseMessage("Account deleted succesfully");
		}
		else
		{
			msg.setResponseCode("-1");
			msg.setResponseMessage("Error deleting account");
		}

		return new Gson().toJson(msg);
	}


	
}
