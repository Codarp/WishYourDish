package com.arpinster.wishyourdish;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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
 * Created by Arpit on 6/12/2017.
 */
public class SelfRecipeAdapter extends ArrayAdapter<SingleSelfRecipe> {

    int resource;
    RecipeSaveHandler handler;
    ArrayList<SingleSelfRecipe> obj;
    public SelfRecipeAdapter(Context context, int resource,ArrayList<SingleSelfRecipe> objects) {
        super(context, resource,objects);
        obj = objects;
        this.resource=resource;
        handler = new RecipeSaveHandler(context,"Mydatabase");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        SelfRecipeHolder holder=null;
        View v = convertView;
        if(v == null){

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource,parent,false);
            holder = new SelfRecipeHolder(v);
            v.setTag(holder);
        }
        else{
            holder = (SelfRecipeHolder) v.getTag();
        }
        holder.button.setFocusable(false);

        final SingleSelfRecipe self = obj.get(position);
        holder.recipe_image.setImageBitmap(self.getImage());
        holder.tv.setText(self.getTitle());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coun=handler.deleteData(self.getTitle());
                if(coun>0)
                    Toast.makeText(getContext(),"Row deleted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(),"No data deleted",Toast.LENGTH_SHORT).show();
                obj.remove(position);
                notifyDataSetChanged();
            }
        });

        return v;
    }

    public class SelfRecipeHolder{

        ImageView recipe_image;
        TextView tv;
        ImageButton button;

        public SelfRecipeHolder(View v){
            recipe_image = (ImageView) v.findViewById(R.id.image_recipe);
            tv = (TextView) v.findViewById(R.id.recipe_title);
            button = (ImageButton) v.findViewById(R.id.remove_button);
        }

    }
}
