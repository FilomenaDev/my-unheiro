package com.filomenadeveloper.myunheiro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Screen_Main extends AppCompatActivity {

    Button registro, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen__main);

        registro = findViewById(R.id.main_join_now_btn);
        login = findViewById(R.id.main_login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen_Main.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen_Main.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}