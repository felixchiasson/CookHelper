package tophaters.cookhelper;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by shanelgauthier on 16-11-27.
 */

public class Ingredient  implements java.io.Serializable{ //New class Ingredient, implements Serializable to be able to be transfered to other activities
    private String name; //only one variable for the name

    public Ingredient(String name) {
        this.name = name;
    } //Construction

    public String getName() {
        return name;
    } //Returns Name of the object

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static Comparator<Ingredient> COMPARE_BY_INGREDIENT = new Comparator<Ingredient>() {
        public int compare(Ingredient one, Ingredient other) {
            return one.getName().compareTo(other.getName());
        }
    };

}


