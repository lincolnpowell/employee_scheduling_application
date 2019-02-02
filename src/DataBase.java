/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This class consists mostly of search methods used to search through the user
data. Right now the searches are of the linear variety. The database can be
upgraded to be faster by implementing faster data structures such a s a binary
tree.
*/
/*
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    ArrayList<User> employees = new ArrayList<>();
    //Simple linear search for a user
    public User[] lastNameSearch(String ln){
        User[] searchResult = new User[employees.size()];
        int count = 0;
        for (User employee : employees) {
            if (employee.getLastName().equalsIgnoreCase(ln)) {
                searchResult[count] = employee;
                count++;
            }
        }
        return searchResult;
    }    
    //simple linear search with an extra value passed in to narrow results
    public  User[] fullNameSearch(String fn, String ln){
        User[] searchResult = new User[employees.size()];
        int count = 0;
        for (User employee : employees) {
            if (employee.getFirstName().equalsIgnoreCase(fn) &&
                    employee.getLastName().equalsIgnoreCase(ln)) {
                searchResult[count] = employee;
                count++;
            }
        }
        return searchResult;
    }
    //linear search by ID
    public User IDSearch(Integer id){
        for(User employee : employees){
            if(employee.getEmployeeID() == id){
                return employee;
            }
        }
        return null;
    }
    //add to the db
    public void addUser(User u){
        employees.add(u);
    }
    //added 26 June 2016
    //methood to change a user's schedule
    //start and finish time set to zero will make the user OFF for that day
    public void changeShift(User u, int day, int start, int finish){
        u.schedule.changeShift(start, finish, day);
    }
    
    //test main
    public static void main(String[] args){
        DataBase db = new DataBase();
        Scheduler sched = new Scheduler();
        User test = new Employee("Billy", "Kid", "BKid", "12345");
        test.changeAddress(1000, "Rodeo Dr.", "Dodge City", "OK", 12345);
        test.addPhone(1, "18001234567");
        test.setShiftPreference("late");
        test.setAvailability(1, 0, 24);
        test.setAvailability(2, 0, 24);
        test.setAvailability(3, 0, 24);
        test.setAvailability(4, 0, 24);
        test.setAvailability(5, 0, 24);
        test.setAvailability(6, 0, 24);
        test.setAvailability(7, 0, 24);
        test.role = "nurse";
        sched.schedule(test);
                System.out.println("" + test.availability);
        System.out.println("" + test.schedule);

        db.addUser(test);
        test = new Employee("Bobby", "Kid", "BKid", "6189");
        test.changeAddress(1000, "Rodeo Dr.", "Dodge City", "OK", 12345);
        test.addPhone(1, "18001234567");
        test.setShiftPreference("mids");
        test.setAvailability(1, 0, 24);
        test.setAvailability(2, 0, 24);
        test.setAvailability(3, 0, 24);
        test.setAvailability(4, 0, 24);
        test.setAvailability(5, 0, 24);
        test.setAvailability(6, 0, 24);
        test.setAvailability(7, 0, 24);
        test.role = "surgeon";
        sched.schedule(test);
        db.addUser(test);
        System.out.println(test.getAddress() + "");
        System.out.println(test.phoneNumbers + "");
        
        test = new Employee("Bob", "Kid", "BKid", "6189");
        test.changeAddress(1000, "Rodeo Dr.", "Dodge City", "OK", 12345);
        test.addPhone(1, "18001234567");
        test.setShiftPreference("mids");
        test.setAvailability(1, 0, 24);
        test.setAvailability(2, 0, 24);
        test.setAvailability(3, 0, 24);
        test.setAvailability(4, 0, 24);
        test.setAvailability(5, 0, 24);
        test.setAvailability(6, 0, 24);
        test.setAvailability(7, 0, 24);
        test.role = "surgeon";
        sched.schedule(test);
        db.addUser(test);
        System.out.println(test.getAddress() + "");
        System.out.println(test.phoneNumbers + "");
        
        test = new Employee("Bill", "Kid", "BKid", "6189");
        test.changeAddress(1000, "Rodeo Dr.", "Dodge City", "OK", 12345);
        test.addPhone(1, "18001234567");
        test.setShiftPreference("mids");
        test.setAvailability(1, 0, 24);
        test.setAvailability(2, 0, 24);
        test.setAvailability(3, 0, 24);
        test.setAvailability(4, 0, 24);
        test.setAvailability(5, 0, 24);
        test.setAvailability(6, 0, 24);
        test.setAvailability(7, 0, 24);
        test.role = "surgeon";
        sched.schedule(test);
        db.addUser(test);
        System.out.println(test.getAddress() + "");
        System.out.println(test.phoneNumbers + "");
        WeekSetup setup = new WeekSetup();
        setup.initialize(db.employees);
        setup.addOpenings(1, 1, 0, 2);
        setup.addOpenings(2, 1, 0, 2);
        setup.addOpenings(3, 1, 0, 2);
        setup.addOpenings(4, 1, 0, 2);
        setup.createSchedule();
        for(int i = 0; i < db.employees.size(); i++){
            System.out.println(db.employees.get(i).firstName);
            System.out.println(db.employees.get(i).schedule + "");
        }
    }
}*/