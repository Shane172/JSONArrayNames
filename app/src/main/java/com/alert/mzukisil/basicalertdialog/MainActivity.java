package com.alert.mzukisil.basicalertdialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private  String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private  static String url ="https://jsonplaceholder.typicode.com/users";
    private  static final String USERNAME= "Samantha";

    ArrayList<HashMap<String, String>> listNames;
    ArrayList<HashMap<String, String>> emailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNames = new ArrayList<>();
        emailList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        Button btnNames= (Button) findViewById(R.id.listName);
        Button btnEmail= (Button) findViewById(R.id.emailShow);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  EmailForUserName().execute();
            }
        });

        btnNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetNames().execute();
            }
        });
        //new GetNames().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public class GetNames extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
             String jsonStr= sh.serviceCall(url);
            Log.e(TAG,"Response From Url "+jsonStr);
            if(jsonStr!=null){

                try {
                   // JSONObject= new
                 //   JSONObject jsonObj = new JSONObject(jsonStr);
                 //  JSONArray contacts = jsonObj.getJSONArray("");
                   JSONArray allNames = new JSONArray(jsonStr);



                   for (int i=0; i<allNames.length();i++){

                       JSONObject c = allNames.getJSONObject(i);
                       String id =c.getString("id");
                       String name= c.getString("name");
                      // String email= c.getString("email");

                       HashMap<String, String> names = new HashMap<>();
                       names.put("id",id);
                       names.put("name", name);
                      // names.put("email",email);

                       listNames.add(names);

                   }
                }catch (final JSONException e){
                    Log.e(TAG,"Json Parsing Error:" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"JSON parsing error :"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else {
                Log.e(TAG, "Could not get JSOn from Server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Could not get JSON from server. check LogCat for possible error",Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void results) {
            super.onPostExecute(results);

            /*
            * Updating parsed JSON data into view
             */

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,listNames,R.layout.list_item,new String[]{"name","email","username"},new int[]{R.id.name,R.id.email,R.id.username}
            );
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class EmailForUserName extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr= sh.serviceCall(url);
            Log.e(TAG,"Response From Url "+jsonStr);
            if(jsonStr!=null){



                try {
                    // JSONObject= new
                    //   JSONObject jsonObj = new JSONObject(jsonStr);
                    //  JSONArray contacts = jsonObj.getJSONArray("");
                    JSONArray emailShows = new JSONArray(jsonStr);

                //    ArrayList<HashMap<String, String>> emailList = new ArrayList<HashMap<String, String>>();

                    for (int i=0; i<emailShows.length();i++){

                        JSONObject c = emailShows.getJSONObject(i);
                      // HashMap<String, String> map = emailShows.get(i);


                     if(c.toString().contains(USERNAME)){
                            String id =c.getString("id");
                            String email= c.getString("email");

                            HashMap<String, String> emails = new HashMap<>();
                            emails.put("id",id);
                            emails.put("email",email);
                        emailList.add(emails);


                      }


                    }
                }catch (final JSONException e){
                    Log.e(TAG,"Json Parsing Error:" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"JSON parsing error :"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else {
                Log.e(TAG, "Could not get JSOn from Server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Could not get JSON from server. check LogCat for possible error",Toast.LENGTH_LONG).show();
                    }
                });
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

             /*
            * Updating parsed JSON data into view
             */

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,emailList,R.layout.list_item,new String[]{"email"},new int[]{R.id.email}
            );
            listView.setAdapter(adapter);
        }

        }


}
