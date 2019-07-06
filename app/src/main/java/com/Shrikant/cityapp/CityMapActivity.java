package com.Shrikant.cityapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CityMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView img_back;
    double lon,lat;
    String city;
    TextView txt_cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        img_back=(ImageView)findViewById(R.id.img_back);
        txt_cityname=(TextView) findViewById(R.id.txt_cityname);

        Intent intent=getIntent();
        // RETRIVING DATA FROM MAINACTIVITY TO GET LAT AND LON FOR LOCATION ON MAP AND CITYNAME //

         city=intent.getStringExtra("cityname");
        String image=intent.getStringExtra("cityimage");
         lon= Double.parseDouble(intent.getStringExtra("citylon"));
         lat= Double.parseDouble(intent.getStringExtra("citylat"));

        txt_cityname.setText(city);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CLOSING ACITVITY USING FINISH //
                finish();
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng location = new LatLng(lon, lat);
        mMap.addMarker(new MarkerOptions().position(location).title(city));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
