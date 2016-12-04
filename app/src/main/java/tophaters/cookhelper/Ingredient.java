package tophaters.cookhelper;

import java.util.Comparator;

/**
 * Created by shanelgauthier on 16-11-27.
 */

public class Ingredient {
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


    public static Comparator<Ingredient> COMPARE_BY_INGREDIENT = new Comparator<Ingredient>() {
        public int compare(Ingredient one, Ingredient other) {
            return one.getName().compareTo(other.getName());
        }
    };}


