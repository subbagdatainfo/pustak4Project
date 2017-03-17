package com.rifki.pustaka11.Holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rifki.pustaka11.R;

/**
 * Created by Muhammad Rifqi on 11/01/2017.
 */

public class HolderItems extends RecyclerView.ViewHolder {
    public TextView txt_judul,txt_penulis;
    public CardView carditem_planet;
    public ImageView img_icon;

    public HolderItems(View itemView) {
        super(itemView);
        txt_judul = (TextView) itemView.findViewById(R.id.txt_judul);
        txt_penulis = (TextView) itemView.findViewById(R.id.txt_penulis);
        carditem_planet = (CardView) itemView.findViewById(R.id.carditem_planet);
        img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
    }
}
