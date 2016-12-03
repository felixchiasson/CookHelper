package tophaters.cookhelper;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_Ingredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ingredient);

    }
    public void onClickSaveIngredient(View v){
        boolean added;
        EditText edit =  (EditText) findViewById(R.id.addIngredientName);
        Ingredient newIngredient= new Ingredient(edit.getText().toString());

        added = CookHelper.getCookHelper().addIngredient(newIngredient);

        if (added == true){
            Toast.makeText(add_Ingredient.this, "Saved", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(add_Ingredient.this, "Ingredient already exists.", Toast.LENGTH_LONG).show();
        }
    }
}
