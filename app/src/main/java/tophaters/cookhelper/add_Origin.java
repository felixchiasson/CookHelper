package tophaters.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintWriter;

public class add_Origin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Basic onCreate, not modified
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__origin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    public void onClickSaveOrigin(View v){ //Added an onClick for saving a new origin
        boolean added; //new boolean to verify if the origin was successfully added
        EditText edit =  (EditText) findViewById(R.id.inputNewOrigin); //get the text from the EditText
        String origin = edit.getText().toString(); //Convert the text to String

        int[] capsLocation = new int[origin.length()]; //Create an array to find the UpperCase Characters
        capsLocation[0] = 1; //First letter is always UpperCase
        char[] originChar = origin.toCharArray(); // convert the string into an Array of char to parse
        for (int i = 0; i<originChar.length; i++){ //For loop through the array
            char c = originChar[i]; //take the Char
            if(c == ' '){ //verify if the character is a space
                capsLocation[i+1] = 1; //if the character is a space, mark the location of the next letter in the capsLocation array with a 1
            }
        }

        origin = String.valueOf(originChar).toLowerCase(); //convert the array of char into a String
        String originString = new String(); //add create a new string to create the string with the right uppercase/lowercase
        for (int y = 0; y< capsLocation.length; y++){ //Start a new loop with capsLocation to find which one is an Uppercase.
            if (capsLocation[y]==1){ //if the location is a 1
                originString = originString + origin.substring(y,y+1).toUpperCase(); //create a substring of the letter and put it to uppercase
            }else{ //otherwise
                originString = originString + origin.substring(y,y+1).toLowerCase(); //create a substring of the letter and put it to lowercase
            }
        }
        Origin newOrigin = new Origin(originString);  //create a new origin object with the right name

        added = CookHelper.getCookHelper().addOrigin(newOrigin); //try to add the new ingredient in the cookHelper if if works, added = true, otherwise added = false

        if (added){ //if it the add was succesful
            Toast.makeText(add_Origin.this, "Saved", Toast.LENGTH_LONG).show(); //display a toast with "Saved" text
            finish();//close the activity

        }else{ //otherwise
            Toast.makeText(add_Origin.this, "Origin already exists.", Toast.LENGTH_LONG).show(); //display a toast with "Origin already exists." text
        }
        }
    }
