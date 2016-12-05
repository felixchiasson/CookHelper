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
    private ArrayList<Recipe> recherche;



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

                try{
                    readIngredients(ingredients);
                    recherche = CookHelper.getCookHelper().search(category, origin, searchIngredients, searchBools);
                }catch (IOException e){
                    Toast.makeText( content_search_activity.this, "String was not valid for search. Refer to help page for details." , Toast.LENGTH_LONG).show();
                    return;

                }
                populateListView();
                registerClickCallBack();





            }});}


    //methode ajouter pour clicker sur les items sune liste
    private void registerClickCallBack(){

        ListView list = (ListView) findViewById(R.id.select_ListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View viewClick, int position, long id){
                Recipe clickedRecipe = CookHelper.getCookHelper().getRecipes().get(position);
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



    public Boolean readIngredients(String received) throws IOException{
        String[] splitString = received.trim().split(" ");
        ArrayList<String> ingredients;

        if(splitString.length%2!=0){
            throw new IOException("String not convertible to search");
        }

        for(int i = 0 ; i<splitString.length;i++){
            splitString[i]=splitString[i].toLowerCase();
        }
        searchBools= new ArrayList<String>();
        ingredients= new ArrayList<String>();
        for(int j=0;j<splitString.length/2;j++){
            if(splitString[2*j].toUpperCase().equals("AND") ||
                    splitString[2*j].toUpperCase().equals("NOT") ||
                    splitString[2*j].toUpperCase().equals("OR")){
                searchBools.add(j,splitString[2*j].toUpperCase());
            }else{
                return false;
            }
            ingredients.add(j,splitString[2*j+1]);
        }
        searchIngredients=new ArrayList<Ingredient>();
        for(int h=0;h<ingredients.size();h++){
            if(CookHelper.getCookHelper().findIngredient(ingredients.get(h))!=null){
                searchIngredients.add(CookHelper.getCookHelper().findIngredient(ingredients.get(h)));
            }else{
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


            // Make name Text
            TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            nameText.setText(currentRecipe.getName());
            return itemView;


        }}



}





