/*
Author: Carl Baumbach
Last update: June 26, 2016

Class description:
This class houses the functionality for what a manager sees after successfully
passing user login authentication.  Functionality will be different than
managers, due to not having administrative rights and managerial duties.
*/

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableModel;

/*
 * EmployeeFrame class - The default frame and root frame after login.
 *     The user is able to modify their weekly schedule and view both
 *     their own individual schedule for the week as well as a company
 *     wide one. 
 */
class EmployeeFrame extends JFrame{
	private static final long serialVersionUID = 1L;
    JPanel panel = new JPanel();
    JTable table;
    Object[][] userSchedule; //individual availablilty
    Object[][] schedule; //company schedule
    final int WINDOW_WIDTH = 600;
    final int WINDOW_HEIGHT = 500;
    
    EmployeeFrame(User user){
        setTitle(user.firstName + " " + user.lastName);
        setLayout(new FlowLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        ModifyScheduleFrame frame = new ModifyScheduleFrame(user);
        
        //radio buttons to select which table to view
    	JRadioButton individualRB = new JRadioButton("My Schedule");
    	JRadioButton companyRB = new JRadioButton("Company Schedule");
    	individualRB.setSelected(true);
    	ButtonGroup scheduleBG = new ButtonGroup();
    	scheduleBG.add(individualRB);
    	scheduleBG.add(companyRB);
        
    	schedule = initializeTable();
    	createCompanySchedule();//initialization of the schedule
    	schedule = setIndividualSchedule(schedule, user);        
    	String [] column_names = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        
    	//ActionListener for the two radio buttons. Default is the individual
    	//schedule, will automatically update the JTable based on which 
    	//radio button is selected in the group
    	ActionListener rbActionListener = new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			JRadioButton rb = (JRadioButton) e.getSource();
    			clearTable(schedule);
    			if(rb == individualRB){
    				schedule = setIndividualSchedule(schedule, user);    				
    			}
    			else{
					schedule = setCompanySchedule(schedule);    				
    			}
    			//refresh the table
    			table.invalidate();
    			table.validate();
    			table.repaint();
    		}
    	};
    	
    	//creates a table with the specific schedule filled in and each column
    	//is a weekday
		table = new JTable(schedule, column_names);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        
        //creates the headers for each row (hours)
        JScrollPane scrollPane = new JScrollPane(table);
        JTable rowTable = new RowHeaders(table, user, 1);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
        
        //ActionListener for the modifyScheduleButton, will open the 
        //availability frame where the user can select which shifts they are
        //able to work
        JButton modifyScheduleButton = new JButton("Modify Default Schedule");
        modifyScheduleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
		        setUserSchedule(frame.getUserSchedule());
				//userSchedule now contains a two dimensional array of weekdays
				//and hours the user is unable to work.
		        boolean change = false;
				for(int shift = 0; shift < userSchedule.length; shift++){
					for(int weekday = 0; weekday < userSchedule[shift].length; weekday++){
						if(userSchedule[shift][weekday].equals("")){
							if(shift == 0) 
								//weekday + 1 because 1 = Sunday
								user.setAvailability(weekday + 1, 5, 24);
							else if(shift == 1) 
								user.setAvailability(weekday + 1, 11, 24);
							else if(shift == 2)
								user.setAvailability(weekday + 1, 0, 15);
							else if(shift == 3)
								user.setAvailability(weekday + 1, 0, 21);
							change = true;
						}
					}
				}
				if(change){
					//if a change was made, a new company schedule is created
					//using the new availability and the table is refreshed 
					createCompanySchedule();
					table.invalidate();
					table.validate();
					table.repaint();
				}
			}        	
        });
        
        //Action upon clicking the close button
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                //Close data through serialization and encrypt the information.
                DataManager.serialize();
                System.exit(0);
            }
        });

    	individualRB.addActionListener(rbActionListener);
    	companyRB.addActionListener(rbActionListener);   
    	
        add(individualRB);
        add(companyRB);
        add(scrollPane);
        add(modifyScheduleButton);
        add(panel);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //createCompanySchedule method - creates a schedule for each user
    private void createCompanySchedule(){
    	for(User u : DataManager.userList){
        	Scheduler s = new Scheduler();
        	s.schedule(u);
    	} 	
    }
    
    //clearTable method - sets each index in the schedule object to null
    private void clearTable(Object[][] schedule){
    	for(int weekday = 0; weekday < 7; weekday++){
    		for(int hour = 0; hour < 25; hour++){
    			schedule[hour][weekday] = null;
    		}
    	}
    }
    
    //initializeTable method - sets each index in the 2D array to ""
	private Object[][] initializeTable(){
    	Object[][] empty_data = new Object[25][7];
    	for(int weekday = 0; weekday < 7; weekday++){
    		for(int hour = 0; hour < 25; hour++){
    			empty_data[hour][weekday] = "";
    		}
    	}
    	return empty_data;
    }
	
	//setIndividualSchedule method - populates the current user's schedule
	private Object[][] setIndividualSchedule(Object[][] schedule, User user){
		for(User u : DataManager.userList){
			if(u.getUserID().equals(user.getUserID())){
				Schedule.Shift[] userSchedule = u.schedule.schedule;
				for(int day = 1; day < userSchedule.length; day++){
					int startHour = userSchedule[day].startHour;
					int finishHour = userSchedule[day].finishHour;
					if(finishHour != 0){
						for(int i = startHour; i < startHour + 10; i++){    							
    						schedule[i][day - 1] = "Working";    							
						}
					}
				}
			}
		}
		return schedule;
	}
	
	//setCompanySchedule method - populates the entire company's schedule
	private Object[][] setCompanySchedule(Object[][] schedule){
		for(User u : DataManager.userList){
			Schedule.Shift[] userSchedule = u.schedule.schedule;
			for(int day = 1; day < userSchedule.length; day++){
				int startHour = userSchedule[day].startHour;
				int finishHour = userSchedule[day].finishHour;
				if(finishHour != 0){
					for(int i = startHour; i < startHour + 10; i++){
						if(schedule[i][day - 1] != null){
							schedule[i][day - 1] += "  " + u.firstName; 
						}
						else{
							schedule[i][day - 1] = u.firstName;
						}
					}
				}
			}
		}
		return schedule;
	}
	
	//setUserSchedule method - sets the global userSchedule variable
	public void setUserSchedule(Object[][] userSchedule){
		this.userSchedule = userSchedule;
	}
}//end EmployeeFrame class

/*
 * ModifyScheduleFrame class - Displays an availability frame where an empty
 *     cell indicates not available during that shift and "Available" 
 *     indicates the user is available. Only 4 shifts are selectable:
 *     Shift 1: 12:00am - 10:00am
 *     Shift 2: 5:00am - 3:00pm
 *     Shift 3: 11:00am - 9:00pm
 *     Shift 4: 2:00pm - 12:00am
 */
class ModifyScheduleFrame extends JFrame{
	private static final long serialVersionUID = 1L;
    final int WINDOW_WIDTH = 600;
    final int WINDOW_HEIGHT = 190;
    JTable table;
    Object[][] userSchedule;
	
	ModifyScheduleFrame(User user){
		setTitle("Modify Schedule");
		setLayout(new FlowLayout());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		JLabel rulesLabel = new JLabel("Shift 1: Midnight - 1000    |    Shift 2: 0500 - 1500    |    Shift 3: 1100 - 2100    |    Shift 4: 1400 - Midnight");
		String [] column_names = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		userSchedule = getUserSchedule(); //could have been modified earlier
		table = new JTable(userSchedule, column_names);
        table.setPreferredScrollableViewportSize(new Dimension(500, 63));
        
        //Sets the row headers (shift numbers)
        JScrollPane scrollPane = new JScrollPane(table);
        JTable rowTable = new RowHeaders(table, user, 2);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
        
        //acceptButton action listener will set each index in the user's schedule
        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {		
				TableModel tableModel = table.getModel();
				for(int shift = 0; shift < 4; shift++){
					for(int weekday = 0; weekday < 7; weekday++){
						Object value = tableModel.getValueAt(shift, weekday);
						userSchedule[shift][weekday] = value;
					}
				}
				dispose();
			}        	
        });
        
        add(rulesLabel);
        add(scrollPane);
        add(acceptButton);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	
	//getUserSchedule method - returns the userSchedule if it exists,
	//otherwise creates fully populated "Available" schedule.
	public Object[][] getUserSchedule(){
		if(userSchedule != null){
			return userSchedule;
		}
		else{
			userSchedule = new Object[4][7];
	    	for(int shift = 0; shift < 4; shift++){
	    		for(int weekday = 0; weekday < 7; weekday++){
	    			userSchedule[shift][weekday] = "Available";
	    		}
	    	}
	    	return userSchedule;
		}
    }
}