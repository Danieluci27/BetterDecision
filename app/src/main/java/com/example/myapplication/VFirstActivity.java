
package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VFirstActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vfirst);

        ImageButton theme_1_button = findViewById(R.id.theme_button);
        ImageButton theme_2_button = findViewById(R.id.theme_button_2);

        theme_1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HypothesisActivity.class);
                startActivity(intent);
            }
        });


    }
}
