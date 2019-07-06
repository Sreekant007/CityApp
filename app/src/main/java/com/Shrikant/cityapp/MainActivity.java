package com.Shrikant.cityapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.Shrikant.cityapp.Models.CityModels;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    ListView list_city;
    CityAdapter cityAdapter;
    ArrayList<CityModels> cityModelsArrayList=new ArrayList<CityModels>();
    ProgressBar progress_bar;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_city=(ListView)findViewById(R.id.list_city);
        progress_bar=(ProgressBar)findViewById(R.id.progress_bar);

        /*API CALL*/
        // NOTE ALL DATA USED IN THIS APP ARE DUMMY AND ARE CREATED USING MOCKUP API //
        //DATA SHOULD NOT BE CONSIDER FOR LOCATION ON MAP //
        // LAT AND LON ARE NOT CORECT //
        CityList();


    }

    public void CityList() {
        cityModelsArrayList.clear();
        progress_bar.setVisibility(View.VISIBLE);
        //APPCONSTANT CONTAINS LIST OF API THAT ARE BEEN CALLED //
        StringRequest strReq = new StringRequest(Request.Method.GET, AppConstant.cities, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                Log.e("jobj", "" + response);
                try {
                    JSONArray jsonArray =new JSONArray(response);
                    if(response==""){
                        progress_bar.setVisibility(View.GONE);

                    }else {

                        progress_bar.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            city = jsonObject.getString("cityname");
                            String countryname = jsonObject.getString("countryname");
                            String id = jsonObject.getString("id");
                            String lat = jsonObject.getString("lat");
                            String lon = jsonObject.getString("lon");
                            String image = jsonObject.getString("cityimage");


                            /*ADDING DATA TO ARRAYLIST*/
                            CityModels cityModels = new CityModels();
                            cityModels.setCityName(city);
                            cityModels.setCountry(countryname);
                            cityModels.setLat(lat);
                            cityModels.setLon(lon);
                            cityModels.setImage(image);
                            cityModelsArrayList.add(cityModels);

                        }

                        /*CREATING LIST ADAPTER AN SETTING THAT ADAPTER TO LISTVIEW*/
                        cityAdapter = new CityAdapter(MainActivity.this, cityModelsArrayList);
                        list_city.setAdapter(cityAdapter);
                        progress_bar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progress_bar.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("error ", error.toString());
                progress_bar.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }


    public class CityAdapter extends ArrayAdapter<CityModels> {
        ArrayList<CityModels> CityModels = new ArrayList<CityModels>();
        private Context mcontext;
        public TextView txt_city, txt_country;

        public CityAdapter(@NonNull Context context, ArrayList<CityModels> CityModels) {
            super(context, R.layout.citylist_adapter, CityModels);
            this.mcontext = context;
            this.CityModels = CityModels;

        }

        @Override
        public void clear() {
            super.clear();

        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public CityModels getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, View view1, ViewGroup arg2) {

            View view = view1;

            try {
                if (view1 == null) {
                    LayoutInflater inflat = (LayoutInflater) mcontext
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflat.inflate(R.layout.citylist_adapter, null);
                    txt_country = (TextView) view.findViewById(R.id.txt_country);
                    txt_city = (TextView) view.findViewById(R.id.txt_city);


                }

                txt_city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this,CityMapActivity.class);

                        /*STORING DATA INTO INTENT TO PASS IN ANOTHER ACTIVITY*/

                        intent.putExtra("cityname",CityModels.get(position).getCityName());
                        intent.putExtra("cityimage",CityModels.get(position).getImage());
                        intent.putExtra("citylat",CityModels.get(position).getLat());
                        intent.putExtra("citylon",CityModels.get(position).getLon());

                        /*JUMPING INTO ANOTHER ACTIVITY USING INTENT*/
                        startActivity(intent);
                    }
                });
                txt_city.setText(CityModels.get(position).getCityName());
                txt_country.setText(CityModels.get(position).getCountry());
            } catch (Exception e) {

            }
            return view;
        }
    }



}


