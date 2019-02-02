/*
Author: Carl Baumbach
Last update: June 26, 2016

Class description:
This class houses the functionality for what a manager sees after successfully
passing user login authentication.  Functionality will be different than
employees.
*/

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * ManagerFrame class - extends the EmployeeFrame class and provides the
 *     manager with the ability to edit a user's first and last name in 
 *     the database.
 */
class ManagerFrame extends EmployeeFrame{
	private static final long serialVersionUID = 1L;

	ManagerFrame(User manager){
		super(manager);
        setTitle(manager.firstName + " " + manager.lastName);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        //modifyUsersButton action listener - brings up the Modify Users
        //frame when selected to edit a user's first and last name in the
        //database.
        JButton modifyUsersButton = new JButton("Modify Users");
        modifyUsersButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ModifyUsersFrame frame = new ModifyUsersFrame(manager);
				frame.setVisible(true);
			}        	
        });
        
        add(modifyUsersButton);
        add(panel);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }//end ManagerFrame no-args constructor	
}//end ManagerFrame class

/*
 * ModifyUsersFrame method - Frame that displays a dropdown list of users
 *     and will update the other fields based on the selection in the 
 *     dropdown list. Only the first and last name fields are editable.
 */
class ModifyUsersFrame extends JFrame{
	private static final long serialVersionUID = 1L;
    final int WINDOW_WIDTH = 265;
    final int WINDOW_HEIGHT = 160;
	
	ModifyUsersFrame(User manager){
		setTitle("Modify Users");
		setLayout(new FlowLayout());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		JLabel idLabel = new JLabel("UserID:");
		JTextField idField = new JTextField(manager.userID);
		idField.setEditable(false);
		idField.setSize(80,50);
		JLabel firstNameLabel = new JLabel("First Name:");
		JTextField firstNameField = new JTextField(manager.firstName, 10);
		firstNameField.setSize(200, 50);
		JLabel lastNameLabel = new JLabel("Last Name:");
		JTextField lastNameField = new JTextField(manager.lastName, 10);
		lastNameField.setSize(200, 50);
		
		//builds a dropdown list of all users
		JComboBox<User> userCB = new JComboBox<User>();
		for(int i = 0; i < DataManager.userList.size(); i++){
			userCB.addItem(DataManager.userList.get(i));
		}
		//updates each field in the frame based on the selection in the 
		//dropdown list
		userCB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JComboBox comboBox = (JComboBox) event.getSource();
				User selectedUser = (User) comboBox.getSelectedItem();
				idField.setText(selectedUser.userID);
				firstNameField.setText(selectedUser.firstName);
				lastNameField.setText(selectedUser.lastName);
			}
		});
		
		//updates the database with the new first and last name for the
		//selected user
		JButton confirmModificationButton = new JButton("Submit");
		confirmModificationButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				User selectedUser = (User) userCB.getSelectedItem();
				selectedUser.firstName = firstNameField.getText();
				selectedUser.lastName = lastNameField.getText();
				dispose();
			}
		});
		
		add(userCB);
		add(idLabel);
		add(idField);
		add(firstNameLabel);
		add(firstNameField);
		add(lastNameLabel);
		add(lastNameField);
        add(confirmModificationButton);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
	}
}