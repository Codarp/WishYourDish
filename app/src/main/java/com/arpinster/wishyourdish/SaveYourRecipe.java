package com.arpinster.wishyourdish;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SaveYourRecipe extends AppCompatActivity {

    Button submit,image_chooser,delete;
    ImageView image;
    EditText title,ingredient,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_your_recipe);

        title = (EditText) findViewById(R.id.recipe_name);
        submit = (Button)findViewById(R.id.submit);
        ingredient = (EditText)findViewById(R.id.recipe_ingredients);
        description = (EditText)findViewById(R.id.description);
        image = (ImageView)findViewById(R.id.recipe_image);
        image_chooser = (Button)findViewById(R.id.image_chooser);
        delete = (Button)findViewById(R.id.delete);
        getSupportActionBar().setTitle("Save Your Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.mybutton));

        final RecipeSaveHandler handler = new RecipeSaveHandler(this,"Mydatabase");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setDrawingCacheEnabled(true);
                image.buildDrawingCache();
                Bitmap bitmap = image.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes = stream.toByteArray();
                boolean b = handler.insertData(title.getText().toString(),ingredient.getText().toString(),description.getText().toString(),bytes);
                if(b==true)
                    Toast.makeText(SaveYourRecipe.this,"Successfully Saved",Toast.LENGTH_LONG);
                else
                    Toast.makeText(SaveYourRecipe.this,"Unsuccessful while saving",Toast.LENGTH_LONG);
                title.setText("");
                ingredient.setText("");
                description.setText("");
                image.setImageDrawable(getResources().getDrawable(R.drawable.default_image));
            }
        });

        image_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose(view);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int m = handler.deleteData(title.getText().toString());
                image.setImageDrawable(getResources().getDrawable(R.drawable.default_image));
                title.setText("");
                ingredient.setText("");
                description.setText("");

                if(m==0)
                    Toast.makeText(SaveYourRecipe.this,"No data found",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SaveYourRecipe.this,"No of rows deleted : "+m,Toast.LENGTH_LONG).show();
            }
        });


    }


    public void choose(View v)
    {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        outState.putParcelable("image", bitmap);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bitmap bitmap = savedInstanceState.getParcelable("image");
        image.setImageBitmap(bitmap);
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
