package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Registrasi extends AppCompatActivity {

    public static final String FILENAME = "login.txt";
    Button simpan;
    EditText username, password, email, name, school, address;
    String user, pass, mail, nama, sekolah, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        String title = "Registrasi";
        getSupportActionBar().setTitle(title);

        username = findViewById(R.id.usernamereg);
        password = findViewById(R.id.passwordreg);
        email = findViewById(R.id.emailreg);
        name = findViewById(R.id.namereg);
        school = findViewById(R.id.schoolreg);
        address = findViewById(R.id.addressreg);
        simpan = findViewById(R.id.btn_savereg);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidation()) {
                    simpanRegistrasi();
                } else {
                    Toast.makeText(Registrasi.this,
                            "Mohon Lengkapi Seluruh Data", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    boolean isValidation() {
        user = username.getText().toString();
        pass = password.getText().toString();
        mail = email.getText().toString();
        nama = name.getText().toString();
        sekolah = school.getText().toString();
        alamat = address.getText().toString();

        if(user.equals("") ||
            pass.equals("") ||
            mail.equals("") ||
            nama.equals("") ||
            sekolah.equals("") ||
            alamat.equals("")) {
            return false;
        } else { return true; }
    }

    void simpanRegistrasi() {
        user = username.getText().toString();
        pass = password.getText().toString();
        mail = email.getText().toString();
        nama = name.getText().toString();
        sekolah = school.getText().toString();
        alamat = address.getText().toString();

        String isiFile = user + ";" + pass + ";" + mail + ";" + nama + ";" + sekolah + ";" + alamat + '\n' ;
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }


}