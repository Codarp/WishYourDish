package com.arpinster.wishyourdish;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button button,random;
    ProgressBar bar;
    EditText edt,to;
    String health="",diet="";
    Spinner spinner_health,spinner_diet;
    String array_spinner_diet[]={"diet-label","balanced","high-fibre","high-protein","low-carb","low-fat","low-sodium"};
    String array_spinner_health[] = {"health-label","vegan","vegetarian","low-sugar","dairy-free","kidney-friendly","low-fat-abs","red-meat-free"
    ,"pecatarian","No-oil-added","tree-nut-free","wheat-free","egg-free","paleo","sugar-conscious","alcohol-free"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getBackground().setAlpha(210);

        edt=(EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.get_connection);
        bar = (ProgressBar)findViewById(R.id.bottom_progress);
        to=(EditText)findViewById(R.id.to);
        random = (Button)findViewById(R.id.random);
        getSupportActionBar().setTitle("Wish Your Dish");

        spinner_health = (Spinner)findViewById(R.id.spinner_health);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,array_spinner_health);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_health.setAdapter(adapter1);
        spinner_diet = (Spinner) findViewById(R.id.spinner_diet);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,array_spinner_diet);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_diet.setAdapter(adapter2);

        spinner_health.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                health = array_spinner_health[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_diet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                diet = array_spinner_diet[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAsyncTask task=new MyAsyncTask(MainActivity.this,edt.getText().toString(),to.getText().toString(),health,diet);
                bar.setVisibility(View.VISIBLE);
                if(internet_connection())
                    task.execute();
                else
                {
                    bar.setVisibility(View.INVISIBLE);
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Error");
                    alert.setMessage("Check your internet connectivity");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alert.setCancelable(true);
                        }
                    });
                    alert.create().show();
                }
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAsyncTask task=new MyAsyncTask(MainActivity.this,"random",to.getText().toString(),health,diet);
                bar.setVisibility(View.VISIBLE);
                if(internet_connection())
                    task.execute();
                else
                {
                    bar.setVisibility(View.INVISIBLE);
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Error");
                    alert.setMessage("Check your internet connectivity");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alert.setCancelable(true);
                        }
                    });
                    alert.create().show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Exit");
            alert.setMessage("Do you really want to exit ?");
            alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alert.setCancelable(true);
                }
            });
            alert.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_us) {
            Intent intent = new Intent(this,AboutUs.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.save_recipe) {
            Intent intent = new Intent(this,SaveYourRecipe.class);
            startActivity(intent);
        }
        else if(id == R.id.view_recipe){
            Intent intent = new Intent(this,SelfRecipes.class);
            startActivity(intent);
        }
        else if(id == R.id.get_meaning){
            Intent intent = new Intent(this,Meaning.class);
            startActivity(intent);
        }
        else if(id==R.id.contact){
            Intent intent = new Intent(this,SendMail.class);
            startActivity(intent);
        }
        else if(id==R.id.bookmarked){
            Intent intent = new Intent(this,BookmarkedRecipes.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyAsyncTask extends AsyncTask<String,Void,ArrayList<Recipes> >{

        Context context;
        String edit_text;
        String to;
        String healthy,diety;
        int flag1=0,flag2=0;
        MyAsyncTask(Context context,String edit_text,String to,String healthy,String diety)
        {
            this.edit_text=edit_text;
            this.context = context.getApplicationContext();
            this.to = to;
            if(healthy.equals("health-label")||healthy.equals(""))
                flag1=1;
            else
                this.healthy="&health="+healthy;
            if(diety.equals("diet-label")||diety.equals(""))
                flag2=1;
            else
                this.diety="&diet="+diety;
        }
        @Override
        protected ArrayList<Recipes> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            ArrayList<Recipes> recipes=new ArrayList<Recipes>();
            BufferedReader br=null;
            if(edit_text.equals(""))
                edit_text="random";
            if(to.equals(""))
                to="10";
            String string="https://api.edamam.com/search?q="+edit_text+"&app_id=a87152af&app_key=" +
                    "d7cb1074859d49f7af6db7eabf672ccc"+"&from=0&to="+to;
            if(flag1==0)
                string=string+this.healthy;
            if(flag2==0)
                string=string+this.diety;
            try {
                if(internet_connection()) {
                    URL url = new URL(string);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    StringBuffer buffer = null;
                    if (connection.getResponseCode() == 200) {
                        InputStream stream = connection.getInputStream();
                        br = new BufferedReader(new InputStreamReader(stream));
                        String abc = "";
                        buffer = new StringBuffer();
                        while ((abc = br.readLine()) != null) {
                            buffer.append(abc);
                        }
                        String str = buffer.toString();
                        if (str != null) {
                            try {
                                JSONObject obj = new JSONObject(str);
                                JSONArray hits = obj.getJSONArray("hits");
                                for (int i = 0; i < hits.length(); i++) {
                                    JSONObject obj1 = hits.getJSONObject(i);
                                    JSONObject obj2 = obj1.getJSONObject("recipe");

                                    String label = obj2.getString("label");
                                    String uri = obj2.getString("url");
                                    String image = obj2.getString("image");
                                    double cal = obj2.getDouble("calories");

                                    JSONArray health = obj2.getJSONArray("healthLabels");
                                    String health_label[] = new String[health.length()];
                                    for (int j = 0; j < health.length(); j++)
                                        health_label[j] = health.getString(j);

                                    JSONArray ingredient = obj2.getJSONArray("ingredientLines");
                                    String ingredient_line[] = new String[ingredient.length()];
                                    for (int j = 0; j < ingredient.length(); j++)
                                        ingredient_line[j] = ingredient.getString(j);

                                    Recipes single = new Recipes(label, uri, image, ingredient_line, health_label, cal);
                                    recipes.add(single);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "No options available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Server error occurred", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(MainActivity.this,"Check your internet connection",Toast.LENGTH_LONG).show();
                return recipes;

            } catch (MalformedURLException e) {
                Toast.makeText(context,"Malformed URL : "+e.getMessage(),Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context,"Some I/O Error Occured : "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(context,"Some error occurred : "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
            finally
            {
                if(connection!=null)
                    connection.disconnect();
                try
                {
                    if(br!=null)
                        br.close();
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"IOException occurred"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipes> recipes) {
            super.onPostExecute(recipes);
            if(recipes==null||recipes.size()>0)
            {
                Intent i = new Intent(context,RecipeList.class);
                i.putParcelableArrayListExtra("name",recipes);
                i.putExtra("q",edit_text);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }
            else {
                Toast.makeText(context, "No options available", Toast.LENGTH_LONG).show();
            }
            bar.setVisibility(View.INVISIBLE);
        }
    }

    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
