package com.example.contador.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contador.R;

import java.util.List;

public class Info_Adapter extends RecyclerView.Adapter<Info_Adapter.ViewHolder> {

    List<Info> nodes;

    public Info_Adapter(List<Info> nodes){
        this.nodes = nodes;
    }

    @NonNull
    @Override
    public Info_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Info_Adapter.ViewHolder holder, int position) {
        holder.bind(nodes.get(position));
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView infotitle;
        private final TextView infomain;
        private final TextView infodetails;
        private final ImageView image;

        ViewHolder(View v) {
            super(v);
            this.infotitle = v.findViewById(R.id.infotitle);
            this.infomain = v.findViewById(R.id.infomain);
            this.infodetails = v.findViewById(R.id.infodetails);
            this.image = v.findViewById(R.id.infoicon);
        }

        void bind (Info info){
            infotitle.setText(info.getInfotitle());
            infomain.setText(info.getInfomain());
            infodetails.setText(info.getInfodetails());
            image.setImageResource(info.getImage());
        }
    }
}


