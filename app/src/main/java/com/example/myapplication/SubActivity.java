package com.example.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    public static SharedPreferences sp_factor;
    public static SharedPreferences sp_factor_2;
    public static int factor_count = 0;
    public static SharedPreferences sp_factor_count;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        ImageButton button_next1 = findViewById(R.id.button_next1);
        EditText et_text_factor1 = findViewById(R.id.et_text_factor1);
        EditText et_text_factor2 = findViewById(R.id.et_text_factor2);
        EditText factor_1 = findViewById(R.id.factor_1);
        EditText factor_2 = findViewById(R.id.factor_2);

        button_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                factor_count = loadfactor_count();
                String name_1 = "factor_1_" + String.valueOf(factor_count);
                String name_2 = "factor_2_" + String.valueOf(factor_count);
                sp_factor = getApplicationContext().getSharedPreferences("factor_1", MODE_PRIVATE);
                sp_factor_2 = getApplicationContext().getSharedPreferences("factor_2", MODE_PRIVATE);

                SharedPreferences.Editor editor_1 = sp_factor.edit();
                SharedPreferences.Editor editor_2 = sp_factor_2.edit();

                String str_1 = et_text_factor1.getText().toString();
                String str_2 = et_text_factor2.getText().toString();
                editor_1.putString(name_1, str_1);
                editor_2.putString(name_2, str_2);
                editor_1.apply();
                editor_2.apply();
                factor_count++;
                savefactor_count();
                Intent intent = new Intent(getApplicationContext(), RandomActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fade_innn, R.anim.fade_out);

            }
        });
    }

    public int loadfactor_count() {
        sp_factor_count = getApplicationContext().getSharedPreferences("factor_count", MODE_PRIVATE);
        Integer count_factor = sp_factor_count.getInt("factor_count", 0);

        return count_factor;
    }

    public void savefactor_count() {
        sp_factor_count = getApplicationContext().getSharedPreferences("factor_count", MODE_PRIVATE);
        SharedPreferences.Editor sp_factor_count_editor = sp_factor_count.edit();
        sp_factor_count_editor.putInt("factor_count", factor_count);
        sp_factor_count_editor.apply();

    }
}