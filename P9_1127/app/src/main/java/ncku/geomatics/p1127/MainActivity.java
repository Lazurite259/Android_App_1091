package ncku.geomatics.p1127;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

//Step 1
public class MainActivity extends AppCompatActivity
        implements LocationListener,
        OnMapReadyCallback,
        SensorEventListener {

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
        } else if (item.getItemId() == R.id.item3) {
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17));
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

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor srA = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor srM = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sm.registerListener(this, srA, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, srM, SensorManager.SENSOR_DELAY_NORMAL);
    }

    Location currentLocation;
    LatLng currentLatLng;
    Marker currentLocationMarker;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        currentLocation = location;
        currentLatLng = new LatLng(lat, lng);
        if (gMap != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
            if (currentLocationMarker != null) {
                currentLocationMarker.remove();
            }
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation));
            markerOptions.position(currentLatLng).title("目前位置");
            currentLocationMarker = gMap.addMarker(markerOptions);
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
        }
    }

    Marker searchLocationMarker;
    double direction = 0;

    public void onClickSearch(View v) {
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        try {
            String place = ((EditText) findViewById(R.id.editTextLocation)).getText().toString();
            List<Address> address = geo.getFromLocationName(place, 1);
            Address address1 = address.get(0);
            LatLng newLatLng = new LatLng(address1.getLatitude(), address1.getLongitude());
            if (gMap != null) {
                if (searchLocationMarker != null) {
                    searchLocationMarker.remove();
                }
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                markerOptions.position(newLatLng).title(place);
                searchLocationMarker = gMap.addMarker(markerOptions);
                LatLngBounds.Builder builder = new LatLngBounds.Builder()
                        .include(searchLocationMarker.getPosition())
                        .include(currentLocationMarker.getPosition());
                LatLngBounds latLngBounds = builder.build();
                gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 80));
                Location searchLocation = new Location("");
                searchLocation.setLatitude(address1.getLatitude());
                searchLocation.setLongitude(address1.getLongitude());
                float distance = currentLocation.distanceTo(searchLocation);
                ((TextView) findViewById(R.id.textViewDistance)).setText(distance + " m");
                direction = Math.atan2(address1.getLongitude() - currentLatLng.longitude, address1.getLatitude() - currentLatLng.latitude);
                direction = Math.toDegrees(direction);
                if (direction < 0) {
                    direction += 360;
                }
                currentLocationMarker.setRotation(angle + (float) direction - 180);
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

    float[] mValues = null;
    float[] aValues = null;
    float angle;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            aValues = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mValues = event.values;
        }
        if (mValues != null && aValues != null && currentLocationMarker != null) {
            float[] Rotation = new float[9];
            float[] degree = new float[3];
            SensorManager.getRotationMatrix(Rotation, null, aValues, mValues);
            SensorManager.getOrientation(Rotation, degree);
            angle = (float) Math.toDegrees(degree[0]);
            if ((angle + "").equals("-0.0")) {
                angle = 180;
            }
            if (direction != 0) {
                currentLocationMarker.setRotation(angle + (float) direction - 180);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}