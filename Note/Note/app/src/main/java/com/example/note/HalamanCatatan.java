package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HalamanCatatan extends AppCompatActivity implements View.OnClickListener {

    public static final String FILENAME = "Catatan.txt";

    public static String TAG = BuildConfig.APPLICATION_ID;
    public static final int REQUEST_CODE_STORAGE = 100;
    int eventID = 0;
    boolean isEditable = false;

    EditText judul_ctt, isi_ctt;
    Button simpan;
    String judul, isi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_catatan);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        judul_ctt = findViewById(R.id.judul_catatan);
        isi_ctt = findViewById(R.id.isi_catatan);
        simpan = findViewById(R.id.btn_simpan);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            judul = extras.getString("judul");
            judul_ctt.setText(judul);
            getSupportActionBar().setTitle("Ubah Catatan");
        } else {
            getSupportActionBar().setTitle("Tambah Catatan");
        }

        eventID = 1;
        onResume();

        simpan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String isi = isi_ctt.getText().toString();

        switch (view.getId()) {
            case R.id.btn_simpan:
                eventID = 2;
                if (!isi_ctt.equals(isi)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkStoragePermission()) {
                            dialogPenyimpanan();
                        }
                    } else {
                        dialogPenyimpanan();
                    }
                }
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkStoragePermission())
                bacaFile();
        } else
            bacaFile();
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                return true;
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (eventID == 1) {
                    bacaFile();
                } else {
                    dialogPenyimpanan();
                }

            }
        }
    }

    public void bacaFile() {
        String path = Environment.getExternalStorageDirectory().toString() + "/NOTE";
        judul = judul_ctt.getText().toString();

        File file = new File(path, judul);

        if (file.exists()) {

            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = br.readLine();

                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            isi = text.toString();
            isi_ctt.setText(text.toString());
        }
    }

    void buatDanUbahFile() {
        judul = judul_ctt.getText().toString();
        isi = isi_ctt.getText().toString();

        String path = Environment.getExternalStorageDirectory().toString() + "/NOTE";
        File parent = new File(path);

        if(parent.exists()) {
            File file = new File(path, judul);
            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file);
                OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
                streamWriter.append(isi_ctt.getText());
                streamWriter.flush();
                streamWriter.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            parent.mkdir();
            File file = new File(path, judul);
            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(isi.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        onBackPressed();

    }



    private void dialogPenyimpanan() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set pesan dari dialog
        alertDialogBuilder
                .setTitle("Simpan Catatan")
                .setMessage("Apakah anda yakin ingin menyimpan catatan ini?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
//                        Beranda.this.finish();
                       buatDanUbahFile();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }



    @Override
    public void onBackPressed() {
        if ((!isi.equals(isi_ctt.getText().toString()))) {
            dialogPenyimpanan();
        }
        super.onBackPressed();
    }

}