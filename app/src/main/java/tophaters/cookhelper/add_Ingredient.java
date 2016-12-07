package tophaters.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_Ingredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Basic onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ingredient);

    }
    public void onClickSaveIngredient(View v){ //Added an onClick for saving the Ingredient
        boolean added; //new boolean to verify if the ingredient was successfully added
        EditText edit =  (EditText) findViewById(R.id.addIngredientName);//get the text from the EditText
        String ingredient = edit.getText().toString(); //Convert the text to String
        int[] capsLocation = new int[ingredient.length()]; //Create an array to find the UpperCase Characters
        capsLocation[0] = 1; //First letter is always UpperCase
        char[] ingredientChar = ingredient.toCharArray(); // convert the string into an Array of char to parse
        for (int i = 0; i<ingredientChar.length; i++){ //For loop through the array
            char c = ingredientChar[i]; //take the Char
            if(c == ' '){ //verify if the character is a space
                ingredientChar[i] = '-'; //Change the space into a '-' for
                capsLocation[i+1] = 1; //if the character is a space, mark the location of the next letter in the capsLocation array with a 1
            }
        }

        ingredient = String.valueOf(ingredientChar).toLowerCase(); //convert the array of char into a String
        String ingredients = new String(); //add create a new string to create the string with the right uppercase/lowercase
        for (int y = 0; y< capsLocation.length; y++){//Start a new loop with capsLocation to find which one is an Uppercase.
            if (capsLocation[y]==1){ //if the location is a 1
                ingredients = ingredients + ingredient.substring(y,y+1).toUpperCase();//create a substring of the letter and put it to uppercase
            }else{ //otherwise
                ingredients = ingredients + ingredient.substring(y,y+1).toLowerCase();//create a substring of the letter and put it to lowercase
            }
        }
        Ingredient newIngredient= new Ingredient(ingredients); //create a new ingredient object with the right name

        added = CookHelper.getCookHelper().addIngredient(newIngredient); //try to add the new ingredient in the cookHelper if if works, added = true otherwise added = false

        if (added == true){ //if it the add was succesful
            Toast.makeText(add_Ingredient.this, "Saved", Toast.LENGTH_LONG).show(); //display a toast with "Saved" text
            finish(); //close the activity

        }else{ //otherwise
            Toast.makeText(add_Ingredient.this, "Ingredient already exists.", Toast.LENGTH_LONG).show(); //display a toast with "Ingredient already exists." text
        }
    }
}
