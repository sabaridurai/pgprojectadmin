package com.example.transportadmin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class addrecord extends AppCompatActivity {
    private EditText transportname1, ownername1, phno1, mail1, transportID1, vehiclename, regno1, regno2, regno3, regno4;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private View scroller;
    private String transportnamevalue;
    private String ownernamevalue;
    private String phnovalue;
    private String mailvalue;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecord);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            //
        }


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ownerreference = (DatabaseReference) firebaseDatabase.getReference();

        Button addvehicle = findViewById(R.id.addvehicle);
        Button idgenerate = findViewById(R.id.idgrnerate);
        Button savebtn = findViewById(R.id.savebtn);

        transportname1 = findViewById(R.id.transportname);
        ownername1 = findViewById(R.id.ownername);
        phno1 = findViewById(R.id.phno);
        mail1 = findViewById(R.id.mail);
        transportID1 = findViewById(R.id.transportid);
        scroller = findViewById(R.id.viwe);
        vehiclename = findViewById(R.id.vehiclename);
        regno1 = findViewById(R.id.regno1);
        regno2 = findViewById(R.id.regno2);
        regno3 = findViewById(R.id.regno3);
        regno4 = findViewById(R.id.regno4);
        progressDialog = new ProgressDialog(addrecord.this);

        idgenerate.setOnClickListener(view -> {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                transportnamevalue = transportname1.getText().toString();
                ownernamevalue = ownername1.getText().toString();
                phnovalue = phno1.getText().toString();
                mailvalue = mail1.getText().toString();


                if (firebaseAuth.getCurrentUser() != null) {
                    if (!TextUtils.isEmpty(transportnamevalue) && !TextUtils.isEmpty(ownernamevalue) && !TextUtils.isEmpty(phnovalue) && !TextUtils.isEmpty(mailvalue)) {
                        progressDialog.setMessage("Creating..");
                        progressDialog.show();
                        transportID1.setText(String.valueOf(firebaseAuth.getUid()));
                        //String transportidvalue = transportID1.getText().toString();
                        Map<String, String> map = new HashMap<>();
                        map.put("Transportname", transportnamevalue);
                        map.put("Ownername", ownernamevalue);
                        map.put("Phone", phnovalue);
                        map.put("Mail", mailvalue);
                        ownerreference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("Transportdetail").setValue(map).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(addrecord.this, "User ID creation success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(addrecord.this, "write Error Accoured", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        });
                    } else {
                        Toast.makeText(addrecord.this, "Empty Value", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    startActivity(new Intent(addrecord.this, adminlogin.class));
                    finish();
                }
            } else {
                Toast.makeText(addrecord.this, "Connection problem pleasecheck", Toast.LENGTH_SHORT).show();
            }
        });


        addvehicle.setOnClickListener(view -> scroller.setVisibility(view.getVisibility()));
        savebtn.setOnClickListener(view -> {
            DatabaseReference ownerref = firebaseDatabase.getReference("owner");
            String vName = vehiclename.getText().toString();
            String one = regno1.getText().toString().toUpperCase(Locale.ROOT);
            String two = regno2.getText().toString();
            String three = regno3.getText().toString().toUpperCase(Locale.ROOT);
            String four = regno4.getText().toString();
            if (!TextUtils.isEmpty(vName) && !TextUtils.isEmpty(one) && !TextUtils.isEmpty(two) && !TextUtils.isEmpty(three) && !TextUtils.isEmpty(four)) {
                progressDialog.setMessage("Saving..");
                progressDialog.show();
                Map<String, String> map1 = new HashMap<>();
                map1.put("regone", one);
                map1.put("regtwo", two);
                map1.put("regthree", three);
                map1.put("regfour", four);
                ownerref.child(Objects.requireNonNull(firebaseAuth.getUid())).child("vehicle").child(vName).setValue(map1).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(addrecord.this, "Details are added", Toast.LENGTH_SHORT).show();
                         vehiclename.getText().clear();
                      regno1.getText().clear();
                         regno2.getText().clear();
                       regno3.getText().clear();
                        regno4.getText().clear();
                    } else {
                        Toast.makeText(addrecord.this, (CharSequence) task1.getException(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });


            } else {
                Toast.makeText(addrecord.this, "Empty value", Toast.LENGTH_SHORT).show();
            }

        });

    }
}