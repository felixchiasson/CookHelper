package tophaters.cookhelper;

import java.util.Comparator;

/**
 * Created by shanelgauthier on 16-11-27.
 */

public class Origin implements java.io.Serializable{ //New class Origin, implements Serializable to be able to be transfered to other activities

    private String name; //Only one

    public Origin(String name) {
        this.name = name;
    } //Construction

    public String getName() {
        return name;
    } //Return name of the object

    public void setName(String name) {
        this.name = name;
    } //Set Name for Object

    public String toString() {
        return name;
    } //To String Method

    public static Comparator<Origin> COMPARE_BY_ORIGIN = new Comparator<Origin>() {
        public int compare(Origin one, Origin other) { //Method to compare 2 Origin objects
            return one.getName().compareTo(other.getName());
        }
    };}

