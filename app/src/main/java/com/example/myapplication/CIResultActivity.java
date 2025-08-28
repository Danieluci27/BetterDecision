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

public class CIResultActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciresultactivity);



        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("daniel_shin_shin_shin").build();
        Realm.setDefaultConfiguration(config);
        Realm realm_2 = Realm.getDefaultInstance();
        EditText CI = findViewById(R.id.confidence_interval);
        Button memo_button = findViewById(R.id.save);
        Button goingBack = findViewById(R.id.backToHome);
        Intent intent = getIntent();
        String CI_Left = intent.getStringExtra("CI_Left");
        String CI_Right = intent.getStringExtra("CI_Right");
        String description = intent.getStringExtra("description");
        String variable = intent.getStringExtra("variable");
        int index = intent.getIntExtra("put", 0);
        String method = "Validation";
        String result = "[" + CI_Left + "," + CI_Right + "]";

        CI.setText(result);




        if(result != null) {
            bool_visited_sp = getApplicationContext().getSharedPreferences("bool_visited",MODE_PRIVATE);
            SharedPreferences.Editor bool_visited_sp_editor = bool_visited_sp.edit();
            String name = "bool_visited" + String.valueOf(index);
            bool_visited_sp_editor.remove(name);
        }
        String myKeyValue = "keyValue" + variable;
        memo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm_2.beginTransaction();
                Note note = realm_2.createObject(Note.class);
                note.setTitle(method);
                note.setDescription(description);
                realm_2.commitTransaction();
                Toast.makeText(CIResultActivity.this, "Result was saved in result compilation", Toast.LENGTH_SHORT).show();

            }
        });
        goingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
             }
        });

    }

}
