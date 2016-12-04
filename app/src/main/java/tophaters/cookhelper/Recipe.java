package tophaters.cookhelper;

/**
 * Created by shanelgauthier on 16-11-20.
 */

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Recipe  implements Serializable {
    //instances variables
    private String name;
    private String description;
    private Uri iconId;
    private int iconIdInt;
    private int preTime;   // in minutes
    private int cookTime;   // in minutes
    private Origin origin; // the origin of the recipe
    private Category category;  // the category of the recipe
    private ArrayList<Ingredient> ingredients; // a list with all the ingredients to use in the recipe
    private static final long serialVersionUID = 1L;


    public Recipe(int cookTime, int preTime, String description,  String name, Uri iconId, Origin origin, Category category, ArrayList<Ingredient> ingredients ) {
        this.cookTime = cookTime;
        this.preTime = preTime;
        this.iconId = iconId;
        this.description = description;
        this.name = name;
        this.origin = origin;
        this.category = category;
        this.ingredients=ingredients;
    }

    public Recipe(int cookTime, int preTime, String description,  String name, int iconId, Origin origin, Category category, ArrayList<Ingredient> ingredients ) {
        this.cookTime = cookTime;
        this.preTime = preTime;
        this.iconIdInt = iconId;
        this.description = description;
        this.name = name;
        this.origin = origin;
        this.category = category;
        this.ingredients=ingredients;
    }

    public Boolean hasIngredient(Ingredient ingredient){
        Boolean flag=false;
        for(int i=0 ; i<ingredients.size() ; i++){
            if(ingredients.get(i)==ingredient){
                flag=true;
                break;
            }
        }
        return flag;
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

    public Uri getIconId() {
        return iconId;
    }

    public void setIconId(Uri iconId) {
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

    //methodes permettant d'ajouter des ingredients
    private void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    private boolean removeIngredient(Ingredient ingredient){
         return ingredients.remove(ingredient);

    }

    public static Comparator<Recipe> COMPARE_BY_Recipe = new Comparator<Recipe>() {
        public int compare(Recipe one, Recipe other) {
            return one.getName().compareTo(other.getName());
        }
    };}



