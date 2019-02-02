/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This class will be responsible for scheduling users. This will start out
as a single user at a time and possibly expand to a hospital-wide schedule.
*/

import java.util.Random;

public class Scheduler {
    void schedule(User u){
        WeeklyAvailability availability = u.getAvailability();
        boolean[] consider = new boolean[8];
        String preference = availability.getPreference();
        //initialize counters
        int shiftsAssigned = 0;
        int daysOpen = 0;
        //initialize calendar
        //scheduling a block of 4 days starting from sunday through wednesday
        Random rand = new Random();
        //start four day block on this day (1 - 4)
        int currentDay = rand.nextInt(4) + 1;
        for (int i = 1; i < consider.length; i++){
            consider[i] = availability.checkAvailability(i);
            if(consider[i]) ++daysOpen;
        }
        u.initializeSchedule();
        //consider throwing exception here if the user has less than 3 days open
        if(daysOpen >= 4){
            do{
                //if we hit saturday we need to go back to sunday                
                if (currentDay >= 8) currentDay = 1;
                //if we are available we set up a shift
                if(consider[currentDay] && 
                        availability.getStartHour(currentDay) <= startTime(preference) &&
                        availability.getFinishHour(currentDay) >= stopTime(preference)){
                        u.schedule.changeShift(startTime(preference), 
                                stopTime(preference), currentDay);
                        shiftsAssigned++;
                        consider[currentDay] = false;
                        currentDay++;
                }
                else currentDay++;                
            }while(shiftsAssigned < 4);
        }
    }
    //quick method for translating preference
    private int startTime(String pref){
        if (pref.equals("early")) return 0;
        if (pref.equals("days")) return 5;
        if (pref.equals("mids")) return 11;
        if (pref.equals("late")) return 14;
        return 0;
    }
    //quick method for translating preference
    private int stopTime(String pref){
        if (pref.equals("early")) return 10;
        if (pref.equals("days")) return 15;
        if (pref.equals("mids")) return 21;
        if (pref.equals("late")) return 24;
        return 0;
    }
    //check availability for a shift
      boolean isAvailable(int day, int startHour, int finishHour, WeeklyAvailability availability){
          if (availability.checkAvailability(day)){
              if (startHour >= availability.getStartHour(day)){
                  if(finishHour <= availability.getFinishHour(day)){
                      return true;
                  }
              }
          }
          return false;
      } 
}