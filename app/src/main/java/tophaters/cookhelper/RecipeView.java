package tophaters.cookhelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;

public class RecipeView extends AppCompatActivity {
    private String name;
    private String prepTime;
    private String cookTime;
    private String origin;
    private String category;
    private String description;
    private String iconId;
    private String ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // pass the recipe object
        final Intent intent = getIntent();

        prepTime = intent.getStringExtra("prepTime");
        name = intent.getStringExtra("name");
        cookTime =intent.getStringExtra("cookTime");
        category = intent.getStringExtra("category");
        origin = intent.getStringExtra("origin");
        description = intent.getStringExtra("description");
        iconId = intent.getStringExtra("picture");
        ingredients = intent.getStringExtra("ingredients");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            //Recipe clickedRecipe = CookHelper.getCookHelper().getRecipes().get(position);
            /*    Recipe clickedRecipe = null;
                boolean found = false;
                ArrayList<Recipe> recipeList = CookHelper.getCookHelper().getRecipes();
                for (int i = 0; i < CookHelper.getCookHelper().getRecipes().size(); i++) {
                    Toast.makeText(RecipeView.this, "Y = " + i, Toast.LENGTH_LONG).show();

                    if (name.toLowerCase().equals(recipeList.get(i).getName().toLowerCase())) {
                        clickedRecipe = recipeList.get(i);
                        found = true;
                        Toast.makeText(RecipeView.this, "Found = True", Toast.LENGTH_LONG).show();

                        break;
                    }
                }*/

            Intent i = new Intent(RecipeView.this, edit_recipe.class);
            i.putExtra("prepTime", prepTime);
            i.putExtra("name", name);
            i.putExtra("cookTime", cookTime);
            i.putExtra("category", category);
            i.putExtra("origin", origin);
            i.putExtra("description", description);
            i.putExtra("picture", iconId);

            /*ArrayList<Ingredient> ingredientList = clickedRecipe.getIngredients();
            String ingredients = null ;
            for (int y =0; y<ingredientList.size(); y++) {
                if (y == 0) {
                    ingredients = ingredientList.get(y).getName();
                } else {
                    ingredients = ingredients + "\n" + ingredientList.get(y).getName();
                }
            }*/
            i.putExtra("ingredients", ingredients);

            //  start the activity
            startActivity(i);

                    }
        });
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        // This is how to change TextView dynamically
        TextView preparation = (TextView)findViewById(R.id.recipe_view_value_preptime);
        preparation.setText(prepTime + " minutes");

        //change the name of each recipe
        TextView recipeName = (TextView)findViewById(R.id.recipe_view_title);
        recipeName.setText(name);

        //change the COOKTIME of each recipe
        TextView cook = (TextView)findViewById(R.id.recipe_view_value_cooktime);
        cook.setText(cookTime + " minutes");


        //change the origin of each recipe
        TextView type = (TextView)findViewById(R.id.recipe_view_value_origin);
        type.setText(origin);


        //change the category of each recipe
        TextView categorie = (TextView)findViewById(R.id.recipe_view_value_category);
        categorie.setText(category);

        //change the description of each recipe
        TextView text = (TextView)findViewById(R.id.textView10);
        text.setText(description);

        //change the picture of each recipe
        ImageView img= (ImageView) findViewById(R.id.defaultRecipeImage);
        img.setImageURI(Uri.parse(iconId));

        TextView ingredientList = (TextView) findViewById(R.id.recipe_value_text_ingredients);
        ingredientList.setText(ingredients);

        // ------------------------------------------

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(name);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }


    @Override
    public void onStop(){
        super.onStop();
        try{
            byte[]  bytes = Serializer.serialize(CookHelper.getCookHelper());
            PrintWriter writer = new PrintWriter("DATA.txt");
            writer.print(bytes);
        }
        catch(java.io.IOException e){}

    }

}
