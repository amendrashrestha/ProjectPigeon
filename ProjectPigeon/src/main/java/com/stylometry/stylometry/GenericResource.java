/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stylometry.stylometry;

import com.stylometry.controller.StylometricAnalysisMain;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 * REST Web Service
 *
 * @author amendrashrestha
 */
@Path("generic")
public class GenericResource {

    StylometricAnalysisMain init = new StylometricAnalysisMain();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * project.pigeon.projectpigeon.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        return "This is test";
    }

    @POST
    @Path("/returnStylometricJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public void getStylometricJSON(@FormParam("posts") String text1) throws SQLException, IOException {
        List<String> firstList = new ArrayList<>();
        firstList.add(text1);

        JSONObject featureObject = init.executePostAnalysis(firstList);
    }
    
    @POST
    @Path("/returnStylometricJSONForUser")
    //@Produces(MediaType.APPLICATION_JSON)
    public void getStylometricJSONForUser(@FormParam("user") String userID) throws SQLException, IOException {

        //System.out.println("User: " + userID);
//        JSONObject featureObject = 
        init.executeAnalysis(userID);
        
//        return Response.status(200)
//                .entity("Stylometry : " + userID)
//                .build();
        //return featureObject;
    }

    @POST
    @Path("/display")
    public Response returnStylometricJSON(
            @FormParam("posts") String posts) throws IOException {

        List<String> firstList = new ArrayList<>();
        firstList.add(posts);
        List<Float> stylo = init.returnPostAnalysis(firstList);
        return Response.status(200)
                .entity("Stylometry : " + stylo)
                .build();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
