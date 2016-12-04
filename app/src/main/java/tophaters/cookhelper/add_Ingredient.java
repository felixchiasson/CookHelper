package tophaters.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        String ingredient = edit.getText().toString();
        Ingredient newIngredient= new Ingredient(ingredient.substring(0,1).toUpperCase() + ingredient.substring(1).toLowerCase());

        added = CookHelper.getCookHelper().addIngredient(newIngredient);

        if (added == true){
            Toast.makeText(add_Ingredient.this, "Saved", Toast.LENGTH_LONG).show();
            Intent a = new Intent(this, Ingredient_List.class);
            startActivity(a);

        }else{
            Toast.makeText(add_Ingredient.this, "Ingredient already exists.", Toast.LENGTH_LONG).show();
        }
    }
}
