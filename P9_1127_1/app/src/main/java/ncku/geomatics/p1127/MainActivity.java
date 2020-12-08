package ncku.geomatics.p1127;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

//Step 1
public class MainActivity extends AppCompatActivity
        implements LocationListener, OnMapReadyCallback {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            if (!item.isChecked()) {
                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                item.setChecked(true);
            } else {
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                item.setChecked(false);
            }
        } else if (item.getItemId() == R.id.item2) {
            if (!item.isChecked()) {
                gMap.setTrafficEnabled(true);
                item.setChecked(true);
            } else {
                gMap.setTrafficEnabled(false);
                item.setChecked(false);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Step 2
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
            return;
        }
        String provider = locationManager.getBestProvider(new Criteria(), true);
        locationManager.requestLocationUpdates(provider, 5000, 5, this);

        SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        smf.getMapAsync(this);
    }

    LatLng currentLocation;
    Marker currentLocationMarker;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        currentLocation = new LatLng(lat, lng);
        if (gMap != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            if (currentLocationMarker != null) {
                currentLocationMarker.remove();
            }
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            markerOptions.position(currentLocation).title("目前位置");
            currentLocationMarker = gMap.addMarker(markerOptions);
            gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    Marker searchLocationMarker;

    public void onClickSearch(View v) {
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        try {
            String place = ((EditText) findViewById(R.id.editTextLocation)).getText().toString();
            List<Address> address = geo.getFromLocationName(place, 1);
            Address address1 = address.get(0);
            LatLng newLocation = new LatLng(address1.getLatitude(), address1.getLongitude());
            if (gMap != null) {
                gMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
                if (searchLocationMarker != null) {
                    searchLocationMarker.remove();
                }
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                markerOptions.position(newLocation).title(place);
                searchLocationMarker = gMap.addMarker(markerOptions);
                gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        } catch (Exception e) {
        }
    }

    GoogleMap gMap = null;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(23, 120.22)));
    }
}