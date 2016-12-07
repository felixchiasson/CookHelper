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
import java.io.PrintWriter;

import static tophaters.cookhelper.CookHelper.getCookHelper;
import static tophaters.cookhelper.R.layout.item_ingredient_view;

public class Ingredient_List extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<Ingredient> adapter;



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



    }}

    public void onResume() {
        super.onResume();

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


                                    Ingredient ingredient =CookHelper.getCookHelper().getIngredients().get(position);
                                    boolean isNotInRecipe = CookHelper.getCookHelper().removeIngredient(ingredient);
                                    if(!isNotInRecipe){
                                        String message =" You can not remove this ingredient because it is currently used in a recipe";

                                        Toast.makeText( Ingredient_List.this, message , Toast.LENGTH_LONG).show();
                                    }

                                    adapter.notifyDataSetChanged();

                                }

                            }
                        });
        list.setOnTouchListener(touchListener);
    }


    private void populateListView() {

        adapter= new MyListAdapter();
        list = (ListView) findViewById(R.id.ingredientsListView);
        list.setAdapter(adapter);

    }


    private class  MyListAdapter extends ArrayAdapter<Ingredient> {

        public MyListAdapter() {
            super(Ingredient_List.this, item_ingredient_view, getCookHelper().getIngredients());
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(item_ingredient_view, parent, false);
            }

            //find the ingredient

            Ingredient currentIngredient = getCookHelper().getIngredients().get(position);


            // Make name Text
            TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            nameText.setText(currentIngredient.getName());
            return itemView;


        }

    }




}


