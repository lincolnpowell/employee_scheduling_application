/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This class constructs a system alert that will go to a manager.
*/

import java.util.Date;

public class Alert {
    //alert type 1, 2, or 3 moree can be added if need be
    private int type;
    //ID for the manager of the employee pertaining to the alert
    private int targetManager;
    //The employee that has generated the alert
    private int targetEmployee;
    private final String TYPE1 = " has exceeded 40 working hours for the week of ";
    private final String TYPE2 = "More than 1 surgeon is working during the following day: ";
    private final String TYPE3 = " has exceeded the 10 hour maximum work time for the day of: ";
    private String message = "Unknown error";
    //construct the alert based on type input
    public Alert(int type, User u){
        this.type = type;
        createMessage(u);
    }
    //construct the alert for surgeons with the date
    public Alert(Date d){
        type = 2;
        createMessage(d);
    }
    //constructs message for cases 1 and 3
    private void createMessage(User u){
        if(type == 1) message = u.getFirstName()+ " " + u.getLastName() + TYPE1;
        if(type == 3) message = u.getFirstName()+ " " + u.getLastName() + TYPE3;
    }
    //constructs message for case 2
    private void createMessage(Date d){
        message = TYPE2 + d;
    }
    //toString override - easy output
    public String toString(){return message;}
}