/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
New solution for number handling phone numbers with strings instead of int or long
class allows for a number to be output using tostring. Phone numbers
may also be changed.
*/

public class PhoneNumber {
    private String phoneNumber;
    private String set1;
    private String set2;
    private String set3;
    private PhoneType type;
    public static final int HOME = 1;
    public static final int WORK = 3;
    public static final int CELL = 2;
    //constructor -- assumes error checking complete
    //used toCharArray() for greater flexibility. I found that
    //substring was a little to restrictive for what I wanted to do.
    public PhoneNumber(int typeKey, String p){
        switch(typeKey){
            case 1:
                type = PhoneType.HOME;
            case 2:
                type = PhoneType.CELL;
            case 3:
                type = PhoneType.WORK;
            default:
                type = PhoneType.HOME;
        }
        char[] temp = p.toCharArray();
        int len = p.length();
        char[] leftover = new char[len - 7];
        //last 4
        set3 = new String(temp, len - 4, 4);
        //middle 3
        set2 = new String(temp, len - 7, 3);
        System.arraycopy(temp, 0, leftover, 0, len - 7);
        //whatever comes at the beginning
        set1 = new String(leftover);
        //construct full number with dashes
        phoneNumber = set1 + "-" + set2 + "-" + set3;
        this.type = type;
    }
    //changes a phone number - this works the same way as it does
    //in the constructor.
    public void changeNumber(String p){
        char[] temp = p.toCharArray();
        int len = p.length();
        char[] leftover = new char[len - 7];        
        set3 = new String(temp, len - 4, 4);
        set2 = new String(temp, len - 7, 3);
        System.arraycopy(temp, 0, leftover, 0, len - 7);
        set1 = new String(leftover);    
        phoneNumber = set1 + "-" + set2 + "-" + set3;
    }
    public String getNoDash(){
        return set1 + set2 + set3;
    }
    //returns the phone type
    public PhoneType getType(){return type;}
    //display whole number
    @Override
    public String toString(){return phoneNumber;}
}