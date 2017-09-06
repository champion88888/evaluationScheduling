package com.mediaocean.scheduler;

import com.mediaocean.model.Team;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by abhishek.sing on 03/09/17.
 */

@Path("scheduler")
public class EventScheduler {
    private final static Logger logger= Logger.getLogger(EventScheduler.class);

    public int[] rotateArray(int [] array){
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String schedulerGenerator(@FormParam("numTeams") int numberofTeams){
        int day=1, expectedLength;
        int number[];
        int matchCompleted = 0;
        int totalMatches = numberofTeams*(numberofTeams-1)/2;
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
        stringBuffer.append("Day: "+day+"\n");
        int count=0;
        for(int i=0;i<expectedLength-1 && matchCompleted<totalMatches;i++){
            for(int j=0;j<(expectedLength/2) && matchCompleted<totalMatches;j++){
                int firstTeam =number[j]-1;
                int secondTeam= number[expectedLength-1-j]-1;
                if(firstTeam>=0 && secondTeam>=0) {
                    while((teams[firstTeam].getDateLastPlayed()==(day-1))|| (teams[firstTeam].getDateLastPlayed()==day )|| (teams[secondTeam].getDateLastPlayed()==(day-1))||(teams[secondTeam].getDateLastPlayed()==day)){
                        day++;
                        stringBuffer.append("\nDay: "+day+"\n");
                        count=0;
                    }
                    stringBuffer.append("\tMatch - "+ count+":\n");
                    stringBuffer.append("\t\tTeam : " + (firstTeam+1) + " vs Team : " + (secondTeam+1) + ",  HomeGround: "+(firstTeam+1)+"\n");
                    teams[firstTeam].setDateLastPlayed(day);
                    teams[secondTeam].setDateLastPlayed(day);
                    count++;
                    matchCompleted++;
                    if(count==2 && matchCompleted<totalMatches){
                            day++;
                            stringBuffer.append("\nDay: "+day+"\n");
                            count=0;
                        }
                }
            }
            number = rotateArray(number);
            printArray(number);
        }
        matchCompleted = 0;
        for(int i=0;i<expectedLength-1 && matchCompleted<totalMatches;i++){
            for(int j=0;j<(expectedLength/2) && matchCompleted<totalMatches;j++){

                int firstTeam =number[j]-1;
                int secondTeam= number[expectedLength-1-j]-1;
                if(firstTeam>=0 && secondTeam>=0) {
                    while((teams[firstTeam].getDateLastPlayed()==(day-1))|| (teams[firstTeam].getDateLastPlayed()==day )|| (teams[secondTeam].getDateLastPlayed()==(day-1))||(teams[secondTeam].getDateLastPlayed()==day)){
                        day++;
                        stringBuffer.append("\nDay: "+day+"\n");
                        count=0;
                    }
                    stringBuffer.append("\tMatch - "+ count+":\n");
                    stringBuffer.append("\t\tTeam : " + (firstTeam+1) + " vs Team : " + (secondTeam+1) + ",  HomeGround: "+(secondTeam+1)+"\n");
                    teams[firstTeam].setDateLastPlayed(day);
                    teams[secondTeam].setDateLastPlayed(day);
                    count++;
                    matchCompleted++;
                    if(count==2 && matchCompleted<totalMatches){
                        day++;
                        stringBuffer.append("\nDay: "+day+"\n");
                        count=0;
                    }
                }
            }
            number = rotateArray(number);
            printArray(number);
        }
        return stringBuffer.toString();
    }

}
