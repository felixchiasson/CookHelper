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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Category_List extends AppCompatActivity {

    private List<Category> myCategories= new ArrayList<Category>();
    private ListView list;
    private ArrayAdapter<Category> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Category_List.this, add_Category.class);
                startActivity(a);


            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            }


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

    }

    // Always refresh activity when going back to it after (for example) adding an item
    // onResume is called after onCreate, thus allowing populateListView to be called here instead of calling it in onCreate.

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


                                    Category category = CookHelper.getCookHelper().getCategories().get(position);
                                    try{boolean isNotInRecipe = CookHelper.getCookHelper().removeCategory(category);
                                    if (!isNotInRecipe) {
                                        String message = " You can not remove this category because it is currently used in a recipe";

                                        Toast.makeText(Category_List.this, message, Toast.LENGTH_LONG).show();
                                    }}
                                    catch (Exception e){}

                                    adapter.notifyDataSetChanged();

                                }

                            }
                        });
        list.setOnTouchListener(touchListener);
    }


    private void populateListView() {

        adapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.categoriesListView);
        list.setAdapter(adapter);

    }


        private class MyListAdapter extends ArrayAdapter<Category> {

            public MyListAdapter() {
                super(Category_List.this, R.layout.item_ingredient_view, CookHelper.getCookHelper().getCategories());
            }

            @Override
            public @NonNull
            View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View itemView = convertView;
                if (itemView == null) {
                    itemView = getLayoutInflater().inflate(R.layout.item_ingredient_view, parent, false);
                }

                //find the ingredient

                Category currentCategory = CookHelper.getCookHelper().getCategories().get(position);


                // Make name Text
                TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
                nameText.setText(currentCategory.getName());
                return itemView;


            }

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