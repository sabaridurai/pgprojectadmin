package com.example.transportadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class fragment_home extends Fragment {
    TextView textView,textView1,textView2;
Button button;

long count=0;
    Set<String> hash_Set = new HashSet<String>();
    Set<String> vehicle=new HashSet<String>();
    Set<String> vehicle1=new HashSet<String>();
    StringBuilder sb=new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

textView= (TextView) view.findViewById(R.id.drivercount);
textView1=(TextView) view.findViewById(R.id.vehiclecount);
textView2=(TextView) view.findViewById(R.id.routescount);
button=view.findViewById(R.id.button);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(requireActivity(),MapsActivity.class);
        startActivity(intent);

    }
});





//driver counth
firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("drivers")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //Log.e(snapshot.getKey(),snapshot.getKey() + "");

                        hash_Set.add(snapshot.getKey());
                        int i=hash_Set.size();
                        textView.setText(String.valueOf(i));
                     //   Log.e("=====", String.valueOf(hash_Set));


                        }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        hash_Set.remove(snapshot.getKey());
                        int i=hash_Set.size();
                        textView.setText(String.valueOf(i));
                        Log.e("_+_++", String.valueOf(hash_Set));
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
//Bus count
            firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("vehicle")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.e(snapshot.getKey(),snapshot.getKey() + "");

                        vehicle.add(snapshot.getKey());
                        int i=vehicle.size();
                     //   Toast.makeText(requireActivity(),String.valueOf(vehicle),Toast.LENGTH_SHORT).show();
                        textView1.setText(String.valueOf(i));
//                        Log.e("_+_++", String.valueOf(vehicle));


                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        vehicle.remove(snapshot.getKey());
                        int i=vehicle.size();
                        textView1.setText(String.valueOf(i));
//                        Log.e("_+_++", String.valueOf(vehicle));
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });




            //routes added vehicle count
        firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("routes").addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.e(snapshot.getKey(),snapshot.getKey() + "");

                        vehicle1.add(snapshot.getKey());
                        int i=vehicle1.size();
                        //   Toast.makeText(requireActivity(),String.valueOf(vehicle),Toast.LENGTH_SHORT).show();
                        textView2.setText(String.valueOf(i));
//                        Log.e("_+_++", String.valueOf(vehicle));


                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        vehicle1.remove(snapshot.getKey());
                        int i=vehicle1.size();
                        textView2.setText(String.valueOf(i));
//                        Log.e("_+_++", String.valueOf(vehicle));
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });




        return view;
    }
}