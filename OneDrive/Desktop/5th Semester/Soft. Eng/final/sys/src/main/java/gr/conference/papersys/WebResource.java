package gr.conference.papersys;

import gr.conference.usersys.User;
import gr.conference.usersys.UserDBHandler;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/papers")
public class WebResource {

    private PaperDBHandler dbHandler = new PaperDBHandler();
    private UserDBHandler userDBHandler = new UserDBHandler();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPaper(Paper paper) {
        ResponseMessage response = dbHandler.createPaper(paper);
        if (response.isSuccess()) {
            return Response.status(Response.Status.CREATED).entity(response).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePaper(Paper paper) {
        ResponseMessage response = dbHandler.updatePaper(paper);
        if (response.isSuccess()) {
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }

    @POST
    @Path("/submit/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitPaper(@PathParam("id") Long paperId) {
        ResponseMessage response = dbHandler.submitPaper(paperId);
        if (response.isSuccess()) {
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }
}
