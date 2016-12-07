package tophaters.cookhelper;

import android.content.Context;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Map;


/**
 * Created by shanelgauthier on 16-11-29.
 */


//cette classe sera le singleton de l'application
public class CookHelper implements java.io.Serializable{
    private static CookHelper cookHelper; // l'instance du Singleton
    private byte[] savedData;
    private String filename ="CookHelper";
    private Uri defaultRecipeA = Uri.parse("android.resource://tophaters.cookhelper/drawable/ic_crepe");
    private Uri defaultRecipeB = Uri.parse("android.resource://tophaters.cookhelper/drawable/ic_salmon");

    // le Singleton va stocker les categories, les recettes, les origns et les ingredients dans des listes
    private   ArrayList<Category> categories;
    private   ArrayList<Recipe> recipes;
    private   ArrayList<Origin> origins;
    private   ArrayList<Ingredient> ingredients;

    private CookHelper() {
        //creer les listes
        categories = new ArrayList<Category>();
        recipes = new ArrayList<Recipe>();
        origins = new ArrayList<Origin>();
        ingredients = new ArrayList<Ingredient>();

        //toutes les singletons vont avoir au depart
        // des ingredients, des categories, des recettes et des origins en commun

        // l'application va offrir ces 4 categories
        addCategory(new Category("Dessert"));
        addCategory(new Category("Sauce"));
        addCategory(new Category("Drink"));
        addCategory(new Category("Sea Food"));


       // l'application va offrir ces 4 origines
        addOrigin(new Origin("French"));
        addOrigin(new Origin("Mexican"));
        addOrigin(new Origin("Canadian"));
        addOrigin(new Origin("Colombian"));
        addCategory(new Category("Sea Food"));

        // l'application va offrir ces 9 ingredients
        addIngredient(new Ingredient("Milk"));
        addIngredient(new Ingredient("Dark-Chocolate"));
        addIngredient(new Ingredient("Butter"));
        addIngredient(new Ingredient("Egg"));
        addIngredient(new Ingredient("Vanilla-Extract"));
        addIngredient(new Ingredient("Sugar"));
        addIngredient(new Ingredient("Flour"));
        addIngredient(new Ingredient("Sugar"));
        addIngredient(new Ingredient("Whipping-cream"));

        // l'application va offrir cette recette

        addRecipe(new Recipe(5, 30, "For the crepes: Put the milk, chocolate and butter in a saucepan and heat to melt. Beat the eggs with the sugar in a bowl. Add the vanilla, then the flour. Beat in the chocolate mixture. Strain into a jug and let sit for 30 minutes. Add more milk or water if necessary to give the consistency of thin cream. " +
                 "Fry the crepes in a nonstick pan. If you do not have a nonstick pan use a bit of oil. For the chocolate sauce: Put the chocolate and cream together in a saucepan and heat, gently, until the chocolate has melted and the mixture is very smooth. Thin, as needed with more cream. Serve hot."
                ,"Chocolate Crepes", defaultRecipeA, origins.get(0) , categories.get(0), (ArrayList<Ingredient>)ingredients.clone()));

        //Voici le array d'Ingredients pour le saumon
        Ingredient a1= new Ingredient("Salmon-Filet");
        Ingredient a2 =new Ingredient("Lemon-Pepper");
        Ingredient a3 = new Ingredient("Garlic-Powder");
        Ingredient a4 = new Ingredient("Salt");
        Ingredient a5 =new Ingredient("Soy-Sauce");
        Ingredient a6 =new Ingredient("Brown-Sugar");
        Ingredient a7 = new Ingredient("Water");
        Ingredient a8 =new Ingredient("Vegetable-Oil");

        ArrayList<Ingredient> salmon_ingredient= new ArrayList<Ingredient>();

        salmon_ingredient.add(a1); addIngredient(a1);
        salmon_ingredient.add(a2); addIngredient(a2);
        salmon_ingredient.add(a3); addIngredient(a3);
        salmon_ingredient.add(a4); addIngredient(a4);
        salmon_ingredient.add(a5); addIngredient(a5);
        salmon_ingredient.add(a6); addIngredient(a6);
        salmon_ingredient.add(a7); addIngredient(a7);
        salmon_ingredient.add(a8); addIngredient(a8);



        addRecipe(new Recipe(15, 16, "Season salmon fillets with lemon pepper, garlic powder, and salt.\n" +
                "In a small bowl, stir together soy sauce, brown sugar, water, and vegetable oil until sugar is dissolved.\n Place fish in a large resealable plastic bag with the soy sauce mixture, " +
                "seal, and turn to coat. Refrigerate for at least 2 hours.\n" +
                "Preheat grill for medium heat.\n" +
                "Lightly oil grill grate. Place salmon on the preheated grill, and discard marinade. Cook salmon for 6 to 8 minutes per side, or until the fish flakes easily with a fork.", "Grilled Salmon", defaultRecipeB, origins.get(0) , categories.get(3), salmon_ingredient));
        addIngredient(new Ingredient("Tomato"));


    }


    // methode qui permet d'obtenir une seule instance de CookHelper
    public static CookHelper getCookHelper(){
        if(cookHelper == null){
            cookHelper = new CookHelper();
        }
        return cookHelper;

    }

    // methodes pour ajouter en ordre alphabetique dans les differentes listes

    //ajouter des categories
    public  boolean addCategory(Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (category.getName().toLowerCase().equals(categories.get(i).getName().toLowerCase())) {
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        categories.add(category);
        Collections.sort(categories, Category.COMPARE_BY_CATEGORY);

        return true;
    }

    //ajouter des origins
    public  boolean addOrigin(Origin origin) {
        for (int i = 0; i < origins.size(); i++) {
            if (origin.getName().toLowerCase().equals(origins.get(i).getName().toLowerCase())) {
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        origins.add(origin);
        Collections.sort(origins, Origin.COMPARE_BY_ORIGIN);

        return true;
    }

    //ajouter des ingredients
    public  boolean addIngredient(Ingredient ingredient) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredient.getName().toLowerCase().equals(ingredients.get(i).getName().toLowerCase())) {
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        ingredients.add(ingredient);
        Collections.sort(ingredients, Ingredient.COMPARE_BY_INGREDIENT);

        return true;
    }

    //ajouter des recettes
    public  boolean addRecipe(Recipe recipe) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipe.getName().toLowerCase().equals(recipes.get(i).getName().toLowerCase())){
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        recipes.add(recipe);
        Collections.sort(recipes, Recipe.COMPARE_BY_Recipe);

        return true;
    }

    //Les methode pour supprimer des ingredients, des catégorie et des recettes.

    //supprimer des ingredient
    public  boolean removeIngredient(Ingredient ingredient) {
        // Check that the ingredient to be removed doesnt exist in any recipe
        for(int i = 0; i<recipes.size(); i++ ){
            Recipe recipeCheck = recipes.get(i);
            for (int j = 0; j<recipeCheck.getIngredients().size(); j++){
                Ingredient ingredientCheck = recipeCheck.getIngredients().get(j);
                if(ingredientCheck == ingredient){
                    return false;
                }
            }
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredient == ingredients.get(i)) {
                ingredients.remove(ingredients.get(i));
                return true;
            }
        }
        return false;


    }
    //supprimer des recettes
    public  boolean removeRecipe(Recipe recipe) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipe == recipes.get(i)) {
                recipes.remove(recipes.get(i));
                return true;
            }
        }
        return false;


    }
    //supprimer des origines
    public  boolean removeOrigin(Origin origin) {
        // Check that the Orgin to be removed doesnt exist in any recipe
        for(int i = 0; i<recipes.size(); i++ ){
            Recipe recipeCheck = recipes.get(i);
            if(recipeCheck.getOrigin() == origin){
                return false;
            }
        }
        for (int i = 0; i < origins.size(); i++) {
            if (origin == origins.get(i)) {
                origins.remove(origins.get(i));
                return true;
            }
        }
        return false;


    }

    //supprimer des category

    public  boolean removeCategory(Category category) {
        // Check that the category to be removed doesnt exist in any recipe
        for(int i = 0; i<recipes.size(); i++ ){
            Recipe recipeCheck = recipes.get(i);
            if(recipeCheck.getCategory() == category){
                return false;
            }
        }
        for (int i = 0; i < categories.size(); i++) {
            if (category == categories.get(i)) {
                categories.remove(categories.get(i));
                return true;
            }
        }
        return false;


    }


    //La fonction qui implemente la recherhce de recette
    public ArrayList<Recipe> search(Category category, Origin origin, ArrayList<Ingredient> ingredients, ArrayList<String> bools) {

        ArrayList<Recipe> recipes = getRecipes();

        if(category!=null){
            recipes = filterCategory(category, recipes);
        }
        if(origin!=null){
            recipes = filterOrigin(origin, recipes);
        }
        if(ingredients!=null && bools!=null){
            for (int i = 0; i < ingredients.size(); i++) {
                if(!bools.get(i).equals("OR")){
                    recipes = filterIngredient(bools.get(i), ingredients.get(i), recipes);
                }
            }
        }

        return recipes;
    }

    private ArrayList<Recipe> filterCategory(Category category, ArrayList<Recipe> recipes){
        ArrayList<Recipe> newRecipes= new ArrayList<Recipe>();
        for(int i=0 ; i<recipes.size();i++){
            if(recipes.get(i).getCategory()== category){
                newRecipes.add(recipes.get(i));
            }
        }
        return newRecipes;
    }

    private ArrayList<Recipe> filterOrigin(Origin origin, ArrayList<Recipe> recipes){
        ArrayList<Recipe> newRecipes= new ArrayList<Recipe>();
        for(int i=0 ; i<recipes.size();i++){
            if(recipes.get(i).getOrigin()== origin){
                newRecipes.add(recipes.get(i));
            }
        }
        return newRecipes;
    }

    private ArrayList<Recipe> filterIngredient(String bool, Ingredient ingredient, ArrayList<Recipe> recipes){
        ArrayList<Recipe> newRecipes= new ArrayList<Recipe>();
        Boolean flag;

        for(int i=0 ; i<recipes.size() ; i++){

            flag=recipes.get(i).hasIngredient(ingredient);


            if(bool.equals("AND") && flag==true){
                newRecipes.add(recipes.get(i));
            }else if(bool.equals("NOT") && flag==false){
                newRecipes.add(recipes.get(i));
            }
        }
        return newRecipes;
    }

    public Ingredient findIngredient(String ing){
        for(int i=0; i<ingredients.size();i++){
            if(ing.toLowerCase().equals(ingredients.get(i).getName().toLowerCase())){
                return ingredients.get(i);
            }
        }
        return null;
    }

    // methodes d'Instances pour les variables d'instances
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Origin> getOrigins() {
        return origins;
    }

    public void setOrigins(ArrayList<Origin> origins) {
        this.origins = origins;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
