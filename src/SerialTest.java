//DEPRECIATED!!!  PLEASE USE CREATEUSERS.JAVA INSTEAD!!!




/*
Author: Lincoln Powell
Last update: June 3, 2016

Class description:
This class is used to recreate the database upon changes to the User class.

NOTE: Due to object serialization concept use for this assignment,
any and all changes to the User class must require the deletion of the
userdata.txt file and to force the recreation of the file using this class'
main function!!!  Failure to do this will result in tracebacks!!!
*/

/*
public class SerialTest {
    public static void main(String[] args) throws ClassNotFoundException {
        DataManager.userList.add(new Manager("Eric", "Bloom", "eb1234", "654321"));
        DataManager.userList.add(new Employee("Lincoln", "Powell", "lp1234", "123456"));
        DataManager.userList.add(new Employee("Kyle", "Miller", "km1234", "98D76C"));
        DataManager.userList.add(new Employee("Carl", "Baumbach", "cb1234", "L@sTCla$$"));
        DataManager.userList.add(new Manager("Clarence", "Huff", "ch1234", "password"));

        System.out.println("Current user database...");
        for(User u : DataManager.userList)
        {
            System.out.print(u.getFirstName());
            System.out.print(" " + u.getLastName());
            System.out.print(" " + u.getUserID());
            System.out.print(" " + u.getPassword());
            System.out.println(" " + u.getAdmin());
        }
        
        DataManager.serialize();
        DataManager.deserialize();
        
        System.out.println("\nCurrent user database...");
        for(User u : DataManager.userList)
        {
            System.out.print(u.getFirstName());
            System.out.print(" " + u.getLastName());
            System.out.print(" " + u.getUserID());
            System.out.print(" " + u.getPassword());
            System.out.println(" " + u.getAdmin());
        }
    }
}*/