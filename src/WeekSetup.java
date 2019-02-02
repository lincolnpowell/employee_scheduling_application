/*
Author: Kyle Miller
Last update: July 3, 2016

Class description:

*/

import java.util.ArrayList;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class WeekSetup {
    //first array field is days (1-7, sunday through saturday)
    //second array field is roles(0: orderly, 1: nurse, 2: doctor
    //last array field is shifts 0: early(0-10), 1: days(5-15), 2: mids(11-21)
    //3: late(14-24)
    private int[][][] openings = new int[8][3][4];
    private ArrayList<ShiftAvailability> availability;
    private ArrayList<User> surgeons = new ArrayList<>();
    private ArrayList<User> unassigned = new ArrayList<>();
    private HashMap<Integer, ShiftAvailability> avail = new HashMap<>();
    //constructor
    public WeekSetup(){
        //set up calendar & roll forward to monday of next week
        GregorianCalendar calendar = new GregorianCalendar();        
        calendar.setGregorianChange(new Date());
        do{
            calendar.roll(DAY_OF_YEAR, true);
        }while(calendar.get(DAY_OF_WEEK) != 1);
        for(int i = 1; i < 8; i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 4; k++){
                    avail.put(key(i,j,k), new ShiftAvailability(j,k,i,calendar.get(DAY_OF_MONTH),calendar.get(MONTH)));
                    openings[i][j][k] = 0;
                }
            }
            calendar.roll(DAY_OF_YEAR, true);
        }
        
    }
    
    public void addOpenings(int day, int role, int shift, int number){
        openings[day][role][shift] = number;
    }
    public void initialize(ArrayList<User> database){
        //value of role determined from the user
        int role = 3;
        //count if an employee has a bad availability
        int counter;
        //loop through and parse availability for all
        for(int i = 0; i < database.size(); i++){
            User current = database.get(i);
            current.initializeSchedule();
            current.availableShifts = "0000";
            if(current.role.equals("orderly")) role = 0;
            if(current.role.equals("nurse")) role = 1;
            if(current.role.equals("doctor")) role = 2;
            if(current.role.equals("surgeon")) {
                surgeons.add(current);
                continue;
            }
            counter = 0;
            for(int j = 1; j < 8; j++){
                if(current.availability.checkAvailability(j)){
                    int notAssigned = 0;
                    if(current.availability.checkEarly(j)) avail.get(key(j,role,0)).addAvailable(current);
                    else notAssigned++;
                    if(current.availability.checkDay(j)) avail.get(key(j,role,1)).addAvailable(current);
                    else notAssigned++;
                    if(current.availability.checkMid(j)) avail.get(key(j,role,2)).addAvailable(current);
                    else notAssigned++;
                    if(current.availability.checkLate(j)) avail.get(key(j,role,3)).addAvailable(current);
                    else notAssigned++;
                    if(notAssigned == 4) counter++;
                }
            }
            //check for an invalid availability - if found add to unassigned list
            if(counter > 3) unassigned.add(current);
        }
    }
    //key generator & translator
    private int key(int day, int role, int shift){
        return(day * 100 + role * 10 + shift);
    }
    //assign 3 surgeons to 5 day shifts
    private void assignSurgeons(){
        User current = surgeons.get(0);
        for(int i = 2; i < 7; i++){
            current.schedule.changeShift(0, 8, i);
        }
        current = surgeons.get(1);
        for(int i = 2; i < 7; i++){
            current.schedule.changeShift(8, 16, i);
        }
        current = surgeons.get(2);
        for(int i = 2; i < 7; i++){
            current.schedule.changeShift(16, 24, i);
        }
    }
    //creates the whole schedule
    public void createSchedule(){
        availability = new ArrayList(avail.values());
        Collections.sort(availability);
        int pending;
        for(int i = 0; i < availability.size(); i++){
            int day = availability.get(i).dayIndicator;
            int role = availability.get(i).roleIndicator;
            int shift = availability.get(i).shiftIndicator;
            pending = openings[day][role][shift];
            while(pending > 0 && !availability.get(i).isEmpty()){
                if(availability.get(i).schedule()) --pending;
            }
        }
        assignSurgeons();
    }
}
