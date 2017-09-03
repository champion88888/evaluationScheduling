package com.mediaocean.scheduler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by abhishek.sing on 03/09/17.
 */

@Path("scheduler")
public class EventScheduler {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String schedulerGenerator(@FormParam("numTeams") int numberofTeams){
        int day=1;
        StringBuffer stringBuffer = new StringBuffer();


        return "Number of Teams : "+ numberofTeams;
    }

}
