package com.arpinster.wishyourdish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    TextView tv,tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.mybutton));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv = (TextView) findViewById(R.id.about_us_text);
        tv.setText("We are the team of young develeopers though new in the field of development, focus only on what are needs of the particular individual"+
        ". And so this app focuses mostly on the necessity of the people and not on the comfort. The user-interface of the app might not seem "+
        "so much full of special effects and overwhelming but the large database of the recipes surely give us the upper hand.\n\n\t"+
        "Most of the times it happens that people don't want to buy other ingredients of the recipes that they already know, or they don't "
        +"know what can be made from the ingredients already present. And this kind of situation can be handled very easily if they make use of this app. "+
        "This app also comes with the feature of saving your own recipes that will act as notes which you may forget with images.");

        tv1 = (TextView) findViewById(R.id.how_to_use);
        tv1.setText("\t* Ingredients should be entered by separating by comma always.\n\n"+
        "\t* Ingredients should never be entered in plural forms for eg :- oat is fine but oats will not give you anything.\n\n"+
        "\t* There is a field named filter by, no of results this will provide you the number of recipes you want the list should display.\n\n"+
        "\t* diet-labels and health-labels can be selected, one from each to filter the results further.\n\n"+
        "\t* Suggestions button will provide you the suggestions or say it will provide the random recipes containing anything.\n\n"+
        "\t* You can bookmark recipes you want by adding plus button in each item present in your search.\n\n"+
        "\t* Bookmarked Recipes can be seen by opening the side drawer or navigation drawer.\n\n"+
        "\t* You can save your own recipe by clicking the option of \"Save Your Recipe\" in navigation drawer and view them in option \"Self Recipes\".\n\n"+
        "\t* For more details about the project you can contact us by sending mail through contact option.");

        TextView tv2 = (TextView) findViewById(R.id.how_to_hack);
        tv2.setText("\t* Enter as many ingredients as you can, becasue this will result in more filtered and accurate results.\n\n"+
        "\t* Enter number of results as many as you can as by default it will show only 10 results (unless you don't have any internet problem).\n\n"+
        "\t* Suggestions provide random results but that can also be filtered using the filers present.\n\n"+
        "\t* Don't Bookmark unnecessarily as all the data is stored in your smart-phone itself.\n\n"+
        "\t* Typing random in the search button wroks same as suggestions.\n");
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