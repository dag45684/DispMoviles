package com.example.contador.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contador.R;

import java.util.List;

public class Upgrade_Adapter extends ArrayAdapter<Upgrade> {

    List<Upgrade> nodes;

    public Upgrade_Adapter(@NonNull Context context, int resource, @NonNull List<Upgrade> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Upgrade upgrade = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_upgrade, parent, false);
        }
        ((ImageView) convertView.findViewById(R.id.icon)).setImageResource(upgrade.getImage());
        ((TextView) convertView.findViewById(R.id.main)).setText(upgrade.getUpgrademain());
        ((TextView) convertView.findViewById(R.id.details)).setText(upgrade.getUpgradedetails());
        ((TextView) convertView.findViewById(R.id.title)).setText(upgrade.getUpgradetitle());
        ((TextView) convertView.findViewById(R.id.main)).setTextColor(Color.BLACK);
        ((TextView) convertView.findViewById(R.id.details)).setTextColor(Color.BLACK);
        ((TextView) convertView.findViewById(R.id.title)).setTextColor(Color.BLACK);
        convertView.findViewById(R.id.base).setBackgroundColor(upgrade.getColor());
        return convertView;
    }

}
