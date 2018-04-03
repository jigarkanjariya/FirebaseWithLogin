package com.example.devanshi.firebasewithlogin.fragement;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.devanshi.firebasewithlogin.R;
import com.example.devanshi.firebasewithlogin.util.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    GPSTracker mloc;
    Button mBtnMyLocation;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requestAccountPermission();
      mBtnMyLocation = view.findViewById(R.id.btn_mylocation);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            LatLng lat = new LatLng(22.2453865, 70.7439328);
            LatLng lat2 = new LatLng(22.2818624, 70.7657219);
            LatLng lat3 = new LatLng(22.283581, 70.7647393);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(lat)
                    .title("GECR")
                    .snippet("kankot Road")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 11.0f));

            mMap.addMarker(new MarkerOptions().position(lat2).title("Atmiya College")
                    .snippet("Kalawad Road")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat2, 11.0f));
            mMap.addMarker(new MarkerOptions().position(lat3).title("Shivam Boys Hostel")
                    .snippet("Kalawad Road")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat3, 11.0f));


            mBtnMyLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mloc = new GPSTracker(getActivity());
                    if (mloc.canGetLocation()) {

                        double latitude = mloc.getLatitude();
                        double longitude = mloc.getLongitude();
                        LatLng currentlatlng = new LatLng(mloc.getLatitude(), mloc.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(currentlatlng)
                                .title("ME")
                                .snippet("CURRENT LOCATION")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlatlng, 11.0f));
                        // \n is for new line
                        Toast.makeText(getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        mloc.showSettingsAlert();
                    }

                }

            });

        }
    }

    private void requestAccountPermission()
    {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            makerequest();
        }

    }
    private void makerequest(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                104);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 104:
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
