package tophaters.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.PrintWriter;

import java.util.ArrayList;

public class recipe_add_form extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private static final int SUCCESS_VALUE = 2;
    ListView ingredientList;
    private Uri selectedImageUri;
    ArrayList mSelected;
    ArrayList<Ingredient> in_list = new ArrayList<>();
    ArrayAdapter<Ingredient> ingredientAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        // DISABLE TOUCH EVENTS WHEN SCROLLING THE LISTVIEW

        ListView lv = (ListView) findViewById(R.id.ingredientList);
        lv.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // ****************************************************



        ingredientAdapter = new ArrayAdapter<Ingredient>(this, android.R.layout.simple_list_item_multiple_choice, CookHelper.getCookHelper().getIngredients());

        /* ingredientList = (ListView) findViewById(R.id.ingredientList);
        ingredientList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ingredientList.setAdapter(ingredientAdapter); */

        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, CookHelper.getCookHelper().getCategories());

        Spinner categorySpinner;
        categorySpinner = (Spinner) findViewById(R.id.add_recipe_category_spinner);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<Origin> originAdapter = new ArrayAdapter<Origin>(this, android.R.layout.simple_spinner_item, CookHelper.getCookHelper().getOrigins());

        Spinner originSpinner;
        originSpinner = (Spinner) findViewById(R.id.add_recipe_origin_spinner);
        originSpinner.setAdapter(originAdapter);
    }

    public void onClickOpenGallery(View v) {

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

    public void onClickOpenIngredientDialog(View v) {
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




        builder.setTitle("Test")
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

    public void onClickSaveRecipe() {


        EditText recipeName = (EditText) findViewById(R.id.recipe_add_name);
        String sRecipeName = recipeName.getText().toString();

        EditText prepTime = (EditText) findViewById(R.id.recipe_add_preptime);
        int sPrepTime = Integer.parseInt(prepTime.getText().toString());

        EditText cookTime = (EditText) findViewById(R.id.recipe_add_cooktime);
        int sCookTime = Integer.parseInt(cookTime.getText().toString());

        EditText description = (EditText) findViewById(R.id.add_recipe_edittext_steps);
        String steps = description.getText().toString();

        Spinner categories = (Spinner) findViewById(R.id.add_recipe_category_spinner);
        Category category = (Category) categories.getSelectedItem();

        Spinner origins = (Spinner) findViewById(R.id.add_recipe_origin_spinner);
        Origin origin = (Origin) origins.getSelectedItem();

        ArrayList<Ingredient> listIngredientToAdd = new ArrayList<>();
        ListView listIngredients = (ListView) findViewById(R.id.ingredientList);
        ArrayAdapter<Ingredient> inAdapter = (ArrayAdapter<Ingredient>)listIngredients.getAdapter();
        for(int i = 0; i<inAdapter.getCount(); i++) {
            listIngredientToAdd.add(inAdapter.getItem(i));
        }

        // Recipe newRecipe = new Recipe(sCookTime, sPrepTime, steps, sRecipeName, selectedImageUri);




        Recipe newRecipe = new Recipe(sCookTime, sPrepTime, steps, sRecipeName, selectedImageUri, origin, category, listIngredientToAdd);
        added = CookHelper.getCookHelper().addRecipe(newRecipe);

        if (added){
            Toast.makeText(recipe_add_form.this, "Saved", Toast.LENGTH_LONG).show();
            finish();

        }else{
            Toast.makeText(recipe_add_form.this, "Recipe already exists.", Toast.LENGTH_LONG).show();
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
