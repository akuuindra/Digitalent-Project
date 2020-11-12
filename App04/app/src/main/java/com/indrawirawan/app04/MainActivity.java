package com.indrawirawan.app04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView label1 = findViewById(R.id.label1);
        final Button btn1 = findViewById(R.id.btn1);

        btn1.setBackgroundColor(Color.RED);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setBackgroundColor(Color.GREEN);
            }
        });


        final TextView counter = findViewById(R.id.counter);
        final Button btn2 = findViewById(R.id.btn2);
        final Button btn3 = findViewById(R.id.btn3);

        btn2.setBackgroundColor(Color.WHITE);
        btn3.setBackgroundColor(Color.WHITE);

        counter.setText(number + "");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setBackgroundColor(Color.TRANSPARENT);
                number = number +1;
                counter.setText(number + "");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setBackgroundColor(Color.TRANSPARENT);
                if (number>0){
                    number = number -1;
                }
                counter.setText(number +"");
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

        if (item.getItemId()==R.id.id_setting)
            startActivity(new Intent(this, SettingActivity.class));
        else if(item.getItemId()== R.id.id_about)
            startActivity(new Intent(this, AboutActivity.class));
        else if (item.getItemId()==R.id.id_logout)
            finish();
        return true;
    }
}