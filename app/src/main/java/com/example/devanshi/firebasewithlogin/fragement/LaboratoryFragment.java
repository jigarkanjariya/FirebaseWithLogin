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


public class LaboratoryFragment extends Fragment
{
    RecyclerView laboratoryrecyclerview;
    DatabaseReference db;
    CommonAdapter commonAdapter;
    ProgressBar mPblaboratorylist;
    ArrayList<Register> list=new ArrayList<>();


    public LaboratoryFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laboratory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        laboratoryrecyclerview=view.findViewById(R.id.laboratory_recyclerview);
        mPblaboratorylist=view.findViewById(R.id.pb_laboratorylist);
        retrieve();

    }
    public void retrieve() {
        mPblaboratorylist.setVisibility(View.VISIBLE);
        db = FirebaseDatabase.getInstance().getReference("Laboratory");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Register register = dataSnapshot1.getValue(Register.class);
                    list.add(register);
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                laboratoryrecyclerview.setLayoutManager(layoutManager);
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
                laboratoryrecyclerview.setAdapter(commonAdapter);
                mPblaboratorylist.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }
}
