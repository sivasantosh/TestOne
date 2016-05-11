package com.example.testone;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PlantListAdapter extends RecyclerView.Adapter<PlantListAdapter.ViewHolder> {
    ArrayList<PlantItem> mPlants;

    PlantListAdapter (ArrayList<PlantItem> items) {
        mPlants = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plant_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PlantItem item = mPlants.get(i);
        viewHolder.common.setText(item.common);
        viewHolder.botanical.setText(item.botanical);
        viewHolder.price.setText(item.price);
        viewHolder.availability.setText(item.availability);
    }

    @Override
    public int getItemCount() {
        return mPlants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView common, botanical, price, availability;
        ViewHolder (View v) {
            super(v);
            common = (TextView) v.findViewById(R.id.commonName);
            botanical = (TextView) v.findViewById(R.id.botanicalName);
            price = (TextView) v.findViewById(R.id.price);
            availability = (TextView) v.findViewById(R.id.availability);
        }
    }
}
