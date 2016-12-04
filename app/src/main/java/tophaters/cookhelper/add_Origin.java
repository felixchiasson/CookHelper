package tophaters.cookhelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        Origin newOrigin = new Origin(origin.substring(0,1).toUpperCase() + origin.substring(1).toLowerCase());

        added = CookHelper.getCookHelper().addOrigin(newOrigin);

        if (added == true){
            Toast.makeText(add_Origin.this, "Saved", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(add_Origin.this, "Origin already exists.", Toast.LENGTH_LONG).show();
        }
    }


}
