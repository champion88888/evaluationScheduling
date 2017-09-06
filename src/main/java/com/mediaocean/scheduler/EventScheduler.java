package com.mediaocean.scheduler;

import com.mediaocean.model.Team;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by abhishek.sing on 03/09/17.
 */

@Path("scheduler")
public class EventScheduler {
    private final static Logger logger= Logger.getLogger(EventScheduler.class);
    private int day, countMatchedPerDay;
    public  int[] rotateArray(int [] array){
        int []returningArray = new int[array.length];
        returningArray[0]=array[0];
        returningArray[1]=array[array.length-1];
        for(int i=2;i<returningArray.length;i++){
            returningArray[i]=array[i-1];
        }
        return returningArray;
    }

    public void printArray(int [] array){
        for(int i=0;i<array.length;i++){
            logger.info(array[i]+ " ");
        }
    }

    public StringBuffer helperFunction(StringBuffer stringBuffer, int expectedLength, int totalMatches,int [] number, Team[] teams, boolean round){
        int matchCompleted=0;
        if(countMatchedPerDay==0) {
            stringBuffer.append("Day: " + day + "\n");
        }
        for(int i=0;i<expectedLength-1 && matchCompleted<totalMatches;i++){
            for(int j=0;j<(expectedLength/2) && matchCompleted<totalMatches;j++){
                int firstTeam =number[j]-1;
                int secondTeam= number[expectedLength-1-j]-1;
                if(firstTeam>=0 && secondTeam>=0) {
                    logger.info(teams[firstTeam].getDateLastPlayed() + " "+ teams[secondTeam].getDateLastPlayed());
                    while((teams[firstTeam].getDateLastPlayed()==(day-1))|| (teams[firstTeam].getDateLastPlayed()==day )|| (teams[secondTeam].getDateLastPlayed()==(day-1))||(teams[secondTeam].getDateLastPlayed()==day)){
                        day++;
                        stringBuffer.append("\nDay: "+day+"\n");
                        countMatchedPerDay=0;
                    }
                    stringBuffer.append("\tMatch - "+ (countMatchedPerDay+1)+":\n");
                    logger.info("round is "+ round);
                    if(round) {
                        stringBuffer.append("\t\tTeam : " + (firstTeam + 1) + " vs Team : " + (secondTeam + 1) + ",  HomeGround: " + (firstTeam + 1) + "\n");
                    }else{
                        stringBuffer.append("\t\tTeam : " + (firstTeam + 1) + " vs Team : " + (secondTeam + 1) + ",  HomeGround: " + (secondTeam + 1) + "\n");
                    }
                    teams[firstTeam].setDateLastPlayed(day);
                    teams[secondTeam].setDateLastPlayed(day);
                    countMatchedPerDay++;
                    matchCompleted++;
                    if(countMatchedPerDay==2 && matchCompleted<totalMatches){
                        day++;
                        stringBuffer.append("\nDay: "+day+"\n");
                        countMatchedPerDay=0;
                    }
                }
            }
            number = rotateArray(number);
            printArray(number);
        }
        return  stringBuffer;
    }

    public void helperFunction(int expectedLength, int totalMatches,int [] number, Team[] teams, boolean round){
        int matchCompleted=0;
        for(int i=0;i<expectedLength-1 && matchCompleted<totalMatches;i++){
            for(int j=0;j<(expectedLength/2) && matchCompleted<totalMatches;j++){
                int firstTeam =number[j]-1;
                int secondTeam= number[expectedLength-1-j]-1;
                if(firstTeam>=0 && secondTeam>=0) {
                    System.out.println(teams[firstTeam].getDateLastPlayed() + " "+ teams[secondTeam].getDateLastPlayed());
                    while((teams[firstTeam].getDateLastPlayed()==(day-1))|| (teams[firstTeam].getDateLastPlayed()==day )|| (teams[secondTeam].getDateLastPlayed()==(day-1))||(teams[secondTeam].getDateLastPlayed()==day)){
                        day++;
                        countMatchedPerDay=0;
                    }

                    teams[firstTeam].setDateLastPlayed(day);
                    teams[secondTeam].setDateLastPlayed(day);
                    countMatchedPerDay++;
                    matchCompleted++;
                    if(countMatchedPerDay==2 && matchCompleted<totalMatches){
                        day++;
                        countMatchedPerDay=0;
                    }
                }
            }
            number = rotateArray(number);
            printArray(number);
        }
    }
    @Path("/html")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String schedulerGenerator(@FormParam("numTeams") int numberofTeams){
        if(numberofTeams<=0){
            return "Number of Teams in Invalid";
        }
        else if(numberofTeams == 1){
            return "Don't you think we already have a winner ? The only team participating is winner. Why do you need tournament for this :(.";
        }
        int expectedLength;
        int number[];

        int totalMatches = numberofTeams*(numberofTeams-1)/2;
        day = 1;
        countMatchedPerDay=0;
        Team teams[]=new Team[numberofTeams];
        StringBuffer stringBuffer = new StringBuffer();
        if(numberofTeams%2==0){
            number = new int[numberofTeams];
            expectedLength = numberofTeams;
            for(int i=0;i<numberofTeams;i++){
                number[i]=i+1;
                teams[i]=new Team(i);
            }
        }else{
            number = new int[numberofTeams+1];
            expectedLength = numberofTeams+1;
            for(int i=0;i<numberofTeams;i++){
                number[i]=i+1;
                teams[i]=new Team(i);
            }
            number[expectedLength-1]=-1;
        }

        stringBuffer = helperFunction(stringBuffer,expectedLength, totalMatches, number, teams,false );
        stringBuffer = helperFunction(stringBuffer,expectedLength, totalMatches, number, teams,true );
        return stringBuffer.toString();
    }

    @Path("/getDays")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getMaxDays(@FormParam("numTeams") int numberofTeams){
        int expectedLength;
        int number[];
        JSONObject json = new JSONObject();
        if(numberofTeams<=0){
            json.put("days", -1);
            return  json.toString();
        }else if(numberofTeams==1){
            json.put("days", 0);
            return json.toString();
        }

        int totalMatches = numberofTeams*(numberofTeams-1)/2;
        day = 1;
        countMatchedPerDay=0;
        Team teams[]=new Team[numberofTeams];
        if(numberofTeams%2==0){
            number = new int[numberofTeams];
            expectedLength = numberofTeams;
            for(int i=0;i<numberofTeams;i++){
                number[i]=i+1;
                teams[i]=new Team(i);
            }
        }else{
            number = new int[numberofTeams+1];
            expectedLength = numberofTeams+1;
            for(int i=0;i<numberofTeams;i++){
                number[i]=i+1;
                teams[i]=new Team(i);
            }
            number[expectedLength-1]=-1;
        }

        helperFunction(expectedLength, totalMatches, number, teams,false );
        helperFunction(expectedLength, totalMatches, number, teams,true );

        json.put("days", day);
        return json.toString();
    }

}
