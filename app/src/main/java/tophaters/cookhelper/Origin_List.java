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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Origin_List extends AppCompatActivity {
    private List<Origin> myOrigin= new ArrayList<Origin>();
    private ArrayAdapter<Origin> adapter;
    ListView list;

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


            populateListView();
            //registerClickCallBack();
            SwipeDismissListViewTouchListener touchListener =
                    new SwipeDismissListViewTouchListener(
                            list,
                            new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {


                                        Origin origin =CookHelper.getCookHelper().getOrigins().get(position);
                                        boolean isNotInRecipe = CookHelper.getCookHelper().removeOrigin(origin);
                                        if(!isNotInRecipe){
                                            String message =" You can not remove this origin because it is currently used in a recipe";

                                            Toast.makeText(Origin_List.this, message , Toast.LENGTH_LONG).show();
                                        }

                                        adapter.notifyDataSetChanged();

                                    }

                                }
                            });
            list.setOnTouchListener(touchListener);


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




    private void populateListView() {

        adapter = new Origin_List.MyListAdapter();
        list = (ListView) findViewById(R.id.originsListView);
        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Origin> {

        public MyListAdapter() {
            super(Origin_List.this, R.layout.item_ingredient_view, CookHelper.getCookHelper().getOrigins());
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_ingredient_view, parent, false);
            }

            //find the ingredient

            Origin currentOrigin = CookHelper.getCookHelper().getOrigins().get(position);


            // Make name Text
            TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            nameText.setText(currentOrigin.getName());
            return itemView;


        }

    }


}

