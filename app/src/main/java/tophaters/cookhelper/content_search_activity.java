package tophaters.cookhelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class content_search_activity extends AppCompatActivity {
    private ArrayAdapter<Origin> adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




populateListView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void populateListView() {

        adapter = new content_search_activity.MyListAdapter();
        list = (ListView) findViewById(R.id.selectIgrendientListView);

        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Origin> {

        public MyListAdapter() {
            super(content_search_activity.this, R.layout.item_view, CookHelper.getCookHelper().getOrigins());
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.select_ingredient_view, parent, false);
            }

            //find the ingredient

           // Origin currentOrigin = CookHelper.getCookHelper().getOrigins().get(position);


            // Make name Text
            //TextView nameText = (TextView) itemView.findViewById(R.id.item_textName);
            //nameText.setText(currentOrigin.getName());
            return itemView;


        }

    }




}















