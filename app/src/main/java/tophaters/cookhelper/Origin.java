package tophaters.cookhelper;

import java.util.Comparator;

/**
 * Created by shanelgauthier on 16-11-27.
 */

public class Origin {

    private String name;

    public Origin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Comparator<Origin> COMPARE_BY_ORIGIN = new Comparator<Origin>() {
        public int compare(Origin one, Origin other) {
            return one.getName().compareTo(other.getName());
        }
    };}

