package com.arpinster.wishyourdish;

import android.graphics.Bitmap;

/**
 * Created by Arpit on 6/12/2017.
 */
public class SingleSelfRecipe {

    String title;
    String ingredients;
    String desciption;
    Bitmap image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
