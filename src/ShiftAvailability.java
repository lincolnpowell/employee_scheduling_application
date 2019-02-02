/*
Author: Kyle Miller
Last update: July 3, 2016

Class description:

*/

import java.util.ArrayList;

public class ShiftAvailability implements Comparable<ShiftAvailability>{
    private ArrayList<User> available = new ArrayList<>();
    public final int roleIndicator;
    public final int shiftIndicator;
    public final int dayIndicator;
    private final int dateIndicator;
    private final int monthIndicator;
    //for role: 0 = orderly, 1 = nurse, 2 = doctor
    //for shift: 0 = early, 1 = day, 2 = mid, 3 = late
    //for day : 1 = sunday ... 7 = saturday
    public ShiftAvailability(int role, int shift, int day, int date, int month){
        roleIndicator = role;
        shiftIndicator = shift;
        dayIndicator = day;
        dateIndicator = date;
        monthIndicator = month;
    }
    public User getAvailable(int i){
        if(available.size() <= i) return available.get(i);
        else return null;
    }
    //adds a possible employee to be slotted in here
    public void addAvailable(User u){
        available.add(u);
    }
    //how many employees can be scheduled here?
    public int count(){
        return available.size();
    }
    //schedule attempt
    public boolean schedule(){
        User u = available.remove(0);
        if (!u.availableShifts.equals("")){
            assign(u);
            return true;
        }
        else return false;
    }
    //assign valid user to appropriate shift
    private void assign(User u){
        switch(shiftIndicator){
            case 0:
                u.schedule.changeShift(0,10,dayIndicator);
                u.availableShifts = u.availableShifts.replaceFirst("0", "");
                break;
            case 1:
                u.schedule.changeShift(5,15,dayIndicator);
                u.availableShifts = u.availableShifts.replaceFirst("0", "");
                break;
            case 2:
                u.schedule.changeShift(11,21,dayIndicator);
                u.availableShifts = u.availableShifts.replaceFirst("0", "");
                break;
            case 3:
                u.schedule.changeShift(14,24,dayIndicator);
                u.availableShifts = u.availableShifts.replaceFirst("0", "");
        }
    }
    boolean isEmpty(){
        return available.isEmpty();
    }
    //overrides - to string and comparable
    @Override
    public int compareTo(ShiftAvailability o) {
        return this.count() - o.count();
    }
    @Override
    public String toString(){
        return "" + count();
    }
}