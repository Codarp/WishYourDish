package com.arpinster.wishyourdish;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class Meaning extends AppCompatActivity {

    EditText health,diet;
    Button button_health,button_diet;
    HashMap< String,String > meaning_health,meaning_diet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning);

        meaning_health = new HashMap< String,String >();
        meaning_diet = new HashMap<String,String>();
        getSupportActionBar().setTitle("Know Your Labels");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.mybutton2));

        button_diet = (Button) findViewById(R.id.diet_meaning);
        button_health = (Button) findViewById(R.id.health_meaning);
        health = (EditText) findViewById(R.id.health_label);
        diet = (EditText) findViewById(R.id.diet_label);

        meaning_health.put("alcohol-free","No alcohol used or contained in the recipe");
        meaning_health.put("celery-free","does not contain celery or derivatives");
        meaning_health.put("crustacean-free","does not contain crustaceans (shrimp, lobster etc.) or derivatives");
        meaning_health.put("dairy-free","No dairy; no lactose");
        meaning_health.put("egg-free","No eggs or products containing eggs");
        meaning_health.put("fish-free","No fish or fish derivatives");
        meaning_health.put("gluten-free","No ingredients containing gluten");
        meaning_health.put("kidney-friendly","per serving – phosphorus less than 250 mg AND potassium less than 500 mg AND sodium: less than 500 mg");
        meaning_health.put("kosher","contains only ingredients allowed by the kosher diet. However it does not guarantee kosher preparation of the ingredients themselves");
        meaning_health.put("low-potassium","Less than 150mg per serving");
        meaning_health.put("lupine-free","does not contain lupine or derivatives");
        meaning_health.put("mustard-free","does not contain mustard or derivatives");
        meaning_health.put("low-fat-abs","Less than 3g of fat per serving");
        meaning_health.put("No-oil-added","No oil added except to what is contained in the basic ingredients");
        meaning_health.put("low-sugar","No simple sugars – glucose, dextrose, galactose, fructose, sucrose, lactose, maltose");
        meaning_health.put("paleo","Excludes what are perceived to be agricultural products; grains, legumes, dairy products, potatoes, refined salt, refined sugar, and processed oils");
        meaning_health.put("peanut-free","No peanuts or products containing peanuts");
        meaning_health.put("pecatarian","Does not contain meat or meat based products, can contain dairy and fish");
        meaning_health.put("pork-free","does not contain pork or derivatives");
        meaning_health.put("red-meat-free","does not contain beef, lamb, pork, duck, goose, game, horse, and other types of red meat or products containing red meat");
        meaning_health.put("sesame-free","does not contain sesame seed or derivatives");
        meaning_health.put("shellfish-free","No shellfish or shellfish derivatives");
        meaning_health.put("soy-free","No soy or products containing soy");
        meaning_health.put("sugar-conscious","Less than 4g of sugar per serving");
        meaning_health.put("tree-nut-free","No tree nuts or products containing tree nuts");
        meaning_health.put("vegan","No meat, poultry, fish, dairy, eggs or honey");
        meaning_health.put("vegetarian","No meat, poultry, or fish");
        meaning_health.put("wheat-free","No wheat, can have gluten though");

        meaning_diet.put("balanced","Protein/Fat/Carb values in 15/35/50 ratio");
        meaning_diet.put("high-fiber","More than 5g fiber per serving");
        meaning_diet.put("high-protein","More than 50% of total calories from proteins");
        meaning_diet.put("low-carb","Less than 20% of total calories from carbs");
        meaning_diet.put("low-fat","Less than 15% of total calories from fat");
        meaning_diet.put("low-sodium","Less than 140mg Na per serving");

        button_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Meaning.this);
                alert.setTitle(health.getText().toString());
                String mean = meaning_health.get(health.getText().toString());
                if(mean!=null)
                    alert.setMessage(mean);
                else
                    alert.setMessage("No data of that label");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert.setCancelable(true);
                    }
                });
                alert.create().show();
            }
        });

        button_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Meaning.this);
                alert.setTitle(diet.getText().toString());
                String mean = meaning_diet.get(diet.getText().toString());
                if(mean!=null)
                    alert.setMessage(mean);
                else
                    alert.setMessage("No data of that label");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert.setCancelable(true);
                    }
                });
                alert.create().show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
