package com.rifki.pustaka11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rifki.pustaka11.Adapter.AdapterItems;

public class MainActivity extends AppCompatActivity {
    private RecyclerView lst_item;
    private LinearLayoutManager linearLayoutManager;
    private ItemObjects itemObjects;
    private AdapterItems adapterItems;
    private SearchView search;
 private static String urlData = "kebudayaan.kemdikbud.go.id/mobile-pustaka/pustakadummy/pustaka/data.php";
   // private static String urlData ="http://rivkiey.com/pustaka/data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst_item = (RecyclerView) findViewById(R.id.lstItem);
        linearLayoutManager = new LinearLayoutManager(this);
        lst_item.setLayoutManager(linearLayoutManager);
        AmbilData(urlData);
    }

    private void AmbilData(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                itemObjects = gson.fromJson(response, ItemObjects.class);
                adapterItems = new AdapterItems(getApplicationContext(), itemObjects.buku);
                lst_item.setAdapter(adapterItems);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), " ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}
