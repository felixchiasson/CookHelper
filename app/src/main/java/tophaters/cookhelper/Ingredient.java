package tophaters.cookhelper;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by shanelgauthier on 16-11-27.
 */

public class Ingredient  implements java.io.Serializable, Parcelable {
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }


    // Parcelable part

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(name);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel pc) {
            return new Ingredient(pc);
        }
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient(Parcel pc) {
        name = pc.readString();
    }



    public static Comparator<Ingredient> COMPARE_BY_INGREDIENT = new Comparator<Ingredient>() {
        public int compare(Ingredient one, Ingredient other) {
            return one.getName().compareTo(other.getName());
        }
    };

}


