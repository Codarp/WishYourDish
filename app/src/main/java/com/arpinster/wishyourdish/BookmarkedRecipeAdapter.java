package com.arpinster.wishyourdish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Arpit on 6/15/2017.
 */
public class BookmarkedRecipeAdapter extends ArrayAdapter<SingleBookmarkedRecipe> {
    RecipeBookmarkHandler handler;
    int resource;
    ArrayList<SingleBookmarkedRecipe> list;
    public BookmarkedRecipeAdapter(Context context, int resource, ArrayList<SingleBookmarkedRecipe> list) {
        super(context, resource, list);
        this.list = list;
        this.resource = resource;
        handler = new RecipeBookmarkHandler(context,"NewDatabase");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        BookmarkRecipeHolder holder;
        View v = convertView;
        if(v == null){

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource,parent,false);
            holder = new BookmarkRecipeHolder(v);
            v.setTag(holder);
        }
        else{
            holder = (BookmarkRecipeHolder) v.getTag();
        }

        holder.bookmark.setFocusable(false);

        final SingleBookmarkedRecipe recipe = list.get(position);
        holder.title.setText(recipe.getTitle());
        holder.health.setText(recipe.getHealth());
        holder.ingredient.setText(recipe.getIngredients());
        holder.img.setImageBitmap(recipe.getImage());
        holder.calories.setText(recipe.getCalories());
        holder.bookmark.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.delete_image));
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = handler.deleteData(recipe.getTitle());
                list.remove(position);
                Toast.makeText(getContext(),"No of rows deleted : "+count,Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        return v;
    }

    public class BookmarkRecipeHolder{
        TextView title,health,ingredient;
        TextView calories;
        ImageView img;
        ImageButton bookmark;

        public BookmarkRecipeHolder(View v){
            title=(TextView)v.findViewById(R.id.title);
            health=(TextView)v.findViewById(R.id.health);
            ingredient=(TextView)v.findViewById(R.id.ingredient);
            img=(ImageView)v.findViewById(R.id.img);
            calories=(TextView)v.findViewById(R.id.calories);
            bookmark = (ImageButton) v.findViewById(R.id.bookmark_button);
        }

    }
}
