package br.senac.rn;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends Activity implements LocationListener {

    private final int TIME = 1000;
    private final float DISTANCE = 1f;

    private LocationManager locationManager;
    private EditText etLongitude, etLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setComponents();
    }

    private void setComponents() {

        etLongitude = (EditText) findViewById(R.id.etLongitude);
        etLatitude = (EditText) findViewById(R.id.etLatitude);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);

        if (!(provider == null && provider.equals(""))) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] ACCESS_FINE_LOCATION = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                return;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] ACCESS_COARSE_LOCATION = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                return;
            }

            locationManager.requestLocationUpdates(provider, TIME, DISTANCE, this);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getApplicationContext(), "Localização não encontrada", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Provedor não encontrado", Toast.LENGTH_LONG).show();
        }
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
