package tophaters.cookhelper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;

public class edit_recipe extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private static final int SUCCESS_VALUE = 2;
    ListView ingredientList;
    private Uri selectedImageUri;
    ArrayList mSelected;
    private ArrayList<Ingredient> in_list;
    private ArrayAdapter<Ingredient> ingredientAdapter;

    private String name;
    private String prepTime;
    private String cookTime;
    private String origin;
    private String category;
    private String description;
    private String iconId;
    private String ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        Intent intent = getIntent(); //Get the information from the previous activity

        prepTime = intent.getStringExtra("prepTime");
        name = intent.getStringExtra("name");
        cookTime = intent.getStringExtra("cookTime");
        category = intent.getStringExtra("category");
        origin = intent.getStringExtra("origin");
        description = intent.getStringExtra("description");
        iconId = intent.getStringExtra("picture");
        ingredients = intent.getStringExtra("ingredients");
        selectedImageUri = Uri.parse(iconId);



        // This is how to change EditView dynamically(inspired by RecipeView
        EditText preparation = (EditText)findViewById(R.id.recipe_add_preptime);
        preparation.setText(prepTime, TextView.BufferType.EDITABLE);

        //change the name of each recipe
        EditText recipeName = (EditText)findViewById(R.id.recipe_add_name);
        recipeName.setText(name, TextView.BufferType.EDITABLE);

        //change the COOKTIME of each recipe
        EditText cook = (EditText)findViewById(R.id.recipe_add_cooktime);
        cook.setText(cookTime, TextView.BufferType.EDITABLE);


        //change the origin of each recipe

        ArrayAdapter<Origin> originAdapter = new ArrayAdapter<Origin>(this, android.R.layout.simple_spinner_item, CookHelper.getCookHelper().getOrigins());
        ArrayList<Origin> listOrigin = CookHelper.getCookHelper().getOrigins();
        Spinner originSpinner;
        originSpinner = (Spinner) findViewById(R.id.add_recipe_origin_spinner);
        originSpinner.setAdapter(originAdapter);
        originSpinner.setSelection(listOrigin.indexOf(origin));



        //change the category of each recipe

        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, CookHelper.getCookHelper().getCategories());
        ArrayList<Category> listCat = CookHelper.getCookHelper().getCategories();
        Spinner categorySpinner;
        categorySpinner = (Spinner) findViewById(R.id.add_recipe_category_spinner);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setSelection(listCat.indexOf(category));

        //change the description of each recipe
        EditText text = (EditText)findViewById(R.id.add_recipe_edittext_steps);
        text.setText(description, TextView.BufferType.EDITABLE);

        //change the picture of each recipe
        ImageView img= (ImageView) findViewById(R.id.imageView2);
        img.setImageURI(Uri.parse(iconId));

        in_list = verifyIngredients();


        ingredientAdapter = new ArrayAdapter<Ingredient>(this, android.R.layout.simple_list_item_multiple_choice, in_list);

        ingredientList = (ListView) findViewById(R.id.ingredientList);
        ingredientList.setAdapter(ingredientAdapter);
        setListViewHeightBasedOnChildren(ingredientList);



        // DISABLE TOUCH EVENTS WHEN SCROLLING THE LISTVIEW
        ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if(in_list.isEmpty()){

                }else{



                    in_list.remove(in_list.get(position));
                    //ingredientAdapter.remove(in_list.get(position));


                    //ingredientList.setAdapter(ingredientAdapter);
                    ingredientAdapter.notifyDataSetChanged();
                    ListView lv = (ListView) findViewById(R.id.ingredientList);
                    setListViewHeightBasedOnChildren(lv);}


            }
        });


        // DISABLE TOUCH EVENTS WHEN SCROLLING THE LISTVIEW

        ingredientList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        // ****************************************************


    }

    public void onClickOpenGallery(View v) { //Open the gallery to take an image

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                ImageView imagetest = (ImageView) findViewById(R.id.imageView2);
                imagetest.setImageURI(selectedImageUri);
            } else if (requestCode == SUCCESS_VALUE) {
                ArrayList<Ingredient> listOfIngredients = new ArrayList<Ingredient>();
                ArrayAdapter<Ingredient> ingredientsAdapter = new ArrayAdapter<Ingredient>(this, android.R.layout.simple_list_item_multiple_choice, listOfIngredients);
                Bundle b = getIntent().getParcelableExtra("test");
                listOfIngredients.add((Ingredient)data.getParcelableExtra("test"));
                ListView inList = (ListView) findViewById(R.id.ingredientList);
                inList.setAdapter(ingredientsAdapter);


            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

    public void onClickOpenIngredientDialog(View v) { //OPen the list to select an ingredient
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        mSelected = new ArrayList();
        final ArrayList<Ingredient> list = (ArrayList<Ingredient>)CookHelper.getCookHelper().getIngredients();
        ArrayList<String> stringIngredients = new ArrayList<>();

        Ingredient item;

        for(int i=0;i<list.size();i++)
        {
            item = list.get(i);
            stringIngredients.add(item.getName());

        }
        final CharSequence[] fol_list = stringIngredients.toArray(new CharSequence[stringIngredients.size()]);




        builder.setTitle("Select Ingredient")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(fol_list, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelected.add(which);
                                } else if (mSelected.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelected.remove(Integer.valueOf(which));
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                        for (int i = 0; i < mSelected.size(); i++){
                            in_list.add(list.get((int)mSelected.get(i)));
                        }

                        ArrayAdapter<Ingredient> dialogAdapter = new ArrayAdapter<Ingredient>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, in_list);

                        ListView ingredientList = (ListView) findViewById(R.id.ingredientList);
                        ingredientList.setAdapter(dialogAdapter);
                        setListViewHeightBasedOnChildren(ingredientList);



                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }


    // ************** SAVE RECIPE *********************

    public void onClickSaveRecipe(View v) { //Click to save the recipe
        boolean added = false;
        Recipe recipe = null;
        boolean found = false;
        if (true) { //changed
            ArrayList<Recipe> recipeList = CookHelper.getCookHelper().getRecipes(); //get the recipes
            for (int i = 0; i < CookHelper.getCookHelper().getRecipes().size(); i++) {//Loop in to find the actual recipe to be modified
                if (name.toLowerCase().equals(recipeList.get(i).getName().toLowerCase())) { //Compare the names of the recipes
                    recipe = recipeList.get(i); //If the recipe is found, take reference of the recipe
                    found = true;
                    break;//stop the loop
                }
            }
            if (found) {

                //Take all the information in the text boxes
                EditText recipeName = (EditText) findViewById(R.id.recipe_add_name);
                String sRecipeName = recipeName.getText().toString();


                EditText prepTime = (EditText) findViewById(R.id.recipe_add_preptime);
                String sPrepTime = prepTime.getText().toString();
                int iPrepTime;
                if (sPrepTime.isEmpty()) {
                    iPrepTime = 0;
                } else {
                    iPrepTime = Integer.parseInt(sPrepTime);
                }

                EditText cookTime = (EditText) findViewById(R.id.recipe_add_cooktime);
                String sCookTime = cookTime.getText().toString();
                int iCookTime;
                if (sCookTime.isEmpty()) {
                    iCookTime = 0;
                } else {
                    iCookTime = Integer.parseInt(sCookTime);
                }


                EditText description = (EditText) findViewById(R.id.add_recipe_edittext_steps);
                String steps = description.getText().toString();

                Spinner categories = (Spinner) findViewById(R.id.add_recipe_category_spinner);
                Category category = (Category) categories.getSelectedItem();

                Spinner origins = (Spinner) findViewById(R.id.add_recipe_origin_spinner);
                Origin origin = (Origin) origins.getSelectedItem();

                ArrayList<Ingredient> listIngredientToAdd = new ArrayList<>();
                ListView listIngredients = (ListView) findViewById(R.id.ingredientList);
                ArrayAdapter<Ingredient> inAdapter = (ArrayAdapter<Ingredient>)ingredientList.getAdapter();
                if (in_list.isEmpty()) {
                    Toast.makeText(edit_recipe.this, "Your recipe cannot have 0 ingredient, please add at least one.", Toast.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < inAdapter.getCount(); i++) {
                        listIngredientToAdd.add(in_list.get(i));

                    }
                    if (sRecipeName.isEmpty()) {
                        Toast.makeText(edit_recipe.this, "Your recipe needs a name!", Toast.LENGTH_LONG).show();
                    } else {
                        sRecipeName = (sRecipeName.substring(0, 1).toUpperCase() + sRecipeName.substring(1).toLowerCase());
                        CookHelper.getCookHelper().removeRecipe(recipe); //Delete the recipe
                        Recipe newRecipe = new Recipe(iCookTime, iPrepTime, steps, sRecipeName, selectedImageUri, origin, category, listIngredientToAdd);//Create the new recipe
                        added = CookHelper.getCookHelper().addRecipe(newRecipe); //Add the new recipe and verify if it is added
                    }

                }


                if (added) { //If the recipe is succesfully added, send the intent to display the modified recipe in the Recipe View
                    Toast.makeText(edit_recipe.this, "Recipe SuccessFully Modified.", Toast.LENGTH_LONG).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("name", recipeName.getText().toString());
                    resultIntent.putExtra("prepTime", prepTime.getText().toString());
                    resultIntent.putExtra("cookTime", cookTime.getText().toString());
                    resultIntent.putExtra("origin", origin.getName());
                    resultIntent.putExtra("category", category.getName());
                    resultIntent.putExtra("picture", selectedImageUri.toString());
                    resultIntent.putExtra("description", steps);

                    String ingredients = null ;
                    for (int y =0; y<in_list.size(); y++) {
                        if (y == 0) {
                            ingredients = in_list.get(y).getName();
                        } else {
                            ingredients = ingredients + "\n" + in_list.get(y).getName();
                        }
                    }
                    resultIntent.putExtra("ingredients", ingredients);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish(); //close the activity

                }
            }
        } else {
            Toast.makeText(edit_recipe.this, "Modification Failed.", Toast.LENGTH_LONG).show();
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, Toolbar.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public ArrayList<Ingredient> verifyIngredients(){ //Methode to return an array of ingredients from the string gotten by the intent
        ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
        String received = ingredients.toString(); //takes all the ingredients
        String[] ingredientStringList = received.trim().split("\n"); //Split the received string
        for (int i = 0; i < ingredientStringList.length; i++){
            if (CookHelper.getCookHelper().findIngredient(ingredientStringList[i])!=null){ //if the ingredient exist
                ingredientList.add(CookHelper.getCookHelper().findIngredient(ingredientStringList[i])); //add it to the new arrayList
            }else {
                Toast.makeText(edit_recipe.this, "There is an error.", Toast.LENGTH_SHORT); //display "There is an error." if the ingredient doesnt exist
            }
        }
        return ingredientList;
    }



}

