/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This class will contain the availability for all days for a user
The days are stored in an array NOTE - the 0 space of the array is left
NULL as the array goes from 0 (null) 1 (sunday) 2(tuesday) all the way to
7 (saturday).
*/

import java.io.Serializable;
import java.util.Random;

public class WeeklyAvailability implements Serializable{
    //array to hold all days
    private DailyAvailability[] days = new DailyAvailability[8];
    //constructor initializes 1 - 7 arrray positions
    //preference for shift type (mids, days, early, or late)
    private String shiftPreference = null;
    public WeeklyAvailability(){
        for(int i = 1; i < days.length; i++){
            days[i] = new DailyAvailability(i);
        }
    }
    //getters for hours, and days
    public int getStartHour(int day){
        return days[day].getStart();
    }
    
    public int getFinishHour(int day){
        return days[day].getFinish();
    }
    
    public String getDay(int day){
        switch (day){
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default: return "";
        }
    }
    //shift preference
    String getPreference(){
    	if(shiftPreference == null){
    		Random rand = new Random();
        	int pref = rand.nextInt(4);
        	if(pref == 0){
        		shiftPreference = "early";
        	}
        	else if(pref == 1){
        		shiftPreference = "days";
        		
        	}
        	else if(pref == 2){
        		shiftPreference = "mids";
        		
        	}
        	else if(pref == 3){
        		shiftPreference = "late";        		
        	}
    	}    	
        return shiftPreference;
    }
    //setters - for days pass in 1 for sunday through 7 for saturday
    public void setStartHour(int day, int time){
        days[day].setStart(time);
    }
    public void setFinishHour(int day, int time){
        days[day].setFinish(time);
    }
    //preference shift setter - mids, days, early, or late
    public void setPreference(String p){
        shiftPreference = p;
    }
    public boolean checkAvailability(int day){
        return days[day].isAvailable();
    }
    public boolean checkEarly(int day){
        return (days[day].getStart() <= 0 && days[day].getFinish() >= 10);
    }
    public boolean checkDay(int day){
        return (days[day].getStart() <= 5 && days[day].getFinish() >= 15);
    }
    public boolean checkMid(int day){
        return (days[day].getStart() <= 11 && days[day].getFinish() >= 21);
    }
    public boolean checkLate(int day){
        return (days[day].getStart() <= 14 && days[day].getFinish() >= 24);
    }
    //toString override
    @Override
    public String toString(){
        String out = "";
        //output schedule for all days
        for (int i = 1; i < days.length; i++){
            out  = out + getDay(i) + "\n" + days[i].getStart() + " - " + 
                    days[i].getFinish() + "\n" + "\n";  
        }
        return out;
    }
}
