package com.phoenix.botly.homepage;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phoenix.botly.R;

public class FeatureMenuViewHolder extends RecyclerView.ViewHolder {


    public TextView textFeatureMenuItemName,textFeatureMenuCode;
    public ImageView imageViewFeatureMenuListItemIcon;
    public CardView cardFeatureMenuItemMaster;

    public FeatureMenuViewHolder(View itemView) {
        super(itemView);
        textFeatureMenuItemName = itemView.findViewById(R.id.textFeatureMenuItemName);
        cardFeatureMenuItemMaster =  itemView.findViewById(R.id.layoutFeatureMenuItemMaster);
        imageViewFeatureMenuListItemIcon =  itemView.findViewById(R.id.imageViewFeatureMenuListItemIcon);
        textFeatureMenuCode =  itemView.findViewById(R.id.textFeatureMenuCode);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
