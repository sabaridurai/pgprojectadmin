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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class
adminlogin extends AppCompatActivity {
    private ProgressDialog progressDialog;
    EditText adminmail, adminpassword;
    Button button;
    String id, pass;
    TextView movetosignup;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            //
        }

        firebaseAuth = FirebaseAuth.getInstance();
        adminmail = findViewById(R.id.adminmail);
        adminpassword = findViewById(R.id.adminpassword);
        button = findViewById(R.id.adminloginbtn);
        movetosignup=findViewById(R.id.movetosignup);

        progressDialog = new ProgressDialog(adminlogin.this);


        //move to singnup
        movetosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveactivity=new Intent(adminlogin.this,adminsignup.class);
                startActivity(moveactivity);
            }
        });
        //value form signup page
        Intent intent1=this.getIntent();
        if (intent1!=null)
        {
            adminmail.setText(intent1.getStringExtra("mailid"));
            adminpassword.setText(intent1.getStringExtra("password"));
        }



        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            if (firebaseAuth.getCurrentUser() != null) {
                Intent intent = new Intent(adminlogin.this, home.class);
                startActivity(intent);
                finish();
            } else {
                button.setOnClickListener(view -> {

                    id = adminmail.getText().toString();
                    pass = adminpassword.getText().toString();

                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(pass)) {
                            progressDialog.setMessage("Login..");
                            progressDialog.show();


                            Task<AuthResult> authResultTask = firebaseAuth.signInWithEmailAndPassword(id, pass).addOnCompleteListener(task -> {

                                if (task.isSuccessful()) {

                                    if (firebaseAuth.getCurrentUser() != null) {

                                        Intent intent = new Intent(adminlogin.this, home.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        Toast.makeText(adminlogin.this, "Did not login properly", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    Toast.makeText(adminlogin.this, id + " Not a admin", Toast.LENGTH_SHORT).show();

                                }
                                progressDialog.dismiss();
                            });

                        } else {
                            Toast.makeText(adminlogin.this, "Empty Value", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(adminlogin.this, "Please connet to the Internet", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        } else {
            Toast.makeText(adminlogin.this, "Pleace check your connection status", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(adminlogin.this, home.class);
            startActivity(intent);
            finish();
        }
    }
}