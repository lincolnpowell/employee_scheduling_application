/*
Author: Kyle Miller
Last update: June 26, 2016

Class description:
This class will contain data associated with an employee's address.
*/

import java.io.Serializable;

public class AddressInfo implements Serializable{
    //address fields
    private final int ZIP_ID;
    private int zip;
    private String address1;
    private String address2 = "";
    private String city;
    private String state;
    private int houseNumber;
    //empty const
    public AddressInfo(){
        ZIP_ID = 0;
    }
    //constructor for a blank residence
    public AddressInfo(int ZIP_ID){
        this.ZIP_ID = ZIP_ID;
        this.houseNumber = 0;
        this.address1 = "";
        this.city = "";
        this.state = "";
        this.zip = 0;
    }
    //constructor for a house
    public AddressInfo(int ZIP_ID,  int houseNumber, String address1,  String city, String state,  int zip){
        this.ZIP_ID = ZIP_ID;
        this.houseNumber = houseNumber;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    //constructor for apartments / condos
    public AddressInfo(int ZIP_ID,  int houseNumber, String address1, String address2, String city, String state,  int zip){
        this.ZIP_ID = ZIP_ID;
        this.houseNumber = houseNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    //field setters 
    public void setZip(int zip){this.zip = zip;}
    public void setHouseNumber(int houseNumber){this.houseNumber = houseNumber;}
    public void setAddress1(String address1){this.address1 = address1;}
    public void setAddress2(String address2){this.address2 = address2;}
    public void setCity(String city){this.city = city;}
    public void setState(String state){this.state = state;}
    //field getters
    public int getZip(){return zip;}
    public int getHouseNumber(){return houseNumber;}
    public String getAddress1(){return address1;}
    public String getAddress2(){return address2;}
    public String getCity(){return city;}
    public String getState(){return state;}
    //toString override for serialization
    public String toString(){
        String out = houseNumber + " " + address1;
        if(!address2.equals("")) out = out + "\n" + address2;
        out = out + "\n" + city + ", " + state + " " + zip;
        return out;
    }
}