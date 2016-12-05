package tophaters.cookhelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


import java.io.PrintWriter;

public class content_search_activity extends AppCompatActivity {
    private ArrayAdapter<Origin> adapter;
    ListView list;
    private ArrayList<String> searchBools;
    private ArrayList<Ingredient> searchIngredients;

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




populateListView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void populateListView() {

        adapter = new content_search_activity.MyListAdapter();
        list = (ListView) findViewById(R.id.selectIgrendientListView);

        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Origin> {

        public MyListAdapter() {
            super(content_search_activity.this, R.layout.item_view, CookHelper.getCookHelper().getOrigins());
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.select_ingredient_view, parent, false);
            }

            //find the ingredient

           // Origin currentOrigin = CookHelper.getCookHelper().getOrigins().get(position);


            // Make name Text
            //TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            //nameText.setText(currentOrigin.getName());
            return itemView;




        }
    }

    }




    public void readIngredients(String received) throws IOException{
        String[] splitString = received.split(" ");
        ArrayList<String> ingredients;
        if(splitString.length%2!=0){
            throw new IOException("String not convertible to search");
        }
        for(int i = 0 ; i<splitString.length;i++){
            splitString[i]=splitString[i].toLowerCase();
        }
        searchBools= new ArrayList<String>(splitString.length/2);
        ingredients= new ArrayList<String>(splitString.length/2);
        for(int j=0;j<splitString.length/2;j++){
            searchBools.set(j,splitString[2*j]);
            ingredients.set(j,splitString[2*j+1]);
        }
        searchIngredients=new ArrayList<Ingredient>;
        for(int h=0;h<ingredients.size();h++){
            searchIngredients.add(CookHelper.getCookHelper().findIngredient(ingredients.get(h)));
        }
    }
}
