package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Login extends AppCompatActivity {

    public static final String FILENAME = "login.txt";
    Button login, registrasi;
    EditText username, password;
    String name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String title = "Login";
        getSupportActionBar().setTitle(title);

        login = findViewById(R.id.btn_login);
        registrasi = findViewById(R.id.btn_registrasi);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registrasi.class);
                startActivity(intent);
            }
        });

    }

    void simpanLogin() {
        name = username.getText().toString();
        pass = password.getText().toString();

        String isiFile = name + ";" + pass +"\n";
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    void login() {
        name = username.getText().toString();
        pass = password.getText().toString();

        File storage = getFilesDir();
        File file = new File(storage, FILENAME);

        if(file.exists()) {

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

            String data = text.toString();
            String[] dataUser = data.split(";");

            if (dataUser[0].equals(name)) {
                Log.d("CHECK", "bisa nih username");
                username.setText("");
                if (dataUser[1].equals(pass)){
                    Log.d("CHECK", "bisa nih password");
                    password.setText("");
//                simpanLogin();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Username salah", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Data tidak tersedia, silahkan registrasi", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
        }

    }

}