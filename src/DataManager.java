/*
Author: Lincoln Powell
Last update: June 3, 2016

Class description:
This class holds static methods used to serialize, deserialize, and
encrypt (using a XOR algorithm) User object data.

This class holds a static List fields for User objects that is essential
for data retrieval and storage of employees.

All methods/fields are static to allow use in LoginDialog.java without the
use of creating a DataManager object.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

public class DataManager{
    static List<User> userList = new ArrayList<>();
    
    //Encrypt and serialize User objects to file.
    static void serialize(){
	try{
            xorEncryptUsers();
	    FileOutputStream fileOut = new FileOutputStream("userdata.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(userList);
            out.close();
            fileOut.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //Deserialize User objects and decrypt the information for use.
    static void deserialize() throws ClassNotFoundException{
	File file = new File("userdata.txt");
	if(file.exists()){
	    try{
                FileInputStream fileIn = new FileInputStream("userdata.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                userList = (List<User>) in.readObject();
                in.close();
                fileIn.close();
		xorEncryptUsers();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
	}
    }

    //XOR encrypt String input and return encrypted string.  Calling the
    //method on encrypted strings decrypts the string into usable data.
    private static String xorEncrypt(String input){
        //Create a key that is the cornerstone of the XOR algorithm.
        //NOTE: If the key is compromised, encryption using this means will
        //fail and all data will be at risk!!!
	String key = "i0$NKm9%b!pI";
	StringBuilder output = new StringBuilder();
	for(int i = 0; i < input.length(); i++)
	    output.append((char) (input.charAt(i) ^ key.charAt(i % key.length())));
	return output.toString();
    }

    //Encrypt/decrypt User object data through the static userList array.
    //This allows for quick encryption/decryption, at a cost of O(n).
    private static void xorEncryptUsers(){
	for(User u : userList){
	    u.setFirstName(xorEncrypt(u.getFirstName()));
            u.setLastName(xorEncrypt(u.getLastName()));
            u.setUserID(xorEncrypt(u.getUserID()));
            u.setPassword(xorEncrypt(u.getPassword()));
            u.setAdmin(xorEncrypt(u.getAdmin()));
	}
    }
}