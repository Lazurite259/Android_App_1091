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
import com.google.android.gms.maps.model.LatLng;
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
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                item.setChecked(true);
            } else {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                item.setChecked(false);
            }
        }
        else if (item.getItemId() == R.id.item2) {
            if (!item.isChecked()) {
                map.setTrafficEnabled(true);
                item.setChecked(true);
            } else {
                map.setTrafficEnabled(false);
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
        ((TextView) findViewById(R.id.textView)).setText(provider);
        locationManager.requestLocationUpdates(provider, 5000, 5, this);

        SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        smf.getMapAsync(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Step 3
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        double alt = location.getAltitude();

        ((EditText) findViewById(R.id.editTextNumberLat)).setText(lat + "");
        ((EditText) findViewById(R.id.editTextNumberLong)).setText(lng + "");
        ((EditText) findViewById(R.id.editTextNumberAlt)).setText(alt + "");

        if (map != null) {
            LatLng currentLocation = new LatLng(lat, lng);
            map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            map.clear();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation).title("NCKU");
            map.addMarker(markerOptions);
        }
    }

    public void onClick(View v) {
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        try {
            double lat = Double.parseDouble(((EditText) findViewById(R.id.editTextNumberLat)).getText().toString());
            double lng = Double.parseDouble(((EditText) findViewById(R.id.editTextNumberLong)).getText().toString());
            List<Address> address = geo.getFromLocation(lat, lng, 1);
//            address = geo.getFromLocationName("台北車站", 1);
//            String str = address.get(0);
            Address address1 = address.get(0);
//            String str = address1.getLatitude() + ", " + address1.getLongitude();
            String str = "";
            for (int i = 0; i <= address1.getMaxAddressLineIndex(); i++) {
                str += address1.getAddressLine(i);
            }
            ((TextView) findViewById(R.id.textView)).setText(str);
        } catch (Exception e) {
            ((TextView) findViewById(R.id.textView)).setText(e.toString());
        }
    }

    GoogleMap map = null;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(23, 120.22)));
        map.moveCamera(CameraUpdateFactory.zoomTo(17));
    }
}