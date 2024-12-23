package com.example.transportadmin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class addroot extends AppCompatActivity {
    private TextView vehiclelist,driverList;
    private EditText vname,from,to,up,down,Dname;
    private FirebaseAuth firebaseAuth;
private FirebaseDatabase firebaseDatabase;
private RadioGroup radioGroup1,radioGroup2;
    Set<String> driverli = new HashSet<>();
String driverid;
    private final Set<String> vehicle= new HashSet<>();
    private final Set<String> drivernameli= new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addroot);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){}
firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        vname=findViewById(R.id.vehiclename);
        driverList=findViewById(R.id.driverlist);
        from=findViewById(R.id.from);
        Dname=findViewById(R.id.namefield);
        to=findViewById(R.id.to);
        up=findViewById(R.id.uptime);
        down=findViewById(R.id.downtime);
        Button button = findViewById(R.id.add);

radioGroup1=findViewById(R.id.uprg);
radioGroup2=findViewById(R.id.downrg);
        vehiclelist=findViewById(R.id.vehiclelist);
        //driverlist

        firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("drivers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                driverli.add(snapshot.getKey());

                int i = driverli.size();
                List<String> list = new ArrayList<>(driverli);
                int j;
                for (j = 0; j < i; j++) {
                    driverid = String.valueOf(list.get(j));
//                    Log.e("dddddid",driverid+list.size()+' '+driverli);
                    firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("drivers").child(driverid).child("Name")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String dname= String.valueOf(snapshot.getValue());
                                    Log.e("xxxx", String.valueOf(snapshot));
                                    HashMap<String, String> mp=new HashMap<>();
                                    mp.put(dname,driverid);
                                    drivernameli.add(dname);

                                    driverList.setText(String.valueOf(drivernameli));
                                    firebaseDatabase.getReference("owner").child(firebaseAuth.getUid()).child("wokingdrivers").setValue(mp);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //vehicle list
  firebaseDatabase.getReference("owner").child(Objects.requireNonNull(firebaseAuth.getUid())).child("vehicle")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
String vehiclename=snapshot.getKey();
                        assert vehiclename != null;
                        String remove1=vehiclename.replace("[","");
String remove2=remove1.replace("]","");
                        vehicle.add(remove2);



                        vehiclelist.setText(String.valueOf(vehicle));



                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {


                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
  button.setOnClickListener(view -> {
      Map<String, String> details = new HashMap<>();
      String name1=vname.getText().toString();
      String from1=from.getText().toString();
      String to1=to.getText().toString();
      String up1=up.getText().toString();



      String down1=down.getText().toString();
      if(!TextUtils.isEmpty(name1)&&!TextUtils.isEmpty(from1)&&!TextUtils.isEmpty(to1)&&!TextUtils.isEmpty(up1)&&!TextUtils.isEmpty(down1)){
          int upid=radioGroup1.getCheckedRadioButtonId();
          int downid=radioGroup2.getCheckedRadioButtonId();
          RadioButton upradioButton=findViewById(upid);
          RadioButton downradioButton=findViewById(downid);


          Log.e("=====___",upradioButton.getText().toString()+"+++"+downradioButton.getText().toString());

       details.put("Dname",Dname.getText().toString());
          details.put("from",from1);
          details.put("to",to1);
          details.put("up",up1+" "+upradioButton.getText().toString());
          details.put("down",down1+" "+downradioButton.getText().toString());



          firebaseDatabase.getReference("owner").child(firebaseAuth.getUid()).child("routes").child(name1).setValue(details).addOnCompleteListener(task -> {
         if (task.isSuccessful()){
             Toast.makeText(addroot.this,"Added successfully",Toast.LENGTH_SHORT).show();
         }
         else
         {
             Toast.makeText(addroot.this,""+task.getException(),Toast.LENGTH_SHORT).show();
         }
          });


      }
      else
      {
          Toast.makeText(addroot.this,"Empty value",Toast.LENGTH_SHORT).show();
      }



  });




    }
}