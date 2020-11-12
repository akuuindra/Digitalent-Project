package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Beranda extends AppCompatActivity {

    public static final String FILENAME = "Catatan.txt";
    public static String TAG = BuildConfig.APPLICATION_ID;
    public static final int REQUEST_CODE_STORAGE = 100;
    ListView listview;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        listview = findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d(TAG, "Click :" + adapterView.getAdapter().getItem(i));
                Intent intent = new Intent(Beranda.this, HalamanCatatan.class);
                Map<String, Object> data = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                intent.putExtra("judul", data.get("name").toString());
                Toast.makeText(Beranda.this, "You clicked " + data.get("name"), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> data = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                dialogKonfirmasi(data.get("name").toString());
                return true;
            }
        });

        fab = findViewById(R.id.btn_input);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Beranda.this, HalamanCatatan.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu1){
            finish();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkStoragePermission())
                getListFiles();
        } else
            getListFiles();
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
                getListFiles();
            }
        }
    }

    private void getListFiles() {
        String path = Environment.getExternalStorageDirectory().toString() + "/NOTE";
        File dir = new File(path);

        if (!dir.exists()) {

            dir.mkdirs();
            final String FILENAME = "Catatan.txt";
            File dataFile = new File(path, FILENAME);

            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String text = "External storage is not available";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                FileOutputStream mOutput = new FileOutputStream(dataFile, false);
                String data = "DATA";
                mOutput.write(data.getBytes());
                mOutput.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File[] files = dir.listFiles();
        String[] fname = new String[files.length];
        String[] datec = new String[files.length];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
        ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < files.length; i++) {
            fname[i] = files[i].getName();
            Date tempDate = new Date(files[i].lastModified());
            datec[i] = dateFormat.format(tempDate);

            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("name", fname[i]);
            itemMap.put("date", datec[i]);
            dataList.add(itemMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, dataList, android.R.layout.simple_list_item_2,
                new String[]{"name", "date"}, new int[]{android.R.id.text1, android.R.id.text2});
        listview.setAdapter(adapter);
    }

    void dialogKonfirmasi (final String judul) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set pesan dari dialog
        alertDialogBuilder
                .setTitle("Hapus Catatan ini?")
                .setMessage("Apakah anda yakin ingin menghapus catatan "+judul+"?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
//                        Beranda.this.finish();
                        hapusFile(judul);
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

    void hapusFile(String filename) {
        String path = Environment.getExternalStorageDirectory().toString() + "/NOTE";

        File file = new File(path, filename);
        if (file.exists()) {
            file.delete();
        }
        getListFiles();
    }




}