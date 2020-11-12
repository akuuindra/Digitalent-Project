package com.indrawirawan.kalkulatorku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText bil1;
    EditText bil2;
    TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bil1 = findViewById(R.id.input1);
        bil2 = findViewById(R.id.input2);
        txtResult = findViewById(R.id.txt_result);

        bil1.setText("0");
        bil2.setText("0");

        Button btnAdd = findViewById(R.id.btn_tambah);
        btnAdd.setOnClickListener(this);
        Button btnSub = findViewById(R.id.btn_kurang);
        btnSub.setOnClickListener(this);
        Button btnMul = findViewById(R.id.btn_kali);
        btnMul.setOnClickListener( this);
        Button btnDiv = findViewById(R.id.btn_bagi);
        btnDiv.setOnClickListener(this);
        Button btnClear = findViewById(R.id.btn_reset);
        btnClear.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        Double val1 = Double.parseDouble(bil1.getText().toString());
        Double val2 = Double.parseDouble(bil2.getText().toString());

        if(v.getId() == R.id.btn_tambah){
            Double res = val1 + val2;
            txtResult.setText(res+"");
        }else if(v.getId() == R.id.btn_kurang){
            Double res = val1 - val2;
            txtResult.setText(res+"");
        }else if(v.getId() == R.id.btn_kali){
            Double res = val1 * val2;
            txtResult.setText(res+"");
        }else if(v.getId() == R.id.btn_bagi){
            Double res = val1 / val2;
            txtResult.setText(res+"");
        }else if(v.getId() == R.id.btn_reset){
            bil1.setText("0");
            bil2.setText("0");
            txtResult.setText("0");
        }
    }
}