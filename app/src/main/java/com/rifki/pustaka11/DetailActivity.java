package com.rifki.pustaka11;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private TextView txt_judul, txt_waktunya, txt_penulis, txt_penerbit, txt_ketersediaan, txt_description;
    private ImageView img_header, img_icon;
    int id = 1;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private static final int MEGABYTE = 1024 * 1024;
    private String folderName = "Pustaka";
    private String fileUrl = "kebudayaan.kemdikbud.go.id/mobile-pustaka/pustakadummy/pustaka/file_buku/";
//    private String fileUrl="http://rivkiey.com/pustaka/file_buku/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(DetailActivity.this);
        final String namaFile = getIntent().getStringExtra("nama_file");
        final String url_preview = getIntent().getStringExtra("url_preview");
        img_header = (ImageView) findViewById(R.id.img_header);
        //img_icon = (ImageView) findViewById(R.id.img_icon);
        txt_judul = (TextView) findViewById(R.id.txt_judul);
        txt_waktunya = (TextView) findViewById(R.id.txt_waktu);
        txt_penulis = (TextView) findViewById(R.id.txt_penulis);
        txt_penerbit = (TextView) findViewById(R.id.txt_penerbit);
        txt_ketersediaan = (TextView) findViewById(R.id.txt_ketersediaan);
        txt_description = (TextView) findViewById(R.id.txt_deskripsi);
        txt_judul.setText(getIntent().getStringExtra("judul"));
        txt_waktunya.setText(getIntent().getStringExtra("waktu"));
        txt_penulis.setText("Penulis : " + getIntent().getStringExtra("penulis"));
        txt_penerbit.setText("Penerbit : " + getIntent().getStringExtra("penerbit"));
        txt_ketersediaan.setText("Ketersediaan :" + getIntent().getStringExtra("ketersediaan"));
        txt_description.setText("Deskripsi : " + getIntent().getStringExtra("deskripsi"));
        Glide.with(getApplicationContext())
                .load("kebudayaan.kemdikbud.go.id/mobile-pustaka/pustakadummy/pustaka/ikon/"
                        + getIntent().getStringExtra("buku_ikon"))
                .placeholder(R.drawable.ntb)
                .into(img_header);

//        Glide.with(getApplicationContext())
//                .load("http://rivkiey.com/pustaka/header/" + getIntent().getStringExtra("foto sampul"))
//                .placeholder(R.drawable.ntb)
//                .into(img_header);
        Button downloadButton = (Button) findViewById(R.id.btn_download);
        Button previewButton = (Button) findViewById(R.id.btn_preview);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(namaFile);
            }
        });
        final Intent i = new Intent(this, PreviewActivity.class);
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, folderName);
                File pdfFile = new File(folder, namaFile);
                if (pdfFile.exists()) {
                    Uri filepath = Uri.fromFile(pdfFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(filepath, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "File Not Started", Toast.LENGTH_SHORT).show();
                        Log.e("error", "" + e);
                    }

                } else {
                    i.putExtra("url_preview", url_preview);
                    startActivity(i);
                }

            }
        });

    }

    public void download(String namaFile) {
        new DownloadFile().execute(fileUrl + namaFile, namaFile);
    }

    private class DownloadFile extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBuilder.setContentTitle("Download")
                    .setContentText("Download in progress")
                    .setSmallIcon(R.drawable.downloadicon);
            // Displays the progress bar for the first time.
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, folderName);
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();
                int fileLength = urlConnection.getContentLength();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                long total = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    total += bufferLength;
                    publishProgress((int) (total * 100 / fileLength));
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //FileDownloader.downloadFile(fileUrl, pdfFile);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update progress
            mBuilder.setProgress(100, values[0], false);
            mNotifyManager.notify(id, mBuilder.build());
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mBuilder.setContentText("Download complete");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
            Toast.makeText(getApplicationContext(), "File saved to folder " + folderName, Toast.LENGTH_SHORT).show();

        }
    }
}

