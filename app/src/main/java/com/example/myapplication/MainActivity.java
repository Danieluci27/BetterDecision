package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("daniel_shin_shin").build();
        Realm.setDefaultConfiguration(config);

        ImageButton button_prediction = findViewById(R.id.button_prediction);
        ImageButton button_results = findViewById(R.id.results);
        ImageButton button_validation = findViewById(R.id.button_validation);
        ImageButton imageButton = findViewById(R.id.imageView);

        button_prediction.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.prediction_translate, R.anim.main_translate);

            }
        });
        button_results.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.result_translate, R.anim.main_translate_for_result);

            }
        });

        button_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HypothesisActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.validation_translate, R.anim.main_translate_for_validation);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), // 현재 화면의 제어권자
                        R.anim.rotate_anim);    // 설정한 에니메이션 파일
                imageButton.startAnimation(anim);



            }
        });
    }
}