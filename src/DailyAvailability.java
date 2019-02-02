/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This helper class will represent a user's availability for a single day.
*/

import java.io.Serializable;

public class DailyAvailability implements Serializable{
    private final int day;
    private int startHour = 0;
    private int finishHour = 24;
    public DailyAvailability(int day){
        this.day = day;
    }
    public DailyAvailability(int day, int startHour, int finishHour){
        this.day = day;
        this.startHour = startHour;
        this.finishHour = finishHour;
    }
    public int getStart(){return startHour;}
    public int getFinish(){return finishHour;}
    public void setStart(int s){
        startHour = s;
    }
    public void setFinish(int f){
        finishHour = f;
    }
    public boolean isAvailable(){
        return (finishHour - startHour >= 10);
    }

}
