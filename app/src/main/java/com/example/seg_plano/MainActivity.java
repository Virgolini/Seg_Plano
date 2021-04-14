package com.example.seg_plano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.maps.MapView;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private LocationManager ubicacion;
    TextView text1,txt2;
    Button btn1;
    //Bundle bundle = new Bundle()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.btnmapa);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                __cambiar(v);
            }
        });
        text1 = (TextView) findViewById(R.id.tv1);
        txt2 = (TextView)findViewById(R.id.tv2);
        String log = String.valueOf(text1), lat = String.valueOf(txt2);
        localizar();
        __registrarloc();
    }


    public void localizar() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 900);
        }
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ubicacion != null && ubicacion == null) {
            String latitud = String.valueOf(loc.getLatitude());
            String longitud = String.valueOf(loc.getLongitude());
            text1.setText(latitud);
            txt2.setText(longitud);
            Log.d("Longitud", latitud);
            Log.d("Latitud", longitud);
            Toast.makeText(MainActivity.this, "Latitud: " + latitud+"\n"+"Longitud: "+ longitud, LENGTH_SHORT).show();
        }
    }

    private void __registrarloc() {
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new LocalListener());
    }

    public class LocalListener implements LocationListener{
        @Override
        public void onLocationChanged(@NonNull Location location) {
            String latitud = String.valueOf(location.getLatitude());
            String longitud = String.valueOf(location.getLongitude());
            Log.d("Longitud",longitud);
            Log.d("Latitud",latitud);
            text1.setText(latitud);
            txt2.setText(longitud);
            Toast.makeText(MainActivity.this, "Latitud: " + latitud+"\n"+"Longitud: "+ longitud, LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }
    public void __cambiar(View view){
        Intent sig = new Intent(this, MapsActivity.class);
        startActivity(sig);
    }
}