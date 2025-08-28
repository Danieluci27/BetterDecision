package com.example.myapplication;
import static com.example.myapplication.DataActivity.sp_3;
import static com.example.myapplication.DataActivity.sp_4;
import static com.example.myapplication.DataActivity.sp_bool;
import static com.example.myapplication.RandomDataActivity.bool_visited_sp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.AlgorithmParameterGenerator;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class WrongActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);

        Button goBack = findViewById(R.id.button_goback);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
