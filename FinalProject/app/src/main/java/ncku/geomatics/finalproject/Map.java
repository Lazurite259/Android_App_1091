package ncku.geomatics.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class Map extends AppCompatActivity implements
        LocationListener,
        OnMapReadyCallback {

    Marker locationMarker, currentLocationMarker;
    SQLiteDatabase db = null;
    Cursor c = null;
    LatLngBounds.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle("殲滅地精大作戰");

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

        db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        c = db.rawQuery("SELECT * FROM table1", null);
    }

    Location currentLocation;
    LatLng currentLatLng;

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
//            builder.include(currentLocationMarker.getPosition());
//            for (int i = 0; i < c.getCount(); i++) {
//                targetLocation.setLatitude(Double.parseDouble(targetLat[i]));
//                targetLocation.setLongitude(Double.parseDouble(targetLng[i]));
//                float distance = currentLocation.distanceTo(targetLocation);
//                if (distance <= 15) {
//                    target = i + 1;
//                    //從googleMap畫面換到打地鼠的畫面
//                    Intent it = new Intent();
//                    it.setClass(this, Game.class);
//                    startActivityForResult(it, 123);
//                }
//            }
        }
    }

    GoogleMap gMap = null;
    Location targetLocation;
    int target;
    String[] targetLat = new String[0];
    String[] targetLng = new String[0];

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        StringBuilder str = new StringBuilder();
        try {
            String place;
            int i;
            builder = new LatLngBounds.Builder();
            for (i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i);
                place = c.getString(1);
                str.append(place);
                List<Address> address = geo.getFromLocationName(place, 1);
                Address address1 = address.get(0);
                LatLng newLatLng = new LatLng(address1.getLatitude(), address1.getLongitude());
                if (gMap != null) {
                    MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                    markerOptions.position(newLatLng).title(place);
                    locationMarker = gMap.addMarker(markerOptions);
                    builder.include(locationMarker.getPosition());
                }
//                targetLat[i] = String.valueOf(address1.getLatitude());
//                targetLng[i] = String.valueOf(address1.getLongitude());
//                targetLocation = new Location("");
//                targetLocation.setLatitude(address1.getLatitude());
//                targetLocation.setLongitude(address1.getLongitude());
            }
            LatLngBounds latLngBounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, width, width, 25));
        } catch (Exception e) {
            str.append(e.toString());
            ((TextView) findViewById(R.id.textView6)).setText(str);
        }
    }

    void update(String mode, int _id) {
        ContentValues cv = new ContentValues(1);
        cv.put("mode", mode);
        db.update("table1", cv, "_id=" + _id, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            update("true", target);
        } else if (requestCode == 123 && resultCode == RESULT_CANCELED) {
        }
    }
}