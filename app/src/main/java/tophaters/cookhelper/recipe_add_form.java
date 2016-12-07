package tophaters.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;

public class recipe_add_form extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private static final int GALLERY_KITKAT_INTENT_CALLED = 3;
    private static final int SUCCESS_VALUE = 2;
    ListView ingredientList;
    private Uri selectedImageUri;
    ArrayList mSelected;
    ArrayList<Ingredient> in_list = new ArrayList<>();
    ArrayAdapter<Ingredient> ingredientAdapter;
    ArrayAdapter<Ingredient> dialogAdapter;
    ListView lv;
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


        // DISABLE TOUCH EVENTS WHEN SCROLLING THE LISTVIEW
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if(in_list.isEmpty()){

                }else{



                    in_list.remove(in_list.get(position));
                    //ingredientAdapter.remove(in_list.get(position));


                    //ingredientList.setAdapter(ingredientAdapter);
                    dialogAdapter.notifyDataSetChanged();
                    ListView lv = (ListView) findViewById(R.id.ingredientList);
                    setListViewHeightBasedOnChildren(lv);}


            }
        });


        // DISABLE TOUCH EVENTS WHEN SCROLLING THE LISTVIEW

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
    }

    public void onClickOpenGallery(View v) {

        if(Build.VERSION.SDK_INT < 19) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);

        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/jpeg");
            startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                ImageView imagetest = (ImageView) findViewById(R.id.imageView2);
                imagetest.setImageURI(selectedImageUri);

            } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {
                selectedImageUri = data.getData();
                int takeFlags = data.getFlags();
                takeFlags &= (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                // Check for the freshest data.

                getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                ImageView imagetest = (ImageView) findViewById(R.id.imageView2);
                imagetest.setImageURI(selectedImageUri);


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

                        dialogAdapter = new ArrayAdapter<Ingredient>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, in_list);

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

    public void onClickSaveRecipe(View v) {
        boolean added = false;
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
            iCookTime = Integer.parseInt(sPrepTime);
        }


        EditText description = (EditText) findViewById(R.id.add_recipe_edittext_steps);
        String steps = description.getText().toString();

        Spinner categories = (Spinner) findViewById(R.id.add_recipe_category_spinner);
        Category category = (Category) categories.getSelectedItem();

        Spinner origins = (Spinner) findViewById(R.id.add_recipe_origin_spinner);
        Origin origin = (Origin) origins.getSelectedItem();

        if (sRecipeName.isEmpty()) {
            Toast.makeText(recipe_add_form.this, "Your recipe needs a name!", Toast.LENGTH_LONG).show();
        } else {
            sRecipeName = (sRecipeName.substring(0, 1).toUpperCase() + sRecipeName.substring(1).toLowerCase());
        }

        ArrayList<Ingredient> listIngredientToAdd = new ArrayList<>();
        ListView listIngredients = (ListView) findViewById(R.id.ingredientList);
        ArrayAdapter<Ingredient> inAdapter = (ArrayAdapter<Ingredient>)listIngredients.getAdapter();
        if (inAdapter == null) {
            Toast.makeText(recipe_add_form.this, "Your recipe cannot have 0 ingredient, please add at least one.", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < inAdapter.getCount(); i++) {
                listIngredientToAdd.add(inAdapter.getItem(i));
            }
            Recipe newRecipe = new Recipe(iCookTime, iPrepTime, steps, sRecipeName, selectedImageUri, origin, category, listIngredientToAdd);
            added = CookHelper.getCookHelper().addRecipe(newRecipe);
        }

        // Recipe newRecipe = new Recipe(sCookTime, sPrepTime, steps, sRecipeName, selectedImageUri);






        if (added){
            Toast.makeText(recipe_add_form.this, "Saved", Toast.LENGTH_LONG).show();
            finish();

        }else{
            Toast.makeText(recipe_add_form.this, "Recipe could not be added", Toast.LENGTH_LONG).show();
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
