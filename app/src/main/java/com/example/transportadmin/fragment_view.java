package com.example.transportadmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;



public class fragment_view extends Fragment {
private Button Logout;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   View view= inflater.inflate(R.layout.fragment_view, container, false);
   firebaseAuth=FirebaseAuth.getInstance();
   Logout=view.findViewById(R.id.logout);
   Logout.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           firebaseAuth.signOut();
           Intent intent=new Intent(requireActivity(),adminlogin.class);
           startActivity(intent);
       }
   });

   return view;
    }
}