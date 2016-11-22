package tophaters.cookhelper;

/**
 * Created by shanelgauthier on 16-11-20.
 */

public class Recipe {
    private String name;
    private int iconId;

    public Recipe(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }


    public int getIconId() {
        return iconId;
    }


}