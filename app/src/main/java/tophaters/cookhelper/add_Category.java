package tophaters.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    public void onClickSaveCategory(View v){ //Save the category when you click
        boolean added; //new boolean to verify if the category was successfully added
        EditText edit =  (EditText) findViewById(R.id.inputNewCategory); //get the text from the EditText
        String category = edit.getText().toString(); //Convert the text to String

        int[] capsLocation = new int[category.length()]; //Create an array to find the UpperCase Characters
        capsLocation[0] = 1; //First letter is always UpperCase
        char[] ingredientChar = category.toCharArray(); // convert the string into an Array of char to parse
        for (int i = 0; i<ingredientChar.length; i++){ //For loop through the array
            char c = ingredientChar[i]; //take the Char
            if(c == ' '){ //verify if the character is a space
                capsLocation[i+1] = 1; //if the character is a space, mark the location of the next letter in the capsLocation array with a 1
            }
        }

        category = String.valueOf(ingredientChar).toLowerCase(); //convert the array of char into a String
        String categoryString = new String(); //add create a new string to create the string with the right uppercase/lowercase
        for (int y = 0; y< capsLocation.length; y++){ //Start a new loop with capsLocation to find which one is an Uppercase.
            if (capsLocation[y]==1){ //if the location is a 1
                categoryString = categoryString + category.substring(y,y+1).toUpperCase();//create a substring of the letter and put it to uppercase
            }else{ //otherwise
                categoryString = categoryString + category.substring(y,y+1).toLowerCase(); //create a substring of the letter and put it to lowercase
            }
        }

        Category newCategory= new Category(categoryString); //create a new category object with the right name

        added = CookHelper.getCookHelper().addCategory(newCategory); //try to add the new category in the cookHelper if if works, added = true otherwise added = false

        if (added == true){ //if it the add was succesful
            Toast.makeText(add_Category.this, "Saved", Toast.LENGTH_LONG).show(); //show a Toast with "Saved" as text
            finish(); //close the activity

        }else{ // if the add failed
            Toast.makeText(add_Category.this, "Category already exists.", Toast.LENGTH_LONG).show(); //show a toast with "Category already exists".
        }
    }

}
