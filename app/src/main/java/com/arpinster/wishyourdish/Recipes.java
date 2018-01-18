package com.arpinster.wishyourdish;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Arpit on 6/10/2017.
 */
public class Recipes implements Parcelable{
    String label;
    String url;
    String image;
    String ingredientLines[];
    String healthLabels[];
    double calories;

    protected Recipes(Parcel in) {
        label = in.readString();
        url = in.readString();
        image = in.readString();
        ingredientLines = in.createStringArray();
        healthLabels = in.createStringArray();
        calories = in.readDouble();
    }

    public Recipes(String label,String url,String image,String ingredientLines[],String healthLabels[],double calories)
    {
        this.label = label;
        this.url = url;
        this.image = image;
        this.ingredientLines = ingredientLines;
        this.healthLabels = healthLabels;
        this.calories = calories;
    }
    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(String[] ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public double getCalories(){
        return calories;
    }

    public void setCalories(double calories){
        this.calories=calories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(String[] healthLabels) {
        this.healthLabels = healthLabels;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(label);
        parcel.writeString(url);
        parcel.writeString(image);
        parcel.writeStringArray(ingredientLines);
        parcel.writeStringArray(healthLabels);
        parcel.writeDouble(calories);
    }
}
