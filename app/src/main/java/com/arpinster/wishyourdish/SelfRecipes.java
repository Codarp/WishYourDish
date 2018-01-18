package com.arpinster.wishyourdish;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SelfRecipes extends AppCompatActivity {

    ArrayList<SingleSelfRecipe> self;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_recipes);

        getSupportActionBar().setTitle("My Recipes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_items));
        getSupportActionBar().setElevation(2);
        listView = (ListView)findViewById(R.id.self_list);
        RecipeSaveHandler handler = new RecipeSaveHandler(this,"Mydatabase");

        self = new ArrayList<SingleSelfRecipe>();
        Cursor cursor = handler.viewData();
        Toast.makeText(this,"Total no of rows : "+cursor.getCount(),Toast.LENGTH_LONG).show();
        while(cursor.moveToNext())
        {
            SingleSelfRecipe recipe = new SingleSelfRecipe();
            recipe.setTitle(cursor.getString(0));
            recipe.setIngredients(cursor.getString(1));
            recipe.setDesciption(cursor.getString(2));
            recipe.setImage(BitmapFactory.decodeByteArray(cursor.getBlob(3),0,cursor.getBlob(3).length));
            self.add(recipe);
        }
        SelfRecipeAdapter adapter = new SelfRecipeAdapter(this,R.layout.row2,self);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SingleSelfRecipe recipe = self.get(i);
                Intent intent = new Intent(SelfRecipes.this,NewRecipe.class);
                intent.putExtra("title",recipe.getTitle());
                intent.putExtra("ingred",recipe.getIngredients());
                intent.putExtra("des",recipe.getDesciption());
                Bitmap bitmap = recipe.getImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                intent.putExtra("image",stream.toByteArray());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
