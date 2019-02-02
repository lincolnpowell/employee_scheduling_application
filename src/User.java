/*
Author: Lincoln Powell
Last update: July 3, 2016 by Kyle Miller

Class description:
The User class is an abstract class that allows the implementation of
the Employee and Manager classes using its fields/methods as a guide for
inheritance.

The use of abstraction is important to allow DataManager.userList to hold
both Employee and Manager objects.
*/

import java.io.Serializable;

public abstract class User implements Serializable{
    String firstName, lastName, userID, password, admin = "false",
        locked = "false", newUser = "true";
    String[][] challengeQuestions = new String[4][2];
   
    User(String firstName, String lastName, String userID, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.password = password;   
    }
    
    User(String firstName, String lastName, String userID, String password, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.password = password;
        this.role = role;
    }

    String getFirstName()                   {return firstName;}
    String getLastName()                    {return lastName;}
    String getUserID()                      {return userID;}
    String getPassword()                    {return password;}
    String getAdmin()                       {return admin;}
    String getChallengeQuestion(int i)	    {return challengeQuestions[i][0];}
    String getChallengeAnswer(int i)        {return challengeQuestions[i][1];}
    String getIsNewUser()                   {return newUser;}
    String getLocked()                      {return locked;}
    String getRole()                        {return role;}
    void setFirstName(String firstName)     {this.firstName = firstName;}
    void setLastName(String lastName)       {this.lastName = lastName;}
    void setUserID(String userID)           {this.userID = userID;}
    void setPassword(String password)       {this.password = password;}
    void setAdmin(String admin)             {this.admin = admin;}

    void setChallengeQuestion(String question, String answer, int index){
	challengeQuestions[index][0] = question;
	challengeQuestions[index][1] = answer;
    }
    
    void setIsNewUser(String isNewUser)     {this.newUser = isNewUser;}
    void setLocked(String locked)           {this.locked = locked;}

    //Items added by Kyle Miller 15 June 2016
    Integer employee_ID;
    PhoneInfo phoneNumbers = new PhoneInfo();
    AddressInfo address = new AddressInfo();
    Schedule schedule = new Schedule();
    WeeklyAvailability availability = new WeeklyAvailability();

    //Items added by Kyle Miller 29 June 2016
    String availableShifts = "0000";
    
    //needs to be "nurse", "orderly", "doctor", or "surgeon"
    String role;

    //sets availability for a specified day (1 = sunday through 7 = saturday)
    void setAvailability(int day, int startHour, int finishHour){
        availability.setStartHour(day, startHour);
        availability.setFinishHour(day, finishHour);
    }
    
    //preference should be "days" (0500 - 1500), "mids" (1100 - 2100), 
    //"early" (0000 - 1000), or "late" (1400 - 2400)
    void setShiftPreference(String preference){
        availability.setPreference(preference);
    }
    
    //set up / change address
    void changeAddress(int houseNumber, String address1,  String city, String state,  int zip){
        address.setHouseNumber(houseNumber);
        address.setAddress1(address1);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
    }
    
    //set up / change address with extra address field (for apartements and such)
    void changeAddress(int houseNumber, String address1, String address2,  String city, String state,  int zip){
        address.setHouseNumber(houseNumber);
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
    }
    
    //returns the whole address - we may need to break this up
    String getAddress(){
        return address.toString();
    }
    
    //gets a phone number with the index passed in
    String getPhoneNumber(int index){
       return phoneNumbers.getPhone(index);
    }
    
    //change a known phone number
    void changePhoneNumber(String targetNumber, String replacement){
        phoneNumbers.changeNumber(targetNumber, replacement);
    }
    
    //how many phone numbers does a user currently have?
    int getPhoneSize(){
        return phoneNumbers.getSize();
    }
    
    //add a number
    void addPhone(int type, String number){
        phoneNumbers.addPhone(type, number);
    }
    
    WeeklyAvailability getAvailability(){
        return availability;
    }
    
    void applySchedule(Schedule s){
        schedule = s;
    }
    
    Integer getEmployeeID(){
        return employee_ID;
    }
    
    void initializeSchedule(){
        schedule.initialize();
    } 
    //end of items added 
    
    @Override
    public String toString(){
        return firstName + " " + lastName;
    }
}

class Employee extends User{
    Employee(String firstName, String lastName, String userID, String password){
            super(firstName, lastName, userID, password);
    }
    
    Employee(String firstName, String lastName, String userID, String password, String role){
            super(firstName, lastName, userID, password, role);
    }
}

class Manager extends User{
    Manager(String firstName, String lastName, String userID, String password){
            super(firstName, lastName, userID, password);
            admin = "true";
    }
    
    Manager(String firstName, String lastName, String userID, String password, String role){
            super(firstName, lastName, userID, password, role);
            admin = "true";
    }
}