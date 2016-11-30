package tophaters.cookhelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * Created by shanelgauthier on 16-11-29.
 */


//cette classe sera le singleton de l'application
public class CookHelper {
    private static CookHelper cookHelper; // l'instance du Singleton

    // le Singleton va stocker les categories, les recettes, les origns et les ingdients dans des lisres
    private ArrayList<Category> categories;
    private ArrayList<Recipe> recipes;
    private ArrayList<Origin> origins;
    private ArrayList<Ingredient> ingredients;

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
        addCategory(new Category("Main Meal"));

       // l'application va offrir ces 4 origines
        addOrigin(new Origin("French"));
        addOrigin(new Origin("Mexican"));
        addOrigin(new Origin("Canadian"));
        addOrigin(new Origin("Colombian"));

        // l'application va offrir ces 4 ingredients
        addIngredient(new Ingredient("Milk"));
        addIngredient(new Ingredient("Dark Chocolate"));
        addIngredient(new Ingredient("Butter"));
        addIngredient(new Ingredient("Egg"));
        addIngredient(new Ingredient("Vanilla Extract"));
        addIngredient(new Ingredient("Sugar"));
        addIngredient(new Ingredient("Flour"));
        addIngredient(new Ingredient("Sugar"));
        addIngredient(new Ingredient("Whipping cream"));


        // l'application va offrir cette recette

        addRecipe(new Recipe(5, 30, "For the crepes: Put the milk, chocolate and butter in a saucepan and heat to melt. Beat the eggs with the sugar in a bowl. Add the vanilla, then the flour. Beat in the chocolate mixture. Strain into a jug and let sit for 30 minutes. Add more milk or water if necessary to give the consistency of thin cream. " +
                "Fry the crepes in a nonstick pan. If you do not have a nonstick pan use a bit of oil. For the chocolate sauce: Put the chocolate and cream together in a saucepan and heat, gently, until the chocolate has melted and the mixture is very smooth. Thin, as needed with more cream. Serve hot."
                ,"Chocolate Crepes", R.drawable.ic_crepe, origins.get(0) , categories.get(0), (ArrayList<Ingredient>)ingredients.clone()));


    }


    // methode qui permet d'obtenir une seule instance de CookHelper
    public static CookHelper getCookHelper(){
        if(cookHelper == null){
            cookHelper= new CookHelper();
        }
        return cookHelper;

    }

    // methodes pour ajouter en ordre alphabetique dans les differentes listes

    //ajouter des categories
    public boolean addCategory(Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (category == categories.get(i)) {
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        categories.add(category);
        Collections.sort(categories, Category.COMPARE_BY_CATEGORY);

        return true;
    }

    //ajouter des origins
    public boolean addOrigin(Origin origin) {
        for (int i = 0; i < origins.size(); i++) {
            if (origin == origins.get(i)) {
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        origins.add(origin);
        Collections.sort(origins, Origin.COMPARE_BY_ORIGIN);

        return true;
    }

    //ajouter des ingredients
    public boolean addIngredient(Ingredient ingredient) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredient == ingredients.get(i)) {
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        ingredients.add(ingredient);
        Collections.sort(ingredients, Ingredient.COMPARE_BY_INGREDIENT);

        return true;
    }

    //ajouter des recettes
    public boolean addRecipe(Recipe recipe) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipe == recipes.get(i)) {
                return false;
            }
        }

        //ON VEUT METTRE EN ORDRE ALPHABETIC LES CATEGORIES
        recipes.add(recipe);
        Collections.sort(recipes, Recipe.COMPARE_BY_Recipe);

        return true;
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
