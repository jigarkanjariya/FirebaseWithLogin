package com.example.devanshi.firebasewithlogin.fragement;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.devanshi.firebasewithlogin.R;

public class HomeFragment extends Fragment
{
    ImageView imgHospital,imgDoctor,imgPharmacy,imgLaboratory;



    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        imgHospital=view.findViewById(R.id.hospital);
        imgDoctor=view.findViewById(R.id.doctor);
        imgLaboratory=view.findViewById(R.id.laboratory);
        imgPharmacy=view.findViewById(R.id.pharmacy);
          return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_1,new HospitalListFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        imgDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_1,new DoctorListFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        imgPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_1,new PharmacyFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        imgLaboratory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_1,new LaboratoryFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
