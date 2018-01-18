package com.arpinster.wishyourdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {

    ArrayList<Recipes> object;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Intent intent = getIntent();
        String q = intent.getStringExtra("q");
        getSupportActionBar().setTitle(q);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_items));
        object = intent.getParcelableArrayListExtra("name");
        lv = (ListView)findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this,R.layout.row,object);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipes recipes = object.get(i);
                String url = recipes.getUrl();
                Intent intent1 = new Intent(RecipeList.this,SingleRecipeView.class);
                intent1.putExtra("url",url);
                startActivity(intent1);
            }
        });
        lv.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
