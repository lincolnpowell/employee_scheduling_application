/*
Author: Lincoln Powell
Last update: July 3, 2016

Class description:
This class is used to create a test data set database for program debugging.
*/

import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class CreateUsers {
    static List<String> userIDs = new ArrayList<>();
    static Random randomNumber = new Random();
    static int surgeonNumber = 0;
    
    /*public static void main(String[] args) throws ClassNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter data set number for testing: ");
        int input = scanner.nextInt();
        
        for(int i = 1; i < input; i++){
            String firstName = NameGenerator.getRandomFirstName(),
                lastName = NameGenerator.getRandomLastName();
            if(i % 8 == 0){
                if(surgeonNumber < 3){
                    DataManager.userList.add(new Manager(firstName,
                                                         lastName,
                                                         getUserID(firstName, lastName),
                                                         "PassWord",
                                                         "surgeon"));
                    surgeonNumber++;
                }
                else{
                    DataManager.userList.add(new Manager(firstName,
                                                         lastName,
                                                         getUserID(firstName, lastName),
                                                         "PassWord",
                                                         getRole()));
                }
            }
            else{
                DataManager.userList.add(new Employee(firstName,
                                                     lastName,
                                                     getUserID(firstName, lastName),
                                                     "PassWord",
                                                     getRole()));
            }
        }
        
        for(User u : DataManager.userList){
            setRandomAvailability(u);
        }
        
        DataManager.userList.add(new Manager("Eric", "Bloom", "EB1234T", "test"));
        DataManager.userList.add(new Employee("Lincoln", "Powell", "LP1234T", "test"));
        DataManager.userList.add(new Employee("Kyle", "Miller", "KM1234T", "test"));
        DataManager.userList.add(new Employee("Carl", "Baumbach", "CB1234T", "test"));
        DataManager.userList.add(new Manager("Clarence", "Huff", "CH1234T", "test"));
        
        System.out.println("\nCurrent user database...");
        for(User u : DataManager.userList)
        {
            System.out.print(u.getFirstName());
            System.out.print(" " + u.getLastName());
            System.out.print(" " + u.getUserID());
            System.out.print(" " + u.getPassword());
            System.out.print(" " + u.getAdmin());
            System.out.println(" " + u.getRole());
        }
        DataManager.serialize();
    }*/
    
    static private String getUserID(String firstName, String lastName){
        String userID = "";
        userID += firstName.substring(0, 1);
        userID += lastName.substring(0, 1);
        for(int i = 0; i < 4; i++){
            userID += randomNumber.nextInt(9) + 1;
        }
        if(doesUserExists(userID)){
            userID = getUserID(firstName, lastName);
        }
        return userID;
    }
    
    static private boolean doesUserExists(String user){
        for(String users : userIDs){
            if(user.equals(users))
                return true;
        }
        return false;
    }
    
    static String getRole(){
        int randInt = randomNumber.nextInt(3);
        switch(randInt){
            case 0:
                return "orderly";
            case 1:
                return "nurse";
            case 2:
                return "doctor";
            default:
                return "nurse";
        }
    }
    
    //add a random availability
    static void setRandomAvailability(User u){
        //storing the roll
        int roll;
        //Max off days is 3 otherwise the schedule is invalid
        int offDays = 3;
        //for each day we roll a random schedule
        for(int i = 1; i < 8; i++){
            //3 possiblities
            roll = randomNumber.nextInt(3);
            switch(roll){
                //case 0 we have the whole day open.
                case 0:
                    u.availability.setStartHour(i, 0);
                    u.availability.setFinishHour(i, 24);
                    break;
                //case 1: we prefer a specific shift for this day    
                case 1:
                    roll = randomNumber.nextInt(4);
                    switch(roll){
                        case 0:
                            u.availability.setStartHour(i, 0);
                            u.availability.setFinishHour(i, 10);
                            break;
                        case 1:
                            u.availability.setStartHour(i, 5);
                            u.availability.setFinishHour(i, 15);
                            break;
                        case 2:
                            u.availability.setStartHour(i, 11);
                            u.availability.setFinishHour(i, 21);
                            break;
                        case 3:
                            u.availability.setStartHour(i, 14);
                            u.availability.setFinishHour(i, 24);
                            break;
                    }
                //case 2 we get the day off
                case 2:
                    //if we're out of off days we have to reroll the result
                    if(offDays > 0){
                        u.availability.setStartHour(i, 0);
                        u.availability.setFinishHour(i, 0);
                        --offDays;
                    }
                    //sets us back to reroll
                    else --i;
                    break;
                default: //if something went wrong we reroll
                    --i;                    
            }
        }
    }
}

class NameGenerator{
    /*
    Declare static integer pointer as a placeholder for a randomly
    generated number.
    */
    static private int pointer;
    
    /*
    Declare and initialize a static String array of first names.
    */
    static String[] firstName = {
        "Leroy", "James", "John", "Robert", "Sean",
        "Nathan", "Andrew", "Jesse", "Ross", "Gregory",
        "Josh", "Jacob", "Jamal", "Malcolm", "Reese",
        "Dewey", "Francis", "Hal", "Jack", "Humphrey",
        "Mario", "Lincoln", "Christopher", "Brandon", "Anthony",
        "Jackson", "Aiden", "Liam", "Lucas", "Noah",
        "Mason", "Ethan", "Caden", "Logan", "Elijah",
        "Ryan", "Eric", "Kyle", "Carl", "William",
        "Isaac", "Henry", "Tyler", "Alex", "Jason",
        "Chase", "Isaiah", "Braeden", "Jordan", "David",
        "Sophie", "Emma", "Patience", "Rose", "Scarlett",
        "Reshelle", "Ann", "Olivia", "Mary", "Emily",
        "Lily", "Chloe", "Ashley", "Jessica", "Isabella",
        "Janet", "Ava", "Megan", "Kaylee", "Jennifer",
        "Sarah", "Victoria", "Natalie", "Lauren", "Camille",
        "London", "Kayla", "Vivian", "Stephanie", "Samantha",
        "Savannah", "Isabelle", "Zoe", "Hannah", "Elizabeth",
        "Leah", "Brooklyn", "Camille", "Bridget", "Claire",
        "Michelle", "Darlene", "Ginger", "Ashe", "Khloe",
        "Marsha", "Pauline", "Rachelle", "Tina", "Valentina"
    };
    
    /*
    Declare and initialize a static String array of last names.
    */
    static String[] lastName = {
        "Smith", "Johnson", "Williams", "Jones", "Brown",
        "Davis", "Miller", "Wilson", "Moore", "Taylor",
        "Anderson", "Thomas", "Jackson", "White", "Harris",
        "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
        "Clark", "Rodriguez", "Lewis", "Lee", "Walker",
        "Hall", "Allen", "Young", "Hernandez", "Powell",
        "King", "Wright", "Lopez", "Hill", "Scott", "Green",
        "Adams", "Baker", "Shelton", "Gonzalez", "Nelson",
        "Mitchell", "Perez", "Roberts", "Turner", "Campbell",
        "Parker", "Wood", "Jenkins", "Long", "Peterson",
        "Gomez", "Bennett", "Greene", "Blue", "Cox",
        "Cruz", "Reyes", "Bloom", "Baumbach", "Richardson",
        "Woods", "Russell", "Simmons", "Coleman", "Patterson",
        "Graham", "Jordan", "Stevens", "Ford", "Rivers",
        "Owens", "McDonald", "McFarland", "McCray", "Freeman",
        "Griffin", "Black", "White", "Stone", "Hunt",
        "Dunn", "Pierce", "Hansen", "Cunningham", "Bradley",
        "Elliott", "Hart", "Austin", "Washington", "Dean",
        "Montgomery", "Gilbert", "Burke", "Carbaugh", "Torres",
        "Lawson", "George", "Wheeler", "Vargas", "Chapman"
    };
    
    static String getRandomFirstName(){
        String name = "";
        Random randomNumber = new Random();
        pointer = randomNumber.nextInt(100);
        return name = firstName[pointer];        
    }
    
    static String getRandomLastName(){
        String name = "";
        Random randomNumber = new Random();
        pointer = randomNumber.nextInt(100);
        return name = lastName[pointer];
    }
}