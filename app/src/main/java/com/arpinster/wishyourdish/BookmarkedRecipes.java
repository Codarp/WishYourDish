package com.arpinster.wishyourdish;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookmarkedRecipes extends AppCompatActivity {

    ListView lv;
    ArrayList<SingleBookmarkedRecipe> bookmark;
    RecipeBookmarkHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_recipes);
        getSupportActionBar().setTitle("Bookmarked Recipes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_items));

        handler = new RecipeBookmarkHandler(this,"NewDatabase");
        Log.i("TAG","here are the bookmarked recipes");
        lv = (ListView) findViewById(R.id.bookmark_list);
        bookmark = new ArrayList<SingleBookmarkedRecipe>();

        Cursor cursor = handler.viewData();
        Toast.makeText(this,"Total no of rows : "+cursor.getCount(),Toast.LENGTH_SHORT).show();
        while(cursor.moveToNext())
        {
            SingleBookmarkedRecipe recipe = new SingleBookmarkedRecipe();
            recipe.setTitle(cursor.getString(0));
            recipe.setHealth(cursor.getString(1));
            recipe.setIngredients(cursor.getString(2));
            recipe.setUrl(cursor.getString(3));
            recipe.setImage(BitmapFactory.decodeByteArray(cursor.getBlob(4),0,cursor.getBlob(4).length));
            recipe.setCalories(cursor.getString(5));
            bookmark.add(recipe);
        }

        BookmarkedRecipeAdapter adapter = new BookmarkedRecipeAdapter(this,R.layout.row,bookmark);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SingleBookmarkedRecipe recipe = bookmark.get(i);
                Intent intent = new Intent(BookmarkedRecipes.this,SingleRecipeView.class);
                intent.putExtra("url",recipe.getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
