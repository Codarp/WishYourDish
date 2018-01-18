package com.arpinster.wishyourdish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Arpit on 6/10/2017.
 */
public class MyAdapter extends ArrayAdapter<Recipes> {

    TextView title,health,ingredient,calories;
    int resource;
    Recipes recipe;
    ImageView img;
    RecipeBookmarkHandler handler;
    ImageButton bookmark;
    ArrayList<Recipes> obj;
    Bitmap btm[];
    public MyAdapter(Context context, int resource, ArrayList<Recipes> objects) {
        super(context, resource, objects);
        this.resource = resource;
        obj = objects;
        btm = new Bitmap[objects.size()];
        handler = new RecipeBookmarkHandler(context,"NewDatabase");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        //if(v==null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, parent, false);
        //}
        title=(TextView)v.findViewById(R.id.title);
        health=(TextView)v.findViewById(R.id.health);
        ingredient=(TextView)v.findViewById(R.id.ingredient);
        img=(ImageView)v.findViewById(R.id.img);
        calories=(TextView)v.findViewById(R.id.calories);
        bookmark = (ImageButton) v.findViewById(R.id.bookmark_button);
        bookmark.setFocusable(false);

        recipe=obj.get(position);

        String healthlabel[] = recipe.getHealthLabels();
        String ingredients[] = recipe.getIngredientLines();

        title.setText(recipe.getLabel());
        health.setText("Health Benefits : ");
        for(int i=0;i<healthlabel.length;i++)
            health.append(" "+healthlabel[i]);
        ingredient.setText("Ingredients : ");
        for(int i=0;i<ingredients.length;i++)
            ingredient.append(" "+ingredients[i]);
        calories.setText("Calories : "+recipe.getCalories());

        if(btm[position]==null)
            new DownloadImageTask(img,position).execute(recipe.getImage());
        else
            img.setImageBitmap(btm[position]);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b;
                if(btm[position]!=null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    btm[position].compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    Log.i("TAG","Going to insert data");
                    b=handler.insertData(recipe.getLabel(), health.getText().toString(), ingredient.getText().toString(), recipe.getUrl(), bytes,calories.getText().toString());
                    if(b)
                        Toast.makeText(getContext(),"Successfully Bookmarked",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"Already Bookmarked",Toast.LENGTH_SHORT).show();
                }
                else {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.default_image);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    byte[] bytes = stream.toByteArray();
                    Toast.makeText(getContext(),"Image Not loaded",Toast.LENGTH_SHORT).show();
                    b=handler.insertData(recipe.getLabel(), health.getText().toString(), ingredient.getText().toString(), recipe.getUrl(), bytes, calories.getText().toString());
                    if(b)
                        Toast.makeText(getContext(),"Successfully Bookmarked",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"Already Bookmarked",Toast.LENGTH_SHORT).show();
                }
                Log.i("TAG","on click has been finished");
            }
        });
        return v;
    }

    public class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{

        ImageView img;
        int position;
        DownloadImageTask(ImageView img,int position)
        {
            this.img=img;
            this.position=position;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay = strings[0];
            Bitmap bitmap = null;
            try{
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            btm[position]=bitmap;
            img.setImageBitmap(bitmap);
        }
    }

}
