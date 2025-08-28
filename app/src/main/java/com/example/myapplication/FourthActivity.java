package com.example.myapplication;
import static com.example.myapplication.RandomActivity.bool_action_sp;
import static com.example.myapplication.SubActivity.sp_factor;
import static com.example.myapplication.SubActivity.sp_factor_2;
import static com.example.myapplication.ThirdActivity.bool;
import static com.example.myapplication.ThirdActivity.sp_1;
import static com.example.myapplication.ThirdActivity.sp_2;
import static com.example.myapplication.ThirdActivity.sp_key;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FourthActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        EditText r_text = findViewById(R.id.text_5);
        Intent intent = getIntent();
        String factor1 = intent.getStringExtra("factor11");
        String factor2 = intent.getStringExtra("factor22");
        int index = intent.getIntExtra("put", 0);
        String slope = intent.getStringExtra("slope");
        String constant = intent.getStringExtra("constant");
        double constant_double = Double.parseDouble(constant);
        String equation = "";
        String title = "Prediction";
        if(constant_double < 0) {
            equation = factor1 + "=" + slope + "(" + factor2 + ")" + "-" + String.valueOf(Math.abs(constant_double));
        }
        else {
            equation = factor1 + "=" + slope + "(" + factor2 + ")" + "+" + String.valueOf(Math.abs(constant_double));
        }
        r_text.setText(equation);
        String description = equation;

        if(equation != null) {
            sp_1.edit().clear().commit();
            sp_2.edit().clear().commit();
            sp_key.edit().clear().apply();
            bool.edit().clear().commit();
            bool_action_sp = getApplicationContext().getSharedPreferences("bool_action" , MODE_PRIVATE);
            SharedPreferences.Editor edit = bool_action_sp.edit();
            String name = "bool_action" + String.valueOf(index);
            edit.remove(name);


        }
        Button save = findViewById(R.id.saveButton);
        Button backk = findViewById(R.id.backButton);
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("1").build();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Realm.init(getApplicationContext());
                RealmConfiguration config = new RealmConfiguration.Builder().name("1").build();
                Realm.setDefaultConfiguration(config);
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Note note = realm.createObject(Note.class);
                note.setTitle(title);
                note.setDescription(description);
                realm.commitTransaction();
                Toast.makeText(FourthActivity.this, "Result was saved in result compilation", Toast.LENGTH_SHORT).show();


            }
        });
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_back);
            }
        });
        

    }

    
}
