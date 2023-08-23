package com.example.testnotice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    ArrayList<ProjectModel> list;
    Context context;

    public ProjectAdapter(ArrayList<ProjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ViewHolder holder, int position) {
        ProjectModel model = list.get(position);

        Picasso.get().load(model.getFeaturedImage()).placeholder(R.drawable.gdsc_sit_ngp).into( holder.itemImage);

        holder.itemHeadline.setText(model.getHeadline());
        holder.itemShortDescription.setText(model.getShortDescription());
        holder.itemPostedBy.setText(model.getPostedBy());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, SingleProductActivity.class);

        intent.putExtra("singleImage", model.getFeaturedImage());
        intent.putExtra("singleHeadline", model.getHeadline());
        intent.putExtra("singleDescription", model.getDescription());
        intent.putExtra("singlePostedBy",model.getPostedBy());

        // Set the flag to create a new task for the SingleProductActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the SingleProductActivity with the intent
        context.startActivity(intent);
    }
});

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemHeadline, itemShortDescription,itemPostedBy;
        ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemHeadline = itemView.findViewById(R.id.itemHeadline);
            itemShortDescription = itemView.findViewById(R.id.itemDescription);
            itemPostedBy = itemView.findViewById(R.id.itemPostedBy);

            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }

}
