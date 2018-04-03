package com.example.devanshi.firebasewithlogin.fragement;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devanshi.firebasewithlogin.HomeActivity;
import com.example.devanshi.firebasewithlogin.R;
import com.example.devanshi.firebasewithlogin.model.Register;
import com.example.devanshi.firebasewithlogin.util.Mypref;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Activity.RESULT_OK;


public class RegisterFragment extends Fragment
{
    EditText registername,registeraddress,registerphone,registerwebsite,registerdescription;
    ImageView registerImage;
    Spinner registerspinner;
    Button btnRegister;
    String  name,address,phone,website,description,picpath;
    DatabaseReference databasehospital;
    private static final String TAG="HospitalFragement";
    private static final int SELECT_PICTURE = 100;
    String[] catagory = { "Doctor", "Hospital", "Pharmacy", "Laboratory"};

    public RegisterFragment() {

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


        View view = inflater.inflate(R.layout.fragment_register, container, false);

        registername=view.findViewById(R.id.register_name);
        registeraddress=view.findViewById(R.id.register_address);
        registerphone=view.findViewById(R.id.register_phone);
        registerwebsite=view.findViewById(R.id.register_website);
        registerdescription=view.findViewById(R.id.register_describe);
        registerImage=view.findViewById(R.id.register_image);
        btnRegister=view.findViewById(R.id.register_register);
        registerspinner=view.findViewById(R.id.register_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, catagory);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        registerspinner.setAdapter(adapter);

        registerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String rootnode = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onCreateView: "+rootnode);
                databasehospital = FirebaseDatabase.getInstance().getReference(rootnode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestAccountPermission();
        registerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                imagepicker();

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = registername.getText().toString();
                address = registeraddress.getText().toString();
                phone = registerphone.getText().toString();
                website = registerwebsite.getText().toString();
                description = registerdescription.getText().toString();

                addhospital();

                Intent i=new Intent(getContext(), HomeActivity.class);
                startActivity(i);
                }


        });
    }
    private void imagepicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    private void addhospital()
    {
       if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(address) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(website) && !TextUtils.isEmpty(description) )
        {
            String id = databasehospital.push().getKey();
            Register register = new Register(id,name,address,phone,website,description,picpath);
            databasehospital.child(id).setValue(register);
        }
        else
        {
            Toast.makeText(getContext(), "Wrong.", Toast.LENGTH_SHORT).show();
        }

    }
    private void requestAccountPermission()
    {

        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                    makerequest();
        }

    }

    private void makerequest(){

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                103);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 103:
            {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        requestAccountPermission();

                }
                else
                {

                }
                return;
            }

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_PICTURE)
            {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c =getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d("HEY","IMAGE path Filename:- " +picturePath);
                picpath = picturePath;
                Mypref mypref=new Mypref();

                mypref.Glide(picpath,getContext(),registerImage);

                registerImage.setImageBitmap(thumbnail);
            }
        }
    }



}
