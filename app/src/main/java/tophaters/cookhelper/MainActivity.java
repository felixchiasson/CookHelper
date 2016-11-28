package tophaters.cookhelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Adapter;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // variable d'instances
    private List<Recipe> myRecipes = new ArrayList<Recipe>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this, recipe_add_form.class);
                startActivity(a);}
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        populateRecipeList();
        populateListView();
        registerClickCallBack();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    //methode ajouter pour clicker sur les items sune liste
    private void registerClickCallBack(){

        ListView list = (ListView) findViewById(R.id.recipesListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View viewClick, int position, long id){
                Recipe clickedRecipe = myRecipes.get(position);
                String message = "You clicked " + position + " this is car make " + clickedRecipe.getName();
                Toast.makeText( MainActivity.this, message , Toast.LENGTH_LONG).show();

            }
        });

    }



    private void populateRecipeList() {

        myRecipes.add(new Recipe(0,0,"", "Lemon and Dill Crusted Salmon", R.drawable.ic_salmon, null, null, null));
        myRecipes.add(new Recipe(0,0,"","Chocolate Crepes", R.drawable.ic_crepe, null, null, null));
        myRecipes.add(new Recipe(0,0,"","Greek Tossed Pasta Salad", R.drawable.ic_recipe, null, null, null));
        myRecipes.add(new Recipe(0,0,"","Filet Mignon", R.drawable.ic_filet, null, null, null));


    }

    private void populateListView() {

        ArrayAdapter<Recipe> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.recipesListView);
        list.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class MyListAdapter extends ArrayAdapter<Recipe> {

        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, myRecipes);
        }

        @Override
        public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //find the recipe

            Recipe currentRecipe = myRecipes.get(position);

            // fill the view

            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentRecipe.getIconId());

            // Make name Text
            TextView nameText = (TextView) itemView.findViewById(R.id.item_txtName);
            nameText.setText(currentRecipe.getName());
            return itemView;


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_help) {
            Intent a = new Intent(MainActivity.this, activity_help_page.class);
            startActivity(a);
        } else if (id == R.id.nav_search) {

            Intent a = new Intent(MainActivity.this, content_search_activity.class);
            startActivity(a);

        } else if (id == R.id.nav_viewCategories) {
            Intent a = new Intent(MainActivity.this, Category_List.class);
            startActivity(a);
        } else if (id == R.id.nav_viewIngredients) {
            Intent a = new Intent(MainActivity.this, Ingredient_List.class);
            startActivity(a);
        } else if (id == R.id.nav_viewOrigins) {
            Intent a = new Intent(MainActivity.this, Origin_List.class);
            startActivity(a);
        }else if (id == R.id.nav_viewRecipe) {
            Intent a = new Intent(MainActivity.this, RecipeView.class);
            startActivity(a);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickTestRecipe(View v) {

        Intent i;

        i = new Intent(this, recipe_add_form.class);
        startActivity(i);

    }

    public void onClickTestIngredient(View v) {

        Intent i;
        i = new Intent(this, add_Ingredient.class);
        startActivity(i);

    }

    public void onClickTestOrigin(View v) {

        Intent i;
        i = new Intent(this, add_Origin.class);
        startActivity(i);

    }

    public void onClickTestCategory(View v) {

        Intent i;
        i = new Intent(this, add_Category.class);
        startActivity(i);

    }

    public void onClickTestRecipeView(View v) {

        Intent i;
        i = new Intent(this, RecipeView.class);
        startActivity(i);
    }
}
