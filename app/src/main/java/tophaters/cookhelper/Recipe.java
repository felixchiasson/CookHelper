package tophaters.cookhelper;

/**
 * Created by shanelgauthier on 16-11-20.
 */
import java.util.ArrayList;
public class Recipe {
    //instances variables
    private String name;
    private String description;
    private int iconId;
    private int preTime;   // in minutes
    private int cookTime;   // in minutes
    private Origin origin; // the origin of the recipe
    private Category category;  // the category of the recipe
    private ArrayList<Ingredient> ingredients; // a list with all the ingredients to use in the recipe


    public Recipe(int cookTime, int preTime, String description,  String name, int iconId, Origin origin, Category category, ArrayList<Ingredient> ingredients ) {
        this.cookTime = cookTime;
        this.preTime = preTime;
        this.iconId = iconId;
        this.description = description;
        this.name = name;
        this.origin = origin;
        this.category = category;
        this.ingredients=ingredients;
    }

    //list of the getters ans setters


    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreTime() {
        return preTime;
    }

    public void setPreTime(int preTime) {
        this.preTime = preTime;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    //methodes a implemeneter

    private void addIngredient(Ingredient ingredient){
        System.out.println("METHODE A IMPLEMENTER");
    }

    private void removeIngredient(Ingredient ingredient){
        System.out.println("METHODE A IMPLEMENTER");
    }


}