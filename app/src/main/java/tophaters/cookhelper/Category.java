package tophaters.cookhelper;

import java.util.Comparator;

/**
 * Created by shanelgauthier on 16-11-27.
 */

public class Category implements java.io.Serializable{ //New class Category, implements Serializable to be able to be transfered to other activities
    private String name; //Only instance variable is a String

    public Category(String name) {
        this.name = name;
    } //Construction to set name

    public String getName() {
        return name;
    } //return names

    public void setName(String name) {
        this.name = name;
    } //set Method to change the name

    public String toString() {
        return name;
    } //to String Method

    public static Comparator<Category> COMPARE_BY_CATEGORY = new Comparator<Category>() {
        public int compare(Category one, Category other) { //Method to compare two Category objects.
            return one.getName().compareTo(other.getName());
        }
    };}

