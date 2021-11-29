package com.example.GroupProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// Implement OnMapReadyCallback.
public class GetLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LatLng location;
    private Marker marker;

    private ImageButton btnBack;
    private ImageButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_get_location);

        String username = ((GroupProject) this.getApplication()).getUsername();

        btnConfirm = findViewById(R.id.btnConfirmEditLocation);
        btnBack = findViewById(R.id.btnBackEditLocation);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        HabitEvent event = (HabitEvent) intent.getSerializableExtra("EVENT");
        Habit habit = (Habit) intent.getSerializableExtra("HABIT");

        location = new LatLng(event.getLatitude(), event.getLongitude());

        btnBack.setOnClickListener(view -> {
            Intent intentBack = new Intent();
            intentBack.putExtra("EVENT", event);
            setResult(AddEventActivity.REQUEST_LOCATION_CHANGE, intentBack);
            finish();
            return;
        });

        btnConfirm.setOnClickListener(view -> {
            event.setLatitude(location.latitude);
            event.setLongitude(location.longitude);

            Intent intentConfirm = new Intent();
            intentConfirm.putExtra("EVENT", event);
            setResult(AddEventActivity.REQUEST_LOCATION_CHANGE, intentConfirm);
            finish();
            return;
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add the marker to the map
        marker = googleMap.addMarker(new MarkerOptions()
                .position(location)
                .draggable(true)
                .title("Event"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                location = marker.getPosition();
            }
        });
    }
}