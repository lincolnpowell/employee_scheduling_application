/*
Author: Lincoln Powell
Last update: June 26, 2016

Class description:
This file houses classes for GUIs:
1) LoginDialog controls login authentication features of the program.
2) CreateChallengeQuestionsDialog houses functionality for establishing user challenge questions for password reset feature.
3) ConfirmUserDialog is 1 out of 3 dialogs that flows the user in using the password reset feature; this dialog ensures the user has
a username in the database.
4) ChallengeQuestionPromptDialog is 1 out of 3 dialogs that flows the user in using the password reset feature; this dialog validates the
user against created challenge questions.
5) ResetPasswordDialog is 1 out of 3 dialogs that flows the user in using the password reset feature; this dialog allows the user
to reset their password against set validation rules.
*/

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class LoginDialog extends JDialog {
    JPanel panel = new JPanel(new GridBagLayout());
    JPanel buttons = new JPanel();
    GridBagConstraints cs = new GridBagConstraints();
    JLabel lUserID = new JLabel("Username: ");
    JLabel lPassword = new JLabel("Password: ");
    JTextField tfUser = new JTextField(15);
    JPasswordField pfPassword = new JPasswordField(15);
    JButton bLogin = new JButton("Login");
    JButton bReset = new JButton("Forgot Password");
    int counter = 0;
    
    LoginDialog(){
        cs.fill = GridBagConstraints.HORIZONTAL;
        setTitle("Login");
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lUserID, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(tfUser, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lPassword, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(pfPassword, cs);
        //--------------------------------------------------------------------
        buttons.add(bLogin);
        
        //Action upon clicking on the Login button
        bLogin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
		boolean isEmployee = false, isManager = false, isLocked = false;
                
                for(User u : DataManager.userList){
		    if(tfUser.getText().toUpperCase().equals(u.getUserID()) && pfPassword.getText().equals(u.getPassword()) &&  u.getAdmin().equals("false") && u.getLocked().equals("false")){
                        isEmployee = true;
                        if(Boolean.valueOf(u.getIsNewUser())){
                            setVisible(false);
                            CreateChallengeQuestionsDialog cqDialog = new CreateChallengeQuestionsDialog(u);
                            cqDialog.setVisible(true);
                            dispose();
                        }
                        else{
                            setVisible(false);
                            JOptionPane.showMessageDialog(null, "Welcome " + u.getFirstName() + " " + u.getLastName() + "!",
                                    "Login", JOptionPane.INFORMATION_MESSAGE);
                            EmployeeFrame frame = new EmployeeFrame(u);
                            frame.setVisible(true);
                            dispose();
                        }
		    }
                    else if(tfUser.getText().toUpperCase().equals(u.getUserID()) && pfPassword.getText().equals(u.getPassword()) &&  u.getAdmin().equals("true") && u.getLocked().equals("false")){
			isManager = true;
                        if(Boolean.valueOf(u.getIsNewUser())){
                            setVisible(false);
                            CreateChallengeQuestionsDialog cqDialog = new CreateChallengeQuestionsDialog(u);
                            cqDialog.setVisible(true);
                            dispose();
                        }
                        else{
                            setVisible(false);
                            JOptionPane.showMessageDialog(null, "Welcome " + u.getFirstName() + " " + u.getLastName() + "!",
                                    "Login", JOptionPane.INFORMATION_MESSAGE);
                            ManagerFrame frame = new ManagerFrame(u);
                            frame.setVisible(true);
                            dispose();
                        }
		    }
                    else if(tfUser.getText().toUpperCase().equals(u.getUserID()) && !pfPassword.getText().equals(u.getPassword()) && u.getLocked().equals("false")){
                        counter++;
                        if(counter % 3 == 0){
                            u.setLocked("true");
                            isLocked = true;
                        }
                    }
                    else if(tfUser.getText().equals(u.getUserID()) && u.getLocked().equals("true"))
                        isLocked = true;
		}
                
                if(!isEmployee && !isManager){
                    setVisible(false);
                    if(tfUser.getText().equals("") && pfPassword.getText().equals("") && isLocked == false){
                        JOptionPane.showMessageDialog(null, "You have not inputted a username and password!",
                                "Login", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(isLocked == true){
                        JOptionPane.showMessageDialog(null, "User account is locked! Please contact a system administrator for further assistance.",
                                "Login", JOptionPane.ERROR_MESSAGE);
                        isLocked = false;
                    }
                    else if(isLocked == false){
                        JOptionPane.showMessageDialog(null, "Wrong username and/or password!",
                                "Login", JOptionPane.ERROR_MESSAGE);
                    }
                    setVisible(true);
                    tfUser.setText("");
                    pfPassword.setText("");
                    tfUser.requestFocus();
                }
            }
        });
        
        buttons.add(bReset);
        
        bReset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                ConfirmUserDialog cuDialog = new ConfirmUserDialog();
                cuDialog.setVisible(true);
                dispose();
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
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) throws ClassNotFoundException{
        //Open serialized data and decrypt the information for use.
        DataManager.deserialize();
        new LoginDialog();
    }
}

class CreateChallengeQuestionsDialog extends JDialog{
    JPanel panel = new JPanel(new GridBagLayout());
    JPanel buttons = new JPanel();
    GridBagConstraints cs = new GridBagConstraints();
    
    String[] stockQuestions = {
	"What was the last name of your worst grade school teacher?",
	"When you were young, what did you not want to be when you grew up?",
	"Who was not your childhood hero? Why?",
	"What was the first name of your worst work supervisor?",
	"What was your least favorite toy/activity as a child?",
	"What is your least favorite team?",
	"What is your least favorite movie?",
	"Recall the funniest high school mascot you have seen?",
	"What school did you attend for fourth grade?",
	"What is your least favorite place you would like to visit?",
	"Recall a city where you visited and got lost. What was the city?",
	"Where was the most embarrassing place you have lost your keys or phone?",
	"Who was the hardest video game boss you have defeated?",
	"Pick two colors for your new sports team. What would be the name?",
	"Recall your worst incident when your car broke down. What happened?",
	"What is your favorite song and your favorite lyric from that song?"
    };

    JComboBox<String> questionList1 = new JComboBox<>(stockQuestions);
    JComboBox<String> questionList2 = new JComboBox<>(stockQuestions);
    JComboBox<String> questionList3 = new JComboBox<>(stockQuestions);
    JComboBox<String> questionList4 = new JComboBox<>(stockQuestions);
    JLabel lAnswer1 = new JLabel("Answer: ");
    JLabel lAnswer2 = new JLabel("Answer: ");
    JLabel lAnswer3 = new JLabel("Answer: ");
    JLabel lAnswer4 = new JLabel("Answer: ");
    JLabel lConfirmAnswer1 = new JLabel("Confirm: ");
    JLabel lConfirmAnswer2 = new JLabel("Confirm: ");
    JLabel lConfirmAnswer3 = new JLabel("Confirm: ");
    JLabel lConfirmAnswer4 = new JLabel("Confirm: ");
    //JLabel lDisclaimer = new JLabel("NOTE: Your answer must be at least 5 characters in length!");
    JPasswordField pfAnswer1 = new JPasswordField(41);
    JPasswordField pfAnswer2 = new JPasswordField(41);
    JPasswordField pfAnswer3 = new JPasswordField(41);
    JPasswordField pfAnswer4 = new JPasswordField(41);
    JPasswordField pfConfirmAnswer1 = new JPasswordField(41);
    JPasswordField pfConfirmAnswer2 = new JPasswordField(41);
    JPasswordField pfConfirmAnswer3 = new JPasswordField(41);
    JPasswordField pfConfirmAnswer4 = new JPasswordField(41);
    JButton bSubmit = new JButton("Submit");
    JButton bCancel = new JButton("Cancel");
    final int WINDOW_WIDTH = 550;
    final int WINDOW_HEIGHT = 394;

    CreateChallengeQuestionsDialog(User u){
        cs.fill = GridBagConstraints.HORIZONTAL;
	setTitle("Create Challenge Questions");
        
        //Components for first question.
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 2;
	panel.add(questionList1, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
	panel.add(lAnswer1, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(pfAnswer1, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lConfirmAnswer1, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(pfConfirmAnswer1, cs);
        
        //Components for second question.
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 2;
	panel.add(questionList2, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
	panel.add(lAnswer2, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(pfAnswer2, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(lConfirmAnswer2, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 1;
        panel.add(pfConfirmAnswer2, cs);
        
        //Components for third question.
        cs.gridx = 0;
        cs.gridy = 6;
        cs.gridwidth = 2;
	panel.add(questionList3, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 7;
        cs.gridwidth = 1;
	panel.add(lAnswer3, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 7;
        cs.gridwidth = 1;
        panel.add(pfAnswer3, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 8;
        cs.gridwidth = 1;
        panel.add(lConfirmAnswer3, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 8;
        cs.gridwidth = 1;
        panel.add(pfConfirmAnswer3, cs);
        
        //Components for fourth question.
        cs.gridx = 0;
        cs.gridy = 9;
        cs.gridwidth = 2;
	panel.add(questionList4, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 10;
        cs.gridwidth = 1;
	panel.add(lAnswer4, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 10;
        cs.gridwidth = 1;
        panel.add(pfAnswer4, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 11;
        cs.gridwidth = 1;
        panel.add(lConfirmAnswer4, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 11;
        cs.gridwidth = 1;
        panel.add(pfConfirmAnswer4, cs);
        //--------------------------------------------------------------------
        
        //OK, Cancel, and disclaimer note components on bottom.
	buttons.add(bSubmit);
	buttons.add(bCancel);
	//panel.add(lDisclaimer);

	//Action upon clicking on the Submit button
        bSubmit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
		if(validateInput()){
                    u.setIsNewUser("false");
                    u.setChallengeQuestion(questionList1.getItemAt(questionList1.getSelectedIndex()),
                        pfConfirmAnswer1.getText().toLowerCase(), 0);
                    u.setChallengeQuestion(questionList2.getItemAt(questionList2.getSelectedIndex()),
                        pfConfirmAnswer2.getText().toLowerCase(), 1);
                    u.setChallengeQuestion(questionList3.getItemAt(questionList3.getSelectedIndex()),
                        pfConfirmAnswer3.getText().toLowerCase(), 2);
                    u.setChallengeQuestion(questionList4.getItemAt(questionList4.getSelectedIndex()),
                        pfConfirmAnswer4.getText().toLowerCase(), 3);
                    if(Boolean.valueOf(u.getAdmin())){
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Welcome " + u.getFirstName() + " " + u.getLastName() + "!",
                                "Login", JOptionPane.INFORMATION_MESSAGE);
                        ManagerFrame frame = new ManagerFrame(u);
                        frame.setVisible(true);
                        dispose();
                    }
                    else{
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Welcome " + u.getFirstName() + " " + u.getLastName() + "!",
                                "Login", JOptionPane.INFORMATION_MESSAGE);
                        EmployeeFrame frame = new EmployeeFrame(u);
                        frame.setVisible(true);
                        dispose();
                    }
                }
	    }
        });

	//Action upon clicking on the Cancel button
        bCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
		setVisible(false);
		LoginDialog login = new LoginDialog();
		login.setVisible(true);
		dispose();
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
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    boolean validateInput(){
	if(pfAnswer1.getText().length() < 5 || pfConfirmAnswer1.getText().length() < 5 ||
                pfAnswer2.getText().length() < 5 || pfConfirmAnswer2.getText().length() < 5 ||
                pfAnswer3.getText().length() < 5 || pfConfirmAnswer3.getText().length() < 5 ||
                pfAnswer4.getText().length() < 5 || pfConfirmAnswer4.getText().length() < 5){
	    setVisible(false);
            JOptionPane.showMessageDialog(null, "Answers must be at least 5 characters in length!",
            	"Login", JOptionPane.ERROR_MESSAGE);
            setVisible(true);
	    pfAnswer1.setText("");
	    pfConfirmAnswer1.setText("");
            pfAnswer2.setText("");
	    pfConfirmAnswer2.setText("");
            pfAnswer3.setText("");
	    pfConfirmAnswer3.setText("");
            pfAnswer4.setText("");
	    pfConfirmAnswer4.setText("");
            pfAnswer1.requestFocus();
            return false;
	}

	if(!pfConfirmAnswer1.getText().equals(pfAnswer1.getText()) ||
                !pfConfirmAnswer2.getText().equals(pfAnswer2.getText()) ||
                !pfConfirmAnswer3.getText().equals(pfAnswer3.getText()) ||
                !pfConfirmAnswer4.getText().equals(pfAnswer4.getText())){
	    setVisible(false);
            JOptionPane.showMessageDialog(null, "Answers do not match!",
            	"Login", JOptionPane.ERROR_MESSAGE);
            setVisible(true);
	    pfAnswer1.setText("");
	    pfConfirmAnswer1.setText("");
            pfAnswer2.setText("");
	    pfConfirmAnswer2.setText("");
            pfAnswer3.setText("");
	    pfConfirmAnswer3.setText("");
            pfAnswer4.setText("");
	    pfConfirmAnswer4.setText("");
            pfAnswer1.requestFocus();
            return false;
	}
        
        if(questionList1.getItemAt(questionList1.getSelectedIndex()).equals(questionList2.getItemAt(questionList2.getSelectedIndex())) ||
                questionList1.getItemAt(questionList1.getSelectedIndex()).equals(questionList3.getItemAt(questionList3.getSelectedIndex())) ||
                questionList1.getItemAt(questionList1.getSelectedIndex()).equals(questionList4.getItemAt(questionList4.getSelectedIndex())) ||
                questionList2.getItemAt(questionList2.getSelectedIndex()).equals(questionList1.getItemAt(questionList1.getSelectedIndex())) ||
                questionList2.getItemAt(questionList2.getSelectedIndex()).equals(questionList3.getItemAt(questionList3.getSelectedIndex())) ||
                questionList2.getItemAt(questionList2.getSelectedIndex()).equals(questionList4.getItemAt(questionList4.getSelectedIndex())) ||
                questionList3.getItemAt(questionList3.getSelectedIndex()).equals(questionList1.getItemAt(questionList1.getSelectedIndex())) ||
                questionList3.getItemAt(questionList3.getSelectedIndex()).equals(questionList2.getItemAt(questionList2.getSelectedIndex())) ||
                questionList3.getItemAt(questionList3.getSelectedIndex()).equals(questionList4.getItemAt(questionList4.getSelectedIndex())) ||
                questionList4.getItemAt(questionList4.getSelectedIndex()).equals(questionList1.getItemAt(questionList1.getSelectedIndex())) ||
                questionList4.getItemAt(questionList4.getSelectedIndex()).equals(questionList2.getItemAt(questionList2.getSelectedIndex())) ||
                questionList4.getItemAt(questionList4.getSelectedIndex()).equals(questionList3.getItemAt(questionList3.getSelectedIndex()))){
            setVisible(false);
            JOptionPane.showMessageDialog(null, "Questions must be unique!",
            	"Login", JOptionPane.ERROR_MESSAGE);
            setVisible(true);
            pfAnswer1.setText("");
	    pfConfirmAnswer1.setText("");
            pfAnswer2.setText("");
	    pfConfirmAnswer2.setText("");
            pfAnswer3.setText("");
	    pfConfirmAnswer3.setText("");
            pfAnswer4.setText("");
	    pfConfirmAnswer4.setText("");
            pfAnswer1.requestFocus();
            return false;
        }
        return true;
    }
}

class ConfirmUserDialog extends JDialog{
    JPanel panel = new JPanel(new GridBagLayout());
    JPanel buttons = new JPanel();
    GridBagConstraints cs = new GridBagConstraints();
    JLabel lUserID = new JLabel("Username: ");
    JTextField tfUser = new JTextField(15);
    JButton bSubmit = new JButton("Submit");
    JButton bCancel = new JButton("Cancel");
    boolean userFound = false, userLocked = false;
    
    ConfirmUserDialog(){
        cs.fill = GridBagConstraints.HORIZONTAL;
        setTitle("Reset Password");
        //---------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lUserID, cs);
        //---------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(tfUser, cs);
        //---------------------------------------------------------------------
        buttons.add(bSubmit);
        
        //Action upon clicking on the Submit button
        bSubmit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                for(User u : DataManager.userList){
                    if(tfUser.getText().toUpperCase().equals(u.getUserID()) && u.getLocked().equals("false")){
                        userFound = true;
			if(Boolean.valueOf(u.getIsNewUser())){
			    setVisible(false);
			    JOptionPane.showMessageDialog(null, "User has not setup challenge questions!",
                            	"Reset Password", JOptionPane.ERROR_MESSAGE);
			    LoginDialog login = new LoginDialog();
			    login.setVisible(true);
			    dispose();
			}
			else{
                            setVisible(false);
                            ChallengeQuestionPromptDialog cqpDialog = new ChallengeQuestionPromptDialog(u);
                            cqpDialog.setVisible(true);
                            dispose();
			}
                    }
                    else if(tfUser.getText().toUpperCase().equals(u.getUserID()) && u.getLocked().equals("true")){
                        userFound = true;
                        userLocked = true;
                    }
                }
                if(!userFound && !userLocked){
                    setVisible(false);
                    JOptionPane.showMessageDialog(null, "Username not found!",
                            "Reset Password", JOptionPane.ERROR_MESSAGE);
                    setVisible(true);
                    tfUser.setText("");
                    tfUser.requestFocus();
                }
                else if(userFound && userLocked){
                    setVisible(false);
                    JOptionPane.showMessageDialog(null, "User account is locked! Please contact a system administrator for further assistance.",
                            "Reset Password", JOptionPane.ERROR_MESSAGE);
                    LoginDialog login = new LoginDialog();
                    login.setVisible(true);
                    dispose();
                }
            }
        });
        
        buttons.add(bCancel);
        
        //Action upon clicking on the Cancel button
        bCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
		setVisible(false);
		LoginDialog login = new LoginDialog();
		login.setVisible(true);
		dispose();
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
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}

class ChallengeQuestionPromptDialog extends JDialog{
    JPanel panel = new JPanel(new GridBagLayout());
    JPanel buttons = new JPanel();
    GridBagConstraints cs = new GridBagConstraints();
    Random random = new Random();
    int number1, number2;
    JLabel lQuestion1, lQuestion2;
    JPasswordField pfAnswer1 = new JPasswordField(41);
    JPasswordField pfAnswer2 = new JPasswordField(41);
    JButton bSubmit = new JButton("Submit");
    JButton bCancel = new JButton("Cancel");
    
    ChallengeQuestionPromptDialog(User u){
        cs.fill = GridBagConstraints.HORIZONTAL;
        getRandomIndexes();
        lQuestion1 = new JLabel(u.getChallengeQuestion(number1) + " ");
        lQuestion2 = new JLabel(u.getChallengeQuestion(number2) + " ");
        setTitle("Reset Password");
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lQuestion1, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(pfAnswer1, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lQuestion2, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(pfAnswer2, cs);
        //--------------------------------------------------------------------
        buttons.add(bSubmit);
        
        //Action upon clicking on the Submit button
        bSubmit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(pfAnswer1.getText().toLowerCase().equals(u.getChallengeAnswer(number1)) &&
                        pfAnswer2.getText().toLowerCase().equals(u.getChallengeAnswer(number2))){
                    setVisible(false);
                    ResetPasswordDialog rpDialog = new ResetPasswordDialog(u);
                    rpDialog.setVisible(true);
                    dispose();
                }
                else{
                    setVisible(false);
                    JOptionPane.showMessageDialog(null, "One or more answers are incorrect!",
                            "Reset Password", JOptionPane.ERROR_MESSAGE);
                    setVisible(true);
                    pfAnswer1.setText("");
                    pfAnswer2.setText("");
                    pfAnswer1.requestFocus();
                }
            }
        });
        
        buttons.add(bCancel);
        
        //Action upon clicking on the Cancel button
        bCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
		setVisible(false);
		LoginDialog login = new LoginDialog();
		login.setVisible(true);
		dispose();
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
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    //This private function initializes number1 and number2 variables to a random
    //integer between the range of 0 to 3 for array use.
    private void getRandomIndexes(){
        number1 = random.nextInt(4);
        number2 = random.nextInt(4);
	//If number1 and number2 are the same number, get another random integer for
	//number2 between the range of 0 to 3.
        while(number1 == number2)
            number2 = random.nextInt(4);
    }
}

class ResetPasswordDialog extends JDialog{
    JPanel panel = new JPanel(new GridBagLayout());
    JPanel buttons = new JPanel();
    GridBagConstraints cs = new GridBagConstraints();
    JLabel lPassword = new JLabel("Password: ");
    JLabel lConfirmPassword = new JLabel("Confirm: ");
    JPasswordField pfPassword = new JPasswordField(15);
    JPasswordField pfConfirmPassword = new JPasswordField(15);
    JButton bSubmit = new JButton("Submit");
    JButton bCancel = new JButton("Cancel");
    
    ResetPasswordDialog(User u){
        cs.fill = GridBagConstraints.HORIZONTAL;
        setTitle("Reset Password");
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lPassword, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(pfPassword, cs);
        //--------------------------------------------------------------------
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lConfirmPassword, cs);
        //--------------------------------------------------------------------
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(pfConfirmPassword, cs);
        //--------------------------------------------------------------------
        buttons.add(bSubmit);
        
        //Action upon clicking on the Submit button
        bSubmit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(validateInput()){
                    u.setPassword(pfConfirmPassword.getText());
                    setVisible(false);
                    LoginDialog login = new LoginDialog();
                    login.setVisible(true);
                    dispose();
                }
            }
        });
        
        buttons.add(bCancel);
        
        //Action upon clicking on the Cancel button
        bCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
		setVisible(false);
		LoginDialog login = new LoginDialog();
		login.setVisible(true);
		dispose();
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
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    boolean validateInput(){
        if(pfPassword.getText().length() < 5 || pfConfirmPassword.getText().length() < 5){
            setVisible(false);
            JOptionPane.showMessageDialog(null, "Passwords must be at least 5 characters in length!",
            	"Reset Password", JOptionPane.ERROR_MESSAGE);
            setVisible(true);
            pfPassword.setText("");
            pfConfirmPassword.setText("");
            pfPassword.requestFocus();
            return false;
        }
        
        if(!pfPassword.getText().equals(pfConfirmPassword.getText())){
            setVisible(false);
            JOptionPane.showMessageDialog(null, "Passwords do not match!",
            	"Reset Password", JOptionPane.ERROR_MESSAGE);
            setVisible(true);
            pfPassword.setText("");
            pfConfirmPassword.setText("");
            pfPassword.requestFocus();
            return false;
        }
        return true;
    }
}