package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationViewHolder> {

    private Context mCtx;
    private List<Information> bookingList;

    public InformationAdapter(Context mCtx, List<Information> bookingList) {
        this.mCtx = mCtx;
        this.bookingList = bookingList;
    }
    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.my_bookings_list_view, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationAdapter.InformationViewHolder holder, int position) {

        Information info = bookingList.get(position);
        holder.textViewName.setText("Name: "+info.name);
        holder.textViewTable.setText("Table Number: "+info.tableNumber);
        holder.textViewTime.setText("Time: "+info.time);
        holder.textViewEmail.setText("Email: "+info.email);
        holder.textViewPh.setText("Phone: "+info.phone);
        holder.textViewDate.setText("Date: "+info.date);
        holder.textViewSeating.setText("Area: "+info.seating);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewTable, textViewTime, textViewEmail, textViewPh, textViewDate;
        TextView textViewSeating;
        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.nameTextView);
            textViewTable = itemView.findViewById(R.id.tableTextView);
            textViewTime = itemView.findViewById(R.id.timeTextView);
            textViewEmail = itemView.findViewById(R.id.emailTextView);
            textViewSeating = itemView.findViewById(R.id.seatingTextView);
            textViewPh = itemView.findViewById(R.id.phTextView);
            textViewDate = itemView.findViewById(R.id.dateTextView);

        }

    }
}
