package com.example.transportadmin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.transportadmin.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private GoogleMap mMap;
    public String latitudeval,longitudeval,driverid;
 private final Set<String> hash_Set = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.transportadmin.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemapfragment);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
Fungetdata();

    }


    private void Fungetdata() {
        firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("drivers")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //Log.e(snapshot.getKey(),snapshot.getKey() + "");

                        hash_Set.add(snapshot.getKey());
                        int i=hash_Set.size();
                        List<String> list = new ArrayList<>(hash_Set);
                        int j;
                      for(j=0;j<i;j++){
                          driverid=String.valueOf(list.get(j));
                          firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("drivers").child(list.get(j)).child("location")
                                  .addValueEventListener(new ValueEventListener() {


                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot snapshot) {




                                          firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("drivers").child(driverid).child("location").child("latitude").addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                  latitudeval=String.valueOf(snapshot.getValue());
                                                  Log.e("latitude",latitudeval);


                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {

                                              }

                                          });
                                          firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("drivers").child(driverid).child("location").child("longitude").addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                  longitudeval=String.valueOf(snapshot.getValue());
                                                  Log.e("longitude",longitudeval);



                                                  LatLng place = new LatLng(Double.parseDouble(latitudeval), Double.parseDouble(longitudeval));
//                                                if(place!=null){ mMap.remove();}

                                                  final MarkerOptions marker = new MarkerOptions().position(place).title("vehicle");
                                                  mMap.clear();
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {

                                              }


                                          });

                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError error) {

                                      }

                                  });
                      }
//                        Toast.makeText(MapsActivity.this, String.valueOf( hash_Set),Toast.LENGTH_SHORT).show();
//                     Funputlatlon(hash_Set);


                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        hash_Set.remove(snapshot.getKey());
                        int i=hash_Set.size();
                        Toast.makeText(MapsActivity.this, String.valueOf( hash_Set),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });



    }


}