package gr.conference.papersys;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/paper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WebResource {

    private PaperDBHandler paperDBHandler = new PaperDBHandler();

    public void PaperResource(PaperDBHandler paperDBHandler) {
        this.paperDBHandler = paperDBHandler;
    }

    @POST
    public Response createPaper(Paper paper) {
        try {
            paperDBHandler.savePaper(paper);
            return Response.status(Response.Status.CREATED)
                    .entity(new ResponseMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ResponseMessage())
                    .build();
        }
    }

    @GET
    @Path("/{paperId}")
    public Response getPaperById(@PathParam("paperId") Long paperId) {
        try {
            Paper paper = paperDBHandler.getPaperById(paperId);
            if (paper != null) {
                return Response.ok(paper).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ResponseMessage())
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ResponseMessage())
                    .build();
        }
    }


}
