package tophaters.cookhelper;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class recipe_add_form extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<Ingredient>(this, android.R.layout.simple_spinner_item, CookHelper.getCookHelper().getIngredients());

        Spinner ingredientSpinner;
        ingredientSpinner = (Spinner) findViewById(R.id.spinnerIngredients);
        ingredientSpinner.setAdapter(ingredientAdapter);
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




        // Recipe newRecipe = new Recipe(sCookTime, sPrepTime, steps, sRecipeName, selectedImageUri);



    }


}
