package com.mediaocean.model;

import org.apache.log4j.Logger;

/**
 * Created by abhishek.sing on 03/09/17.
 */
public class Team {
    private final static Logger logger= Logger.getLogger(Team.class);
    private int teamNumber;
    private int dateLastPlayed=-1;

    public Team(int teamNumber){
        this.teamNumber=teamNumber;
    }

    public int getTeamNumber(){
        return teamNumber;
    }

    public int getDateLastPlayed(){
        return dateLastPlayed;
    }


    public void setDateLastPlayed(int date){
        this.dateLastPlayed=date;
    }
}
