package com.example.devanshi.firebasewithlogin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.devanshi.firebasewithlogin.model.Register;
import com.example.devanshi.firebasewithlogin.R;
import com.example.devanshi.firebasewithlogin.util.CustomItemClickListener;
import com.example.devanshi.firebasewithlogin.util.Mypref;

import java.util.ArrayList;

/**
 * Created by Devanshi on 20-02-2018.
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.MyViewHolder> {

         ArrayList<Register> sortlist;
         Context context;
     CustomItemClickListener listener;



    public CommonAdapter(ArrayList<Register> sortlist)
    {
    this.sortlist=sortlist;
    }

    public CommonAdapter(Context context, ArrayList<Register> hospitallist)
    {
        this.context=context;
        this.sortlist=hospitallist;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        final CommonAdapter.MyViewHolder viewHolder = new CommonAdapter.MyViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) 
    {
        Register register =sortlist.get(position);

        holder.name.setText(register.getName());
        holder.description.setText(register.getDescription());
        Mypref mypref=new Mypref();
        mypref.Glide(register.getPhoto(),context,holder.image);


    }

    @Override
    public int getItemCount() {
        return sortlist.size();
    }
    public CommonAdapter(Context mContext, ArrayList<Register> data, CustomItemClickListener listener) {
        this.sortlist = data;
        this.context = mContext;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder 
    {
        TextView name,description;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            
            name=itemView.findViewById(R.id.listname);
            description=itemView.findViewById(R.id.listdescription);
            image=itemView.findViewById(R.id.listimage);
        }
    }
}
