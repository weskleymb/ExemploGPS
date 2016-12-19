package br.senac.rn;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class Principal extends Activity implements LocationListener {

    private final int TIME = 1000;
    private final float DISTANCE = 1f;

    private LocationManager manager;
    private Location location;
    private EditText etLongitude, etLatitude;
    private Button btVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setComponents();
        setEvents();
    }

    private void setComponents() {

        etLongitude = (EditText) findViewById(R.id.etLongitude);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        btVer = (Button) findViewById(R.id.btVer);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = manager.getBestProvider(new Criteria(), false);

        if (provider != null && !provider.equals("")) {

            String access_fine = Manifest.permission.ACCESS_FINE_LOCATION;
            String access_coarse = Manifest.permission.ACCESS_COARSE_LOCATION;
            int permission = PackageManager.PERMISSION_GRANTED;

            if (ActivityCompat.checkSelfPermission(this, access_fine) != permission) {
                String[] ACCESS_FINE_LOCATION = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, ACCESS_FINE_LOCATION, permission);
                return;
            }

            if (ActivityCompat.checkSelfPermission(this, access_coarse) != permission) {
                String[] ACCESS_COARSE_LOCATION = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, ACCESS_COARSE_LOCATION, permission);
                return;
            }

            manager.requestLocationUpdates(provider, TIME, DISTANCE, this);
            location = manager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getApplicationContext(), "Localização não encontrada", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Provedor não encontrado", Toast.LENGTH_LONG).show();
        }
    }

    private void setEvents() {
        btVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng posicao = new LatLng(location.getLatitude(), location.getLongitude());

                Bundle args = new Bundle();
                args.putParcelable("localizacao", posicao);

                Intent intent = new Intent(Principal.this, Mapa.class);
                intent.putExtra("args", args);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        etLongitude.setText(String.valueOf(location.getLongitude()));
        etLatitude.setText(String.valueOf(location.getLatitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

}
