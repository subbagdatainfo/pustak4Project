package com.rifki.pustaka11;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Rifqi on 11/01/2017.
 */

public class ItemObjects {
    @SerializedName("buku")
    public List<Children> buku;

    public class Children {
        @SerializedName("nama_buku")
        public String nama_buku;
        @SerializedName("nama_penulis")
        public String nama_penulis;
        @SerializedName("nama_file")
        public String nama_file;
        @SerializedName("buku_icon")
        public String buku_icon;
        @SerializedName("date_created")
        public String date_created;
        @SerializedName("nama_penerbit")
        public String nama_penerbit;
        @SerializedName("ketersediaan")
        public String ketersediaan;
        @SerializedName("foto_sampul")
        public String foto_sampul;
        //        @SerializedName("mass")
//        public String mass;
//        @SerializedName("volume")
//        public String volume;
        @SerializedName("description")
        public String description;
        @SerializedName("url_preview")
        public String url_preview;
    }
}
