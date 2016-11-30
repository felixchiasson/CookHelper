package tophaters.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Origin_List extends AppCompatActivity {
    private List<Origin> myOrigin= new ArrayList<Origin>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin__list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Origin_List.this, add_Origin.class);
                startActivity(a);


            }
        });
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            populateOriginList();
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



    private void populateOriginList() {
        myOrigin.add(new Origin("French"));
        myOrigin.add(new Origin("Canadian"));
        myOrigin.add(new Origin("Mexican"));
        myOrigin.add(new Origin("Asiatique"));
        myOrigin.add(new Origin("Grecque"));
        myOrigin.add(new Origin("Italian"));
        myOrigin.add(new Origin("Japonaise"));
        myOrigin.add(new Origin("Cubain"));
        myOrigin.add(new Origin("Tunaisien"));
        myOrigin.add(new Origin("Chinois"));




    }

    private void populateListView() {

        ArrayAdapter<Origin> adapter = new Origin_List.MyListAdapter();
        ListView list = (ListView) findViewById(R.id.originsListView);
        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Origin> {

        public MyListAdapter() {
            super(Origin_List.this, R.layout.item_ingredient_view, myOrigin);
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_ingredient_view, parent, false);
            }

            //find the ingredient

            Origin currentOrigin = myOrigin.get(position);


            // Make name Text
            TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            nameText.setText(currentOrigin.getName());
            return itemView;


        }

    }


}

