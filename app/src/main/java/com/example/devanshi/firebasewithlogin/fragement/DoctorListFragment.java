package com.example.devanshi.firebasewithlogin.fragement;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.devanshi.firebasewithlogin.R;
import com.example.devanshi.firebasewithlogin.adapter.CommonAdapter;
import com.example.devanshi.firebasewithlogin.model.Register;
import com.example.devanshi.firebasewithlogin.util.CustomItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DoctorListFragment extends Fragment
{
    RecyclerView doctorrecyclerview;
    DatabaseReference db;
    CommonAdapter commonAdapter;
    ProgressBar  mPbDoctorlist;
    ArrayList<Register>list=new ArrayList<>();

    public DoctorListFragment() {

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
        db = FirebaseDatabase.getInstance().getReference("Doctor");
        return inflater.inflate(R.layout.fragment_doctor_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorrecyclerview=view.findViewById(R.id.doctor_recyclerview);
        mPbDoctorlist=view.findViewById(R.id.pb_doctorlist);
        retrieve();

    }
    public void retrieve() {
        mPbDoctorlist.setVisibility(View.VISIBLE);
        db = FirebaseDatabase.getInstance().getReference("Doctor");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Register register = dataSnapshot1.getValue(Register.class);
                    list.add(register);
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                doctorrecyclerview.setLayoutManager(layoutManager);
                commonAdapter = new CommonAdapter(getContext(), list);
                commonAdapter = new CommonAdapter(getContext(), list, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        String hospitalphone = list.get(position).getPhone();
                        String hospitalwebsite = list.get(position).getWebsite();
                        String hospitaladdress = list.get(position).getAddress();
                        String hospitalname = list.get(position).getName();
                        String hospitaldescription = list.get(position).getDescription();
                        String hospitalphoto = list.get(position).getPhoto();

                        bundle.putString("photo", hospitalphoto);
                        bundle.putString("name", hospitalname);
                        bundle.putString("address", hospitaladdress);
                        bundle.putString("phone", hospitalphone);
                        bundle.putString("website", hospitalwebsite);
                        bundle.putString("description", hospitaldescription);

                        DetailFragment detailHospitalFragment = new DetailFragment();
                        detailHospitalFragment.setArguments(bundle);
                        ft.replace(R.id.container_1, detailHospitalFragment);
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });
                doctorrecyclerview.setAdapter(commonAdapter);
                mPbDoctorlist.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }
}
