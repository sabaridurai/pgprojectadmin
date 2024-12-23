package com.example.transportadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_addpersion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_addpersion extends Fragment {


    ImageButton img1,img2,img4,img5;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_addpersion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_addpersion.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_addpersion newInstance(String param1, String param2) {
        fragment_addpersion fragment = new fragment_addpersion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View view=inflater.inflate(R.layout.fragment_addpersion, container, false);
img1=view.findViewById(R.id.img1);
img5=view.findViewById(R.id.img9);
img5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
Intent intent34=new Intent(requireActivity(),calendaractivity.class);
startActivity(intent34);
    }
});
img1.setOnClickListener(view1 -> startActivity(new Intent(getActivity(),addrecord.class)));
img2=view.findViewById(R.id.img2);
img2.setOnClickListener(view12 -> startActivity(new Intent(getActivity(),viewcarddata.class)));
img4=view.findViewById(R.id.img4);
img4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intentroot=new Intent(requireActivity(),addroot.class);
        startActivity(intentroot);
    }
});

        // Inflate the layout for this fragment
        return view;
    }
}