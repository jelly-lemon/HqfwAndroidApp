package com.example.hqfwandroidapp.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.DiscoveryViewHolder> {


    private ArrayList<String> mList = new ArrayList<>();

    public DiscoveryAdapter(ArrayList<String> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public DiscoveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_discovery, parent, false);
        return new DiscoveryViewHolder(v);
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoveryViewHolder holder, int position) {
        holder.tv_name.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class DiscoveryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name) TextView tv_name;


        public DiscoveryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
