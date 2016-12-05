package tophaters.cookhelper;

import java.util.Comparator;

/**
 * Created by shanelgauthier on 16-11-27.
 */

public class Category {
    private String name;

    public Category(String name) {
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

    public static Comparator<Category> COMPARE_BY_CATEGORY = new Comparator<Category>() {
        public int compare(Category one, Category other) {
            return one.getName().compareTo(other.getName());
        }
    };}

