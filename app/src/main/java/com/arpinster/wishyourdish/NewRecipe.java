package com.arpinster.wishyourdish;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class NewRecipe extends AppCompatActivity {

    ImageView self_image;
    TextView ingredient,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String des = intent.getStringExtra("des");
        String ingred = intent.getStringExtra("ingred");
        byte[] bytes = intent.getByteArrayExtra("image");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.mybutton));

        self_image = (ImageView)findViewById(R.id.self_image);
        ingredient = (TextView)findViewById(R.id.ingred);
        description = (TextView)findViewById(R.id.des);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        self_image.setImageBitmap(bitmap);
        ingredient.setText("Ingredient : \n"+ingred);
        description.setText("Descriptiom : \n"+des);

        getSupportActionBar().setTitle(title);
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
