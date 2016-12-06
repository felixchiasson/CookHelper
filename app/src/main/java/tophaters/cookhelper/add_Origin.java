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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__origin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    public void onClickSaveOrigin(View v){
        boolean added;
        EditText edit =  (EditText) findViewById(R.id.inputNewOrigin);
        String origin = edit.getText().toString();

        int[] capsLocation = new int[origin.length()];
        capsLocation[0] = 1;
        char[] originChar = origin.toCharArray();
        for (int i = 0; i<originChar.length; i++){
            char c = originChar[i];
            if(c == ' '){
                capsLocation[i+1] = 1;
            }
        }

        origin = String.valueOf(originChar).toLowerCase();
        String originString = new String();
        for (int y = 0; y< capsLocation.length; y++){
            if (capsLocation[y]==1){
                originString = originString + origin.substring(y,y+1).toUpperCase();
            }else{
                originString = originString + origin.substring(y,y+1).toLowerCase();
            }
        }
        Origin newOrigin = new Origin(originString);

        added = CookHelper.getCookHelper().addOrigin(newOrigin);

        if (added){
            Toast.makeText(add_Origin.this, "Saved", Toast.LENGTH_LONG).show();
            finish();

        }else{
            Toast.makeText(add_Origin.this, "Origin already exists.", Toast.LENGTH_LONG).show();
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
