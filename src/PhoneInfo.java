/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This phone info class allows for extra phone numbers to
be entered in to a user's info with only 1 ID.
This class was updated to use only 1 array list of phone numbers instead
of dual array lists. Type is now integrated in to the phone number class.
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhoneInfo implements Serializable{
    //fields for Phone Info
    private final int PHONE_ID;
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();
    //private List<String> phoneType = new ArrayList<>();
    //private List<String> phoneNumber = new ArrayList<>();
    //empty const
    public PhoneInfo(){
        PHONE_ID = 0;
    }
    //constructor for phone information
    public PhoneInfo(int PHONE_ID){
        this.PHONE_ID = PHONE_ID;    
    }
    //add a number
    public void addPhone(int type, String phoneNumber){
        this.phoneNumbers.add(new PhoneNumber(type, phoneNumber));
    }
    //remove a number
    public void removePhone(PhoneNumber p){
        if (phoneNumbers.contains(p)){
            int index = phoneNumbers.indexOf(p);
            phoneNumbers.remove(index);
        }
    }
    //change a number
    public void changeNumber(String target, String replacement){
        for (PhoneNumber phoneNumber : phoneNumbers) {
            if (phoneNumber.getNoDash().equals(target)) {
                phoneNumber.changeNumber(target);
            }
        }
    }
    //how many numbers are there?
    public int getSize(){return phoneNumbers.size();}
    //get a number at certain index (p)
    public String getPhone(int p){return phoneNumbers.get(p) + "";}
    //get the phone type at an index (t)
    public PhoneType getType(int t){return phoneNumbers.get(t).getType();}
    //to string override - prints all phone numbers as a list
    public String toString(){
        String out = "";
        for (PhoneNumber phoneNumber : phoneNumbers) {
            out = phoneNumber + "\n";
        }
        return out;
    }
}