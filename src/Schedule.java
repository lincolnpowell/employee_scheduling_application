/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This class will represent an employees schedule for a week.
*/

import java.io.Serializable;
import static java.util.Calendar.*;
import java.util.Date;
import java.util.GregorianCalendar;

public class Schedule implements Serializable{
    //private inner class represents a single day's shift
    public class Shift implements Serializable{
        //start and finish times of a shift
        int startHour;
        int finishHour;
        int date;
        int month;
        String position;
        Shift(int startHour, int finishHour, int date, int month, String poistion){
            this.startHour = startHour;
            this.finishHour = finishHour;
            this.date = date;
            this.month = month;
            this.position = position;
        }
    }
    //1 = sunday... through 7 = saturday - days corespond to the index in the
    //schedule array
    public Shift[] schedule = new Shift[8];
    private String defaultPosition = "";
    private boolean isOpen = true;
    //default constructor which provides a base fill for the array
    public Schedule(){
    }
    //add a shift with default position
    public void addShift(int startHour, int stopHour, int day, int date, int month){
       schedule[day] = new Shift(startHour, stopHour, date, month, defaultPosition);
    }//add shift with a custom position
    public void addShift(int startHour, int stopHour, int day, int date, int month, 
            String cPos){
        schedule[day] = new Shift(startHour, stopHour, date, month, cPos);
    }
    public void changeShift(int startHour, int stopHour, int day){
        schedule[day].startHour = startHour;
        schedule[day].finishHour = stopHour;
    }
    //sets initial array values  - these can be altered later
    public void initialize(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setGregorianChange(new Date());
            do{
                calendar.roll(DAY_OF_YEAR, true);
            }while(calendar.get(DAY_OF_WEEK) != 1);
        for(int i = 1; i < schedule.length; i++){
            addShift(0,0,i,calendar.get(DAY_OF_MONTH),calendar.get(MONTH));
            calendar.roll(DAY_OF_YEAR, true);
        }
    }
    //translate day to String
    public String getDay(int day){
        switch (day){
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default: return "";
        }
    }
    //translate month to String
    public String getMonth(int month){
        switch (month){
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default: return "";
        }
    }
    
    //sets the default position for someone
    public void setPosition(String pos){
        defaultPosition = pos;
    }
    //toString override
    @Override
    public String toString(){
        String out = "";
        for(int i = 1; i < schedule.length; i++){
            if(schedule[i].startHour == 0 && schedule[i].finishHour == 0)
                out = out + getMonth(schedule[i].month) + "\n" + 
                        schedule[i].date + "\n" + getDay(i) + "\nOFF\n";
            else
                out = out + getMonth(schedule[i].month) + "\n" + 
                        schedule[i].date + "\n" + getDay(i) + "\n" +
                        schedule[i].startHour + " - " + schedule[i].finishHour +
                        "\n";    
        }
        return out;
    }
    
    //possible methods for access control for changing schedules.... may not be used.
    
    public void closeSchedule(){
        isOpen = false;
    }
    public void openSchedule(User u){
        if(u.getAdmin().equals("true"))isOpen = true;
    }
}