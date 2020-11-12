package com.indrawirawan.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity {
    EditText editUsername, editPassword, editNamaLengkap, editEmail, editAsalSekolah, editAlamat;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");

        editUsername = findViewById(R.id.regUsername);
        editPassword = findViewById(R.id.regPassword);
        editEmail = findViewById(R.id.regEmail);
        editNamaLengkap = findViewById(R.id.regNamaLengkap);
        editAsalSekolah = findViewById(R.id.regAsalSekolah);
        editAlamat = findViewById(R.id.regAlamat);
        btnSimpan = findViewById(R.id.regSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidation()){
                    simpanFileData();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else  {
                    Toast.makeText(RegisterActivity.this, "Mohon Lengkapi data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isValidation(){
        if (editUsername.getText().toString().equals("") ||
                editPassword.getText().toString().equals("") ||
                editEmail.getText().toString().equals("") ||
                editNamaLengkap.getText().toString().equals("") ||
                editAsalSekolah.getText().toString().equals("") ||
                editAlamat.getText().toString().equals("")){
            return false;
        } else {
            return true;
        }
    }

    void simpanFileData(){
        String isiFile = editUsername.getText().toString()+ ";"+
                editPassword.getText().toString()+";"+
                editEmail.getText().toString()+";"+
                editNamaLengkap.getText().toString()+";"+
                editAsalSekolah.getText().toString()+";"+
                editAlamat.getText().toString();
        File file = new File(getFilesDir(), editUsername.getText().toString());

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
