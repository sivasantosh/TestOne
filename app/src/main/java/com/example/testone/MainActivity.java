package com.example.testone;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mPlantListView;
    private PlantListAdapter mAdapter;
    private ArrayList<PlantItem> mPlants = new ArrayList<PlantItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlantListView = (RecyclerView) findViewById(R.id.plantListView);
        mPlantListView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new PlantListAdapter(mPlants);
        mPlantListView.setAdapter(mAdapter);

        new LoadDataTask().execute("https://gist.githubusercontent.com/edwingsm/11368543/raw/dd30694a3b176606f025c33b5e3a9edc6e300c51/plants.json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    class LoadDataTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            JSONObject obj = getJSONFromUrl(params[0]);

            try {
                JSONObject cat = obj.getJSONObject("CATALOG");
                JSONArray plant = cat.getJSONArray("PLANT");

                for (int i = 0; i < plant.length(); i++) {
                    JSONObject obj1 = plant.getJSONObject(i);

                    PlantItem item = new PlantItem(
                            obj1.getString("COMMON")
                            , obj1.getString("BOTANICAL")
                            , obj1.getString("ZONE")
                            , obj1.getString("LIGHT")
                            , obj1.getString("PRICE")
                            , obj1.getString("AVAILABILITY")
                    );

                    mPlants.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private JSONObject getJSONFromUrl (String Url) {
        try {
            URL url = new URL(Url);
            InputStream in;
            in = url.openConnection().getInputStream();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder strBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                strBuilder.append(inputStr);
            }

            JSONObject obj;
            obj = new JSONObject(strBuilder.toString());

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}