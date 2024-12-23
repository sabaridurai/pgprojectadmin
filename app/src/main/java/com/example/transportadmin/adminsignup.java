package com.example.transportadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class adminsignup extends AppCompatActivity {
Button signup;
EditText mail,password,con_pass;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsignup);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            //
        }
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        signup=findViewById(R.id.adminsignupbtn);
        mail=findViewById(R.id.adminsignupmail);
        password=findViewById(R.id.adminpassword);
        con_pass=findViewById(R.id.adminsignuppassword);
        ProgressDialog progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(view -> {
            String smailid = mail.getText().toString();
            String spass = password.getText().toString();
            String sconpass = con_pass.getText().toString();
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            {
                if (!TextUtils.isEmpty(smailid) && !TextUtils.isEmpty(spass)) {

                    if(spass.equals(sconpass)) {
                        progressDialog.setMessage("Registering Please wait");
                        progressDialog.show();

                        firebaseAuth.createUserWithEmailAndPassword(smailid, spass).addOnCompleteListener(adminsignup.this, task -> {

                            if (task.isSuccessful()) {

                                Toast.makeText(adminsignup.this, "Account creation is successful", Toast.LENGTH_LONG).show();
                                Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(adminsignup.this, "please check and verify your email", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(adminsignup.this, adminlogin.class);
                                        intent.putExtra("mailid",smailid);
                                        intent.putExtra("password",spass);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(adminsignup.this, "Error in send verify link then retry", Toast.LENGTH_LONG).show();

                                    }


                                });
                            } else {
                                Toast.makeText(adminsignup.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }


                            progressDialog.dismiss();


                        });
                    }
                    else{
                        Toast.makeText(adminsignup.this,"Password and conform password not match",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(adminsignup.this, "Empty value", Toast.LENGTH_LONG).show();
                }



            }
            else {
                Toast.makeText(adminsignup.this,"Please connect to the Internet",Toast.LENGTH_SHORT).show();
            }


        });



    }
}