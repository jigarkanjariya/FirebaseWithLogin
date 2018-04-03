package com.example.devanshi.firebasewithlogin.fragement;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.devanshi.firebasewithlogin.R;
import com.example.devanshi.firebasewithlogin.util.Mypref;


public class DetailFragment extends Fragment {


    String phone="";
    public DetailFragment() {
    }


    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_hospital, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestAccountPermission();
        ImageView hospitalimage=view.findViewById(R.id.hospital_detailphoto);
        TextView hospitalname=view.findViewById(R.id.hospital_detailname);
        TextView hospitaladdress=view.findViewById(R.id.hospital_detailaddress);
        TextView hospitalphone=view.findViewById(R.id.hospital_detailphone);
        final TextView hospitalwebsite=view.findViewById(R.id.hospital_detailwebsite);
        TextView hospitaldescription=view.findViewById(R.id.hospital_detaildescription);

        Glide.with(getContext()).load(getArguments().getString("photo")).into(hospitalimage);
        hospitalname.setText(getArguments().getString("name"));
        hospitaladdress.setText(getArguments().getString("address"));
        hospitalphone.setText(getArguments().getString("phone"));
        hospitalwebsite.setText(getArguments().getString("website"));
        hospitaldescription.setText(getArguments().getString("description"));


        hospitalphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone = getArguments().getString("phone");
                Uri uri = Uri.parse("tel:" + phone);
                Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
                startActivity(callIntent);
            }

        });

        hospitalwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String website = getArguments().getString("website");
                android.support.v4.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("website",website);
                WebViewFragment detailHospitalFragment = new WebViewFragment();
                detailHospitalFragment.setArguments(bundle);
                ft.replace(R.id.container_1,detailHospitalFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    }

    private void requestAccountPermission()
    {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            makerequest();
        }

    }
    private void makerequest(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CALL_PHONE},
                102);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 102:
            {

                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                            makerequest();

                }
                else
                {


                }
                return;
            }

        }
    }
}
