package com.rifki.pustaka11.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.rifki.pustaka11.DetailActivity;
import com.rifki.pustaka11.Holder.HolderItems;
import com.rifki.pustaka11.ItemObjects;
import com.rifki.pustaka11.R;

import java.util.List;

/**
 * Created by Muhammad Rifqi on 11/01/2017.
 */

public class AdapterItems extends RecyclerView.Adapter<HolderItems> {
    Context context;
    List<ItemObjects.Children> itemObjects;
    public AdapterItems(Context context, List<ItemObjects.Children> itemObjects) {
        this.context = context;
        this.itemObjects = itemObjects;
    }

    @Override
    public HolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem,null);
        HolderItems holderItem = new HolderItems(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(HolderItems holder, final int position) {
        holder.txt_judul.setText(itemObjects.get(position).nama_buku);
        holder.txt_penulis.setText(itemObjects.get(position).nama_penulis);
        Glide.with(context).load("stfp://kebudayaan.kemdikbud.go.id/mobile-pustaka/pustakadummy/pustaka/ikon/"
                +itemObjects.get(position).buku_icon).placeholder(R.drawable.ntb)
                .into(holder.img_icon);
//        Glide.with(context).load("http://rivkiey.com/pustaka/ikon/"
//                + itemObjects.get(position).buku_icon)
//                //.placeholder(R.drawable.ntb)
//        .into(holder.img_icon);
        holder.carditem_planet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DetailActivity.class);
                intent.putExtra("judul",itemObjects.get(position).nama_buku);
                intent.putExtra("nama_file",itemObjects.get(position).nama_file);
                intent.putExtra("waktu",itemObjects.get(position).date_created);
                intent.putExtra("penulis",itemObjects.get(position).nama_penulis);
                intent.putExtra("buku_ikon",itemObjects.get(position).buku_icon);
                intent.putExtra("penerbit",itemObjects.get(position).nama_penerbit);
                intent.putExtra("url_preview",itemObjects.get(position).url_preview);
                intent.putExtra("ketersediaan",itemObjects.get(position).ketersediaan);
                intent.putExtra("deskripsi",itemObjects.get(position).description);
                intent.putExtra("foto sampul",itemObjects.get(position).foto_sampul);

                v.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }
}
