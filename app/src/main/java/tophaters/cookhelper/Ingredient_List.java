package tophaters.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Ingredient_List extends AppCompatActivity {
    private List<Ingredient> myIngredients = new ArrayList<Ingredient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient__list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Ingredient_List.this, add_Ingredient.class);
                startActivity(a);


            }
        });
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            populateIngredientList();
            populateListView();
            //registerClickCallBack();
    }}

    //methode ajouter pour clicker sur les items sune liste
//    private void registerClickCallBack(){
//
//        ListView list = (ListView) findViewById(R.id.ingredientsListView);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View viewClick, int position, long id){
//                Ingredient clickedIngredient = myIngredients.get(position);
//                String message = "You clicked " + position + " this is car make " + clickedIngredient.getName();
//                Toast.makeText( Ingredient_List.this, message , Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//    }



    private void populateIngredientList() {
        myIngredients.add(new Ingredient("Tomate"));
        myIngredients.add(new Ingredient("Sel"));
        myIngredients.add(new Ingredient("Poivron"));
        myIngredients.add(new Ingredient("Poivre"));
        myIngredients.add(new Ingredient("Fraise"));
        myIngredients.add(new Ingredient("Jambon"));
        myIngredients.add(new Ingredient("Gingembre"));
        myIngredients.add(new Ingredient("Chocolat"));
        myIngredients.add(new Ingredient("Pain blanc"));


    }

    private void populateListView() {

        ArrayAdapter<Ingredient> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.ingredientsListView);
        list.setAdapter(adapter);

    }


    private class MyListAdapter extends ArrayAdapter<Ingredient> {

        public MyListAdapter() {
            super(Ingredient_List.this, R.layout.item_ingredient_view, myIngredients);
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_ingredient_view, parent, false);
            }

            //find the ingredient

            Ingredient currentIngredient = myIngredients.get(position);


            // Make name Text
            TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            nameText.setText(currentIngredient.getName());
            return itemView;


        }

    }



}


