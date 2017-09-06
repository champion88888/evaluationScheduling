package com.mediaocean.model;

import org.apache.log4j.Logger;

/**
 * Created by abhishek.sing on 06/09/17.
 */
public class Match {
    private final static Logger logger= Logger.getLogger(Match.class);

    private int firstTeam, secondTeam, homeground, day, winner;

    public Match(int firstTeam, int secondTeam, int homeground, int day){
        this.firstTeam=firstTeam;
        this.secondTeam=secondTeam;
        this.homeground=homeground;
        this.day=day;
    }

    public int getFirstTeam()
    {
        return firstTeam;
    }
    public int getSecondTeam(){
        return secondTeam;
    }
    public int getHomeground(){
        return homeground;
    }
    public int getDay(){
        return day;
    }
    public void setWinner(int winner){
        this.winner=winner;
    }

}
