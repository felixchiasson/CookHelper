package tophaters.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static tophaters.cookhelper.R.layout.item_ingredient_view;

public class content_search_activity extends AppCompatActivity {
    private ArrayAdapter<Recipe> adapter;
    private ListView list;
    private ArrayList<String> searchBools;
    private ArrayList<Ingredient> searchIngredients;
    private ArrayList<String> numbreOr;
    private ArrayList<Recipe> recherche;
    private ArrayList<Integer> recipesOr;
    private Integer numberOfOrs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, CookHelper.getCookHelper().getCategories());

        Spinner categorySpinner;
        categorySpinner = (Spinner) findViewById(R.id.category_search);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<Origin> originAdapter = new ArrayAdapter<Origin>(this, android.R.layout.simple_spinner_item, CookHelper.getCookHelper().getOrigins());

        Spinner originSpinner;
        originSpinner = (Spinner) findViewById(R.id.origin_search);
        originSpinner.setAdapter(originAdapter);




        Button button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // ************** SAVE Search HERE *********************
                EditText search = (EditText) findViewById(R.id.text_ingredients);
                String ingredients = search.getText().toString();


                Spinner categories = (Spinner) findViewById(R.id.category_search);
                Category category = (Category) categories.getSelectedItem();

                Spinner origins = (Spinner) findViewById(R.id.origin_search);
                Origin origin = (Origin) origins.getSelectedItem();
                boolean flag = true;

                try{
                    if(!ingredients.matches("")){
                        flag =readIngredients(ingredients); // Instanciates

                    }else{
                        searchBools=null;
                        searchIngredients=null;
                    }
                    if(!flag){
                        Toast.makeText(content_search_activity.this, "A component of the string was mispelled or did not exist" , Toast.LENGTH_LONG).show();
                    }

                    recherche = CookHelper.getCookHelper().search(category, origin, searchIngredients, searchBools);
                    ArrayList<Ingredient>  orIngredients= getOrIngredients(searchBools, searchIngredients);
                                       if(orIngredients!=null){
                        numberOfOrs=orIngredients.size();
                    }else{
                        numberOfOrs=0;
                    }

                    recipesOr = orInRecipes(recherche,orIngredients );
                    sortSearchResult(recherche, recipesOr);

                }catch (IOException e){
                    Toast.makeText( content_search_activity.this, "String was not valid for search. Refer to help page for details." , Toast.LENGTH_LONG).show();
                    return;

                }
               if (flag){
                   populateListView();
               }
                registerClickCallBack();





            }});}


    //methode ajouter pour clicker sur les items sune liste
    private void registerClickCallBack(){

        ListView list = (ListView) findViewById(R.id.select_ListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View viewClick, int position, long id){
                Recipe clickedRecipe = recherche.get(position);
                Intent i = new Intent(content_search_activity.this, RecipeView.class);
                i.putExtra("prepTime", clickedRecipe.getPreTime()+" minutes");
                i.putExtra("name", clickedRecipe.getName()+"");
                i.putExtra("cookTime", clickedRecipe.getCookTime()+" minutes");
                i.putExtra("category", clickedRecipe.getCategory().getName()+"");
                i.putExtra("origin", clickedRecipe.getOrigin().getName()+"");
                i.putExtra("description", clickedRecipe.getDescription());
                i.putExtra("picture", clickedRecipe.getIconId()+"");



                //  start the activity
                startActivity(i);


            }
        });

    }


    // Parsing method for the boolean expression
    // instanciates class variables searchBools and searchIngredients
    public Boolean readIngredients(String received) throws IOException{
        String[] splitString = received.trim().split(" "); //Trims and splits the received string
        ArrayList<String> ingredients;//Array of the ingredient names (strings)

        if(splitString.length%2!=0){
            throw new IOException("String not convertible to search");
        }

        for(int i = 0 ; i<splitString.length;i++){
            splitString[i]=splitString[i].toLowerCase();
        }
        searchBools= new ArrayList<String>();//Class instance, defines the boolean terms for the latest search
        ingredients= new ArrayList<String>();
        for(int j=0;j<splitString.length/2;j++){
            if(splitString[2*j].toUpperCase().equals("AND") ||
                    splitString[2*j].toUpperCase().equals("NOT") ||
                    splitString[2*j].toUpperCase().equals("OR")){ //If a boolean is not valid, returns false, search is invalid
                searchBools.add(j,splitString[2*j].toUpperCase());
            }else{
                searchBools=null; //Since search is invalid, return false and make both instance variable null
                searchIngredients=null;
                return false;
            }
            ingredients.add(j,splitString[2*j+1]);
        }
        searchIngredients=new ArrayList<Ingredient>();
        for(int h=0;h<ingredients.size();h++){
            if(CookHelper.getCookHelper().findIngredient(ingredients.get(h))!=null){//checks if ingredient exists
                searchIngredients.add(CookHelper.getCookHelper().findIngredient(ingredients.get(h)));
            }else{
                searchBools=null;   //Since search is invalid, return false and make both instance variable null
                searchIngredients=null;
                return false;
            }
        }
        return true;
    }




    private void populateListView() {


        adapter = new content_search_activity.MyListAdapter();
        list = (ListView) findViewById(R.id.select_ListView);

        list.setAdapter(adapter);

    }

    private class  MyListAdapter extends ArrayAdapter<Recipe> {
                public MyListAdapter() {
            super(content_search_activity.this, item_ingredient_view, recherche);
       }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(item_ingredient_view, parent, false);
            }

            //find the ingredient

            Recipe currentRecipe = recherche.get(position);
            Integer currentString = recipesOr.get(position);


            // Make name Text
            TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            nameText.setText(currentRecipe.getName());

            // Make name Text
            TextView name = (TextView) itemView.findViewById(R.id.txt_OrNumber);
            name.setText(currentString+"/"+ numberOfOrs);
            return itemView;


        }
    }

    //Filters the ingredient list to the list of optional ingridients (OR)
    public ArrayList<Ingredient> getOrIngredients(ArrayList<String> bools, ArrayList<Ingredient> ings){
        ArrayList<Ingredient> orIngredients= new ArrayList<>();
        if(bools==null || ings ==null){ // if either arrays are null, no ingredients was given, so return null
            return null;
        }
        if(bools.size()>0) { //Skips for loop if no bools
            for (int i = 0; i < bools.size(); i++) {//Adds all ingredient preceded by or in the array
                if (bools.get(i).toUpperCase().equals("OR")) {
                    orIngredients.add(ings.get(i));
                }
            }
            return orIngredients;
        }else{
            return null;
        }
    }

    // Creates corresponding array of integer matching the amount of ingredients
    // preceded by OR in each recipe.
    public ArrayList<Integer> orInRecipes(ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients){
        ArrayList<Integer> matchingOrs = new ArrayList<>(recipes.size());
        int counter;
        if(ingredients!=null){ //Does not count if there is no ingredients
            for(int i=0;i<recipes.size();i++){
                counter=0;
                for(int j=0;j<ingredients.size();j++){//
                    if(recipes.get(i).hasIngredient(ingredients.get(j))){
                        counter++;
                    }
                }
                matchingOrs.add(counter);
            }
        }else{
            for(int i=0;i<recipes.size();i++){
                matchingOrs.add(0);
            }
        }

        return matchingOrs;
    }

    public void sortSearchResult(ArrayList<Recipe> recipes, ArrayList<Integer> orsOfRecipes){
        ArrayList<Recipe> newRecipes= new ArrayList<>(recipes.size());
        ArrayList<Integer> newOrsOfRecipes = new ArrayList<>(orsOfRecipes.size());
        int counter;

        if(orsOfRecipes.size()<=0){
            return;
        }else{
            counter = numberOfOrs;
        }

        while(counter>=0){
            for(int i=0;i<orsOfRecipes.size();i++){
                if(orsOfRecipes.get(i)==counter){
                    newRecipes.add(recipes.get(i));
                    newOrsOfRecipes.add(orsOfRecipes.get(i));
                }
            }
            counter--;
        }
        recherche=newRecipes;
        recipesOr=newOrsOfRecipes;
    }


}





