package com.example.contador.utils;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contador.R;

import java.util.List;

public class Leader_Adapter extends RecyclerView.Adapter<Leader_Adapter.ViewHolder> {

    List<Leader> nodes;

    public Leader_Adapter(List<Leader> nodes){
        this.nodes = nodes;
    }

    @NonNull
    @Override
    public Leader_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leader, parent, false);
        ((TextView) v.findViewById(R.id.playerlead)).setTextColor(Color.BLACK);
        ((TextView) v.findViewById(R.id.poslead)).setTextColor(Color.BLACK);
        ((TextView) v.findViewById(R.id.scorelead)).setTextColor(Color.BLACK);
        ((LinearLayout) v.findViewById(R.id.leaderglobal)).setBackgroundColor(nodes.get(0).getColor());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Leader_Adapter.ViewHolder holder, int position) {
        holder.bind(nodes.get(position));
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView player;
        private final TextView score;
        private final TextView pos;

        ViewHolder(View v) {
            super(v);
            this.player = v.findViewById(R.id.playerlead);
            this.score = v.findViewById(R.id.scorelead);
            this.pos = v.findViewById(R.id.poslead);
        }

        void bind (Leader leader){
            player.setText(leader.getPlayer());
            score.setText(leader.getScore());
            pos.setText(leader.getPos());
            if (!leader.getPos().equals("1")) {
                pos.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            }
            if (leader.getPos().equals("2")) {
                pos.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
            }
            if (leader.getPos().equals("3")) {
                pos.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            }
        }
    }
}


