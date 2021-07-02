package com.prakriti.parseimagesapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.BitSet;

import de.hdodenhof.circleimageview.CircleImageView;

public class RCAdapter extends RecyclerView.Adapter<RCAdapter.MyViewHolder> {
// implement further
    
    private ArrayList<String> imageTextsList = new ArrayList<>();
    private ArrayList<Bitmap> imagesList = new ArrayList<>();
    private Context context;


    public RCAdapter(ArrayList<String> imageTextsList, ArrayList<Bitmap> imagesList, Context context) {
        this.imageTextsList = imageTextsList;
        this.imagesList = imagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.circleImageView.setImageBitmap(imagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }


    // inner class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // my_list_item

        CircleImageView circleImageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.myImageView);
            textView = itemView.findViewById(R.id.myTextView);
        }
    }

}
