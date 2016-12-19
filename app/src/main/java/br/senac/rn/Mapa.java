package br.senac.rn;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        SupportMapFragment mapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_view);
        mapa.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
//        location = (Location) intent.get("localizacao");
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 111);
//        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//        System.out.println(location.getAltitude());

        args = getIntent().getParcelableExtra("args");
        LatLng localizacao = args.getParcelable("localizacao");
        mMap.addMarker(new MarkerOptions().position(localizacao).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));

    }

}
